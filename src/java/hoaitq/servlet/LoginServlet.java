/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.invalidate.Invalidate;
import hoaitq.user.UserDAO;
import hoaitq.user.UserDTO;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QH
 */
public class LoginServlet extends HttpServlet {

    private final String HOME_PAGE = "HomeServlet";
    private final String LOGIN_PAGE = "login.jsp";
    private final String ADMIN_PAGE = "AdminServlet";

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
        String url = LOGIN_PAGE;
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        HttpSession session = request.getSession();
        try {
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("INVALID", "Please filled all fields");
            } else {
                if (Invalidate.checkEmail(username)) {
                    UserDAO dao = new UserDAO();
                    String hex = Invalidate.toHexString(Invalidate.getSHA(password));
                    UserDTO dto = dao.checkLogin(username, hex);
                    if (dto != null) {
                        session.setAttribute("USERINFO", dto);
                        if (dto.getRole() > 0) {
                            url = ADMIN_PAGE;
                        } else {
                            url = HOME_PAGE;
                        }
                    } else {
                        request.setAttribute("INVALID", "Invalid username or password");
                    }
                } else {
                    request.setAttribute("INVALID", "Username must be an email");
                }
            }
        } catch (SQLException ex) {
            log("LoginServlet_SQLExcepton: " + ex.getMessage());
        } catch (NamingException ex) {
            log("LoginServlet_NamingException: " + ex.getMessage());
        } catch (NoSuchAlgorithmException ex) {
            log("LoginServlet_NoSuchAlgorithmException: " + ex.getMessage());
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
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
