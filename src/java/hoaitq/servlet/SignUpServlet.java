/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.invalidate.Invalidate;
import hoaitq.user.UserDAO;
import hoaitq.user.UserErrorDTO;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author QH
 */
@WebServlet(name = "SignUpServlet", urlPatterns = {"/SignUpServlet"})
public class SignUpServlet extends HttpServlet {

    private final String SIGN_UP = "signup.jsp";
    private final String LOGIN_PAGE = "otp.jsp";

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
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String confirm = request.getParameter("txtConfirm");
        String fullname = request.getParameter("txtFullname");

        String url = SIGN_UP;

        try {
            boolean flag = false;

            UserErrorDTO error = new UserErrorDTO();
            if (username.isEmpty()) {
                error.setUsernameError("Username cannot be empty");
                flag = true;
            } else {
                if (!Invalidate.checkEmail(username)) {
                    error.setUsernameError("Username must be an email");
                    flag = true;
                }
            }

            if (password.isEmpty()) {
                error.setPasswordError("Password cannot be empty");
                flag = true;
            } else {
                if (confirm.isEmpty()) {
                    error.setConfirmError("Confirm cannot be empty");
                    flag = true;
                } else {
                    if (!Invalidate.checkConfirmNotMatch(confirm, password)) {
                        error.setConfirmError("Confirm not match with your password");
                        flag = true;
                    }
                }
            }

            if (fullname.isEmpty()) {
                error.setFullnameError("Fullname cannot be empty");
                flag = true;
            } else {
                if (!Invalidate.checkFullname(fullname)) {
                    error.setFullnameError("Fullname must be a-z and not contain speccial characters");
                    flag = true;
                }
            }
            if (flag) {
                request.setAttribute("SIGNUPERROR", error);
            } else {
                byte[] hex = Invalidate.getSHA(password);
                String hexString = Invalidate.toHexString(hex);
                UserDAO dao = new UserDAO();
                int result = dao.signUp(username, hexString, fullname);
                if (result > 0) {
                    request.setAttribute("SIGNUP", "Sign up Successfully");
                    url = LOGIN_PAGE;
                }
            }

        } catch (NoSuchAlgorithmException ex) {
            log("SignUpServlet_NoSuchAlgorithmException: " + ex.getMessage());
        } catch (SQLException ex) {
            if (ex.getMessage().contains("duplicate")) {
                UserErrorDTO error = new UserErrorDTO();
                error.setUsernameError("Username has existed, please try another name");
                request.setAttribute("SIGNUPERROR", error);
            } else {
                log("SignUpServlet_SQLException: " + ex.getMessage());
            }
        } catch (NamingException ex) {
            log("SignUpServlet_NamingException: " + ex.getMessage());
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
