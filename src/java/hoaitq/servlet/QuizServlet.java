/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.answer.AnswerDAO;
import hoaitq.answer.AnswerDTO;
import hoaitq.history.HistoryDAO;
import hoaitq.history.HistoryDetailDAO;
import hoaitq.question.QuestionDAO;
import hoaitq.question.QuestionDTO;
import hoaitq.subject.SubjectDAO;
import hoaitq.subject.SubjectDTO;
import hoaitq.user.UserDTO;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
@WebServlet(name = "QuizServlet", urlPatterns = {"/QuizServlet"})
public class QuizServlet extends HttpServlet {

    private final String QUIZ = "quiz.jsp";

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
        String url = QUIZ;
        HttpSession session = request.getSession();
        String sub = request.getParameter("dropSub");
        if (sub == null) {
            sub = (String) session.getAttribute("SUBQUIZ");
        } else {
            session.setAttribute("SUBQUIZ", sub);
        }

        int ques = 1;
        if (request.getParameter("index") != null) {
            ques = Integer.parseInt(request.getParameter("index"));
        }

        try {
            QuestionDAO quesDAO = new QuestionDAO();
            AnswerDAO ansDAO = new AnswerDAO();
            SubjectDAO subDAO = new SubjectDAO();
            HistoryDAO hisDAO = new HistoryDAO();
            SubjectDTO subDTO = subDAO.getIdSubject(sub);
            HistoryDetailDAO hisdetailDAO = new HistoryDetailDAO();
            ArrayList<QuestionDTO> listQuestion = (ArrayList<QuestionDTO>) session.getAttribute("LISTQUESTION");
            if (listQuestion == null) {
                listQuestion = quesDAO.getQuestionRand(subDTO.getNumques(), subDTO.getId());
                session.setAttribute("LISTQUESTION", listQuestion);
            }
            ArrayList<AnswerDTO> listAns = new ArrayList<>();
            int idQues = listQuestion.get(ques - 1).getId();
            listAns = ansDAO.getAnswer(idQues);
            session.setAttribute("LISTANSWER", listAns);

            String check_his = (String) session.getAttribute("HISTORY");
            if (check_his == null) {
                UserDTO user = (UserDTO) session.getAttribute("USERINFO");

                hisDAO.insertHis(subDTO.getId(), user.getUsername());
                session.setAttribute("HISTORY", "adsfgh");
            }
            String check_his_detail = (String) session.getAttribute("HISTORY_DETAIL");
            if (check_his_detail == null) {
                int id_his = hisDAO.maxId();
                session.setAttribute("IDHIS", String.valueOf(id_his));

                for (QuestionDTO questionDTO : listQuestion) {
                    String correct = quesDAO.getCorrectAnswer(questionDTO.getId());
                    hisdetailDAO.insertAnswer(id_his, questionDTO.getId(), questionDTO.getContent(), correct);
                }

                session.setAttribute("HISTORY_DETAIL", "sdfsdfds");
            }
            String id_his = (String) session.getAttribute("IDHIS");
            String answer_user = hisdetailDAO.getAnswerChoice(Integer.parseInt(id_his), idQues);
            if (answer_user == null) {
                session.setAttribute("ANSWER_CHOOSE", "");
            } else {
                session.setAttribute("ANSWER_CHOOSE", answer_user);
            }
            String timeNow = (String) session.getAttribute("BEGINQUIZ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            if (timeNow == null) {
                timeNow = dateFormat.format(cal.getTime());
                session.setAttribute("BEGINQUIZ", timeNow);
            }

            String timeEnd = (String) session.getAttribute("ENDQUIZ");
            if (timeEnd == null) {
                cal.add(Calendar.MINUTE, subDTO.getNumtime());
                timeEnd = dateFormat.format(cal.getTime());
                session.setAttribute("ENDQUIZ", timeEnd);
            }
            Date end = dateFormat.parse(timeEnd);
            Date now = new Date();

            long time = end.getTime() - now.getTime();
            long second = (time / 1000 % 60) + 1;
            long min = time / (60 * 1000) % 60;
            long hour = time / (60 * 60 * 1000);

            session.setAttribute("HOUR", hour);
            session.setAttribute("MIN", min);
            session.setAttribute("SECOND", second);
            session.setAttribute("QUESTIONQUIZ", listQuestion.get(ques - 1));
            session.setAttribute("ENDPAGE", subDTO.getNumques());
            session.setAttribute("INDEX", ques);
            session.setAttribute("TIME", subDTO.getNumtime());
            session.setAttribute("SUBJECT", subDTO.getName());
        } catch (SQLException ex) {
            log("QuizServlet_SQLException: " + ex.getMessage());
        } catch (NamingException ex) {
            log("QuizServlet_NamingException: " + ex.getMessage());
        } catch (ParseException ex) {
            log("QuizServlet_ParseException: " + ex.getMessage());
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
