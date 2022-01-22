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


    public static boolean setUtente(String email,String nome,String cognome, String u_password){
        Connection conn1 = null;
        boolean setted = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO utente (id,email,nome,cognome,password,ruolo) VALUES (0,'"+email+"','"+nome+"','"+cognome+"','"+u_password+"','studente')");
            System.out.println("Utente aggiunto");
            setted = true;
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
        return setted;
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
            ResultSet rs1 = st1.executeQuery("SELECT * FROM corso ORDER BY id");
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

    public static void setDocente(String nome,String cognome){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO docente (id,nome,cognome) VALUES (0,'"+nome+"','"+cognome+"')");
            System.out.println("Docente aggiunto");
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

    public static ArrayList<Ripetizioni> getAllRipetizioni(){
        Connection conn1 = null;
        ArrayList<Ripetizioni> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT d.nome AS nome_docente,d.cognome AS cognome_docente,d.id AS id_docente, c.nome AS corso, c.id AS id_corso FROM (docente d JOIN insegnamento i ON (d.id=i.docente)) JOIN corso c ON(c.id=i.corso);");
            while (rs1.next()) {
                Ripetizioni ripetizioni = new Ripetizioni(rs1.getString("nome_docente"),rs1.getString("cognome_docente"),rs1.getInt("id_docente"),rs1.getString("corso"),rs1.getInt("id_corso") );
                out.add(ripetizioni);
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

    public static void setPrenotazione(int docente,int corso, String giorno, int ora,int utente ){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO prenotazione (docente,corso,utente,data,ora) VALUES ('"+docente+"','"+corso+"','"+utente+"','"+giorno+"','"+ora+"')");
            System.out.println("Prenotazione effettuata");
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

    public static void deleteDocente(int id){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM docente WHERE id='"+id+"'");
            System.out.println("Docente aggiunto");
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

    public static void deleteCorso(int id){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM corso WHERE id='"+id+"'");
            System.out.println("Corso Eliminato");
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

    public static void deleteStudente(int id){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM utente WHERE id='"+id+"'");
            System.out.println("utente Eliminato");
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

    public static ArrayList<RipetizioniPrenotate> getRipetizioniUtente(int id){
        Connection conn1 = null;
        ArrayList<RipetizioniPrenotate> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);


            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT d.cognome AS docente, c.nome AS corso, u.email AS utente, p.data AS data, p.ora AS ora FROM ((corso c JOIN prenotazione p ON(c.id=p.corso))JOIN utente u ON (u.id=p.utente))JOIN docente d ON (d.id=p.docente) WHERE u.id='"+id+"';");
            while (rs1.next()) {
                RipetizioniPrenotate ripetizioni = new RipetizioniPrenotate(rs1.getString("docente"),rs1.getString("corso"),rs1.getString("utente"),rs1.getString("data"),rs1.getInt("ora") );
                out.add(ripetizioni);
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
