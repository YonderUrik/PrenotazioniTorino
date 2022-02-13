package com.example.prenotazionitorinoweb;


import DAO.DAO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet(name = "signupservlet", value = "/signup-servlet")
public class signupServlet extends HttpServlet {
    public void init() {
        DAO.registerDriver();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }


    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if(!nome.equals("") && !cognome.equals("") && !email.equals("") && !password.equals("")){
            if(DAO.emailnotGetted(email)){
                if(DAO.setUtente(email,nome,cognome,password)){
                    out.print("Utente registrato");
                }else{
                    out.print("Utente non registrato");
                }
            }else{
                out.print("Utente giá registrato");
            }
        }


    }


}

