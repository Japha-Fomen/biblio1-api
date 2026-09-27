package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Entity.Membre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembreRepository extends JpaRepository<Membre, Long> {
    Page<Membre> findAll(Pageable pageable);
    Optional<Membre> findById(Long id);
    Optional<Membre> findByEmail(String email);
    boolean existsByEmail(String email);
    @Override
    boolean existsById(Long aLong);
    void deleteById(Long id);
    void deleteByEmail(String email);
}
