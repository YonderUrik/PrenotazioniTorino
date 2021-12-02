package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


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
            st.executeUpdate("INSERT INTO utente (id,email,nome,cognome,password,ruolo) VALUES (0,'"+email+"','"+nome+"','"+cognome+"','"+u_password+"','studente')");
            System.out.println("Utente aggiunto");
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

    public static boolean emailnotGetted(String email){
        Connection conn1 = null;
        boolean out = false;
        ArrayList<utente> users = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT email FROM utente WHERE email='"+email+"'");
            System.out.println("query presa");
            while(rs1.next()){
                System.out.println("utente : "+rs1.getString("email"));
                utente user = new utente(rs1.getString("email"));
                users.add(user);
            }
            if(users.isEmpty() || Objects.equals(users.get(0).getEmail(), "")){
                out = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                    System.out.println("connesione chiusa");
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        System.out.println("out : "+out);
        return out;
    }

    public static ArrayList<utente> getAllUtente(){
        Connection conn1 = null;
        ArrayList<utente> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM utente WHERE ruolo='studente'");
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

    public static ArrayList<corsi> getAllCorsi(){
        Connection conn1 = null;
        ArrayList<corsi> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM corso");
            while (rs1.next()) {
                corsi c = new corsi(rs1.getInt("id"),rs1.getString("nome"));
                out.add(c);
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


    public static ArrayList<docente> getAllDocenti(){
        Connection conn1 = null;
        ArrayList<docente> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT * FROM docente");
            while (rs1.next()) {
                docente d = new docente(rs1.getInt("id"),rs1.getString("nome"),rs1.getString("cognome"));
                out.add(d);
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

    public static ArrayList<RipetizioniPrenotate> getAllPrenotazioni(){
        Connection conn1 = null;
        ArrayList<RipetizioniPrenotate> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT d.cognome AS docente, c.nome AS corso, u.nome AS utente,p.data,p.ora FROM (((prenotazione p JOIN docente d ON (d.id=p.docente))JOIN corso c ON (c.id=p.corso))JOIN utente u ON u.id=p.utente);");
            while (rs1.next()) {
                RipetizioniPrenotate rp = new RipetizioniPrenotate(rs1.getString("docente"),rs1.getString("corso"),rs1.getString("utente"), rs1.getString("data"), rs1.getInt("ora") );
                out.add(rp);
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

    public static void setCorso(String nome){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO corso (id,nome) VALUES (0,'"+nome+"')");
            System.out.println("Corso aggiunto");
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

}
