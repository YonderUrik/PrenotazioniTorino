package com.example.prenotazionitorinoweb;

import DAO.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

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
            if(sessione.equals(s.getId())) {
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
