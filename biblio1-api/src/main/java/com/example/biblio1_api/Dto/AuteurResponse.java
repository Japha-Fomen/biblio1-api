package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AuteurResponse(
        @NotNull String codeAuteur,
        @NotNull @Size(max = 100)
        @NotBlank(message = "nom de l'auteur ne doit etre vide")String nom,
        @NotNull @Size(max = 100) String prenom,
        @NotNull @Size(max = 60)  String nationalite
) {
}
