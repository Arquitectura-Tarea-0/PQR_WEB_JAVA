/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import Modelo.Request;
import Modelo.User;
import Provider.RequestProvider;
import Provider.UserProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;

/**
 *
 * @author Romario
 */
public class crearNuevaSolicitud extends HttpServlet {

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
        String asunto = request.getParameter("asuntoPQR");
        String tipo = request.getParameter("tipoPQR");
        String texto = request.getParameter("textPQR");
        String token = (String)request.getSession().getAttribute("token");
        Request solicitud = new Request();
        RequestProvider requestProvider = new RequestProvider();
        solicitud.setSubject(asunto);
        solicitud.setDescription(texto);
        
        switch(tipo){
            case "Petición":
                solicitud.setRequestType("claim");
                break;
            case "Queja":
                solicitud.setRequestType("complain");
                break;
            case "Reclamo":
                solicitud.setRequestType("request");
                break;
        }
        solicitud.setRequestState("settled");
        requestProvider.createRequest(solicitud, token);
        
        if(solicitud!=null){
            response.sendRedirect("listaPQRs.html");
        }else{
            response.sendRedirect("errorRegistro.html");
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
