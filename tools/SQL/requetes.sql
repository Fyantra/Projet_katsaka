-- maj Recolte Parcelle
UPDATE "public".recolteparcelles 
SET idrecolte = 1,
 idparcelle = 1,
 nombreostotal = 1,
 poidstotal = 1,
 longueurospartige = 1 
WHERE idRecolteParcelle = 1 ;

-- avoir id dernierement ajouté apres controle.insert
SELECT * from controles WHERE idControle = (SELECT max(idControle) FROM controles)

--controleParcelle.lControleParcellesMemeDateHeure
SELECT * from v_controleParcellesDateHeure WHERE dateheure = timestamp'2023-01-15 00:00:00';

--anomalieControle.fetchCpAvant 
SELECT *
FROM v_controleParcellesDateHeure
WHERE dateheure < '2023-01-15' AND idparcelle = 1
ORDER BY dateheure DESC
LIMIT 1;