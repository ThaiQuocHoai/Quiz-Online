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
import hoaitq.subject.SubjectDTO;
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
@WebServlet(name = "SearchSubServlet", urlPatterns = {"/SearchSubServlet"})
public class SearchSubServlet extends HttpServlet {

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
        String url = ADMIN_PAGE;
        HttpSession session = request.getSession();
        String sub = "";
        if (request.getParameter("dropSub") == null) {
            sub = (String) session.getAttribute("SUBSELECT");
            if (sub == null) {
                sub = "SWE102";
                session.setAttribute("SUBSELECT", sub);
            }
        } else {
            sub = request.getParameter("dropSub");
            session.setAttribute("SUBSELECT", sub);
        }
        int index = 1;
        if (request.getParameter("index") == null) {
            index = 1;
        } else {
            index = Integer.parseInt(request.getParameter("index"));
        }
        SubjectDAO subDAO = new SubjectDAO();
        QuestionDAO questionDAO = new QuestionDAO();
        AnswerDAO answerDAO = new AnswerDAO();
        ArrayList<QuestionDTO> listQuestion = new ArrayList<>();
        ArrayList<AnswerDTO> listAnswer = new ArrayList<>();
        Map<QuestionDTO, ArrayList<AnswerDTO>> map = new HashMap<>();
        TreeMap<QuestionDTO, ArrayList<AnswerDTO>> sorted = null;

        try {
            SubjectDTO subDTO = subDAO.getIdSubject(sub);
            String sql = "select questionID, content, status, createDate, subjectID "
                    + "from Question "
                    + "where subjectID = " + subDTO.getId()
                    + " order by content asc "
                    + " offset " + (index - 1) * 20 + " rows "
                    + " fetch next 20 rows only ";
            listQuestion = questionDAO.getQuestion(sql);
            if (listQuestion == null) {

            } else {
                for (QuestionDTO ques : listQuestion) {
                    listAnswer = answerDAO.getAnswer(ques.getId());
                    map.put(ques, listAnswer);
                }
                sorted = new TreeMap<>(map);
            }
            int count = 0;
            count = questionDAO.countQuestion(subDTO.getId());

            int endPage = count / 20;
            if (count % 20 != 0) {
                endPage++;
            }
            String indexS = String.valueOf(index);
            session.setAttribute("INDEX", indexS);
            session.setAttribute("ENDPAGE", endPage);
            session.setAttribute("SUBSELECT", sub);
            session.setAttribute("MAPSUB", sorted);
            session.setAttribute("SEARCHSUB", "asdasdasdaasdasdasdasdasd");
            session.setAttribute("SEARCHNAME", "");
            session.setAttribute("SEARCHSTATUS", "");
            session.setAttribute("SEARCHVALUE", "");
            session.setAttribute("SELECTSTA", "");
        } catch (SQLException ex) {
            log("AdminServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("AdminServlet_NamingException: " + ex.getMessage());
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
