package com.example.biblio1_api.Repository;

import com.example.biblio1_api.Entity.Genre;
import com.example.biblio1_api.Entity.Livre;
import jakarta.persistence.criteria.JoinType;
import jakarta.validation.constraints.Positive;
import org.springframework.data.jpa.domain.Specification;

public final class LivreSpecification {
    private LivreSpecification() {}
    public static Specification<Livre> titreContient(String fragment) {
        return (racine,requete,cb)->fragment==null||fragment.isBlank()?null
                :cb.like(cb.lower(racine.get("titre")), "%"+ fragment +"%");
    }
    public static Specification<Livre> genreEst(Genre genre) {
        return (racine,requete,cb)->genre==null?null
                :cb.equal(cb.lower(racine.get("genre")), genre);
    }
    public static Specification<Livre> publieApres(@Positive Integer year) {
        return (racine,requete,cb)-> year==null?null
                :cb.greaterThanOrEqualTo(racine.get("anneePublication"), year);
    }
    public static Specification<Livre> nationaliteAuteur(String nationalite) {
        return (racine,requete,cb)->nationalite==null?null
                :cb.equal(racine.join("auteur", JoinType.INNER).get("nationalite"), nationalite);
    }
}
