-- =====================================================================
-- biblio1-api — V3 : rattachement d'une fiche membre a un compte
-- Colonne nullable a dessein : un bibliothecaire doit pouvoir inscrire
-- au comptoir une personne qui n'a pas encore de compte en ligne.
-- =====================================================================

ALTER TABLE membre ADD COLUMN utilisateur_id BIGINT;

ALTER TABLE membre ADD CONSTRAINT uk_membre_utilisateur UNIQUE (utilisateur_id);

ALTER TABLE membre ADD CONSTRAINT fk_membre_utilisateur
    FOREIGN KEY (utilisateur_id) REFERENCES utilisateur (id);