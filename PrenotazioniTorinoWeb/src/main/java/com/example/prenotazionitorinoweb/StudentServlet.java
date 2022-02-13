package com.example.prenotazionitorinoweb;


import DAO.DAO;
import DAO.utente;
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


@WebServlet(name = "studentservlet", value = "/student-servlet")
public class StudentServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String post=request.getParameter("post");
        PrintWriter out = response.getWriter();
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        if(sessione.equals(s.getId())){
            if(Objects.equals(post, "eliminastudenti")){
                int id=Integer.parseInt(request.getParameter("id"));
                DAO.deleteStudente(id);
            }
        }else{
            s.invalidate();
            out.print("sessione scaduta");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<utente> studenti= DAO.getAllUtente();

        JsonArray allStudent;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)) {
            allStudent = new JsonArray();
            for (utente utente : studenti) {
                String nome = utente.getNome();
                String cognome = utente.getCognome();
                String ruolo = utente.getRuolo();
                int id = utente.getId();
                JsonObject studente = new JsonObject();
                studente.addProperty("nome", nome);
                studente.addProperty("cognome", cognome);
                studente.addProperty("ruolo", ruolo);
                studente.addProperty("id", id);
                allStudent.add(studente);
            }
            out.print(allStudent);
        }else if(s.isNew()){
            out.print((Object) null);
            s.invalidate();

        }


    }


}