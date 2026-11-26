package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UtilisateurUpdateRequest(
                                       @Size(max =180,message = "la taille maximale de l'email est de 180")
                                       String email,
                                       @Size(max = 100,message = "le nom ne peut contenir plus de 100 caracteres")
                                       String nom,
                                       @Size(max = 100,message = "le prenom ne peut contenir plus de 100 caracteres")
                                       String prenom,
                                       @Size(min = 8, max = 100)
                                       String OldPassword,
                                       @Size(min =8, max = 100,message = "le mot de passe ne peut contenir plus de 100 caracteres ou moins de 8")
                                       String password) {
}
