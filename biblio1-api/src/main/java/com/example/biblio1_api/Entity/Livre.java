package com.example.biblio1_api.Entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Table(
        name = "livre",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_livre_isbn", columnNames = "isbn")
        }
)

public class Livre {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 13)
    private String isbn;
    @Column(nullable = false,length = 200)
    private String titre;
    @Column(name = "annee_publication")
    private Integer anneePublication;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 20)
    private Genre genre;
    @Column(name = "nombre_exemplaires", nullable = false)
    private int nombreExemplaires = 1;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auteur_id",foreignKey =@ForeignKey(name = "fk_livre_auteur"))
    private Auteur auteur;
    @CreationTimestamp
    @Column(name = "cree_Le",updatable = false)
    private Instant creeLe;
    @UpdateTimestamp
    @Column(name = "modifie_Le")
    private Instant modifieLe;
    @Version
    private long version;

    // constructeurs
    protected Livre() {
    }
    public Livre(String isbn, String titre, Integer anneePublication){
        this.isbn = isbn;
        this.titre = titre;
        this.anneePublication = anneePublication;
    }

    //methode utilitaire
    public boolean estDisponible(long empruntEnCours){
        return empruntEnCours < nombreExemplaires;
    }

    // getters and setters
    public Long getId() {
        return id;
    }
    public Genre getGenre(){
        return this.genre;
    }
    public void setGenre(Genre genre){
        this.genre = genre;
    }
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getTitre() {
        return titre;
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public Integer getAnneePublication() {
        return anneePublication;
    }
    public void setAnneePublication(Integer anneePublication) {
        this.anneePublication = anneePublication;
    }
    public Instant getCreeLe() {
        return creeLe;
    }
    public Instant getModifieLe() {
        return modifieLe;
    }

    public long getVersion() {
        return version;
    }
    public Auteur getAuteur() {
        return auteur;
    }
    public void setAuteur(Auteur auteur) {
        this.auteur = auteur;
    }

    public void setNombreExemplaires(int nombreExemplaires) {
        this.nombreExemplaires = nombreExemplaires;
    }
}
