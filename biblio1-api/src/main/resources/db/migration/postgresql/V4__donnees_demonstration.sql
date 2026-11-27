-- =====================================================================
-- biblio1-api — V4 : jeu de donnees de demonstration
-- Auteurs du domaine public. Les ISBN sont illustratifs.
-- =====================================================================

INSERT INTO auteur (code_auteur, nom, prenom, nationalite, date_naissance) VALUES
  ('HUGO001',  'Hugo',   'Victor',    'francaise',  '1802-02-26'),
  ('ROY001',   'Roy',    'Gabrielle', 'canadienne', '1909-03-22'),
  ('CAMUS001', 'Camus',  'Albert',    'francaise',  '1913-11-07'),
  ('HEBERT01', 'Hebert', 'Anne',      'canadienne', '1916-08-01'),
  ('VERNE001', 'Verne',  'Jules',     'francaise',  '1828-02-08');

INSERT INTO livre (isbn, titre, genre, annee_publication, nombre_exemplaires,
                   auteur_id, cree_le, modifie_le, version)
SELECT '9782070000001', 'Les Misérables', 'ROMAN', 1862, 3, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'HUGO001'
UNION ALL
SELECT '9782070000002', 'Notre-Dame de Paris', 'ROMAN', 1831, 2, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'HUGO001'
UNION ALL
SELECT '9782070000003', 'Bonheur d''occasion', 'ROMAN', 1945, 4, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'ROY001'
UNION ALL
SELECT '9782070000004', 'La Détresse et l''Enchantement', 'BIOGRAPHIE', 1984, 2, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'ROY001'
UNION ALL
SELECT '9782070000005', 'L''Étranger', 'ROMAN', 1942, 5, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'CAMUS001'
UNION ALL
SELECT '9782070000006', 'La Peste', 'ROMAN', 1947, 3, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'CAMUS001'
UNION ALL
SELECT '9782070000007', 'Le Mythe de Sisyphe', 'ESSAI', 1942, 2, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'CAMUS001'
UNION ALL
SELECT '9782070000008', 'Kamouraska', 'ROMAN', 1970, 3, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'HEBERT01'
UNION ALL
SELECT '9782070000009', 'Les Fous de Bassan', 'ROMAN', 1982, 2, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'HEBERT01'
UNION ALL
SELECT '9782070000010', 'Vingt mille lieues sous les mers', 'SCIENCE_FICTION', 1870, 4, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'VERNE001'
UNION ALL
SELECT '9782070000011', 'Le Tour du monde en quatre-vingts jours', 'ROMAN', 1873, 3, a.id, now(), now(), 0
  FROM auteur a WHERE a.code_auteur = 'VERNE001';