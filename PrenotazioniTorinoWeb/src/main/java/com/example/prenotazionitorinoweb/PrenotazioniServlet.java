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


@WebServlet(name = "prenotazioniservlet", value = "/prenotazioni-servlet")
public class PrenotazioniServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String post=request.getParameter("post");
        if(Objects.equals(post, "elimina")){
            int docente=Integer.parseInt(request.getParameter("docente"));
            int corso=Integer.parseInt(request.getParameter("corso"));
            int utente=Integer.parseInt(request.getParameter("utente"));
            String data=request.getParameter("data");
            int ora=Integer.parseInt(request.getParameter("ora"));
            DAO.deletePrenotazione(docente,corso,utente,data,ora);
        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<RipetizioniPrenotate> prenotazioni= DAO.getAllPrenotazioni();

        JsonArray allPrenotazioni;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)){
            allPrenotazioni = new JsonArray();
            for (RipetizioniPrenotate ripetizioniPrenotate : prenotazioni) {
                String docente = ripetizioniPrenotate.getDocente();
                String corso = ripetizioniPrenotate.getCorso();
                String utente = ripetizioniPrenotate.getUtente();
                String data = ripetizioniPrenotate.getData();
                int ora = ripetizioniPrenotate.getOra();
                int idCorso = ripetizioniPrenotate.getIdCorso();
                int idDocente = ripetizioniPrenotate.getIdDocente();
                int idUtente = ripetizioniPrenotate.getIdUtente();
                String stato = ripetizioniPrenotate.getStato();
                JsonObject prenot = new JsonObject();
                prenot.addProperty("docente", docente);
                prenot.addProperty("corso", corso);
                prenot.addProperty("utente", utente);
                prenot.addProperty("data", data);
                prenot.addProperty("ora", ora);
                prenot.addProperty("idCorso", idCorso);
                prenot.addProperty("idDocente", idDocente);
                prenot.addProperty("idUtente", idUtente);
                prenot.addProperty("stato", stato);
                allPrenotazioni.add(prenot);

            }
            out.print(allPrenotazioni);
        }else if(s.isNew()){
            out.print((Object) null);
            s.invalidate();
        }


    }


}