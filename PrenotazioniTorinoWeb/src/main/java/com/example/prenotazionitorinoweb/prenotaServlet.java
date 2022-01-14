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


@WebServlet(name = "prenotaservlet", value = "/prenota-servlet")
public class prenotaServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession s = request.getSession();
        int id= Integer.parseInt(s.getAttribute("id").toString());
        int docente= Integer.parseInt(request.getParameter("id_docente"));
        int corso= Integer.parseInt(request.getParameter("id_corso"));
        String giorno= request.getParameter("giorno");
        int ora= Integer.parseInt(request.getParameter("ora"));
        DAO.setPrenotazione(docente,corso,giorno,ora,id);



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {




    }


}