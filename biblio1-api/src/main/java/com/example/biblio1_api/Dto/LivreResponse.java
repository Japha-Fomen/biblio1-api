package com.example.biblio1_api.Dto;

import com.example.biblio1_api.Entity.Genre;

import java.time.Instant;

public record LivreResponse(Long id,
                            String isbn,
                            String titre,
                            Integer anneePublication,
                            Genre genre,
                            int nombreExemplaires,
                            AuteurResume auteur,
                            Instant creeLe) {

    public record AuteurResume(Long id, String nomComplet) { }

}
