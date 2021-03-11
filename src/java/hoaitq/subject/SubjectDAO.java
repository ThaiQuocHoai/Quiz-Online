/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.subject;

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
public class SubjectDAO implements Serializable {

    public ArrayList<SubjectDTO> getSubject() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<SubjectDTO> list = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select subjectID, subjectName, numQues, numTime "
                        + "from Subject ";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int ques = rs.getInt(3);
                    int time = rs.getInt(4);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    SubjectDTO dto = new SubjectDTO(id, name, ques, time);
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

    public SubjectDTO getIdSubject(String subName) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select subjectID, subjectName, numQues, numTime "
                        + "from Subject "
                        + "where subjectName = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, subName);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int ques = rs.getInt(3);
                    int time = rs.getInt(4);
                    SubjectDTO dto = new SubjectDTO(id, name, ques, time);
                    return dto;
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

        return null;
    }

    public SubjectDTO getSubjectPaging(int index, int status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select distinct s.subjectID, subjectName, numQues, numTime "
                        + "from Subject s join Question q on s.subjectID = q.subjectID "
                        + "where q.status = ? "
                        + "order by subjectID "
                        + "offset ? rows "
                        + "fetch next 1 rows only ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, status);
                pst.setInt(2, index);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int ques = rs.getInt(3);
                    int time = rs.getInt(4);
                    SubjectDTO dto = new SubjectDTO(id, name, ques, time);
                    return dto;
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

        return null;
    }

    public SubjectDTO getSubjectPagingbyName(int index, String content) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select distinct s.subjectID, subjectName, numQues, numTime "
                        + "from Subject s join Question q on s.subjectID = q.subjectID "
                        + "where q.content like ? "
                        + "order by subjectID "
                        + "offset ? rows "
                        + "fetch next 1 rows only ";
                pst = con.prepareStatement(sql);
                pst.setString(1, "%" + content + "%");
                pst.setInt(2, index);
                rs = pst.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    String name = rs.getString(2);
                    int ques = rs.getInt(3);
                    int time = rs.getInt(4);
                    SubjectDTO dto = new SubjectDTO(id, name, ques, time);
                    return dto;
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

        return null;
    }

    public int getNumques(int id) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        int count = 0;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select numQues "
                        + "from Subject "
                        + "where subjectID = ? ";
                pst = con.prepareStatement(sql);
                pst.setInt(1, id);
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

    public ArrayList<String> getSubName() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        ArrayList<String> list = null;
        try {
            con = DBHelpers.getConnection();
            if (con != null) {
                String sql = "select distinct s.subjectName "
                        + "from Subject s join History h on s.subjectID = h.subjectID "
                        + "where s.subjectID = h.subjectID ";
                pst = con.prepareStatement(sql);
                rs = pst.executeQuery();
                while (rs.next()) {
                    String subjectName = rs.getString(1);
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(subjectName);
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
