/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hoaitq.user;

import hoaitq.utilities.DBHelpers;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author QH
 */
public class UserDAO implements Serializable {

    public UserDTO checkLogin(String username, String password) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            con = DBHelpers.getConnection();
            if( con != null){
                String sql = "select email, [password], name, status, role "
                        + "from [User] "
                        + "where email =? and [password] = ? ";
                pst = con.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                rs = pst.executeQuery();
                if(rs.next()){
                    String email = rs.getString(1);
                    String pass = rs.getString(2);
                    String name = rs.getString(3);
                    String status = rs.getString(4);
                    int role = rs.getInt(5);
                    UserDTO dto = new UserDTO(email, pass, name, status, role);
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
    
        public int signUp(String username, String password, String fullname) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement pst = null;
        int rs = 0;
        try {
            con = DBHelpers.getConnection();
            if( con != null){
                String sql = "insert into [User](email, password, name, role, status) "
                        + "values(?,?,?,0,'new') ";
                pst = con.prepareStatement(sql);
                pst.setString(1, username);
                pst.setString(2, password);
                pst.setString(3, fullname);
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
        return rs;
    }

}
