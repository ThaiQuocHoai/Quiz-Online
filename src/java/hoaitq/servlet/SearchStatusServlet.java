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
@WebServlet(name = "SearchStatusServlet", urlPatterns = {"/SearchStatusServlet"})
public class SearchStatusServlet extends HttpServlet {

    private final String ADMIN_PAGE = "admin.jsp";

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
        HttpSession session = request.getSession();
        String url = ADMIN_PAGE;
        int index = 1;
        Map<QuestionDTO, ArrayList<AnswerDTO>> map = new HashMap<>();
        TreeMap<QuestionDTO, ArrayList<AnswerDTO>> sorted = null;
        if (request.getParameter("index") == null) {
            String ind = (String) session.getAttribute("INDEX");
            if (ind != null) {
                index = Integer.parseInt(ind);
                session.setAttribute("INDEX", String.valueOf(index));
            }
        } else {
            index = Integer.parseInt(request.getParameter("index"));
            session.setAttribute("INDEX", String.valueOf(index));
        }
        int status = 1;
        if (request.getParameter("dropStatus") == null) {
            String Nstatus = (String) session.getAttribute("SELECTSTA");
            if (Nstatus.equals("1")) {
                status = 1;
            } else {
                status = 0;
            }
        } else {
            if (request.getParameter("dropStatus").equals("Active")) {
                status = 1;
            } else {
                status = 0;
            }
        }

        try {
            QuestionDAO quesDAO = new QuestionDAO();
            SubjectDAO subDAO = new SubjectDAO();
            AnswerDAO ansDAO = new AnswerDAO();
            int endPage = quesDAO.countQuestionbyStatus(status);
            if (index > endPage) {
                index = 1;
            }
            String subjectName = "";
            if (subDAO.getSubjectPaging(index - 1, status) != null) {
                subjectName = subDAO.getSubjectPaging(index - 1, status).getName();
                ArrayList<QuestionDTO> listQuestion = quesDAO.getQuestionNoPaging(subDAO.getSubjectPaging(index - 1, status).getId(), status);
                if (listQuestion == null) {

                } else {
                    for (QuestionDTO questionDTO : listQuestion) {
                        ArrayList<AnswerDTO> listAns = ansDAO.getAnswer(questionDTO.getId());
                        map.put(questionDTO, listAns);
                    }
                    sorted = new TreeMap<>(map);
                }
            }

            session.setAttribute("SUBSELECT", subjectName);
            session.setAttribute("ENDPAGE", endPage);
            session.setAttribute("MAPSUB", sorted);
            session.setAttribute("SEARCHSUB", "");
            session.setAttribute("SEARCHNAME", "");
            session.setAttribute("SEARCHVALUE", "");
            session.setAttribute("SELECTSTA", String.valueOf(status));

            session.setAttribute("SEARCHSTATUS", "asdfghjkl;sdfghjkl;sdfghjk");
        } catch (SQLException ex) {
            log("SearchStatusServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("SearchStatusServlet_NamingException: " + ex.getMessage());
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
