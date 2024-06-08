/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import connect.Connect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import objets.events.Controle;
import objets.events.ControleParcelle;
import objets.infrastructures.Parcelle;

/**
 *
 * @author Fy
 */
public class InsererControle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            try {
                String date = request.getParameter("dateControle");
                String heure = request.getParameter("heureControle");

                System.out.println("date "+date+" heure "+heure);
                
                Controle controle = new Controle(date, heure);
                System.out.println("construction controle OK");
                ControleParcelle cp = new ControleParcelle();

                ArrayList<Parcelle> lp = new Parcelle().select(new Connect().getConnectionPgsql(), true);
                System.out.println("select all Parcelle OK");
                ArrayList<ControleParcelle> lCp = new ArrayList<>();
                for (int i = 0; i < lp.size(); i++) {
                    Parcelle parcelle = lp.get(i);
                    String nbTiges = request.getParameter("nbTiges" + parcelle.getIdParcelle());
                    String nbOs = request.getParameter("nbOs" + parcelle.getIdParcelle());
                    String lgOs = request.getParameter("lgOs" + parcelle.getIdParcelle());
                    String couleur = request.getParameter("couleur" + parcelle.getIdParcelle());
                    
                    System.out.println("construct cp for parcelle "+parcelle.getDescription());
                    cp = new ControleParcelle(parcelle, nbTiges, nbOs, lgOs, couleur);
                    lCp.add(cp);
                }
                
                System.out.println("construction lControlleParcelle OK");

                controle.setlControleParcelles(lCp);

                int idControle = controle.insererWithLcontroleParcelles(null);

                Controle ctrl = Controle.getById(idControle, null);

                request.setAttribute("controleAnomalie", ctrl);

                RequestDispatcher dispat = getServletContext().getRequestDispatcher("/anomalieControle.jsp");

                dispat.forward(request, response);

            } catch (Exception e) {
                request.setAttribute("exception", e);

                RequestDispatcher dispat = getServletContext().getRequestDispatcher("/exception.jsp");

                dispat.forward(request, response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
