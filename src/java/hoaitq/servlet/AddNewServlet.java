/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.answer.AnswerDAO;
import hoaitq.invalidate.Invalidate;
import hoaitq.question.QuestionDAO;
import hoaitq.subject.SubjectDAO;
import hoaitq.subject.SubjectDTO;
import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
@WebServlet(name = "AddNewServlet", urlPatterns = {"/AddNewServlet"})
public class AddNewServlet extends HttpServlet {

    private final String ADMIN_PAGE = "SearchSubServlet";
    private final String ADD_PAGE = "add.jsp";

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
        String url = ADD_PAGE;
        String sub = request.getParameter("dropSub");
        if (sub.isEmpty()) {
            sub = "SWE102";
        }
        String question = request.getParameter("txtQuestion");
        String right = request.getParameter("rdoAnswer");
        String correct = "";
        String answer1 = request.getParameter("txtAnswer1");
        String answer2 = request.getParameter("txtAnswer2");
        String answer3 = request.getParameter("txtAnswer3");
        String answer4 = request.getParameter("txtAnswer4");
        String[] answer = {answer1, answer2, answer3, answer4};
        HttpSession session = request.getSession();

        try {
            int flag = 0;
            if (right == null) {
                request.setAttribute("ANSWERERROR", "Please choose correct answer");
                flag = 1;
            } else {
                if(right.equals("1")){
                    correct = answer1;
                } else if(right.equals("2")){
                    correct = answer2;
                } else if(right.equals("3")){
                    correct = answer3;
                } else{
                    correct = answer4;
                }
            }

            if (!Invalidate.checkAnswer(answer1, answer2, answer3, answer4) || question.isEmpty()) {
                request.setAttribute("ANSWERERROR", "Please fill all fields");
                flag = 1;
            } else {
                String error = Invalidate.checkDupAnswer(answer1, answer2, answer3, answer4);
                if (!error.isEmpty()) {
                    request.setAttribute("ANSWERERROR", error);
                    flag = 1;
                }
            }
            if (flag == 0) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String now = dateFormat.format(new Date());
                SubjectDAO subDAO = new SubjectDAO();
                QuestionDAO quesDAO = new QuestionDAO();
                AnswerDAO ansDAO = new AnswerDAO();
                SubjectDTO subDTO = subDAO.getIdSubject(sub);
                int insertQ = quesDAO.createQuestion(question, now, subDTO.getId());
                if(insertQ > 0){
                    int questionID = quesDAO.maxID();
                    for (String s : answer) {
                        if(s.equals(correct)){
                            ansDAO.createAnswer(s, 1, questionID);
                        } else {
                            ansDAO.createAnswer(s, 0, questionID);
                        }
                    }
                    url=ADMIN_PAGE;
                }
                
            }
            session.setAttribute("SUBSELECT", sub);
        } catch (SQLException ex) {
            log("AddNewServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("AddNewServlet_NamingException: " + ex.getMessage());
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
