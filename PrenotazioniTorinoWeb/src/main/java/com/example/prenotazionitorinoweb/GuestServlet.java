package com.example.prenotazionitorinoweb;


import DAO.DAO;
import DAO.Ripetizioni;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


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
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<Ripetizioni> ripetizioni= DAO.getAllRipetizioni();
        JsonArray allRipetizioni;
        allRipetizioni = new JsonArray();
        for (Ripetizioni value : ripetizioni) {
            String nome_docente = value.getNome_docente();
            String cognome_docente = value.getCognome_docente();
            String corso = value.getNome_corso();
            String giorno = value.getGiorno();
            int ora = value.getOra();
            int idDocente = value.getIdDocente();
            int idCorso = value.getIdCorso();
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
