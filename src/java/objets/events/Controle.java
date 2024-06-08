/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objets.events;

import annot.Datetime;
import annot.TableAnnotation;
import annot.TsyMiditraAnnotation;
import connect.Connect;
import dao.ObjetBdd;
import fonctions.Utilitaire;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author Fy
 */
@TableAnnotation(nomTable = "controles")
public class Controle extends ObjetBdd {

    int idControle = -1;
    Timestamp dateHeure;
    @TsyMiditraAnnotation
    ArrayList<ControleParcelle> lControleParcelles;

    public Controle(String date, String heure) throws Exception {
        if ("".equals(date)) {
            throw new Exception("date du controle vide");
        }
        if ("".equals(heure)) {
            throw new Exception("heure du controle vide");
        }

        date = date.trim();
        heure = heure.trim();
        heure = heure + ":00";

        //date = Utilitaire.traiterDate(date, "-");

        System.out.println("date + \" \" + heure "+date + " " + heure);
        Timestamp dh = Timestamp.valueOf(date + " " + heure);
        this.setDateHeure(dh);
    }

    public Controle() {

    }

    public int insererWithLcontroleParcelles(Connection connection) throws Exception {
        //inserer controle 
        //avoir son ID
        //ControleParcelle.setControle pour chaque ControleParcelle
        //ControleParcelle.insert pour chaque controleParcelle
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new Connect().getConnectionPgsql();
        }

        int idNewControle = this.inserereAndGetId(connection);
        this.setIdControle(idNewControle);
        
        for (int i = 0; i < this.getlControleParcelles().size(); i++) {
            ControleParcelle cp = this.getlControleParcelles().get(i);
            cp.setControle(this);
            cp.insert(connection, false);
        }

        if (open == true) {
            connection.commit();
            connection.close();
        }
        
        return this.getIdControle();
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

                String requete = "INSERT INTO controles\n"
                        + "	(dateheure) VALUES \n"
                        + "    ( timestamp'"+this.getDateHeure()+"' )";
                System.out.println("Controle.inserereAndGetId 1 " + requete);
                //System.out.println(requete);
                int rowInsert = stat.executeUpdate(requete);

                connection.commit();
                
                requete = "SELECT * from controles WHERE idControle = (SELECT max(idControle) FROM controles)";
                System.out.println("Controle.inserereAndGetId 2 " + requete);
                
                ResultSet resultSet = stat.executeQuery(requete);
                
                if(resultSet == null) throw new Exception("Controle.inserereAndGetId 2 rencontre un pb -> resultset null");

                resultSet.next();
                
                return resultSet.getInt("idcontrole");
                
            } catch (SQLException e) {

                connection.rollback();

                System.out.println("La recuperation du Controle.inserereAndGetId a echoue");

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

    public static Controle getById(int idControle, Connection connection) throws Exception {
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new Connect().getConnectionPgsql();
        }

        Controle ctrl = new Controle();
        ctrl.setIdControle(idControle);
        ctrl = (Controle) ctrl.select(connection, false).get(0);
        ctrl.fetchDateHeure(connection);

        //etablir lControleParcelles + anomalies par parcelle
        ControleParcelle cp = new ControleParcelle();
        cp.setControle(ctrl);

        ArrayList<ControleParcelle> lcp = cp.select(connection, false);
        ArrayList<ControleParcelle> lcp2 = new ArrayList<>();
        for (int i = 0; i < lcp.size(); i++) {
            ControleParcelle get = lcp.get(i);
            get.getControle().fetchDateHeure(connection);
            get.detecteAnomalieControle(connection);
            lcp2.add(get);
        }

        ctrl.setlControleParcelles(lcp2);

        if (open == true) {
            connection.close();
        }

        return ctrl;
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

                String requete = "select * from controles where idcontrole = " + getIdControle();
                System.out.println("Controle.fetchDateHeure " + requete);
                //System.out.println(requete);
                ResultSet resultSet = stat.executeQuery(requete);

                if (resultSet == null) {
                    throw new Exception("pas de idcontrole " + getIdControle());
                }

                resultSet.next();

                Timestamp dateHeureSql = resultSet.getTimestamp("dateheure");

                this.setDateHeure(dateHeureSql);

            } catch (SQLException e) {

                connection.rollback();

                System.out.println("La recuperation du DateHeure controles a echoue");

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

    public static ArrayList<Controle> selectAll(Connection connection) throws Exception {
        boolean open = false;
        if (connection == null) {
            open = true;
            connection = new connect.Connect().getConnectionPgsql();
        }

        ArrayList<Controle> lc = new Controle().select(connection, false);
        for (int i = 0; i < lc.size(); i++) {
            Controle get = lc.get(i);
            get.fetchDateHeure(connection);
        }

        if (open == true) {
            connection.close();
        }

        return lc;
    }

    public int getIdControle() {
        return idControle;
    }

    public void setIdControle(int idControle) {
        this.idControle = idControle;
    }

    public Timestamp getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(Timestamp dateHeure) {
        this.dateHeure = dateHeure;
    }

    public ArrayList<ControleParcelle> getlControleParcelles() {
        return lControleParcelles;
    }

    public void setlControleParcelles(ArrayList<ControleParcelle> lControleParcelles) {
        this.lControleParcelles = lControleParcelles;
    }
}
