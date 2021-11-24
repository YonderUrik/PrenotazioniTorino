package com.example.prenotazionitorinoweb;


import DAO.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@WebServlet(name = "autservlet", value = "/aut-servlet")
public class AutServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");


        ServletContext ctx = getServletContext();
        RequestDispatcher rd = ctx.getRequestDispatcher("/index.html");

        String email = request.getParameter("utente");
        String password = request.getParameter("password");
        HttpSession s = request.getSession(); //estraggo il session ID
        String jsessionID = s.getId();
        System.out.println("JSessionID:" + jsessionID);
        System.out.println("email ricevuto:" + email);
        System.out.println("password ricevuta:" + password);

       if (Objects.equals(email, "guest")){
            //AVVIO LA SESSIONE PER GUEST
            String role = "guest";
            s.setAttribute("email",email);
            s.setAttribute("ruolo", role);
            JSONObject guest = new JSONObject();
            guest.put("email",email);
            guest.put("ruolo",role);

            //CAMBIO
       }else if (jsessionID!=null) {
            //VERIFICO L'UTENTE
            ArrayList<utente> utente= DAO.getUtente(email,password);
            if(utente.get(0).getEmail().equals(email) && utente.get(0).getPassword().equals(password)){
                String role = utente.get(0).getRuolo();
                s.setAttribute("email", email);
                s.setAttribute("ruolo", role);
                System.out.println(s.getAttribute("ruolo"));
                //CAMBIO
            }else{
                System.out.println("Utente errato");
            }
       }
    }






    private String getRole(String username, String password){
        String temp= null;
        ArrayList<utente> utente= DAO.getUtente(username,password);
        if(utente.isEmpty()){
            return temp;
        }
        if(Objects.equals(utente.get(0).getEmail(),username) && Objects.equals(utente.get(0).getPassword(),password)){
            temp=utente.get(0).getRuolo();
        }

        return temp;
    }

}
