package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Entity.Emprunt;
import com.example.biblio1_api.Entity.Livre;
import com.example.biblio1_api.Entity.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EmpruntRepository extends JpaRepository<Emprunt,Long> {
    long countByMembreIdAndStatut(Long memberId, StatutEmprunt statut);
    long countByLivreIdAndStatut(Long livreId, StatutEmprunt statut);
    List<Emprunt>
    findByMembreIdOrderByDateEmpruntDesc(Long memberId);

    @Query("""
            select e from Emprunt e join fetch e.livre l
            join fetch e.membre m
            where e.statut = 'EN_COURS' and e.dateRetourPrevue < :date
            """)
    List<Emprunt> trouverEnRetard(@Param("date") LocalDate date);
    @Modifying(flushAutomatically = true,clearAutomatically = true)
    @Query("""
           update Emprunt e set e.statut = 'perdu' where 
           e.statut= 'EN_COURS' and e.dateRetourPrevue < :limite
            """)
    int marqueCommePerdus(@Param("limite")LocalDate limite);
    List<Emprunt> findByMembreId(Long membreId);

    int countByMembreId(Long membreId);
}
