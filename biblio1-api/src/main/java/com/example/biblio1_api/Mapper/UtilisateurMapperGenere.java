package com.example.biblio1_api.Mapper;

import com.example.biblio1_api.Dto.UtilisateurCreationRequest;
import com.example.biblio1_api.Dto.UtilisateurResponse;
import com.example.biblio1_api.Entity.Utilisateur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapperGenere {
    UtilisateurResponse versResponse(Utilisateur utilisateur);
    Utilisateur versEntity(UtilisateurCreationRequest request);
}
