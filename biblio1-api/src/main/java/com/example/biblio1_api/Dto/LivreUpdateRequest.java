package com.example.biblio1_api.Dto;

import com.example.biblio1_api.Entity.Genre;
import jakarta.validation.constraints.*;

public record LivreUpdateRequest(
                                  @Size(max = 255,message ="Le titre ne peut dépasser 255 caractères")
                                  String titre,
                                  @Min(value=1450,message = "Année invalide")
                                  @Max(value = 2100,message ="Année invalide")
                                  Integer anneePublication,

                                  Genre genre,
                                  @Positive(message = "Le nombre d'exemplaires doit être supérieur à zéro")
                                  Integer nombreExemplaires) {
}
