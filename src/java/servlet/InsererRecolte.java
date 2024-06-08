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
import objets.events.Recolte;
import objets.events.RecolteParcelle;
import objets.infrastructures.Parcelle;

/**
 *
 * @author Yoan
 */
public class InsererRecolte extends HttpServlet {

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
                String date = request.getParameter("dateRecolte");
                String heure = request.getParameter("heureRecolte");

                System.out.println("date " + date + " heure " + heure);

                Recolte Recolte = new Recolte(date, heure);
                System.out.println("construction Recolte OK");
                RecolteParcelle rp = new RecolteParcelle();

                ArrayList<Parcelle> lp = new Parcelle().select(new Connect().getConnectionPgsql(), true);
                System.out.println("select all Parcelle OK");
                ArrayList<RecolteParcelle> lrp = new ArrayList<>();
                for (int i = 0; i < lp.size(); i++) {
                    Parcelle parcelle = lp.get(i);
                    int save = Integer.parseInt(request.getParameter("save" + parcelle.getIdParcelle()));
                    if (save == 1) {
                        String nbOsTotal = request.getParameter("nbOsTotal" + parcelle.getIdParcelle());
                        String poidstotal = request.getParameter("poidstotal" + parcelle.getIdParcelle());
                        String lgOs = request.getParameter("lgOs" + parcelle.getIdParcelle());

                        System.out.println("construct rp for parcelle " + parcelle.getDescription());
                        rp = new RecolteParcelle(parcelle, nbOsTotal, poidstotal, lgOs);
                        lrp.add(rp);
                    }
                }

                System.out.println("construction lRecolteParcelle OK");

                Recolte.setlRecolteParcelles(lrp);

                int idRecolte = Recolte.insererWithLRecolteParcelles(null);

                Recolte recolte = Recolte.getById(idRecolte, null);

                request.setAttribute("recolte", recolte);

                RequestDispatcher dispat = getServletContext().getRequestDispatcher("/detailRecolte.jsp");

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
