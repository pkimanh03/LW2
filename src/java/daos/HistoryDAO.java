/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import conn.DBConnection;
import dtos.HistoryDTO;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;

/**
 *
 * @author pkimanh03
 */
public class HistoryDAO implements Serializable {
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private final int PAGE_SIZE = 5;
    
    private void closeConnection() throws SQLException {
        if (rs != null) rs.close();
        if (stm != null) stm.close();
        if (conn != null) conn.close();
    }
    
    public boolean saveHistory(HistoryDTO dto) throws SQLException, NamingException {
        boolean result = false;
        try {
            conn = DBConnection.getDBConnection();
            String sql = "INSERT INTO History(student, questions, q_subject, created_at, result) VALUES (?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
            stm.setString(1, dto.getStudent());
            stm.setString(2, dto.getQuestions());
            stm.setInt(3, dto.getSubjectId());
            stm.setLong(4, dto.getCreatedAt());
            stm.setFloat(5, dto.getResult());
            result = stm.executeUpdate() > 0;
        } finally {
        closeConnection();
    }
        return result;
    }
    
    public List<HistoryDTO> getHistories(String student, int subjectId, int index) throws SQLException, NamingException {
        List<HistoryDTO> result = new ArrayList();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT id, questions, result, created_at FROM History "
                    + "WHERE student = ? AND q_subject = ? "
                    + "ORDER BY created_at DESC OFFSET ? ROW FETCH NEXT ? ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setString(1, student);
            stm.setInt(2, subjectId);
            stm.setInt(3, (index - 1) * PAGE_SIZE);
            stm.setInt(4, PAGE_SIZE);
            
            rs = stm.executeQuery();
            while (rs.next()) {
                HistoryDTO dto = new HistoryDTO();
                dto.setId(rs.getInt("id"));
                dto.setStudent(rs.getString("questions"));
                dto.setResult(rs.getFloat("result"));
                dto.setCreatedAt(rs.getLong("created_at"));
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public int getTotalHistories(String student, int subjectId) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT COUNT(id) as total FROM History "
                    + "WHERE student = ? AND q_subject = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, student);
            stm.setInt(2, subjectId);
            
            rs = stm.executeQuery();
            if (rs.next()) {
                int total = rs.getInt("total");
                if (total % PAGE_SIZE > 0) {
                    result = (total / PAGE_SIZE) + 1;
                } else {
                    result = total / PAGE_SIZE;
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
