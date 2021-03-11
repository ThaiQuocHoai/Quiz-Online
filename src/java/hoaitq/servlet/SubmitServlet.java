/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.history.HistoryDAO;
import hoaitq.history.HistoryDTO;
import hoaitq.history.HistoryDetailDAO;
import hoaitq.history.HistoryDetailDTO;
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
@WebServlet(name = "SubmitServlet", urlPatterns = {"/SubmitServlet"})
public class SubmitServlet extends HttpServlet {

    private final String HISTORY = "SearchHistoryServlet";

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

        String url = HISTORY;
        HttpSession session = request.getSession();
        String sub = (String) session.getAttribute("SUBJECT");

        try {
            SubjectDAO subDAO = new SubjectDAO();
            HistoryDAO hisDAO = new HistoryDAO();
            HistoryDetailDAO hisdetailDAO = new HistoryDetailDAO();
            SubjectDTO subDTO = subDAO.getIdSubject(sub);
            ArrayList<HistoryDTO> hisDTO = hisDAO.getHistory();
            for (HistoryDTO his : hisDTO) {
                int correct = 0;
                ArrayList<HistoryDetailDTO> hisdetailDTO = hisdetailDAO.getHistoryDetail(his.getId());
                for (HistoryDetailDTO hisdetail : hisdetailDTO) {
                    if (hisdetail.getCorrect_answer().equals(hisdetail.getUser_answer())) {
                        correct++;
                    }
                }
                float point = (float) correct * 10 / (subDAO.getNumques(his.getSubjectID()));
                hisDAO.updateScore(his.getId(), point);

            }
            ArrayList<String> listSub = subDAO.getSubName();
            session.setAttribute("SUBCHOOSE", listSub.get(0));
            session.setAttribute("LISTSUB", listSub);
            session.setAttribute("SUBCHOOSE", sub);
            session.setAttribute("ANSWER_CHOOSE", null);
            session.setAttribute("BEGINQUIZ", null);
            session.setAttribute("ENDQUIZ", null);
            session.setAttribute("LISTQUESTION", null);
            session.setAttribute("HISTORY", null);
            session.setAttribute("HISTORY_DETAIL", null);
            session.setAttribute("HOUR", null);
            session.setAttribute("MIN", null);
            session.setAttribute("SECOND", null);
            session.setAttribute("QUESTIONQUIZ", null);
            session.setAttribute("ENDPAGE", null);
            session.setAttribute("INDEX", null);
            session.setAttribute("TIME", null);
        } catch (SQLException ex) {
            log("QuizServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("QuizServlet_NamingException: " + ex.getMessage());
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
