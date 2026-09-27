package com.example.biblio1_api.Mapper;

import com.example.biblio1_api.Dto.LivreCreationRequest;
import com.example.biblio1_api.Dto.LivreResponse;
import com.example.biblio1_api.Entity.Auteur;
import com.example.biblio1_api.Entity.Livre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface LivreMapperGenere {
    @Mapping(target = "auteur", source = "livre", qualifiedByName = "toAuteurResume")
    LivreResponse versReponse(Livre livre);

    @Named("toAuteurResume")
    default LivreResponse.AuteurResume toAuteurResume(Livre livre) {
        if (livre == null || livre.getAuteur() == null) return null;

        Long id = livre.getAuteur().getId();
        String nomComplet = livre.getAuteur().getPrenom() + " " + livre.getAuteur().getNom();

        return new LivreResponse.AuteurResume(id, nomComplet);
    }

    Livre versEntite(LivreCreationRequest requete, Auteur auteur);

}
