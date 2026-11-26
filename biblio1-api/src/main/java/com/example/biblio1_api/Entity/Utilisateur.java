package com.example.biblio1_api.Entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,length = 100)
    private String nom;
    @Column(nullable = false,length = 100)
    private String prenom;
    @Column(nullable = false,unique = true,length = 180)
    private String email;
    @Column(nullable = false,length = 100)
    private String password;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name="utilisateur_role",joinColumns = @JoinColumn(name = "utilisateur_id"))
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Set<Role> roles= new HashSet<>();
    @Column(nullable = false)
    Boolean actif=true;

    protected Utilisateur() {}
    public Utilisateur(String nom, String prenom, String email, String password, Set<Role> roles) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.roles=roles;
    }

    public Long getId() {
        return id;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public boolean isActif() { return actif; }

    public void setNom(String nom) { this.nom = nom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }
}
