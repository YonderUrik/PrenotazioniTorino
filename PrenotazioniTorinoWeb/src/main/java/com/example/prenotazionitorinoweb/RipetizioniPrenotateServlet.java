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


@WebServlet(name = "ripetizioniprenotateservlet", value = "/ripetizioni-prenotate-servlet")
public class RipetizioniPrenotateServlet extends HttpServlet {
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
        HttpSession s = request.getSession();
        int id= Integer.parseInt(s.getAttribute("id").toString());
        System.out.println(id);
        String sessione = request.getParameter("sessione");
        String sessionID = s.getId();
        ArrayList<RipetizioniPrenotate> rip=DAO.getRipetizioniUtente(id);
        JsonArray PrenotUtente=null;
        PrenotUtente=new JsonArray();
        for(int i=0;i< rip.size();i++){
            String docente=rip.get(i).getDocente();
            String corso=rip.get(i).getCorso();
            String utente=rip.get(i).getUtente();
            String giorno=rip.get(i).getData();
            int ora=rip.get(i).getOra();
            JsonObject prenotazionesingola=new JsonObject();
            prenotazionesingola.addProperty("docente",docente);
            prenotazionesingola.addProperty("corso",corso);
            prenotazionesingola.addProperty("utente",utente);
            prenotazionesingola.addProperty("data",giorno);
            prenotazionesingola.addProperty("ora",ora);
            PrenotUtente.add(prenotazionesingola);

        }
        out.print(PrenotUtente);

    }


}