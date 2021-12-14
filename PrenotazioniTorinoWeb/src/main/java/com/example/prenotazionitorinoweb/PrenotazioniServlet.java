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


@WebServlet(name = "prenotazioniservlet", value = "/prenotazioni-servlet")
public class PrenotazioniServlet extends HttpServlet {
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
        ArrayList<RipetizioniPrenotate> prenotazioni= DAO.getAllPrenotazioni();

        JsonArray allPrenotazioni=null;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)){
            allPrenotazioni = new JsonArray();
            for (int i = 0; i < prenotazioni.size(); i++) {
                String docente = prenotazioni.get(i).getDocente();
                String corso = prenotazioni.get(i).getCorso();
                String utente = prenotazioni.get(i).getUtente();
                String data = prenotazioni.get(i).getData();
                int ora = prenotazioni.get(i).getOra();
                JsonObject prenot = new JsonObject();
                prenot.addProperty("docente", docente);
                prenot.addProperty("corso", corso);
                prenot.addProperty("utente", utente);
                prenot.addProperty("data", data);
                prenot.addProperty("ora", ora);
                allPrenotazioni.add(prenot);
            }
            out.print(allPrenotazioni);
        }else if(s.isNew()){
            out.print(allPrenotazioni);
            s.invalidate();
        }


    }


}