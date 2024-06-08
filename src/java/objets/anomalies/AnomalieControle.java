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
import java.sql.Timestamp;
import objets.events.ControleParcelle;

/**
 *
 * @author Fy
 */
public class AnomalieControle {

    int idAnomalieControle = -1;
    ControleParcelle cpAvant;
    ControleParcelle cpActuel;
    int anomalieNombreTiges = -1;
    int anomalieNombreOsParTige = -1;
    int anomalieLongueurOsParTige = -1;
    int anomalieCroissanceMoyenneOsParTige = -1;
    int anomalieCouleur = -1;
    String phraseAucuneAnomalie = "aucune";
    String phraseVol = "vol";
    String phraseEngrais = "manque engrais";
    String phraseEau = "manque arrosage";
    
    public String getPhraseNombreTiges() {
        if(getAnomalieNombreTiges() == 0) return getPhraseAucuneAnomalie();
        return phraseVol;
    }
    
    public String getPhraseNombreOsParTige() {
        if(getAnomalieNombreOsParTige()== 0) return getPhraseAucuneAnomalie();
        return phraseVol;
    }
    
    public String getPhraseLongueurOsParTige() {
        if(getAnomalieLongueurOsParTige()== 0) return getPhraseAucuneAnomalie();
        return phraseEngrais;
    }
    
    public String getPhraseCroissanceMoyenneOsParTige() {
        if(getAnomalieCroissanceMoyenneOsParTige()== 0) return getPhraseAucuneAnomalie();
        return phraseEngrais;
    }
    
    public String getPhraseCouleur() {
        if(getAnomalieCouleur()== 0) return getPhraseAucuneAnomalie();
        return phraseEau;
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
                String dateHeureCpActuel = this.getCpActuel().getControle().getDateHeure().toString();
                int idParcelle = this.getCpActuel().getParcelle().getIdParcelle();
                String requete = "SELECT *\n"
                        + "FROM v_controleParcellesDateHeure\n"
                        + "WHERE dateheure < '"+dateHeureCpActuel+"' AND idparcelle = "+idParcelle+"\n"
                        + "ORDER BY dateheure DESC\n"
                        + "LIMIT 1";
                System.out.println("AnomalieControle.fetchCpAvant " + requete);
                //System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    System.out.println("AnomalieControle.fetchCpAvant null -> 1er controle");
                    this.setCpAvant(null);
                    return null;
                }
                
                else {
                    resultSet.next();

                    //recuperer id controleparcelle -> selectById 
                    int idCpAvant = resultSet.getInt("idcontroleparcelle");
                    ControleParcelle cpAv = new ControleParcelle();
                    cpAv.setIdControleParcelle(idCpAvant);
                    cpAv = (ControleParcelle) cpAv.select(connection, false).get(0);
                    this.setCpAvant(cpAv);
                    return this.getCpAvant();
                }


            } catch (SQLException e) {

                connection.rollback();

                System.out.println("AnomalieControle.fetchCpAvant a echoue");

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

    public int getAnomalieCroissanceMoyenneOsParTige() {
        return anomalieCroissanceMoyenneOsParTige;
    }

    public void setAnomalieCroissanceMoyenneOsParTige(int anomalieCroissanceMoyenneOsParTige) {
        this.anomalieCroissanceMoyenneOsParTige = anomalieCroissanceMoyenneOsParTige;
    }

    public int getIdAnomalieControle() {
        return idAnomalieControle;
    }

    public void setIdAnomalieControle(int idAnomalieControle) {
        this.idAnomalieControle = idAnomalieControle;
    }

    public ControleParcelle getCpAvant() throws Exception {
        if(cpAvant == null) {
            cpAvant = new ControleParcelle();
            cpAvant.setNombreTiges(0);
            cpAvant.setNombreOsParTige(0);
            cpAvant.setLongueurOsParTige(0);
            cpAvant.setCouleur(0);
        }
        return cpAvant;
    }

    public void setCpAvant(ControleParcelle cpAvant) {
        this.cpAvant = cpAvant;
    }

    public ControleParcelle getCpActuel() {
        return cpActuel;
    }

    public void setCpActuel(ControleParcelle cpActuel) {
        this.cpActuel = cpActuel;
    }

    public int getAnomalieNombreTiges() {
        return anomalieNombreTiges;
    }

    public void setAnomalieNombreTiges(int anomalieNombreTiges) {
        this.anomalieNombreTiges = anomalieNombreTiges;
    }

    public int getAnomalieNombreOsParTige() {
        return anomalieNombreOsParTige;
    }

    public void setAnomalieNombreOsParTige(int anomalieNombreOsParTige) {
        this.anomalieNombreOsParTige = anomalieNombreOsParTige;
    }

    public int getAnomalieLongueurOsParTige() {
        return anomalieLongueurOsParTige;
    }

    public void setAnomalieLongueurOsParTige(int anomalieLongueurOsParTige) {
        this.anomalieLongueurOsParTige = anomalieLongueurOsParTige;
    }

    public int getAnomalieCouleur() {
        return anomalieCouleur;
    }

    public void setAnomalieCouleur(int anomalieCouleur) {
        this.anomalieCouleur = anomalieCouleur;
    }

}
