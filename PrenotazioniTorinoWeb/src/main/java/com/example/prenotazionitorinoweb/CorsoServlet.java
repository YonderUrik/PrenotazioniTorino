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


@WebServlet(name = "corsoservlet", value = "/corso-servlet")
public class CorsoServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        String post=request.getParameter("post");
        if(Objects.equals(post, "aggiungi")){
            String nome= request.getParameter("nome");
            DAO.setCorso(nome);
        } else if(Objects.equals(post, "elimina")){
            int id=Integer.parseInt(request.getParameter("id"));
            DAO.deleteCorso(id);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out=response.getWriter();
        ArrayList<corsi> corsi= DAO.getAllCorsi();

        JsonArray allCourses=null;
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        String sessionID = s.getId();
        if(sessione.equals(sessionID)) {
            allCourses = new JsonArray();
            for (int i = 0; i < corsi.size(); i++) {
                int id = corsi.get(i).getId();
                String nome = corsi.get(i).getNome();
                JsonObject course = new JsonObject();
                course.addProperty("id", id);
                course.addProperty("nome", nome);
                allCourses.add(course);

            }
        }
        out.print(allCourses);



    }


}