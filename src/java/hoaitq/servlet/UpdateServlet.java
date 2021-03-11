/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.answer.AnswerDAO;
import hoaitq.answer.AnswerDTO;
import hoaitq.question.QuestionDAO;
import hoaitq.subject.SubjectDAO;
import hoaitq.subject.SubjectDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author QH
 */
@WebServlet(name = "UpdateServlet", urlPatterns = {"/UpdateServlet"})
public class UpdateServlet extends HttpServlet {
    private final String ADMIN = "SearchSubServlet";
    private final String UPDATE_PAGE = "update.jsp";

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
        String url = ADMIN;
        HttpSession session = request.getSession();
        String button = request.getParameter("btAction");
        String questionID = request.getParameter("txtIDQues");
        String questionName = request.getParameter("txtNameQues");
        String subname = request.getParameter("txtSub");
        String index = request.getParameter("index");
        String status = request.getParameter("txtActive");
        QuestionDAO quesDAO = new QuestionDAO();
        AnswerDAO ansDAO = new AnswerDAO();
        SubjectDAO subDAO = new SubjectDAO();
        try {
            if(button.equals("Update")){
                url = UPDATE_PAGE;
                ArrayList<SubjectDTO> listSub = subDAO.getSubject();
                ArrayList<AnswerDTO> listAns = ansDAO.getAnswer(Integer.parseInt(questionID));
                session.setAttribute("QUESID", questionID);
                session.setAttribute("QUESNAME", questionName);
                session.setAttribute("ANSWERLIST", listAns);
                session.setAttribute("SUBSELECT", subname);
                session.setAttribute("SUB", listSub);
                session.setAttribute("ACTIVE", status);
            }else {
                int id = Integer.parseInt(questionID);;
                int result = quesDAO.deleteQuestion(id);
                if(result > 0){
                    request.setAttribute("DELETE", "Delete SUCCESS");
                }
            }
            session.setAttribute("INDEX", String.valueOf(index));
        } catch(SQLException ex){
            log("UppdateServlet_SQLException: "+ex.getMessage());
        } catch(NamingException ex){
            log("UppdateServlet_NamingException: "+ex.getMessage());
        }
        
        finally{
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
