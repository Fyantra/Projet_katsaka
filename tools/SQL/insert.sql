INSERT INTO "public".recoltes (dateheure) VALUES ( '2023-06-30' );

-- fananina parcelle 1 iany no asina données afahana micalcul anle poids approximatif
INSERT INTO "public".recolteparcelles
	( idrecolte, idparcelle, nombreostotal, poidstotal, longueurospartige) VALUES 
    ( 1, 1, 6, 20, 15);

INSERT INTO responsables (nom) VALUES ( 'Fy' ),( 'Joseph' );

INSERT INTO "public".parcelles
	( description, idresponsable, longueur, largeur, limitecontrolelongueur) VALUES 
    ( 'P1', 1, 100, 100, 15 ),
    ( 'P2', 1, 100, 100, 15 ),
    ( 'P3', 2, 100, 100, 15 ),
    ( 'P4', 2, 100, 100, 15 );

-- 1er control
INSERT INTO "public".controles
	(dateheure) VALUES 
    ( timestamp'2023-01-01' );

INSERT INTO "public".controleparcelles
	( idcontrole, idparcelle, nombretiges, nombreospartige, longueurospartige, couleur) VALUES 
    ( 1, 1, 5, 2, 8, 20),
    ( 1, 2, 4, 3, 9, 15),
    ( 1, 3, 6, 1, 7, 18),
    ( 1, 4, 5, 3, 5, 22);

-- 2e control (apres 2 semaines)
INSERT INTO "public".controles
	(dateheure) VALUES 
    ( timestamp'2023-01-15' );

INSERT INTO "public".controleparcelles
	( idcontrole, idparcelle, nombretiges, nombreospartige, longueurospartige, couleur) VALUES 
    ( 2, 1, 4, 3, 12, 22),
    ( 2, 2, 6, 4, 13, 13),
    ( 2, 3, 7, 5, 7, 19),
    ( 2, 4, 7, 2, 7, 18);
    