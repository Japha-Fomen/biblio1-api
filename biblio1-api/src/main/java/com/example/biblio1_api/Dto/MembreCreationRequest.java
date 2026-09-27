package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record MembreCreationRequest(@NotBlank @Size(max = 180,message ="ne peut pas depasser 180")
                                    @Pattern(regexp = "^[a-z]+\\.[a-z]+@[a-z0-9.-]+\\.[a-z]{2,}$",
                                            message = "L'adresse courriel doit respecter le format prenom.nom@domaine")
                                    String email,
                                    @NotBlank(message = "ne peut pas etre vide.")
                                    @Size(max = 100,message ="ne peut pas depasser 100")
                                    String nom,
                                    @NotBlank
                                    @Size(max = 100,message ="ne peux depasser 100")
                                    String prenom) {
}
