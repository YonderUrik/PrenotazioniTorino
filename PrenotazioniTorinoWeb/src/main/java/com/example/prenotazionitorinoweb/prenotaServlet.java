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
        int docente= Integer.parseInt(request.getParameter("id_docente"));
        int corso= Integer.parseInt(request.getParameter("id_corso"));
        String giorno= request.getParameter("giorno");
        int ora= Integer.parseInt(request.getParameter("ora"));
        ArrayList<id> utente= DAO.getIdUtente(request.getParameter("utente"));
        DAO.setPrenotazione(docente,corso,giorno,ora,utente.get(0).getId());


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {





        /*
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("nome ricevuto:" + nome);
        System.out.println("cognome ricevuto:" + cognome);
        System.out.println("email ricevuto:" + email);
        System.out.println("password ricevuta:" + password);
        if(!nome.equals("") && !cognome.equals("") && !email.equals("") && !password.equals("")){
            if(DAO.emailnotGetted(email)){
                DAO.setUtente(email,nome,cognome,password);
                System.out.println("Utente registrato");
            }else{
                System.out.println("L'email risulta associata ad un altro account");
            }
        }*/


    }


}