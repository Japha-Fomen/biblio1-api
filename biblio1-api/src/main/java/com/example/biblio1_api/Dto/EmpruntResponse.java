package com.example.biblio1_api.Dto;

import com.example.biblio1_api.Entity.Emprunt;
import com.example.biblio1_api.Entity.StatutEmprunt;

import java.time.LocalDate;

public record EmpruntResponse(
        Long id,
        Long livreId,
        String titreLivre,
        Long membreId,
        LocalDate dateEmprunt,
        LocalDate dateRetourPrevue,
        LocalDate dateRetourEffective,
        StatutEmprunt statut
) {
    public static EmpruntResponse depuis(Emprunt e) {
        return new EmpruntResponse(
                e.getId(),
                e.getLivre().getId(),
                e.getLivre().getTitre(),
                e.getMembre().getId(),
                e.getDateEmprunt(),
                e.getDateRetourPrevue(),
                e.getDateRetourEffective(),
                e.getStatut());
    }
}
