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


@WebServlet(name = "studentservlet", value = "/student-servlet")
public class StudentServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String post=request.getParameter("post");
        if(Objects.equals(post, "eliminastudenti")){
            int id=Integer.parseInt(request.getParameter("id"));
            DAO.deleteStudente(id);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<utente> studenti= DAO.getAllUtente();

        JsonArray allStudent = null;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)) {
            allStudent = new JsonArray();
            for (int i = 0; i < studenti.size(); i++) {
                String nome = studenti.get(i).getNome();
                String cognome = studenti.get(i).getCognome();
                String ruolo = studenti.get(i).getRuolo();
                int id=studenti.get(i).getId();
                JsonObject studente = new JsonObject();
                studente.addProperty("nome", nome);
                studente.addProperty("cognome", cognome);
                studente.addProperty("ruolo", ruolo);
                studente.addProperty("id",id);
                allStudent.add(studente);
            }
            out.print(allStudent);
        }else if(s.isNew()){
            out.print(allStudent);
            s.invalidate();

        }


    }


}