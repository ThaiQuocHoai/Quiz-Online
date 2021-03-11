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
import hoaitq.user.UserDTO;
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
@WebServlet(name = "SearchHistoryServlet", urlPatterns = {"/SearchHistoryServlet"})
public class SearchHistoryServlet extends HttpServlet {

    private final String HISTORY = "history.jsp";

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
        String url = HISTORY;
        SubjectDAO subDAO = new SubjectDAO();
        String sub = request.getParameter("dropSubject");
        String check = (String) session.getAttribute("SUBCHOOSE");
        String index = request.getParameter("index");
        int ques = 1;
        if (index == null) {
            index = (String) session.getAttribute("INDEX1");
            if (index != null) {
                ques = Integer.parseInt(index);
            } else {
                ques = 1;
            }
            session.setAttribute("INDEX1", String.valueOf(ques));
        } else {
            ques = Integer.parseInt(index);
            session.setAttribute("INDEX1", String.valueOf(ques));
        }
        if (check != null && sub != null) {
            if (!check.equals(sub)) {
                ques = 1;
                session.setAttribute("INDEX1", String.valueOf(ques));
            }
        }
        Map<HistoryDTO, ArrayList<HistoryDetailDTO>> map = null;
        TreeMap<HistoryDTO, ArrayList<HistoryDetailDTO>> sorted = null;
        UserDTO userDTO = (UserDTO) session.getAttribute("USERINFO");
        try {
            if (sub == null) {
                sub = (String) session.getAttribute("SUBCHOOSE");
                if (sub == null) {
                    sub = subDAO.getSubName().get(0);
                    session.setAttribute("SUBCHOOSE", sub);
                }
            } else {
                session.setAttribute("SUBCHOOSE", sub);
            }
            HistoryDAO hisDAO = new HistoryDAO();
            HistoryDetailDAO hisdetailDAO = new HistoryDetailDAO();
            SubjectDTO subDTO = subDAO.getIdSubject(sub);
            ArrayList<HistoryDTO> listHis = hisDAO.getHistoryPaging(ques - 1, subDTO.getId(), userDTO.getUsername());
            int count = hisDAO.countHisPaging(subDTO.getId(), userDTO.getUsername());
            for (HistoryDTO hisDTO : listHis) {
                if (map == null) {
                    map = new HashMap<>();
                }
                ArrayList<HistoryDetailDTO> listHisDetail = hisdetailDAO.getHistoryDetail(hisDTO.getId());
                map.put(hisDTO, listHisDetail);
            }
            sorted = new TreeMap<>(map);
            int endPage = count / 2;
            if (count % 2 != 0) {
                endPage++;
            }
            ArrayList<String> listSub = subDAO.getSubName();
            session.setAttribute("LISTSUB", listSub);
            session.setAttribute("HISMAP", sorted);
            session.setAttribute("ENDPAGE", endPage);
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
