package com.example.biblio1_api.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "emprunt",indexes = {
        @Index(name = "idx_emprunt_membre",columnList = "membre_id"),
        @Index(name = "idx_emprunt_statut",columnList = "statut")
})
public class Emprunt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "livre_id")
    private Livre livre;
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "membre_id")
    private Membre membre;
    @Column(name = "date_emprunt",nullable = false)
    private LocalDate dateEmprunt;
    @Column(name = "date_retour_prevue", nullable = false)
    private LocalDate dateRetourPrevue;
    @Column(name = "date_retour_effective")
    private LocalDate dateRetourEffective;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private StatutEmprunt statut=StatutEmprunt.EN_COURS;

    //constructeur
    protected Emprunt(){

    }
    public Emprunt(Livre livre,Membre membre,int dureeJours){
        this.livre = livre;
        this.membre = membre;
        this.dateEmprunt = LocalDate.now();
        this.dateRetourPrevue = dateEmprunt.plusDays(dureeJours);
    }

    //methodes utilitaires

    public void enregistrerRetour(LocalDate date){
       this.dateRetourEffective = date;
       this.statut=date.isAfter(dateRetourPrevue)?
               StatutEmprunt.RETOURNE_EN_RETARD:
               StatutEmprunt.RETOURNE;
    }
    public long joursDeRetard(LocalDate reference){
        LocalDate fin=dateRetourEffective!=null?dateRetourEffective
                :reference;
        return fin.isAfter(dateRetourPrevue)?
                ChronoUnit.DAYS.between(dateRetourPrevue,fin)
                : 0;
    }

    // getters and setters

    public Long getId() { return id; }
    public Livre getLivre() { return livre; }
    public Membre getMembre() { return membre; }
    public LocalDate getDateEmprunt() { return dateEmprunt; }
    public LocalDate getDateRetourPrevue() { return dateRetourPrevue; }
    public LocalDate getDateRetourEffective() { return dateRetourEffective; }
    public StatutEmprunt getStatut() { return statut; }
}
