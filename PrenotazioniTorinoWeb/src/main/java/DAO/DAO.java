package DAO;

import java.sql.*;
import java.util.ArrayList;


public class DAO {
    private static final String url1 = "jdbc:mysql://localhost:3306/prenotazionitorino";
    private static final String user = "root";
    private static final String password = "";

    public static void  registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }

    public static ArrayList<utente> getUtente(String email, String u_password){
        Connection conn1 = null;
        ArrayList<utente> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM utente WHERE email='"+email+"' AND password='"+u_password+"'");
            while (rs1.next()) {
                utente u = new utente(rs1.getString("cognome"),rs1.getString("nome"),rs1.getInt("id"),rs1.getString("email"),rs1.getString("password"),rs1.getString("ruolo"));
                out.add(u);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return out;

    }


    public static void setUtente(String email,String nome,String cognome, String u_password){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO utente (id,email,nome,cognome,password,ruolo) VALUES (0,'"+email+"','"+nome+"','"+cognome+"','"+u_password+"')");
            st.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }

    }

    public static boolean emailGetted(String email){
        Connection conn1 = null;
        boolean out = true;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT email FROM utente WHERE email='"+email+"");
            String user = rs1.getString("email");
            if(email == user){
                out = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return out;

    }
}
