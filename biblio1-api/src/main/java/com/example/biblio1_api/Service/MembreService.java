package com.example.biblio1_api.Service;

import com.example.biblio1_api.Dto.MembreCreationRequest;
import com.example.biblio1_api.Dto.MembreResponse;
import com.example.biblio1_api.Dto.MembreUpdateRequest;
import com.example.biblio1_api.Entity.Livre;
import com.example.biblio1_api.Entity.Membre;
import com.example.biblio1_api.Exception.ConflitMetierException;
import com.example.biblio1_api.Exception.RessourceIntrouvableException;
import com.example.biblio1_api.Mapper.MembreMapperGenere;
import com.example.biblio1_api.Repository.EmpruntRepository;
import com.example.biblio1_api.Repository.MembreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MembreService {
    @Autowired
    private MembreRepository membreRepository;
    private MembreMapperGenere mapper;
    EmpruntRepository empruntRepository;

    public MembreService (MembreRepository membreRepository, MembreMapperGenere mapper, EmpruntRepository empruntRepository) {
        this.membreRepository = membreRepository;
        this.mapper = mapper;
        this.empruntRepository = empruntRepository;
    }
    @Transactional
    public Membre creerMembre(MembreCreationRequest request) {
        if(membreRepository.existsByEmail(request.email())) {
            throw new ConflitMetierException("ce membre existe deja");
        }
        Membre membre= mapper.versEntite(request);
        return membreRepository.save(membre);


    }
    @Transactional
    public MembreResponse updateMembre(Long id,MembreUpdateRequest request) {
        Membre membre = membreRepository.findById(id)
                .orElseThrow(() -> new RessourceIntrouvableException("membre", id));
        if(request.email() != null) { membre.setEmail(request.email()); }
        if(request.nom() != null) { membre.setNom(request.nom()); }
        if(request.prenom() != null) { membre.setPrenom(request.prenom()); }
        if(request.actif()!= null) { membre.setActif(request.actif()); }
    return   mapper.versReponse(membreRepository.save(membre));
    }
    @Transactional
    public void deleteMembre(Long id) {
        if(!membreRepository.existsById(id)) {
            throw new RessourceIntrouvableException("membre", id);
        }
        membreRepository.deleteById(id);
    }
    public MembreResponse findMembreById(Long id) {
        Membre membre = membreRepository.findById(id).orElseThrow(()->new RessourceIntrouvableException("membre",id));
        return mapper.versReponse(membre);
    }
    public MembreResponse findMembreByEmail(String email) {
        Membre membre = membreRepository.findByEmail(email).orElseThrow(()->new RessourceIntrouvableException("membre",2l));
        return mapper.versReponse(membre);
    }
    public Page<MembreResponse> findAllMembre(Pageable pageable) {
        return membreRepository.findAll(pageable).map(mapper::versReponse);
    }
    public int compterEmprunts(Long membreId) {
        return empruntRepository.countByMembreId(membreId);
    }
}
