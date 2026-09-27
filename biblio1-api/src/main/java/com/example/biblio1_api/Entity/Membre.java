package com.example.biblio1_api.Entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "membre",uniqueConstraints = @UniqueConstraint(name = "uk_membre_email",columnNames = "email"))
public class Membre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false,length = 180)
    private String email;
    @Column(nullable = false,length = 100)
    private String nom;
    @Column(length = 100)
    private String prenom;
    @Column(name = "date_inscription", nullable = false)
    private LocalDate dateInscription= LocalDate.now();
    @Column(nullable = false)
    private boolean actif=true;

    //constructeurs

    protected Membre() {

    }
    public Membre(String email, String nom, String prenom) {
        this.email = email;
        this.nom = nom;
        this.prenom = prenom;
    }

    //getters and setters
    public long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public LocalDate getDateInscription() {
        return dateInscription;
    }
    public boolean isActif() {
        return actif;
    }
   public void desactiver() {
       this.actif = false;
   }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void setActif(boolean actif) {
        this.actif = actif;
    }
}
