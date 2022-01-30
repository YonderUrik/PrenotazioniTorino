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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet(name = "guestservlet", value = "/guest-servlet")
public class GuestServlet extends HttpServlet {
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
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<Ripetizioni> ripetizioni= DAO.getAllRipetizioni();
        JsonArray allRipetizioni=null;
        allRipetizioni = new JsonArray();

            for (int i = 0; i < ripetizioni.size(); i++) {
                String nome_docente = ripetizioni.get(i).getNome_docente();
                String cognome_docente = ripetizioni.get(i).getCognome_docente();
                String corso = ripetizioni.get(i).getNome_corso();
                String giorno=ripetizioni.get(i).getGiorno();
                int ora=ripetizioni.get(i).getOra();
                int idDocente=ripetizioni.get(i).getIdDocente();
                int idCorso=ripetizioni.get(i).getIdCorso();
                JsonObject ripet = new JsonObject();
                ripet.addProperty("nome_docente", nome_docente);
                ripet.addProperty("cognome_docente", cognome_docente);
                ripet.addProperty("giorno", giorno);
                ripet.addProperty("corso", corso);
                ripet.addProperty("ora", ora);
                ripet.addProperty("id_corso", idCorso);
                ripet.addProperty("id_docente", idDocente);
                allRipetizioni.add(ripet);
            }
            out.print(allRipetizioni);



    }


}
