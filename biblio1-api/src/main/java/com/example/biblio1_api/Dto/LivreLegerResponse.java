package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LivreLegerResponse(@NotNull(message = "id notnull")long id,
                         @NotBlank(message = "doit contenir un titre")String titre,
                         @NotBlank(message = "L'ISBN est obligatoire")
                         @Pattern(regexp ="\\d{13}",message ="L'ISBN doit contenir exactement 13 chiffres" )
                         String isbn) {
}
