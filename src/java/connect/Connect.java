package connect;

import java.sql.*;

public class Connect {
    Connection con;
    private final String bdd = "katsaka";
    private final String user = "postgres";
    private final String mdp = "0000";

    public Connection getConnectionPgsql() throws Exception {
        try {
            // Class dbDriver = Class.forName("org.postgresql.Driver");
            Class.forName("org.postgresql.Driver");
            String jdbcURL = "jdbc:postgresql://localhost:5432/"+bdd;
            con = DriverManager.getConnection(jdbcURL, user, mdp);

            con.setAutoCommit(false);

            return con;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new Exception("Connexion postgres interrompue");

    }

    public Connection getConnection() {
        return this.con;
    }

    public Connection getConnexionOracle() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}