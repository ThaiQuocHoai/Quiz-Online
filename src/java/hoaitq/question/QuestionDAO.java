/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.question;

import hoaitq.utilities.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.NamingException;

/**
 *
 * @author QH
 */
public class QuestionDAO implements Serializable {

    public ArrayList<QuestionDTO> getQuestion(String query) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuestionDTO> list = null;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = query;
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String content = rs.getString(2);
                    int status = rs.getInt(3);
                    String date = rs.getString(4);
                    int subId = rs.getInt(5);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    QuestionDTO dto = new QuestionDTO(id, content, status, date, subId);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return list;
    }

    public int countQuestion(int sub) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select COUNT(questionID) "
                        + "from Question "
                        + "where subjectID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, sub);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return count;
    }

    public int countQuestionActive(int sub) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select COUNT(questionID) "
                        + "from Question "
                        + "where subjectID = ? and status = 1 ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, sub);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return count;
    }

    public int maxID() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select questionID "
                        + "from Question "
                        + "where questionID >= ALL(Select questionID from Question) ";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return count;
    }

    public int createQuestion(String content, String createDate, int subID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "insert into Question(content, status, createDate,subjectID) "
                        + "values(?,1,?,?) ";
                pst = con.prepareStatement(sql);
                pst.setString(1, content);
                pst.setString(2, createDate);
                pst.setInt(3, subID);
                rs = pst.executeUpdate();
                if (rs > 0) {
                    return rs;
                }
            }

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return rs;
    }

    public int deleteQuestion(int questionID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "update Question "
                        + "set status = 0 "
                        + "where questionID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, questionID);
                rs = pst.executeUpdate();
                if (rs > 0) {
                    return rs;
                }
            }

        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return 0;
    }

    public int countQuestionbyStatus(int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "Select count(distinct subjectID) "
                        + "from Question "
                        + "where status = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, status);
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return count;
    }

    public ArrayList<QuestionDTO> getQuestionNoPaging(int subjectID, int Status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuestionDTO> list = null;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select questionID, content, status, createDate, subjectID "
                        + "from Question "
                        + "where subjectID = ? and status=? "
                        + " order by content asc ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, subjectID);
                pst.setInt(2, Status);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String content = rs.getString(2);
                    int status = rs.getInt(3);
                    String date = rs.getString(4);
                    int subId = rs.getInt(5);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    QuestionDTO dto = new QuestionDTO(id, content, status, date, subId);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public int countQuestionbyName(String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "Select count(distinct subjectID) "
                        + "from Question "
                        + "where content like ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + searchValue + "%");
                rs = pst.executeQuery();
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }

        return count;
    }

    public ArrayList<QuestionDTO> getQuestionNoPagingbyName(int subjectID, String searchValue) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuestionDTO> list = null;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select questionID, content, status, createDate, subjectID "
                        + "from Question "
                        + "where subjectID = ? and content like ? "
                        + " order by content asc ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, subjectID);
                pst.setString(2, "%" + searchValue + "%");
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String content = rs.getString(2);
                    int status = rs.getInt(3);
                    String date = rs.getString(4);
                    int subId = rs.getInt(5);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    QuestionDTO dto = new QuestionDTO(id, content, status, date, subId);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(dto);
                }
            }

        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return list;
    }

    public void updateQuestion(int id, String content, int Status, int subID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "update Question "
                        + "set content = ?, status =?, subjectID = ? "
                        + "where questionID = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, content);
                pst.setInt(2, Status);
                pst.setInt(3, subID);
                pst.setInt(4, id);
                rs = pst.executeUpdate();
            }
        } finally {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public ArrayList<QuestionDTO> getQuestionRand(int count, int id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<QuestionDTO> listQ = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select top (?) questionID, content, status, createDate, subjectID "
                        + "from Question "
                        + "where subjectID = ? "
                        + "order by newID() ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, count);
                pst.setInt(2, id);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int questionID = rs.getInt(1);
                    String content = rs.getString(2);
                    int status = rs.getInt(3);
                    String createDate = rs.getString(4);
                    int subID = rs.getInt(5);
                    QuestionDTO dto = new QuestionDTO(questionID, content, status, createDate, subID);
                    if (listQ == null) {
                        listQ = new ArrayList<>();
                    }
                    listQ.add(dto);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return listQ;
    }

    public String getCorrectAnswer(int id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String correct = "";
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select a.content "
                        + "from Question q join Answer a on q.questionID = a.questionID "
                        + "where q.questionID = ? and a.isCorrect = 1 ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);
                rs = pst.executeQuery();
                if(rs.next()){
                    correct = rs.getString(1);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return correct;
    }

}
