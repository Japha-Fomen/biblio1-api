package com.example.biblio1_api.Service;

import com.example.biblio1_api.Dto.CritereRecherche;
import com.example.biblio1_api.Dto.LivreResponse;
import com.example.biblio1_api.Entity.Livre;
import com.example.biblio1_api.Mapper.LivreMapperGenere;
import com.example.biblio1_api.Repository.LivreRepository;
import com.example.biblio1_api.Repository.LivreSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Transactional(readOnly=true)
@Service

public class RechercheLivreService {
    private final LivreRepository repository;
    private final LivreMapperGenere mapper;

    public RechercheLivreService(LivreRepository repository, LivreMapperGenere mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    public Page<LivreResponse> rechercheLivre(CritereRecherche critere,Pageable pageable) {
        Specification<Livre> specification=Specification.where(LivreSpecification.titreContient(critere.titre()))
                .and(LivreSpecification.genreEst(critere.genre()))
                .and(LivreSpecification.publieApres(critere.anneeMin()))
                .and(LivreSpecification.nationaliteAuteur(critere.nationalite()));
        return repository.findAll(specification,pageable).map(mapper::versReponse);
    }
}
