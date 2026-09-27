package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record AuteurCreationRequest(@NotBlank
                                    @Pattern(regexp = "AUT-\\d{4}",
                                            message = "Le code auteur doit respecter le format AUT-XXXX")
                                    String codeAuteur,
                                     @Size(max = 100,message = "le nom ne peut pas depasser 100")
                                    @NotBlank(message = "nom de l'auteur ne doit etre vide")String nom,
                                    @NotNull @Size(max = 100,message = "le prenom ne peut pas depasser 100") String prenom,
                                    @NotNull @Size(max = 60)  String nationalite,
                                    @NotNull LocalDate dateNaissance) {
}
