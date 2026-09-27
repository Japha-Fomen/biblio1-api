package com.example.biblio1_api.Config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "biblio1")
public record ProprietesBiblio(
       @Valid Emprunt emprunt,
       @Valid EnregistrementLivre enregistrementLivre,
       @Valid Penalite penalite

) {
    public record Emprunt(
            @Positive int maxiSimultanes,
            @Positive int DureeJours//jours total de location
    ){
        public long maxSimultanes() {
            return maxiSimultanes;
        }

        public int dureeJours() {
            return DureeJours;
        }
    }
    public record EnregistrementLivre(@Min(1) int  nombreExemplaire ){}
    public record Penalite(@Positive double etudiant,@Positive double standard){}
}
