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


@WebServlet(name = "docenteservlet", value = "/docente-servlet")
public class DocenteServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String post=request.getParameter("post");
        if(Objects.equals(post, "aggiungi")){
            String nome= request.getParameter("nome");
            String cognome= request.getParameter("cognome");
            DAO.setDocente(nome,cognome);
        }else if(Objects.equals(post, "elimina")){
            int id= Integer.parseInt(request.getParameter("id"));
            DAO.deleteDocente(id);
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<docente> docente= DAO.getAllDocenti();

        JsonArray allDocenti=null;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)) {
            allDocenti = new JsonArray();
            for (int i = 0; i < docente.size(); i++) {
                int id = docente.get(i).getId();
                String nome = docente.get(i).getNome();
                String cognome = docente.get(i).getCognome();
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

