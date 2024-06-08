/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.events;

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
import objets.infrastructures.Parcelle;

/**
 *
 * @author F
 */
@TableAnnotation(nomTable = "recoltes")
public class Recolte extends ObjetBdd {

    int idRecolte = -1;
    Timestamp dateHeure;
    @TsyMiditraAnnotation
    ArrayList<RecolteParcelle> lRecolteParcelles;
    @TsyMiditraAnnotation
    Prevision prevision;

    public Recolte(String date, String heure) throws Exception {
        if ("".equals(date)) {
            throw new Exception("date du Recolte vide");
        }
        if ("".equals(heure)) {
            throw new Exception("heure du Recolte vide");
        }

        date = date.trim();
        heure = heure.trim();
        heure = heure + ":00";

        //date = Utilitaire.traiterDate(date, "-");
        System.out.println("date + \" \" + heure " + date + " " + heure);
        Timestamp dh = Timestamp.valueOf(date + " " + heure);
        this.setDateHeure(dh);
    }

    public Recolte() {

    }

    public void insertOrUpdateRecolteParcelle(Connection connection) throws Exception {
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new Connect().getConnectionPgsql();
        }

        for (int i = 0; i < this.getlRecolteParcelles().size(); i++) {
            RecolteParcelle rp = this.getlRecolteParcelles().get(i);
            rp.setRecolte(this);
            //update
            if(rp.getIdRecolteParcelle() > 0) {
                //rp.update(new Connect().getConnectionPgsql(), true);
                rp.maj(connection);
            }
            //insert
            else {
                rp.insert(connection, false);
            }   
        }

        if (open == true) {
            connection.commit();
            connection.close();
        }
    }

    public RecolteParcelle isAlreadySaved(Parcelle p) {
        for (int i = 0; i < lRecolteParcelles.size(); i++) {
            RecolteParcelle get = lRecolteParcelles.get(i);
            if (get.getParcelle().getIdParcelle() == p.getIdParcelle()) {
                return get;
            }
        }
        return null;
    }

    public int insererWithLRecolteParcelles(Connection connection) throws Exception {
        //inserer Recolte 
        //avoir son ID
        //RecolteParcelle.setRecolte pour chaque RecolteParcelle
        //RecolteParcelle.insert pour chaque RecolteParcelle
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new Connect().getConnectionPgsql();
        }

        int idNewRecolte = this.inserereAndGetId(connection);
        this.setIdRecolte(idNewRecolte); 

        for (int i = 0; i < this.getlRecolteParcelles().size(); i++) {
            RecolteParcelle rp = this.getlRecolteParcelles().get(i);
            rp.setRecolte(this);
            rp.insert(connection, false);
        }

        if (open == true) {
            connection.commit();
            connection.close();
        }

        return this.getIdRecolte();
    }

    public int inserereAndGetId(Connection connection) throws Exception {
        boolean isOpened = false;

        try {
            if (connection == null) {

                isOpened = true;

                Connect connexion = new Connect();

                connection = connexion.getConnectionPgsql();

            }

            Statement stat = connection.createStatement();

            try {

                String requete = "INSERT INTO recoltes\n"
                        + "	(dateheure) VALUES \n"
                        + "    ( timestamp'" + this.getDateHeure() + "' )";
                System.out.println("Recolte.inserereAndGetId 1 " + requete);
                //System.out.println(requete);
                int rowInsert = stat.executeUpdate(requete);

                connection.commit();

                requete = "SELECT * from recoltes WHERE idRecolte = (SELECT max(idRecolte) FROM Recoltes)";
                System.out.println("Recolte.inserereAndGetId 2 " + requete);

                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    throw new Exception("Recolte.inserereAndGetId 2 rencontre un pb -> resultset null");
                }

                resultSet.next();

                return resultSet.getInt("idRecolte");

            } catch (SQLException e) {

                connection.rollback();

                System.out.println("La recuperation du Recolte.inserereAndGetId a echoue");

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

        return -1;
    }

    public static ArrayList<Recolte> selectAll(Connection connection) throws Exception {
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new connect.Connect().getConnectionPgsql();
        }

        ArrayList<Recolte> lc = new Recolte().select(connection, false);
        for (int i = 0; i < lc.size(); i++) {
            Recolte get = lc.get(i);
            get.fetchDateHeure(connection);
        }

        if (open == true) {
            connection.close();
        }

        return lc;
    }

    public static Recolte getById(int idRecolte, Connection connection) throws Exception {
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new Connect().getConnectionPgsql();
        }

        Recolte recolte = new Recolte();
        recolte.setIdRecolte(idRecolte);
        recolte = (Recolte) recolte.select(connection, false).get(0);
        recolte.fetchDateHeure(connection);

        //etablir lRecolteParcelles + anomalies par parcelle
        RecolteParcelle rp = new RecolteParcelle();
        rp.setRecolte(recolte);

        ArrayList<RecolteParcelle> lrp = rp.select(connection, false);
        ArrayList<RecolteParcelle> lrp2 = new ArrayList<>();
        for (int i = 0; i < lrp.size(); i++) {
            RecolteParcelle get = lrp.get(i);
            get.getRecolte().fetchDateHeure(connection);
            get.detecteAnomalieRecolte(connection);
            lrp2.add(get);
        }

        System.out.println("Recolte.getById : detecteAnomalieRecolte OK");
        recolte.setlRecolteParcelles(lrp2);

        //prevision
        Prevision prev = new Prevision();
        prev.setRecolte(recolte);
        System.out.println("Recolte.getById : etablirPrevision debut");
        prev.etablirPrevision(connection);

        System.out.println("Recolte.getById : etablirPrevision OK");

        recolte.setPrevision(prev);

        if (open == true) {
            connection.close();
        }

        return recolte;
    }

    public void fetchDateHeure(Connection connection) throws Exception {
        boolean isOpened = false;

        try {
            if (connection == null) {

                isOpened = true;

                Connect connexion = new Connect();

                connection = connexion.getConnectionPgsql();

            }

            Statement stat = connection.createStatement();

            try {

                String requete = "select * from recoltes where idrecolte = " + getIdRecolte();
                System.out.println("recolte.fetchDateHeure " + requete);
                //System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    throw new Exception("pas de idrecolte " + getIdRecolte());
                }

                resultSet.next();

                Timestamp dateHeureSql = resultSet.getTimestamp("dateheure");

                this.setDateHeure(dateHeureSql);

            } catch (SQLException e) {

                connection.rollback();

                System.out.println("La recuperation du DateHeure recolte a echoue");

                System.out.println(e.getMessage());
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (isOpened == true) {
                connection.close();
            }
        }
    }

    public int getIdRecolte() {
        return idRecolte;
    }

    public void setIdRecolte(int idRecolte) {
        this.idRecolte = idRecolte;
    }

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public ArrayList<RecolteParcelle> getlRecolteParcelles() {
        return lRecolteParcelles;
    }

    public void setlRecolteParcelles(ArrayList<RecolteParcelle> lRecolteParcelles) {
        this.lRecolteParcelles = lRecolteParcelles;
    }

    public Prevision getPrevision() {
        return prevision;
    }

    public void setPrevision(Prevision prevision) {
        this.prevision = prevision;
    }

}
