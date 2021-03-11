/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.servlet;

import hoaitq.answer.AnswerDAO;
import hoaitq.answer.AnswerDTO;
import hoaitq.invalidate.Invalidate;
import hoaitq.question.QuestionDAO;
import hoaitq.subject.SubjectDAO;
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
@WebServlet(name = "SaveServlet", urlPatterns = {"/SaveServlet"})
public class SaveServlet extends HttpServlet {
    
    private final String UPDATE_PAGE = "update.jsp";
    private final String ADMIN = "SearchSubServlet";

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
        String url = UPDATE_PAGE;
        HttpSession session = request.getSession();
        String correct="";
        
        String[] answer = request.getParameterValues("txtAnswer");
        String[] answerRight = request.getParameterValues("rdoAnswer");
        
        String sub = request.getParameter("dropSub");
        String status = request.getParameter("dropStatus");
        int active =0;
        if(status.equals("Active")){
            active = 1;
        }
        String quesID = (String) session.getAttribute("QUESID");
        String quesName = request.getParameter("txtQuestion");
        
        try {
            int flag = 0;
            if(answerRight != null){
                correct = answerRight[0];
            } else {
                request.setAttribute("UPDATEERROR", "Please choose correct answer");
                flag = 1;
            }
            if(!Invalidate.checkAnswer(answer[0], answer[1], answer[2], answer[3]) || quesName.isEmpty()){
                request.setAttribute("UPDATEERROR", "Please fill all fields");
                flag = 1;
            } else {
                String error = Invalidate.checkDupAnswer(answer[0], answer[1], answer[2], answer[3]);
                if (!error.isEmpty()) {
                    request.setAttribute("UPDATEERROR", error);
                    flag = 1;
                }
            }
            
            if(flag == 0){
                AnswerDAO ansDAO = new AnswerDAO();
                QuestionDAO quesDAO = new QuestionDAO();
                SubjectDAO subDAO = new SubjectDAO();
                
                quesDAO.updateQuestion(Integer.parseInt(quesID), quesName, active, subDAO.getIdSubject(sub).getId());
                ArrayList<AnswerDTO> listAns = (ArrayList<AnswerDTO>) session.getAttribute("ANSWERLIST");
                for(int i=0; i<listAns.size() ; i++){
                    if(correct.equals(answer[i])){
                       ansDAO.updateAnswer(listAns.get(i).getId(), answer[i], 1); 
                    } else{
                        ansDAO.updateAnswer(listAns.get(i).getId(), answer[i], 0);
                    }
                    
                }
                url =ADMIN;
            }
            
            
        } catch(SQLException ex){
            log("SaveServlet_SQLException: "+ex.getMessage());
        } catch(NamingException ex){
            log("SaveServlet_NamingException: "+ex.getMessage());
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
