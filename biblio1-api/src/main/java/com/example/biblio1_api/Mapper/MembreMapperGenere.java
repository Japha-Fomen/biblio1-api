package com.example.biblio1_api.Mapper;

import com.example.biblio1_api.Dto.MembreCreationRequest;
import com.example.biblio1_api.Dto.MembreResponse;
import com.example.biblio1_api.Entity.Membre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MembreMapperGenere {
    Membre versEntite(MembreCreationRequest membreCreationRequest);
    MembreResponse versReponse(Membre membre);
}
