package com.example.biblio1_api.Service;

import com.example.biblio1_api.Dto.LivreCreationRequest;
import com.example.biblio1_api.Dto.LivreResponse;
import com.example.biblio1_api.Dto.LivreUpdateRequest;
import com.example.biblio1_api.Entity.Auteur;
import com.example.biblio1_api.Entity.Livre;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Mapper.LivreMapperGenere;
import com.example.biblio1_api.Repository.AuteurRepository;
import com.example.biblio1_api.Repository.LivreRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly=true)
public class LivreService {
    private final LivreRepository livreRepository;
    private final AuteurRepository auteurRepository;
    private final LivreMapperGenere mapper;

    public LivreService(LivreRepository livreRepository,
                        AuteurRepository auteurRepository,
                        LivreMapperGenere mapper) {
        this.livreRepository = livreRepository;
        this.auteurRepository = auteurRepository;
        this.mapper = mapper;
    }

    public Page<LivreResponse> lister(Pageable pageable) {
        return livreRepository.findAll(pageable).map(mapper::versReponse);
    }
    public LivreResponse parId(Long id) {
        Livre livre = livreRepository.findWithAuteurById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Livre", id));
        return mapper.versReponse(livre);
    }

    public LivreResponse parIsbn(String isbn) {
        return livreRepository.findByIsbn(isbn)
                .map(mapper::versReponse)
                .orElseThrow(() -> new RessourceIntrouvableException("Livre avec ISBN " + isbn));
    }

    public Page<LivreResponse> rechercheParTitre(String fragment, Pageable pageable) {
        return livreRepository.findByTitreContainingIgnoreCase(fragment, pageable)
                .map(mapper::versReponse);
    }

    @Transactional
    public LivreResponse creer(LivreCreationRequest requete) {
        if (livreRepository.existsByIsbn(requete.isbn())) {
            throw new ConflitMetierException("Un livre avec l'ISBN " + requete.isbn() + " existe déjà");
        }

        Auteur auteur = auteurRepository.findById(requete.auteurId())
                .orElseThrow(() -> new RessourceIntrouvableException("Auteur", requete.auteurId()));

        Livre livre = mapper.versEntite(requete, auteur);
        Livre sauvegarde = livreRepository.save(livre);
        return mapper.versReponse(sauvegarde);
    }
    @Transactional
    public LivreResponse modifier(Long id, LivreUpdateRequest requete) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("Livre", id));

        if (requete.titre() != null) {
            livre.setTitre(requete.titre());
        }
        if (requete.anneePublication() != null) {
            livre.setAnneePublication(requete.anneePublication());
        }
        if (requete.nombreExemplaires() != null) {
            if (requete.nombreExemplaires() < 0) {
                throw new ConflitMetierException("Le nombre d'exemplaires ne peut être négatif");
            }
            livre.setNombreExemplaires(requete.nombreExemplaires());
        }
        // Pas d'appel à save() : l'entité est managée, Hibernate détecte la modification
        return mapper.versReponse(livre);
    }

    @Transactional
    public void supprimer(Long id) {
        if (!livreRepository.existsById(id)) {
            throw new RessourceIntrouvableException("Livre", id);
        }
        livreRepository.deleteById(id);
    }
}
