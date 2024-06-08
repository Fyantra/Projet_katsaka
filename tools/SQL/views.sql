create or replace view v_controleParcellesDateHeure as (
    SELECT cp.*, c.dateheure 
    FROM controleparcelles cp 
    JOIN controles c ON c.idcontrole = cp.idcontrole
);