package com.example.biblio1_api.Dto;

import com.example.biblio1_api.Entity.Genre;
import jakarta.validation.constraints.*;

public record LivreCreationRequest(@NotBlank(message = "L'ISBN est obligatoire")
                                   @Pattern(regexp ="\\d{13}",message ="L'ISBN doit contenir exactement 13 chiffres" )
                                   String isbn,
                                   @NotBlank(message ="Le titre est obligatoire" )
                                   @Size(max = 200,message ="Le titre ne peut pas dépasser 200 caractères")
                                   String titre, @NotNull(message ="L'année de publication est obligatoire" )
                                   @Min(value=1450,message = "Année invalide")
                                   @Max(value = 2100,message ="Année invalide")
                                   Integer anneePublication,
                                   @NotNull(message = "Le genre est obligatoire")
                                   Genre genre,
                                   @NotNull
                                   @Positive(message = "Le nombre d'exemplaires doit être supérieur à zéro")
                                   Integer nombreExemplaires,
                                   @NotNull(message = "L'identifiant de l'auteur est obligatoire")
                                   Long auteurId
                                   ) {
}
