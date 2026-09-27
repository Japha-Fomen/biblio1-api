package com.example.biblio1_api.Service;

import com.example.biblio1_api.Dto.AuteurCreationRequest;
import com.example.biblio1_api.Dto.AuteurResponse;
import com.example.biblio1_api.Dto.AuteurUpdateRequest;
import com.example.biblio1_api.Entity.Auteur;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Mapper.AuteurMapperGenere;
import com.example.biblio1_api.Repository.AuteurRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class AuteurService {
    private AuteurRepository auteurRepository;
    private AuteurMapperGenere mapper;

    public AuteurService(AuteurRepository auteurRepository, AuteurMapperGenere mapper) {
        this.auteurRepository = auteurRepository;
        this.mapper = mapper;
    }

    // retrouver un auteur par son id
   public AuteurResponse byId(Long id) {
        Auteur auteur = this.auteurRepository.findById(id).orElse(null);
       return mapper.versReponse(auteur);
    }
    // retrouver un auteur par l'id de son livre
    public AuteurResponse findByLivresId(Long livreId) {
        Auteur auteur = this.auteurRepository.findByLivresId(livreId).orElse(null);
        return mapper.versReponse(auteur);
    }
    public AuteurResponse findByCodeAuteur(String codeAuteur) {
        Auteur auteur = this.auteurRepository.findByCodeAuteur(codeAuteur).orElse(null);
        return mapper.versReponse(auteur);
    }
    //afficher la liste des auteurs du catalogue
    public Page<AuteurResponse> lister(Pageable pageable) {
        return auteurRepository.findAll(pageable)
                .map(mapper::versReponse);
    }
    @Transactional
    public Auteur cree(AuteurCreationRequest request) {
        if(auteurRepository.existsByCodeAuteur(request.codeAuteur())){
                throw new ConflitMetierException("Un livre avec l'ISBN " + request.codeAuteur() + " existe déjà");
        }
        Auteur auteur= mapper.versEntite(request);
        return auteurRepository.save(auteur);

    }

    // modifier les informations sur un
@Transactional
public AuteurResponse modification(Long id, AuteurUpdateRequest request) {
    Auteur auteur = auteurRepository.findById(id)
            .orElseThrow(() -> new RessourceIntrouvableException("Auteur", id));
    if (request.prenom() != null) auteur.setPrenom(request.prenom());
    if (request.nom() != null) auteur.setNom(request.nom());
    if (request.dateNaissance() != null) auteur.setDateNaissance(request.dateNaissance());
    if (request.nationalite() != null) auteur.setNationalite(request.nationalite());

    return mapper.versReponse(auteurRepository.save(auteur));
}
    // supprimer un auteur
    @Transactional
    public void supprimerAuteur(Long id) {
        if(!auteurRepository.existsById(id)){
            throw new RessourceIntrouvableException("Livre", id);
        }
        auteurRepository.deleteById(id);
    }


}
