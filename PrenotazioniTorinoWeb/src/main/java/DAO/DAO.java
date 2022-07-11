package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


public class DAO {
    private static final String url1 = "jdbc:mysql://localhost:8889/prenotazionitorino?useSSL=false";
    private static final String user = "root";
    private static final String password = "root";

    public static void  registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    public static boolean emailnotGetted(String email){
        Connection conn1 = null;
        boolean out = false;
        ArrayList<utente> users = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT email FROM utente WHERE email='"+email+"'");
            while(rs1.next()){
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
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
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
            ResultSet rs1 = st1.executeQuery("SELECT d.cognome AS docente, c.nome AS corso, u.nome AS utente,p.data,p.ora, d.id AS idDocente, c.id AS idCorso, u.id AS idUtente, p.stato AS stato FROM (((prenotazione p JOIN docente d ON (d.id=p.docente))JOIN corso c ON (c.id=p.corso))JOIN utente u ON u.id=p.utente);");
            while (rs1.next()) {
                RipetizioniPrenotate rp = new RipetizioniPrenotate(rs1.getString("docente"),rs1.getString("corso"),rs1.getString("utente"), rs1.getString("data"), rs1.getString("stato"),rs1.getInt("ora"),rs1.getInt("idCorso"),rs1.getInt("idDocente"),rs1.getInt("idUtente") );
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

    public static boolean setUtente(String email,String nome,String cognome, String u_password){
        Connection conn1 = null;
        boolean setted = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO utente (id,email,nome,cognome,password,ruolo) VALUES (0,'"+email+"','"+nome+"','"+cognome+"','"+u_password+"','studente')");
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

    public static boolean setCorso(String nome){
        Connection conn1 = null;
        boolean result = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO corso (id,nome) VALUES (0,'"+nome+"')");
            result = true;
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
        return result;
    }

    public static void setDocente(String nome,String cognome){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("INSERT INTO docente (id,nome,cognome) VALUES (0,'"+nome+"','"+cognome+"')");
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
            ResultSet rs1 = st1.executeQuery("SELECT d.nome AS nome_docente,d.cognome AS cognome_docente,d.id AS id_docente, c.nome AS corso, c.id AS id_corso,i.giorno AS giorno, i.ora AS ora FROM (docente d JOIN insegnamento i ON (d.id=i.docente)) JOIN corso c ON(c.id=i.corso) WHERE stato=0;");
            while (rs1.next()) {
                Ripetizioni ripetizioni = new Ripetizioni(rs1.getString("nome_docente"),rs1.getString("cognome_docente"),rs1.getString("corso"),rs1.getString("giorno"),rs1.getInt("ora"),rs1.getInt("id_corso"),rs1.getInt("id_docente") );
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
            ResultSet rs = st.executeQuery("SELECT cognome FROM docente WHERE id='"+docente+"'");
            String nome_d=null;
            while(rs.next()){
                nome_d = rs.getString("cognome");
            }
            rs = st.executeQuery("SELECT nome FROM corso WHERE id='"+corso+"'");
            String nome_c=null;
            while(rs.next()){
                nome_c = rs.getString("nome");
            }
            System.out.println(nome_d + nome_c);
            st.executeUpdate("INSERT INTO prenotazione (id,docente,corso,utente,data,ora,stato,n_docente,n_corso) VALUES (0,'"+docente+"','"+corso+"','"+utente+"','"+giorno+"','"+ora+"','prenotata','"+nome_d+"','"+nome_c+"')");
            st.executeUpdate("UPDATE insegnamento SET stato= 1 WHERE insegnamento.docente='"+docente+"' AND   insegnamento.giorno='"+giorno+"' AND insegnamento.ora='"+ora+"'   " );
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

    public static boolean deleteDocente(int id){
        Connection conn1 = null;
        boolean value = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM docente WHERE id='"+id+"'");
            value = true;
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
        return value;
    }

    public static boolean deleteCorso(int id){
        Connection conn1 = null;
        boolean value = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM corso WHERE id='"+id+"'");
            value = true;
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
        return value;
    }

    public static void deleteStudente(int id){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM utente WHERE id='"+id+"'");
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

    public static void deletePrenotazione(int docente,int corso, int utente, String data, int ora){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM prenotazione WHERE docente='"+docente+"' && corso='"+corso+"' && utente='"+utente+"' && data='"+data+"' && ora='"+ora+"'");
            st.executeUpdate("UPDATE insegnamento SET stato= 0 WHERE insegnamento.corso='"+corso+"' AND insegnamento.docente='"+docente+"' AND   insegnamento.giorno='"+data+"' AND insegnamento.ora='"+ora+"'   " );
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
            ResultSet rs1 = st1.executeQuery("SELECT p.n_docente AS docente, p.n_corso AS corso, u.email AS utente, p.data AS data, p.ora AS ora, p.docente AS idDocente, p.corso AS idCorso, u.id AS idUtente, p.stato AS stato  FROM utente u JOIN prenotazione p ON (u.id=p.utente) WHERE u.id='"+id+"';");
            while (rs1.next()) {
                RipetizioniPrenotate ripetizioni = new RipetizioniPrenotate(rs1.getString("docente"),rs1.getString("corso"),rs1.getString("utente"),rs1.getString("data"),rs1.getString("stato"),rs1.getInt("ora"),rs1.getInt("idCorso"),rs1.getInt("idDocente"),rs1.getInt("idUtente") );
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

    public static void conferma(int docente,int corso, String giorno, int ora,int utente ){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE prenotazione SET stato='effettuata' WHERE prenotazione.utente='"+utente+"' AND prenotazione.docente='"+docente+"' AND prenotazione.corso='"+corso+"' AND prenotazione.data='"+giorno+"' AND prenotazione.ora='"+ora+"'; " );
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

    public static void disdetta(int docente,int corso, String giorno, int ora,int utente ){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("UPDATE prenotazione SET stato='disdetta' WHERE prenotazione.utente='"+utente+"' AND prenotazione.docente='"+docente+"' AND prenotazione.corso='"+corso+"' AND prenotazione.data='"+giorno+"' AND prenotazione.ora='"+ora+"';  " );
            st.executeUpdate("UPDATE insegnamento SET stato= 0 WHERE  insegnamento.docente='"+docente+"' AND   insegnamento.giorno='"+giorno+"' AND insegnamento.ora='"+ora+"'   " );
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

    public static boolean setAssociazione(int corso,int docente, int disp){
        Connection conn1 = null;
        boolean result = false;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            if(disp == 1){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','18',0)");
                st.close();
                result = true;
            }else if(disp==2){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','18',0)");
                st.close();
                result = true;
            }else if(disp==3){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','18',0)");
                st.close();
                result = true;
            }else if(disp==4){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','18',0)");
                st.close();
                result = true;
            }else if(disp==5){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','18',0)");
                st.close();
                result = true;
            }else if(disp==6){
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','lunedì','18',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','martedì','18',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','mercoledì','18',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','giovedì','18',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','15',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','16',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','17',0)");
                st.executeUpdate("INSERT INTO insegnamento (corso,docente,giorno,ora,stato) VALUES ('"+corso+"','"+docente+"','venerdì','18',0)");
                st.close();
                result = true;
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
            return result;
        }
    }


    public static ArrayList<associazioni> getAssociazioni(){
        Connection conn1 = null;
        ArrayList<associazioni> out = new ArrayList<>();
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st1 = conn1.createStatement();
            ResultSet rs1 = st1.executeQuery("SELECT DISTINCT  docente,  corso FROM insegnamento ");
            while (rs1.next()) {
                associazioni a = new associazioni(rs1.getInt("docente"),rs1.getInt("corso") );
                out.add(a);
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

    public static void deleteAssociazione(int docente,int corso){
        Connection conn1 = null;
        try {
            conn1 = DriverManager.getConnection(url1, user, password);
            Statement st = conn1.createStatement();
            st.executeUpdate("DELETE FROM insegnamento WHERE docente='"+docente+"' && corso='"+corso+"'");
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
