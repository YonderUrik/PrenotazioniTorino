package com.example.prenotazionitorinoweb;

import DAO.DAO;
import DAO.associazioni;
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

    @WebServlet(name = "associazioneservlet", value = "/associazione-servlet")
    public class AssociazioneServlet extends HttpServlet {
        public void init() {
            DAO.registerDriver();
        }
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            String sessione = request.getParameter("sessione");
            String post=request.getParameter("post");
            HttpSession s = request.getSession();
            if(!s.isNew() && s.getAttribute("ruolo").equals("admin")) {
                if(Objects.equals(post, "aggiungi")){
                    int corso=Integer.parseInt(request.getParameter("corso"));
                    int docente=Integer.parseInt(request.getParameter("docente"));
                    int disp= Integer.parseInt(request.getParameter("disp"));
                    if(DAO.setAssociazione(corso,docente,disp)){
                        out.print("true");
                    }else{
                        out.print("false");
                    }
                }else if(Objects.equals(post, "elimina")){
                    int docente=Integer.parseInt(request.getParameter("docente"));
                    int corso=Integer.parseInt(request.getParameter("corso"));
                    DAO.deleteAssociazione(docente,corso);
                }


            }else{
                s.invalidate();
                out.print("sessione scaduta");
            }
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            response.setContentType("application/json");
            PrintWriter out=response.getWriter();
            ArrayList<associazioni> associazioni= DAO.getAssociazioni();
            JsonArray allAssociazioni=new JsonArray();
            for (associazioni value : associazioni){
                int corso = value.getIdCorso();
                int docente = value.getIdDocente();
                JsonObject associazione = new JsonObject();
                associazione.addProperty("corso", corso);
                associazione.addProperty("docente", docente);
                allAssociazioni.add(associazione);
            }
            out.print(allAssociazioni);
        }





    }
