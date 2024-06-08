/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.events;

import annot.FieldAnnotation;
import annot.FkAnnotation;
import annot.TableAnnotation;
import annot.TsyMiditraAnnotation;
import connect.Connect;
import dao.ObjetBdd;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import objets.anomalies.AnomalieControle;
import objets.infrastructures.Parcelle;

/**
 *
 * @author Fy
 */
@TableAnnotation(nomTable = "controleparcelles")
public class ControleParcelle extends ObjetBdd {

    int idControleParcelle = -1;
    @FkAnnotation(fieldName = "idControle")
    @FieldAnnotation(nomColonne = "idControle", miditra = true)
    Controle controle;
    @FkAnnotation(fieldName = "idParcelle")
    @FieldAnnotation(nomColonne = "idParcelle", miditra = true)
    Parcelle parcelle;
    double nombreTiges = -1;
    double nombreOsParTige = -1;
    double longueurOsParTige = -1;
    double couleur = -1; // couleur (ne doit pas etre < 0 et > 100)
    @TsyMiditraAnnotation
    AnomalieControle anomalieControle;
    
    public ControleParcelle (Parcelle p, String nombreTiges, String nombreOsParTige, String longueurOsParTige, String couleur) throws Exception {
        if("".equals(nombreTiges)) throw new Exception("nombre de Tiges vide - parcelle "+p.getDescription());
        if("".equals(nombreOsParTige)) throw new Exception("nombre Os ParTige vid - parcelle "+p.getDescription());
        if("".equals(longueurOsParTige)) throw new Exception("longueur Os Par Tige vide - parcelle "+p.getDescription());
        if("".equals(couleur)) throw new Exception("couleur vide - parcelle "+p.getDescription());
        
        this.setNombreTiges(Double.parseDouble(nombreTiges));
        this.setNombreOsParTige(Double.parseDouble(nombreOsParTige));
        this.setLongueurOsParTige(Double.parseDouble(longueurOsParTige));
        this.setCouleur(Double.parseDouble(couleur));
        
        if(p == null) throw  new Exception("ControleParcelle constructor -> parcelle null");
        this.setParcelle(p);
    }
    
    public ControleParcelle () {
        
    }
    
     public static ArrayList<ControleParcelle> getLControleParcellesExcluant(ArrayList<ControleParcelle> a_exclure, ArrayList<ControleParcelle> lCp) {
        ArrayList<ControleParcelle> rep = new ArrayList<>();
        for (int i = 0; i < lCp.size(); i++) {
            ControleParcelle get = lCp.get(i);
            if(get.meInLcp(a_exclure) == false) {
                rep.add(get);
            }
        }
        return rep;
    } 

    public boolean meInLcp(ArrayList<ControleParcelle> lcp) {
        for (ControleParcelle cp : lcp) {
            if(this.getIdControleParcelle() == cp.getIdControleParcelle()) return true;
        }
        return false;
    } 
    
    public double getPoidsApproximatif(double poidsUnitaire, double longueurUnitaire) throws Exception {
        if(longueurUnitaire <= 0) throw new Exception("ControleParcelle.getPoidsApproximatif : longueurUnitaire <= 0");
        double pApprox = (this.getLongueurOsParTige() * poidsUnitaire) / longueurUnitaire;
        return pApprox;
    }
    
    public double getNombreOsTotalApprox() {
        return this.getNombreOsParTige() * this.getNombreTiges();
    }
    
    public double getPourcentageCroissMoyenne() throws Exception {
        double rapportCroissMoyenne = this.calculCroissMoyenne(null);
        return (rapportCroissMoyenne - 1) * 100;
    }
    
    public double getPourcentageCroiss() throws Exception {
        ControleParcelle cpAvant = this.fetchCpAvant(null);
        double rapportLgActuelAvant = getRapportLongueurOs(cpAvant);
        return (rapportLgActuelAvant - 1) * 100;
    }

    public AnomalieControle detecteAnomalieControle(Connection connection) throws Exception {
        AnomalieControle anoCtl = new AnomalieControle();
        anoCtl.setCpActuel(this);
        // set du controleParcelle avant
        ControleParcelle cpAvant = anoCtl.fetchCpAvant(connection);

        // initialisation pas d'anomalie
        int init = 0; //si tu changes , changes condition phrases dans anomalieControle
        anoCtl.setAnomalieNombreTiges(init);
        anoCtl.setAnomalieNombreOsParTige(init);
        anoCtl.setAnomalieLongueurOsParTige(init);
        anoCtl.setAnomalieCouleur(init);
        anoCtl.setAnomalieCroissanceMoyenneOsParTige(init);

        if (cpAvant != null) {
            int anomalie = 1;
            // nb tiges
            if (this.getNombreTiges() < cpAvant.getNombreTiges()) {
                anoCtl.setAnomalieNombreTiges(anomalie);
            }

            // nb os par tiges de mais
            if (this.getNombreOsParTige() < cpAvant.getNombreOsParTige()) {
                anoCtl.setAnomalieNombreOsParTige(anomalie);
            }

            // longueur os par tige (tant que <= 15 cm)
            if (this.getParcelle().getLimiteControleLongueur() >= this.getLongueurOsParTige()) {
                if (this.getLongueurOsParTige() < cpAvant.getLongueurOsParTige()) {
                    anoCtl.setAnomalieLongueurOsParTige(anomalie);
                }
            }

            // couleur (ne doit pas etre < 0 et > 100)
            if (this.getCouleur() < cpAvant.getCouleur()) {
                anoCtl.setAnomalieCouleur(anomalie);
            }

            // croissance moyenne
            double rapportCroiss = this.getRapportLongueurOs(cpAvant);
            double rapportCroissMoyenne = this.calculCroissMoyenne(connection);

            if (rapportCroiss < rapportCroissMoyenne) {
                anoCtl.setAnomalieCroissanceMoyenneOsParTige(anomalie);
            }
        }

        this.setAnomalieControle(anoCtl);

        return this.getAnomalieControle();
    }

    public double calculCroissMoyenne(Connection connection) throws Exception {
        ArrayList<ControleParcelle> lCp = ControleParcelle.getLControleParcellesMemeDateHeure(this.getControle().getDateHeure(), connection);
        ArrayList<ControleParcelle> lCpExcluantMoi = ControleParcelle.getLControleParcellesExcluant(this, lCp);

        double sommeRapportCroiss = 0;
        for (int i = 0; i < lCpExcluantMoi.size(); i++) {
            ControleParcelle get = lCpExcluantMoi.get(i);
            ControleParcelle cpAvant = get.fetchCpAvant(connection);
            sommeRapportCroiss += get.getRapportLongueurOs(cpAvant);
        }

        return sommeRapportCroiss / lCpExcluantMoi.size();
    }

    public static ArrayList<ControleParcelle> getLControleParcellesExcluant(ControleParcelle cp, ArrayList<ControleParcelle> lCp) {
        ArrayList<ControleParcelle> rep = new ArrayList<>();
        for (int i = 0; i < lCp.size(); i++) {
            ControleParcelle get = lCp.get(i);
            if (get.getIdControleParcelle() != cp.getIdControleParcelle()) {
                rep.add(get);
            }
        }
        return rep;
    }

    public static ArrayList<ControleParcelle> getLControleParcellesMemeDateHeure(Timestamp dateHeure, Connection connection)
            throws Exception {
        boolean isOpened = false;

        try {
            if (connection == null) {

                isOpened = true;

                Connect connexion = new Connect();

                connection = connexion.getConnectionPgsql();

            }

            Statement stat = connection.createStatement();

            try {

                String requete = "SELECT * from v_controleParcellesDateHeure WHERE dateheure = timestamp'" + dateHeure + "'";
                System.out.println("ControleParcelle.lControleParcellesMemeDateHeure " + requete);
                // System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    throw new Exception("pas de ControleParcelle.lControleParcellesMemeDateHeure " + dateHeure);
                }

                ArrayList<ControleParcelle> lCp = new ArrayList<>();
                ControleParcelle cp = new ControleParcelle();

                while (resultSet.next()) {
                    cp = new ControleParcelle();
                    cp.setIdControleParcelle(resultSet.getInt("idcontroleparcelle"));
                    cp = (ControleParcelle) cp.select(connection, false).get(0);
                    cp.getControle().fetchDateHeure(connection);
                    cp.fetchCpAvant(connection);
                    lCp.add(cp);
                }

                return lCp;

            } catch (SQLException e) {

                connection.rollback();

                System.out.println(
                        "La recuperation du ControleParcelle.lControleParcellesMemeDateHeure controles a echoue");

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
                System.out.println("============ControleParcelle.fetchCpAvant==================");
                String dateHeureCpActuel = this.getControle().getDateHeure().toString();
                int idParcelle = this.getParcelle().getIdParcelle();
                String requete = "SELECT *\n"
                        + "FROM v_controleParcellesDateHeure\n"
                        + "WHERE dateheure < '" + dateHeureCpActuel + "' AND idparcelle = " + idParcelle + "\n"
                        + "ORDER BY dateheure DESC\n"
                        + "LIMIT 1";
                System.out.println("AnomalieControle.fetchCpAvant " + requete);
                //System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    return null;
                } else {
                    resultSet.next();

                    //recuperer id controleparcelle -> selectById 
                    int idCpAvant = resultSet.getInt("idcontroleparcelle");
                    ControleParcelle cpAv = new ControleParcelle();
                    cpAv.setIdControleParcelle(idCpAvant);
                    cpAv = (ControleParcelle) cpAv.select(connection, false).get(0);
                    return cpAv;
                }

            } catch (SQLException e) {

                connection.rollback();

                System.out.println("ControleParcelle.fetchCpAvant a echoue");

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

    public double getRapportLongueurOs(ControleParcelle cpAvant) throws Exception {
        if (cpAvant == null) {
            System.out.println("ControleParcelle.getRapportLongueurOs -> cpAvant = null");
            return 0;
        }
        if (cpAvant.getLongueurOsParTige() <= 0) {
            throw new Exception("ControleParcelle.getRapportLongueurOs -> cpAvant.getLongueurOsParTige() <= 0 ");
        }
        return this.getLongueurOsParTige() / cpAvant.getLongueurOsParTige();
    }

    public Controle getControle() {
        return controle;
    }

    public void setControle(Controle controle) {
        this.controle = controle;
    }

    public int getIdControleParcelle() {
        return idControleParcelle;
    }

    public void setIdControleParcelle(int idControleParcelle) {
        this.idControleParcelle = idControleParcelle;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public double getNombreTiges() {
        return nombreTiges;
    }

    public void setNombreTiges(double nombreTiges) throws Exception {
        if(nombreTiges < 0) throw new Exception("nombreTiges < 0");
        this.nombreTiges = nombreTiges;
    }

    public double getNombreOsParTige() {
        return nombreOsParTige;
    }

    public void setNombreOsParTige(double nombreOsParTige) throws Exception {
        if(nombreOsParTige < 0) throw new Exception("nombreOsParTige < 0");
        this.nombreOsParTige = nombreOsParTige;
    }

    public double getLongueurOsParTige() {
        return longueurOsParTige;
    }

    public void setLongueurOsParTige(double longueurOsParTige) throws Exception {
        if(longueurOsParTige < 0) throw new Exception("longueurOsParTige < 0");
        this.longueurOsParTige = longueurOsParTige;
    }

    public double getCouleur() {
        return couleur;
    }

    public void setCouleur(double couleur) throws Exception {
        if(couleur < 0) throw new Exception("couleur < 0");
        this.couleur = couleur;
    }

    public AnomalieControle getAnomalieControle() {
        return anomalieControle;
    }

    public void setAnomalieControle(AnomalieControle anomalieControle) {
        this.anomalieControle = anomalieControle;
    }

}
