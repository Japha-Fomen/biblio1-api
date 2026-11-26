package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UtilisateurResponse(@NotBlank(message = "l'email ne peut etre vide")
                                  @Size(max =180,message = "la taille maximale de l'email est de 180")
                                  String email,
                                  @NotBlank(message = "le nom ne peut etre vide")
                                  @Size(max = 100,message = "le nom ne peut contenir plus de 100 caracteres")
                                  String nom,
                                  @NotNull
                                  @Size(max = 100,message = "le prenom ne peut contenir plus de 100 caracteres")
                                  String prenom) {
}
