package com.example.prenotazionitorinoweb;

import DAO.DAO;
import DAO.docente;
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

@WebServlet(name = "docenteservlet", value = "/docente-servlet")
public class DocenteServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String post=request.getParameter("post");
        PrintWriter out = response.getWriter();
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        if(!s.isNew() && s.getAttribute("ruolo").equals("admin")) {
            if (Objects.equals(post, "aggiungi")) {
                String nome = request.getParameter("nome");
                String cognome = request.getParameter("cognome");
                DAO.setDocente(nome, cognome);
            }else if (Objects.equals(post, "elimina")) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (!DAO.deleteDocente(id)) {
                    out.print("false");
                }
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
        ArrayList<docente> docente= DAO.getAllDocenti();

        JsonArray allDocenti=null;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(!s.isNew() && s.getAttribute("ruolo").equals("admin")) {
            allDocenti = new JsonArray();
            for (docente value : docente){
                int id = value.getId();
                String nome = value.getNome();
                String cognome = value.getCognome();
                JsonObject docenti = new JsonObject();
                docenti.addProperty("id", id);
                docenti.addProperty("nome", nome);
                docenti.addProperty("cognome", cognome);
                allDocenti.add(docenti);
            }
        }

        out.print(allDocenti);



    }


}

