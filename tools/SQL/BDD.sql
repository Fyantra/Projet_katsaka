CREATE  TABLE "public".controles ( 
	idcontrole           bigserial  NOT NULL  ,
	dateheure            timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	CONSTRAINT pk_controles PRIMARY KEY ( idcontrole )
 );

CREATE  TABLE "public".recoltes ( 
	idrecolte            bigserial  NOT NULL  ,
	dateheure            timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL ,
	CONSTRAINT pk_recoltes PRIMARY KEY ( idrecolte ) 
 );

CREATE  TABLE "public".responsables ( 
	idresponsable        bigserial  NOT NULL  ,
	nom                  text  NOT NULL  ,
	CONSTRAINT pk_responsables PRIMARY KEY ( idresponsable )
 );

CREATE  TABLE "public".parcelles ( 
	idparcelle           bigserial NOT NULL  ,
	description          text  NOT NULL  ,
	idresponsable        bigint  NOT NULL  ,
	longueur             real DEFAULT 1 NOT NULL  ,
	largeur              real DEFAULT 1 NOT NULL  ,
	limitecontrolelongueur real DEFAULT 15, 
	CONSTRAINT pk_parcelles PRIMARY KEY ( idparcelle )
 );

CREATE  TABLE "public".recolteparcelles ( 
	idrecolteparcelle    bigserial  NOT NULL  ,
	idrecolte            bigint  NOT NULL  ,
	idparcelle           bigint  NOT NULL  ,
	nombreostotal        real  NOT NULL  ,
	poidstotal           real  NOT NULL  ,
	longueurospartige    real  NOT NULL  ,
	CONSTRAINT pk_recolteparcelles PRIMARY KEY ( idrecolteparcelle )
 );

CREATE  TABLE "public".controleparcelles ( 
	idcontroleparcelle   bigserial  NOT NULL  ,
	idcontrole           bigint  NOT NULL  ,
	idparcelle           bigint  NOT NULL  ,
	nombretiges          real DEFAULT 0 NOT NULL  ,
	nombreospartige      real DEFAULT 0 NOT NULL  ,
	longueurospartige    real  NOT NULL  ,
	couleur              real  NOT NULL  ,
	CONSTRAINT pk_controleparcelles PRIMARY KEY ( idcontroleparcelle )
 );

ALTER TABLE "public".controleparcelles ADD CONSTRAINT fk_controleparcelles_controles FOREIGN KEY ( idcontrole ) REFERENCES "public".controles( idcontrole );

ALTER TABLE "public".controleparcelles ADD CONSTRAINT fk_controleparcelles_parcelles FOREIGN KEY ( idparcelle ) REFERENCES "public".parcelles( idparcelle );

ALTER TABLE "public".parcelles ADD CONSTRAINT fk_parcelles_responsables FOREIGN KEY ( idresponsable ) REFERENCES "public".responsables( idresponsable );

ALTER TABLE "public".recolteparcelles ADD CONSTRAINT fk_recolteparcelles_recoltes FOREIGN KEY ( idrecolte ) REFERENCES "public".recoltes( idrecolte );

ALTER TABLE "public".recolteparcelles ADD CONSTRAINT fk_recolteparcelles_parcelles FOREIGN KEY ( idparcelle ) REFERENCES "public".parcelles( idparcelle );

COMMENT ON TABLE "public".controles IS 'idControle, dateHeure';

COMMENT ON TABLE "public".recoltes IS 'idRecolte, dateHeure';

COMMENT ON TABLE "public".responsables IS 'idResponsable, nom';

COMMENT ON TABLE "public".parcelles IS 'idParcelle, idResponsable, longueur, largeur';

COMMENT ON TABLE "public".recolteparcelles IS 'idRecolteParcelle, \nidRecolte, \nidParcelle,\nnombreOsTotal,\npoidsTotal,\nlongueurOsParTige';

COMMENT ON TABLE "public".controleparcelles IS 'idControleParcelle, idControle, idParcelle, nombreTiges, nombreOsParTige, longueurOsParTige, couleur';

DROP TABLE controles;
DROP TABLE recoltes;
DROP TABLE parcelles cascade;
DROP TABLE recolteparcelles cascade;
DROP TABLE controleparcelles cascade;