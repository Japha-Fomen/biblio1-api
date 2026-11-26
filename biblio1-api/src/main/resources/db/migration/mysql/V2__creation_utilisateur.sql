-- =====================================================================
-- biblio1-api — V2 : comptes utilisateurs et roles (MySQL)
-- Equivalent de db/migration/postgresql/V2__creation_utilisateur.sql
-- =====================================================================

CREATE TABLE utilisateur (
    id        BIGINT       NOT NULL AUTO_INCREMENT,
    nom       VARCHAR(100) NOT NULL,
    prenom    VARCHAR(100) NOT NULL,
    email     VARCHAR(180) NOT NULL,
    password  VARCHAR(100) NOT NULL,
    actif     BIT          NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE utilisateur_role (
    utilisateur_id BIGINT      NOT NULL,
    role           VARCHAR(30) NOT NULL,
    PRIMARY KEY (utilisateur_id, role)
) ENGINE=InnoDB;

ALTER TABLE utilisateur ADD CONSTRAINT uk_utilisateur_email UNIQUE (email);

ALTER TABLE utilisateur_role ADD CONSTRAINT fk_utilisateur_role_utilisateur
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id);