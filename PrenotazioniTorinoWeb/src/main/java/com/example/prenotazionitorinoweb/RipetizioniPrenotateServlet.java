package com.example.prenotazionitorinoweb;


import DAO.DAO;
import DAO.RipetizioniPrenotate;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;


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
            throws IOException {
       
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        HttpSession s = request.getSession();
        String controllo= request.getParameter("android");
        int id;
        System.out.println(controllo);
        if(Objects.equals(request.getParameter("android"), "android")){
            id= Integer.parseInt(request.getParameter("id"));
        }else{
            id= Integer.parseInt(s.getAttribute("id").toString());
        }

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
            String stato=rip.get(i).getStato();
            int ora=rip.get(i).getOra();
            int idCorso = rip.get(i).getIdCorso();
            int idDocente = rip.get(i).getIdDocente();
            int idUtente = rip.get(i).getIdUtente();
            JsonObject prenotazionesingola=new JsonObject();
            prenotazionesingola.addProperty("docente",docente);
            prenotazionesingola.addProperty("corso",corso);
            prenotazionesingola.addProperty("utente",utente);
            prenotazionesingola.addProperty("data",giorno);
            prenotazionesingola.addProperty("ora",ora);
            prenotazionesingola.addProperty("stato",stato);
            prenotazionesingola.addProperty("idCorso", idCorso);
            prenotazionesingola.addProperty("idDocente", idDocente);
            prenotazionesingola.addProperty("idUtente", idUtente);
            PrenotUtente.add(prenotazionesingola);

        }
        out.print(PrenotUtente);

    }


}