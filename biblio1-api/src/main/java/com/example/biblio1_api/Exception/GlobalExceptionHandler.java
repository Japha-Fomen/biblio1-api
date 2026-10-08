package com.example.biblio1_api.Exception;

import com.example.biblio1_api.Dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Traduit les exceptions de l'application en réponses HTTP cohérentes.
 *
 * L'héritage de ResponseEntityExceptionHandler conserve le traitement correct
 * des exceptions internes de Spring MVC (400 sur un corps illisible, 405 sur un
 * verbe non supporté, 404 sur une route inconnue) au lieu de les écraser en 500.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /** Ressource demandée inexistante. */
    @ExceptionHandler(RessourceIntrouvableException.class)
    public ResponseEntity<ErrorResponse> handleIntrouvable(RessourceIntrouvableException ex,
                                                           HttpServletRequest requete) {
        return reponse(HttpStatus.NOT_FOUND, ex.getMessage(), requete.getRequestURI());
    }

    /** Règle métier violée : plafond d'emprunts, exemplaire indisponible, retour déjà effectué. */
    @ExceptionHandler(ConflitMetierException.class)
    public ResponseEntity<ErrorResponse> handleConflitMetier(ConflitMetierException ex,
                                                             HttpServletRequest requete) {
        return reponse(HttpStatus.CONFLICT, ex.getMessage(), requete.getRequestURI());
    }

    /**
     * Contrainte d'unicité violée au niveau de la base (ISBN ou courriel en double)
     * lorsqu'elle n'a pas été interceptée plus tôt par le service.
     * Le détail technique part dans les journaux, pas dans la réponse.
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrite(DataIntegrityViolationException ex,
                                                         HttpServletRequest requete) {
        log.warn("Violation de contrainte en base sur {}", requete.getRequestURI(), ex);
        return reponse(HttpStatus.CONFLICT,
                "La ressource existe déjà ou viole une contrainte d'unicité.",
                requete.getRequestURI());
    }

    /**
     * Échec de validation Bean Validation sur un corps de requête.
     * Tous les champs fautifs sont renvoyés dans "erreurs", pas seulement le premier.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        Map<String, String> erreurs = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(err ->
                erreurs.putIfAbsent(err.getField(), libelle(err.getDefaultMessage())));

        ex.getBindingResult().getGlobalErrors().forEach(err ->
                erreurs.putIfAbsent(err.getObjectName(), libelle(err.getDefaultMessage())));

        String message = erreurs.size() > 1
                ? "La requête contient " + erreurs.size() + " champs invalides."
                : "La requête contient un champ invalide.";

        ErrorResponse corps = new ErrorResponse(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                message,
                chemin(request),
                erreurs);

        return new ResponseEntity<>(corps, HttpStatus.BAD_REQUEST);
    }

    /**
     * Dernier recours. Trace complète dans les journaux, message neutre au client :
     * le détail d'une exception interne ne doit jamais sortir de l'application.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInattendue(Exception ex,
                                                          HttpServletRequest requete) {
        log.error("Erreur inattendue sur {}", requete.getRequestURI(), ex);
        return reponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Une erreur interne est survenue. Veuillez réessayer plus tard.",
                requete.getRequestURI());
    }

    // ----- utilitaires -----

    private ResponseEntity<ErrorResponse> reponse(HttpStatus status, String message, String chemin) {
        String texte = (message == null || message.isBlank())
                ? status.getReasonPhrase()
                : message;
        ErrorResponse corps = new ErrorResponse(Instant.now(), status.value(), texte, chemin, null);
        return new ResponseEntity<>(corps, status);
    }

    private String libelle(String message) {
        return (message == null || message.isBlank()) ? "valeur invalide" : message;
    }

    private String chemin(WebRequest request) {
        if (request instanceof ServletWebRequest servletRequest) {
            return servletRequest.getRequest().getRequestURI();
        }
        return request.getDescription(false).replaceFirst("^uri=", "");
    }
}