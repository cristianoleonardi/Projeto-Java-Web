package br.com.monitoranuvem.view;

import br.com.monitoranuvem.controller.SendAlertsControl;
import br.com.monitoranuvem.model.SendAlerts;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Cristiano
 */
public class sendAlertView extends HttpServlet {

    private ServletContext context;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.context = config.getServletContext();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.lang.ClassNotFoundException
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException, ParseException {

        //Montar string XML retorno
        StringBuilder sb = new StringBuilder();

        SendAlertsControl sac = new SendAlertsControl();
        ArrayList<SendAlerts> listaSendAlerts = sac.listaSendAlerts();

        boolean sendAlertsAdded = false;
        for (SendAlerts sendAlerts : listaSendAlerts) {
            sendAlertsAdded = true;
            sb.append("<alert>");
            sb.append("<name>").append(sendAlerts.getAlerts().getNameAlerts()).append("</name>");
            sb.append("<status>").append(sendAlerts.getAlerts().getStatusProvider()).append("</status>");
            sb.append("<provider>").append(sendAlerts.getAlerts().getProv().getNome()).append("</provider>");
            sb.append("</alert>");
        }

        if (sendAlertsAdded) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            response.getWriter().write("<sendAlerts>" + sb.toString() + "</sendAlerts>");
        } else {
            //Nothing to show
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            throw new ServletException("ClassNotFoundException", ex);
        } catch (SQLException ex) {
            throw new ServletException("SQLException", ex);
        } catch (ParseException ex) {
            throw new ServletException("ParseException", ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            throw new ServletException("ClassNotFoundException", ex);
        } catch (SQLException ex) {
            throw new ServletException("SQLException", ex);
        } catch (ParseException ex) {
            throw new ServletException("ParseException", ex);
        }
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