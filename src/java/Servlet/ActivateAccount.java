/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Bean.CustomerMB;
import Controller.CustomerSB;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ileven
 */
@WebServlet(name = "ActivateAccount", urlPatterns = {"/ActivateAccount"})
public class ActivateAccount extends HttpServlet implements Serializable{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @EJB
    private CustomerSB csb;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
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
        
        
        String email = request.getParameter("key1");
        String token = request.getParameter("key2");
//        Connection con = MyConnection.getConnection();
//        try {
//            PreparedStatement ps = con.prepareStatement("SELECT email, token FROM customers WHERE email=? AND token=?");
//            ps.setString(1, email);
//            ps.setString(2, token);
//            ResultSet rs = ps.executeQuery();
//            if(rs.next()){
//                PreparedStatement pst = con.prepareStatement("UPDATE customers SET enabled='1' WHERE email=?");
//                pst.setString(1, email);
//                int i = pst.executeUpdate();
//                if(i==1){
//                    response.sendRedirect("http://localhost:8080/Ecommerce-1773019/");
//                }else{
//                    response.sendRedirect("google.com");
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
        if(csb.enableCustomer(email, token)){
            response.sendRedirect("http://localhost:8080/Ecommerce-1773019/faces/accountActivated.xhtml");
        }
        
        System.out.println(email);
        System.out.println(token);
    
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
