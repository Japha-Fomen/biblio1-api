package com.example.biblio1_api.Dto;

import com.example.biblio1_api.Entity.Genre;

public record CritereRecherche(String titre, Genre genre, Integer anneeMin, String nationalite) { }

