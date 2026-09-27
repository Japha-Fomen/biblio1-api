package com.example.biblio1_api.Dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuteurUpdateRequest(@Size(max = 100)String prenom,
                                  @Size(max = 100) String nom,
                                  @Past LocalDate dateNaissance,
                                  @Size(max = 60)String nationalite) {
}
