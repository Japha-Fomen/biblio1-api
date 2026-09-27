package com.example.biblio1_api.Mapper;

import com.example.biblio1_api.Dto.AuteurCreationRequest;
import com.example.biblio1_api.Dto.AuteurResponse;
import com.example.biblio1_api.Entity.Auteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuteurMapperGenere {
    Auteur versEntite(AuteurCreationRequest requete);
    AuteurResponse versReponse(Auteur auteur);
}
