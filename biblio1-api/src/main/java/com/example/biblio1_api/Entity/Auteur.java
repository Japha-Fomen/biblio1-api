package com.example.biblio1_api.Entity;

import jakarta.persistence.*;
import org.hibernate.action.internal.OrphanRemovalAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auteur",indexes= @Index(name="idx_auteur_nom", columnList="nom"))
public class Auteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code_auteur", nullable = false, unique = true, length = 8)
    private String codeAuteur;
    @Column(nullable=false,length =100)
    private String nom;
    @Column(nullable=false,length =100)
    private String prenom;
    @Column(nullable=false,length =60)
    private String nationalite;
    @Column(nullable=false,name = "date_naissance")
    private LocalDate dateNaissance;
    @OneToMany(mappedBy ="auteur",cascade=CascadeType.ALL, orphanRemoval = true)
    private List<Livre> livres= new ArrayList<>();

    //constructeur
    protected Auteur() {}//jpa usage
    public Auteur(String codeAuteur,String nom, String prenom, String nationalite) {
        this.codeAuteur = codeAuteur;
        this.nom = nom;
        this.prenom = prenom;
        this.nationalite = nationalite;
    }
      //methodes utilitaires
    public void AjouterLivre(Livre livre){
        livres.add(livre);
        livre.setAuteur(this);
    }
    public void retirerLivre(Livre livre){
        livres.remove(livre);
        livre.setAuteur(null);
    }


    // getters and setters
    public String getCodeAuteur() {
        return codeAuteur;}
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public long getId() {
        return id;
    }
    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public String getNationalite() {
        return nationalite;
    }
    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    public List<Livre> getLivres() {
        return livres;
    }

}
