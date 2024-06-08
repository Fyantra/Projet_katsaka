/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.anomalies;

import connect.Connect;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import objets.events.ControleParcelle;
import objets.events.Recolte;
import objets.events.RecolteParcelle;

/**
 *
 * @author Fy
 */
public class AnomalieRecolte {
    int idAnomalieRecolte = -1;
    Recolte recolteActuelle;
    RecolteParcelle recolteParcelle;
    ControleParcelle dernierControleParcelle;
    int anomalieNombreOsParTige = -1;
    String phraseAucuneAnomalie = "aucune";
    String phraseVol = "vol";
    
    public String getPhraseNombreOsParTige() {
        if(getAnomalieNombreOsParTige()== 0) return getPhraseAucuneAnomalie();
        return phraseVol;
    }
    
     public ControleParcelle fetchCpAvant(Connection connection) throws Exception {
        boolean isOpened = false;

        try {
            if (connection == null) {

                isOpened = true;

                Connect connexion = new Connect();

                connection = connexion.getConnectionPgsql();

            }

            Statement stat = connection.createStatement();

            try {
                 System.out.println("============AnomalieControle.fetchCpAvant==================");
                String dateHeureActuel = this.getRecolteActuelle().getDateHeure().toString();
                int idParcelle = this.getRecolteParcelle().getParcelle().getIdParcelle();
                String requete = "SELECT *\n"
                        + "FROM v_controleParcellesDateHeure\n"
                        + "WHERE dateheure < '"+dateHeureActuel+"' AND idparcelle = "+idParcelle+"\n"
                        + "ORDER BY dateheure DESC\n"
                        + "LIMIT 1";
                System.out.println("AnomalieControle.fetchCpAvant " + requete);
                //System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    System.out.println("AnomalieRecolte.fetchCpAvant null -> 1er controle");
                    this.setDernierControleParcelle(null);
                    return null;
                }
                
                else {
                    resultSet.next();

                    //recuperer id controleparcelle -> selectById 
                    int idCpAvant = resultSet.getInt("idcontroleparcelle");
                    ControleParcelle cpAv = new ControleParcelle();
                    cpAv.setIdControleParcelle(idCpAvant);
                    cpAv = (ControleParcelle) cpAv.select(connection, false).get(0);
                    this.setDernierControleParcelle(cpAv);
                    return this.getDernierControleParcelle();
                }


            } catch (SQLException e) {

                connection.rollback();

                System.out.println("AnomalieRecolte.fetchCpAvant a echoue");

                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (isOpened == true) {
                connection.close();
            }
        }
        return null;
    }

    public String getPhraseAucuneAnomalie() {
        return phraseAucuneAnomalie;
    }

    public void setPhraseAucuneAnomalie(String phraseAucuneAnomalie) {
        this.phraseAucuneAnomalie = phraseAucuneAnomalie;
    }
     
     

    public RecolteParcelle getRecolteParcelle() {
        return recolteParcelle;
    }

    public void setRecolteParcelle(RecolteParcelle recolteParcelle) {
        this.recolteParcelle = recolteParcelle;
    }

    public int getIdAnomalieRecolte() {
        return idAnomalieRecolte;
    }

    public void setIdAnomalieRecolte(int idAnomalieRecolte) {
        this.idAnomalieRecolte = idAnomalieRecolte;
    }

    public Recolte getRecolteActuelle() {
        return recolteActuelle;
    }

    public void setRecolteActuelle(Recolte recolteActuelle) {
        this.recolteActuelle = recolteActuelle;
    }

    public ControleParcelle getDernierControleParcelle() {
        return dernierControleParcelle;
    }

    public void setDernierControleParcelle(ControleParcelle dernierControleParcelle) {
        this.dernierControleParcelle = dernierControleParcelle;
    }

    public int getAnomalieNombreOsParTige() {
        return anomalieNombreOsParTige;
    }

    public void setAnomalieNombreOsParTige(int anomalieNombreOsParTige) {
        this.anomalieNombreOsParTige = anomalieNombreOsParTige;
    }
    
    
}
