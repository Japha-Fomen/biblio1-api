package com.example.biblio1_api.Dto;

public class LivreLeger {

    private final Long id;
    private final String titre;
    private final String isbn;

    public LivreLeger(Long id, String titre, String isbn) {
        this.id = id;
        this.titre = titre;
        this.isbn = isbn;
    }

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getIsbn() {
        return isbn;
    }
}
