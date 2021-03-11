/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.answer;

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
public class AnswerDAO implements Serializable {

    public ArrayList<AnswerDTO> getAnswer(int questID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<AnswerDTO> list = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                    String sql = "select idAnswer, content, isCorrect, questionID "
                            + "from Answer "
                            + "where questionID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, questID);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String content = rs.getString(2);
                    int isCorrect = rs.getInt(3);
                    int questionID = rs.getInt(4);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    AnswerDTO dto = new AnswerDTO(id, content, isCorrect, questionID);
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

    public int createAnswer(String content, int isCorrect, int questID) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "insert into Answer(content, isCorrect, questionID) "
                        + "values(?,?,?) ";
                pst = con.prepareStatement(sql);
                pst.setString(1, content);
                pst.setInt(2, isCorrect);
                pst.setInt(3, questID);
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

    public void updateAnswer(int id, String content, int isCorrect) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "update Answer "
                        + "set content = ?, isCorrect = ? "
                        + "where idAnswer = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, content);
                pst.setInt(2, isCorrect);
                pst.setInt(3, id);
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

}
