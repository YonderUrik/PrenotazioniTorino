package com.example.prenotazionitorinoweb;


import DAO.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet(name = "autservlet", value = "/aut-servlet")
public class AutServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("post");
        processRequest(request, response);
        System.out.println("uscito post");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        processRequest(request, response);
        System.out.println("uscito get");
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        String email = request.getParameter("utente");
        String password = request.getParameter("password");
        String sessione=request.getParameter("sessione");
        HttpSession s = request.getSession(); //estraggo il session ID
        String jsessionID = s.getId();
        s.setMaxInactiveInterval(60);
        System.out.println("JSessionID:" + jsessionID);
        System.out.println("email ricevuto:" + email);
        System.out.println("password ricevuta:" + password);


       if (Objects.equals(email, "guest")){
            //AVVIO LA SESSIONE PER GUEST
            String role = "ospite";
            s.setAttribute("email",email);
            s.setAttribute("ruolo", role);
           JsonObject ospite=new JsonObject();
           ospite.addProperty("email",email);
           ospite.addProperty("ruolo",role);
           System.out.println(ospite.get("ruolo"));
           out.print(ospite);
       }else if(jsessionID!=null && !jsessionID.equals(sessione)){
            //VERIFICO L'UTENTE
            ArrayList<utente> utente= DAO.getUtente(email,password);
            if(utente.get(0).getEmail().equals(email) && utente.get(0).getPassword().equals(password)){
                String role = utente.get(0).getRuolo();
                s.setAttribute("email", email);
                s.setAttribute("ruolo", role);
                JsonObject utente2=new JsonObject();
                utente2.addProperty("email",email);
                utente2.addProperty("ruolo",role);
                utente2.addProperty("sessione", jsessionID);
                utente2.addProperty("nome", utente.get(0).getNome());
                utente2.addProperty("cognome", utente.get(0).getCognome());
                System.out.println("stampa: "+utente2.get("ruolo"));
                System.out.println(utente2);
                out.print(utente2);
            }else{
                System.out.println("Utente errato");
            }
       }else{
           //SESSIONE GIA AUTENTICATA
       }
    }








}
