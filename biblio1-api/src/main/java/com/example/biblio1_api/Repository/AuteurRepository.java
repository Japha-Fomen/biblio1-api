package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Entity.Auteur;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AuteurRepository extends JpaRepository<Auteur ,Long> {
    Optional<Auteur> findById(@NotNull(message = "L'identifiant de l'auteur est obligatoire") Long aLong);
    Optional<Auteur> findByCodeAuteur(String codeAuteur);
    boolean existsByCodeAuteur(String codeAuteur);
    boolean existsById(Long aLong);
    Optional<Auteur> findByLivresId(Long livreId);
    void deleteById(Long id);
    void deleteByCodeAuteur(String codeAuteur);
    @Override
    List<Auteur> findAll();

}
