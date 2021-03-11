/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.history;

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
public class HistoryDetailDAO implements Serializable {

    public void insertAnswer(int hisID, int quesID, String question, String correct) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "insert into History_Detail(hisID, quesID, question, correct_answer) "
                        + "values(?, ?, ?, ?) ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, hisID);
                pst.setInt(2, quesID);
                pst.setString(3, question);
                pst.setString(4, correct);
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

    public void updateUserAnser(String user, int id_his, int id_ques) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "update History_Detail "
                        + "set user_answer = ? "
                        + "where hisID = ? and quesID = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, user);
                pst.setInt(2, id_his);
                pst.setInt(3, id_ques);
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

    public String getAnswerChoice(int idHis, int idQues) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        String choice = "";
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select user_answer "
                        + "from History_Detail "
                        + "where quesID = ? and hisID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, idQues);
                pst.setInt(2, idHis);
                rs = pst.executeQuery();
                if (rs.next()) {
                    choice = rs.getString(1);
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

        return choice;
    }

    public ArrayList<HistoryDetailDTO> getHistoryDetail(int idHis) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<HistoryDetailDTO> list = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select hisID, quesID, question, correct_answer, user_answer "
                        + "from History_Detail "
                        + "where hisID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, idHis);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int hisID = rs.getInt(1);
                    int quesID = rs.getInt(2);
                    String question = rs.getString(3);
                    String correct_anwer = rs.getString(4);
                    String user_answer = rs.getString(5);
                    HistoryDetailDTO dto = new HistoryDetailDTO(hisID, quesID, question, correct_anwer, user_answer);
                    if(list == null){
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

}
