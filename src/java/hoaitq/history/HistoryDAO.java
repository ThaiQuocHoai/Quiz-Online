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
public class HistoryDAO implements Serializable {

    public void insertHis(int subjectID, String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "insert into History(subjectID, email, score) "
                        + "values(?, ?, 0) ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, subjectID);
                pst.setString(2, email);
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

    public int maxId() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select id "
                        + "from History "
                        + "where id>= ALL(Select id from History) ";
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

    public ArrayList<HistoryDTO> getHistory() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<HistoryDTO> list = null;
        int count = 0;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select id, score, date, subjectID, email "
                        + "from History ";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    float score = rs.getFloat(2);
                    String date = rs.getString(3);
                    int subjectID = rs.getInt(4);
                    String email = rs.getString(5);
                    HistoryDTO dto = new HistoryDTO(id, score, date, subjectID, email);
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

    public void updateScore(int hisID, float score) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "update History "
                        + "set score = ? "
                        + "where id = ? ";
                pst = con.prepareStatement(sql);
                pst.setFloat(1, score);
                pst.setInt(2, hisID);
                pst.executeUpdate();
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

    public ArrayList<HistoryDTO> getHistoryPaging(int index, int idSub, String Email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<HistoryDTO> list = null;
        int count = 0;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select id, score, date, subjectID, email "
                        + "from History "
                        + "where subjectID = ? and email = ? "
                        + "order by id desc "
                        + "offset ?*2 rows "
                        + "fetch next 2 rows only ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, idSub);
                pst.setString(2, Email);
                pst.setInt(3, index);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    float score = rs.getFloat(2);
                    String date = rs.getString(3);
                    int subjectID = rs.getInt(4);
                    String email = rs.getString(5);
                    HistoryDTO dto = new HistoryDTO(id, score, date, subjectID, email);
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
    
    public int countHisPaging(int id, String email) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;

        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select count(id) "
                        + "from History "
                        + "where subjectID = ? and email = ? "; 
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);
                pst.setString(2, email);
                rs = pst.executeQuery();
                if(rs.next()){
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

}
