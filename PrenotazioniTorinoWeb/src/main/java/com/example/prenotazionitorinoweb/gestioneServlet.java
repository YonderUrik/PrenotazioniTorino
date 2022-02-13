package com.example.prenotazionitorinoweb;


import DAO.DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "gestioneservet", value = "/gestione-servlet")
public class gestioneServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String sessione = request.getParameter("sessione");
        HttpSession s = request.getSession();
        if(sessione.equals(s.getId())){
            String post = request.getParameter("post");
            int id = Integer.parseInt(s.getAttribute("id").toString());
            int docente = Integer.parseInt(request.getParameter("id_docente"));
            int corso = Integer.parseInt(request.getParameter("id_corso"));
            String data = request.getParameter("data");
            int ora = Integer.parseInt(request.getParameter("ora"));
            if (post.equals("conferma")) {
                DAO.conferma(docente, corso, data, ora, id);
                System.out.println(data);
            } else if (post.equals("disdici")) {
                System.out.println("disdetta");
                DAO.disdetta(docente, corso, data, ora, id);
            }
        }else{
            out.print("sessione scaduta");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}