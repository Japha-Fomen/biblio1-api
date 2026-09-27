package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Dto.LivreLeger;
import com.example.biblio1_api.Entity.Genre;
import com.example.biblio1_api.Entity.Livre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivreRepository extends JpaRepository<Livre, Long>,
        JpaSpecificationExecutor<Livre> {
    // 1. Requêtes dérivées du nom de la méthode
    Optional<Livre> findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Livre> findByGenre(Genre genre);

    Page<Livre> findByTitreContainingIgnoreCase(String fragment, Pageable pageable);

    List<Livre> findByAnneePublicationBetweenOrderByAnneePublicationDesc(int debut, int fin);

    long countByAuteurId(Long auteurId);
    void deleteByAuteurId(Long auteurId);
    // 2. JPQL explicite
    @Query("""
            select l from Livre l join fetch l.auteur a 
            where lower(a.nom)=lower(:nom)
            """)
    List<Livre> rechercheParNomAuteur(@Param("nom") String nom);

    // 3. Projection vers un DTO directement en JPQL
    @Query("""
           select new com.example.biblio1_api.Dto.LivreLegerResponse(l.id, l.titre, l.isbn)
           from Livre l
           where l.genre = :genre
           """)
    List<LivreLeger> resumesParGenre(@Param("genre") Genre genre);
    // 4. SQL natif quand JPQL ne suffit pas
    @Query(value = """
           select l.* from livre l
           where to_tsvector('french', l.titre) @@ plainto_tsquery('french', :recherche)
           """, nativeQuery = true)
    List<Livre> rechercheTexteIntegral(@Param("recherche") String recherche);
    // 5. Chargement contrôlé de l'association
    @EntityGraph(attributePaths = "auteur")
    Page<Livre> findAll(Pageable pageable);

    @EntityGraph(attributePaths = "auteur")
    Optional<Livre> findWithAuteurById(Long id);
}
