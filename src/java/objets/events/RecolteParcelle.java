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
import objets.anomalies.AnomalieRecolte;
import objets.infrastructures.Parcelle;

/**
 *
 * @author Fy
 */
@TableAnnotation(nomTable = "recolteparcelles")
public class RecolteParcelle extends ObjetBdd {

    int idRecolteParcelle = -1;
    @FkAnnotation(fieldName = "idRecolte")
    @FieldAnnotation(nomColonne = "idRecolte", miditra = true)
    Recolte recolte;
    @FkAnnotation(fieldName = "idParcelle")
    @FieldAnnotation(nomColonne = "idParcelle", miditra = true)
    Parcelle parcelle;
    double nombreOsTotal = -1;
    double poidsTotal = -1;
    double longueurOsParTige = -1;
    @TsyMiditraAnnotation
    AnomalieRecolte AnomalieRecolte;

    public RecolteParcelle(Parcelle p, String nbOsTotal, String poidstotal, String lgOs) throws Exception {
        if ("".equals(nbOsTotal)) {
            throw new Exception("nbOsTotal vide - parcelle " + p.getDescription());
        }
        if ("".equals(poidstotal)) {
            throw new Exception("poidstotal vide - parcelle " + p.getDescription());
        }
        if ("".equals(lgOs)) {
            throw new Exception("lgOs vide - parcelle " + p.getDescription());
        }

        this.setNombreOsTotal(Double.parseDouble(nbOsTotal));
        this.setPoidsTotal(Double.parseDouble(poidstotal));
        this.setLongueurOsParTige(Double.parseDouble(lgOs));

        if (p == null) {
            throw new Exception("RecolteParcelle constructor -> parcelle null");
        }
        this.setParcelle(p);
    }

    public RecolteParcelle() {

    }

    public void maj(Connection connection) throws Exception {
        boolean isOpened = false;

        try {
            if (connection == null) {

                isOpened = true;

                Connect connexion = new Connect();

                connection = connexion.getConnectionPgsql();

            }

            Statement stat = connection.createStatement();

            try {

                String requete = "UPDATE recolteparcelles \n"
                        + "SET idrecolte = "+getRecolte().getIdRecolte()+",\n"
                        + " idparcelle = "+getParcelle().getIdParcelle()+",\n"
                        + " nombreostotal = "+getNombreOsTotal()+",\n"
                        + " poidstotal = "+getPoidsTotal()+",\n"
                        + " longueurospartige = "+getLongueurOsParTige()+" \n"
                        + "WHERE idRecolteParcelle = "+getIdRecolteParcelle();
                System.out.println("Recolte.maj " + requete);
                //System.out.println(requete);
                int rowUpdate = stat.executeUpdate(requete);

            } catch (SQLException e) {

                connection.rollback();

                System.out.println("RecolteParcelle.maj a echoue");

                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (isOpened == true) {
                connection.commit();
                connection.close();
            }
        }
    }

    public AnomalieRecolte detecteAnomalieRecolte(Connection connection) throws Exception {
        AnomalieRecolte anoRec = new AnomalieRecolte();
        anoRec.setRecolteActuelle(this.getRecolte());
        anoRec.setRecolteParcelle(this);
        // set du controleParcelle avant
        ControleParcelle cpAvant = anoRec.fetchCpAvant(connection);
        cpAvant.getControle().fetchDateHeure(connection);
        anoRec.setDernierControleParcelle(cpAvant);

        // initialisation pas d'anomalie
        int init = 0; //si tu changes , changes condition phrases dans AnomalieRecolte
        anoRec.setAnomalieNombreOsParTige(init);

        if (cpAvant != null) {
            int anomalie = 1;

            // nb os par tiges de mais
            if (this.getNombreOsTotal() < cpAvant.getNombreOsTotalApprox()) {
                anoRec.setAnomalieNombreOsParTige(anomalie);
            }
        } else {
            throw new Exception("vous essayez de faire une recolte sans controle");
        }

        this.setAnomalieRecolte(anoRec);

        return this.getAnomalieRecolte();
    }

    public double getPoidsUnitaire() {
        return this.getPoidsTotal() / this.getNombreOsTotal();
    }

    public Recolte getRecolte() {
        return recolte;
    }

    public void setRecolte(Recolte recolte) {
        this.recolte = recolte;
    }

    public void setIdRecolteParcelle(String idRpStr) throws Exception {
        int id = Integer.parseInt(idRpStr);
        this.setIdRecolteParcelle(id);
    }

    public int getIdRecolteParcelle() {
        return idRecolteParcelle;
    }

    public void setIdRecolteParcelle(int idRecolteParcelle) throws Exception {
        if (idRecolteParcelle <= 0) {
            throw new Exception("RecolteParcelle : idRecolteParcelle <= 0");
        }
        this.idRecolteParcelle = idRecolteParcelle;
    }

    public Parcelle getParcelle() {
        return parcelle;
    }

    public void setParcelle(Parcelle parcelle) {
        this.parcelle = parcelle;
    }

    public double getNombreOsTotal() {
        return nombreOsTotal;
    }

    public void setNombreOsTotal(double nombreOsTotal) throws Exception {
        if (nombreOsTotal <= 0) {
            throw new Exception("RecolteParcelle : nombreOsTotal <= 0");
        }
        this.nombreOsTotal = nombreOsTotal;
    }

    public double getPoidsTotal() {
        return poidsTotal;
    }

    public void setPoidsTotal(double poidsTotal) throws Exception {
        if (poidsTotal <= 0) {
            throw new Exception("RecolteParcelle : poidsTotal <= 0");
        }
        this.poidsTotal = poidsTotal;
    }

    public double getLongueurOsParTige() {
        return longueurOsParTige;
    }

    public void setLongueurOsParTige(double longueurOsParTige) throws Exception {
        if (longueurOsParTige <= 0) {
            throw new Exception("RecolteParcelle : longueurOsParTige <= 0");
        }
        this.longueurOsParTige = longueurOsParTige;
    }

    public AnomalieRecolte getAnomalieRecolte() {
        return AnomalieRecolte;
    }

    public void setAnomalieRecolte(AnomalieRecolte AnomalieRecolte) {
        this.AnomalieRecolte = AnomalieRecolte;
    }

}
