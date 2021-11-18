package com.danieleroccaforte.ium.studentbooking;


import java.sql.*;

public class DAO {

    private static final String url = "http://10.0.2.2:8080/studentbooking";
    private static final String user = "root";
    private static final String password = "";

    public static void registerDriver(){
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver correttamente registrato");
        } catch (SQLException e){
            System.out.println("Errrore: " + e.getMessage());
        }
    }

    public static boolean verifyLogin(String email, String u_password){
        Connection conn = null;
        boolean verified = false;
        try{
            System.out.println("VERIFY 1");
            conn =  DriverManager.getConnection(url,user,password);
            System.out.println("VERIFY 2");
            Statement st = conn.createStatement();
            System.out.println("VERIFY 3");
            String d_password = crypt(u_password);
            ResultSet rs = st.executeQuery("SELECT email,password FROM COURSES WHERE email = '" + email + "' AND password = '" + d_password + "'");
            String db_email = rs.getString("email");
            String db_password = rs.getString("password");
            if(email.equals(db_email) && d_password.equals(db_password)){
                verified = true;
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }
            }
        }
        return verified;
    }

    public static boolean checkedSignup(String email, String u_password) {
        Connection conn = null;
        boolean verified = false;
        try {
            conn = DriverManager.getConnection(url, user, password);
            Statement st = conn.createStatement();
            String c_password = crypt(u_password);
            ResultSet rs = st.executeQuery("SELECT EMAIL,PASSWORD FROM COURSES WHERE EMAIL = '" + email + "' AND password = '" + c_password + "'");
            String db_email = rs.getString("email");
            String db_password = rs.getString("password");
            rs.close();
            if (!db_email.equals("") && !email.equals(db_email) && !c_password.equals(db_password)) {
                verified = true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e2) {
                    System.out.println(e2.getMessage());
                }
            }
        }
        return verified;
    }

    public static void insertSignup(String name, String surname, String email, String u_password){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url,user,password);
            Statement st = conn.createStatement();
            String c_password = crypt(u_password);

        if(!email.equals("") && !u_password.equals("")) {
                st.executeUpdate("INSERT INTO courses (name,surname,email,password) VALUES (0,'" + name + "','" + surname + "','" + email + "','" + c_password + "')");
        }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            if(conn != null){
                try{
                    conn.close();
                } catch (SQLException e2){
                    System.out.println(e2.getMessage());
                }
            }
        }
    }

    private static String crypt(String p){
        int j;
        StringBuilder c = new StringBuilder();
        for (int i = 0; i<p.length(); i++){
            j=(int)p.charAt(i) + 3;
            if(j>90) j-=26;
            c.append((char) j);
        }
        return c.toString();
    }
}
