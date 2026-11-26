package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Dto.UtilisateurResponse;
import com.example.biblio1_api.Entity.Role;
import com.example.biblio1_api.Entity.Utilisateur;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    @Override
    Optional<Utilisateur> findById(Long id);

    Page<Utilisateur> findAll(Pageable pageable);

    Page<Utilisateur> findByRolesContains(Role role, Pageable pageable);

    Optional<Utilisateur> findByEmail(String email);

    @Override
    boolean existsById(Long id);

    boolean existsByEmail(String email);

    @Override
    void deleteById(Long id);
}
