package com.example.prenotazionitorinoweb;


import DAO.DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


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
        System.out.println("uscito da prenota servlet");



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}