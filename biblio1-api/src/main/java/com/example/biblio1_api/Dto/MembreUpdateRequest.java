package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MembreUpdateRequest(
                                  @Pattern(regexp = "^[a-z]+\\.[a-z]+@[a-z0-9.-]+\\.[a-z]{2,}$",
                                         message = "L'adresse courriel doit respecter le format prenom.nom@domaine")
                                  @Size(max = 180,message ="ne peux depasser 180")
                                  String email,
                                  @Size(max = 100,message ="ne peux depasser 100")
                                 String nom,
                                  @Size(max = 100,message ="ne peux depasser 100")
                                  String prenom,
                                  Boolean actif) {
}
