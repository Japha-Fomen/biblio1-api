package com.example.biblio1_api.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

/**
 * Corps de réponse renvoyé pour toute erreur de l'API.
 *
 * timestamp : instant absolu, en UTC, indépendant du fuseau du serveur.
 * status    : code HTTP, répété dans le corps pour faciliter la journalisation côté client.
 * message   : phrase lisible par un humain.
 * path      : URI appelée, pour corréler avec les journaux.
 * erreurs   : détail par champ, présent uniquement sur un échec de validation.
 *
 * Les champs nuls sont omis du JSON : une erreur 404 ne traîne pas un "erreurs": null.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
        Instant timestamp,
        int status,
        String message,
        String path,
        Map<String, String> erreurs
) {
}