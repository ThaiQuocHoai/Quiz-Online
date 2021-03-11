/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.answer.AnswerDAO;
import hoaitq.answer.AnswerDTO;
import hoaitq.question.QuestionDAO;
import hoaitq.question.QuestionDTO;
import hoaitq.subject.SubjectDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
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
@WebServlet(name = "SearchNameServlet", urlPatterns = {"/SearchNameServlet"})
public class SearchNameServlet extends HttpServlet {

    private final String ADMIN = "admin.jsp";

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
        int index = 1;
        Map<QuestionDTO, ArrayList<AnswerDTO>> map = new HashMap<>();
        TreeMap<QuestionDTO, ArrayList<AnswerDTO>> sorted = null;
        if (request.getParameter("index") == null) {
            String ind = (String) session.getAttribute("INDEX");
            if(ind != null){
                index = Integer.parseInt(ind);
                session.setAttribute("INDEX", String.valueOf(index));
            }
        } else {
            index = Integer.parseInt(request.getParameter("index"));
            session.setAttribute("INDEX", String.valueOf(index));
        }

        String searchValue = request.getParameter("txtSearch");
        if(searchValue == null){
            searchValue = (String) session.getAttribute("SEARCHVALUE");
        } else {
            session.setAttribute("SEARCHVALUE", searchValue);
        }

        try {
            SubjectDAO subDAO = new SubjectDAO();
            QuestionDAO quesDAO = new QuestionDAO();
            AnswerDAO ansDAO = new AnswerDAO();
            int endPage = quesDAO.countQuestionbyName(searchValue);
            if(index > endPage){
                index = 1;
            }
            String subjectName = subDAO.getSubjectPagingbyName(index-1, searchValue).getName();
            ArrayList<QuestionDTO> listQuest = quesDAO.getQuestionNoPagingbyName(subDAO.getSubjectPagingbyName(index-1, searchValue).getId(), searchValue);
            if (listQuest == null) {

            } else {
                for (QuestionDTO questionDTO : listQuest) {
                    ArrayList<AnswerDTO> listAns = ansDAO.getAnswer(questionDTO.getId());
                    map.put(questionDTO, listAns);
                }
                sorted = new TreeMap<>(map);
            }
            session.setAttribute("SUBSELECT", subjectName);
            session.setAttribute("ENDPAGE", endPage);
            session.setAttribute("MAPSUB", sorted);
            session.setAttribute("SEARCHSUB", "");
            session.setAttribute("SEARCHNAME", "asdfghjkl");
            session.setAttribute("SEARCHSTATUS", "");
            session.setAttribute("SELECTSTA", "");

        } catch (SQLException ex) {
            log("SearchNameServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchNameServlet_NamingException: " + ex.getMessage());
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
