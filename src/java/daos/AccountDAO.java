/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import conn.DBConnection;
import dtos.AccountDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author pkimanh03
 */
public class AccountDAO implements Serializable {
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    
    private void closeConnection() throws SQLException {
        if (rs != null) rs.close();
        if (stm != null) stm.close();
        if (conn != null) conn.close();
    }
    
    public AccountDTO create(String email, String password, String fullname) throws SQLException, NamingException {
        AccountDTO result = null;
        try {
            String sql = "INSERT INTO Account(account_email, account_password, account_name) VALUES (?,?,?)";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setString(3, fullname);
            boolean created = stm.executeUpdate() > 0;
            if (created) {
                result = new AccountDTO(email, fullname, AccountDTO.STUDENT_ROLE);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public AccountDTO check(String email, String password) throws SQLException, NamingException {
        AccountDTO result = null;
        try {
            String sql = "SELECT account_role, account_name FROM Account WHERE account_email = ? AND account_password = ? AND account_status = ?";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, password);
            stm.setString(3, AccountDTO.NEW_STATUS);
            rs = stm.executeQuery();
            if (rs.next()) {
                String role = rs.getString("account_role");
                String fullname = rs.getString("account_name");
                result = new AccountDTO(email, fullname, role);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
