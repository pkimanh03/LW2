/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daos;

import conn.DBConnection;
import dtos.AnswersDTO;
import dtos.QuestionsDTO;
import dtos.SubjectDTO;
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
public class QuestionDAO implements Serializable {

    private final int PAGE_SIZE = 5;
    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;

    private void closeConnection() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (conn != null) {
            conn.close();
        }
    }

    public List<SubjectDTO> getSubjectList() throws SQLException, NamingException {
        List<SubjectDTO> result = new ArrayList();
        try {
            String sql = "SELECT id, subject_name, number_of_question, timer FROM SUBJECTS";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String subjectName = rs.getString("subject_name");
                int numberOfQuestion = rs.getInt("number_of_question");
                int timer = rs.getInt("timer");
                SubjectDTO dto = new SubjectDTO(id, subjectName, numberOfQuestion, timer);
                result.add(dto);
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalQuestions() throws NamingException, SQLException {
        int result = 0;
        try {
            String sql = "SELECT COUNT(id) as total FROM Questions";
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("total");
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getQuestionAfterCreate(long createdAt, String actor) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBConnection.getDBConnection();
            String selectSql = "SELECT id FROM Questions WHERE created_at = ? AND created_by = ?";
            stm = conn.prepareStatement(selectSql);
            stm.setLong(1, createdAt);
            stm.setString(2, actor);
            rs = stm.executeQuery();
            if (rs.next()) {
                result = rs.getInt("id");
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    public long insertQuestion(String question, int subject, String actor) throws SQLException, NamingException {
        long createdAt = System.currentTimeMillis();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "INSERT INTO Questions(question_content, subject_id, created_at, created_by) VALUES (?,?,?,?) ";
            stm = conn.prepareStatement(sql);
            stm.setString(1, question);
            stm.setInt(2, subject);
            stm.setLong(3, createdAt);
            stm.setString(4, actor);
            createdAt = stm.executeUpdate() <= 0 ? 0 : createdAt;
        } finally {
            closeConnection();
        }
        return createdAt;
    }

    public boolean insertAnswers(int questionId, String actor, String answerContent, boolean isCorrect) throws SQLException, NamingException {
        boolean result = false;
        long createdAt = System.currentTimeMillis();

        try {
            conn = DBConnection.getDBConnection();
            String sql = "INSERT INTO Answers(answer_content, question_id, is_correct, created_at, created_by) VALUES (?,?,?,?,?)";
            stm = conn.prepareStatement(sql);
                stm.setString(1, answerContent);
                stm.setInt(2, questionId);
                stm.setBoolean(3, isCorrect);
                stm.setLong(4, createdAt);
                stm.setString(5, actor);
            result = stm.executeUpdate() > 0;

        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalPageBySubject(int subjectId) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT COUNT(id) as total "
                    + "FROM Questions q "
                    + "WHERE q.subject_id = ? AND q.question_status = 1";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subjectId);
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

    public List<QuestionsDTO> findQuestionBySubject(int subjectId, int index) throws SQLException, NamingException {
        List<QuestionsDTO> result = new ArrayList();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT q.id as questionId, q.question_content as question, q.subject_id as qSubject, q.question_status as qStatus "
                    + "FROM Questions q "
                    + "WHERE q.subject_id = ? AND q.question_status = 1 "
                    + "ORDER BY q.created_at DESC OFFSET ? ROW FETCH NEXT ? ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subjectId);
            stm.setInt(2, (index - 1) * PAGE_SIZE);
            stm.setInt(3, PAGE_SIZE);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("questionId");
                String question = rs.getString("question");
                int subject = rs.getInt("qSubject");
                boolean status = rs.getBoolean("qStatus");
                QuestionsDTO dto = new QuestionsDTO(id, question, subject, status, null);
                result.add(dto);
            }

            if (result.size() > 0) {
                for (QuestionsDTO q : result) {
                    String qSql = "SELECT id as answerId, answer_content as answer, is_correct as isCorrect FROM Answers WHERE question_id = ?";
                    stm = conn.prepareStatement(qSql);
                    stm.setInt(1, q.getId());
                    rs = stm.executeQuery();
                    List<AnswersDTO> answers = new ArrayList();
                    while (rs.next()) {
                        int id = rs.getInt("answerId");
                        String answer = rs.getString("answer");
                        boolean isCorrect = rs.getBoolean("isCorrect");
                        AnswersDTO dto = new AnswersDTO(id, answer, isCorrect);
                        answers.add(dto);
                    }
                    q.setAnswers(answers);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public int getTotalPageByKeyword(String keyword) throws SQLException, NamingException {
        int result = 0;
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT COUNT(id) as total "
                    + "FROM Questions q "
                    + "WHERE q.question_content LIKE ? AND q.question_status = 1 ";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
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

    public List<QuestionsDTO> findQuestionByKeyword(String keyword, int index) throws SQLException, NamingException {
        List<QuestionsDTO> result = new ArrayList();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT q.id as questionId, q.question_content as question, q.subject_id as qSubject, q.question_status as qStatus "
                    + "FROM Questions q "
                    + "WHERE q.question_content LIKE ? AND q.question_status = 1 "
                    + "ORDER BY q.created_at DESC OFFSET ? ROW FETCH NEXT ? ROWS ONLY";
            stm = conn.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            stm.setInt(2, (index - 1) * PAGE_SIZE);
            stm.setInt(3, PAGE_SIZE);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("questionId");
                String question = rs.getString("question");
                int subject = rs.getInt("qSubject");
                boolean status = rs.getBoolean("qStatus");
                QuestionsDTO dto = new QuestionsDTO(id, question, subject, status, null);
                result.add(dto);
            }

            if (result.size() > 0) {
                for (QuestionsDTO q : result) {
                    String qSql = "SELECT id as answerId, answer_content as answer, is_correct as isCorrect FROM Answers WHERE question_id = ?";
                    stm = conn.prepareStatement(qSql);
                    stm.setInt(1, q.getId());
                    rs = stm.executeQuery();
                    List<AnswersDTO> answers = new ArrayList();
                    while (rs.next()) {
                        int id = rs.getInt("answerId");
                        String answer = rs.getString("answer");
                        boolean isCorrect = rs.getBoolean("isCorrect");
                        AnswersDTO dto = new AnswersDTO(id, answer, isCorrect);
                        answers.add(dto);
                    }
                    q.setAnswers(answers);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public QuestionsDTO findQuestionById(int id) throws SQLException, NamingException {
        QuestionsDTO result = null;
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT q.id as questionId, q.question_content as question, q.subject_id as qSubject, q.question_status as qStatus "
                    + "FROM Questions q "
                    + "WHERE q.id = ? ";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, id);
            rs = stm.executeQuery();
            if (rs.next()) {
                String question = rs.getString("question");
                int subject = rs.getInt("qSubject");
                boolean status = rs.getBoolean("qStatus");
                result = new QuestionsDTO(id, question, subject, status, null);

                String qSql = "SELECT id as answerId, answer_content as answer, is_correct as isCorrect FROM Answers WHERE question_id = ?";
                stm = conn.prepareStatement(qSql);
                stm.setInt(1, id);
                rs = stm.executeQuery();
                List<AnswersDTO> answers = new ArrayList();
                while (rs.next()) {
                    int answerId = rs.getInt("answerId");
                    String answer = rs.getString("answer");
                    boolean isCorrect = rs.getBoolean("isCorrect");
                    AnswersDTO answerDto = new AnswersDTO(answerId, answer, isCorrect);
                    answers.add(answerDto);
                }
                result.setAnswers(answers);
            }
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<Integer> getAnswerIdsByQuestion(int questionId) throws SQLException, NamingException {
        List<Integer> result = new ArrayList();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT id FROM Answers WHERE question_id = ?";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, questionId);
            rs = stm.executeQuery();
            while (rs.next()) {
                result.add(rs.getInt("id"));
            }
        } finally {
            closeConnection();
        }
        return result;
    }

    public boolean updateQuestion(String question, int subject, String actor, int questionId) throws NamingException, SQLException, Exception {
        boolean result = false;
        long createdAt = System.currentTimeMillis();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "UPDATE Questions SET question_content = ?, subject_id = ?, updated_at = ?, updated_by = ? WHERE id = ?";
            stm = conn.prepareStatement(sql);
            stm.setString(1, question);
            stm.setInt(2, subject);
            stm.setInt(3, (int) createdAt);
            stm.setString(4, actor);
            stm.setInt(5, questionId);
            result = stm.executeUpdate() > 0;  
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean updateAnswer(String answer, boolean isCorrect, int answerId, String actor) throws SQLException, NamingException {
        boolean result = false;
        long createdAt = System.currentTimeMillis();
        try {
            conn = DBConnection.getDBConnection();
            String aSql = "UPDATE Answers SET answer_content = ?, is_correct = ?, updated_at = ?, updated_by = ? WHERE id = ?";
            stm = conn.prepareStatement(aSql);
            stm.setString(1, answer);
            stm.setBoolean(2, isCorrect);
            stm.setLong(3, createdAt);
            stm.setString(4, actor);
            stm.setInt(5, answerId);
            result = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public boolean delete(int id) throws SQLException, NamingException {
        boolean result = false;
        try {
            String sql = "UPDATE Questions SET question_status = 0 WHERE id = " + id;
            conn = DBConnection.getDBConnection();
            stm = conn.prepareStatement(sql);
            result = stm.executeUpdate() > 0;
        } finally {
            closeConnection();
        }
        return result;
    }
    
    public List<QuestionsDTO> findQuestionBySubjectForQuiz(int subjectId) throws SQLException, NamingException {
        List<QuestionsDTO> result = new ArrayList();
        try {
            conn = DBConnection.getDBConnection();
            String sql = "SELECT q.id as questionId, q.question_content as question, q.subject_id as qSubject, q.question_status as qStatus "
                    + "FROM Questions q "
                    + "WHERE q.subject_id = ? AND q.question_status = 1 ";
            stm = conn.prepareStatement(sql);
            stm.setInt(1, subjectId);
            rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("questionId");
                String question = rs.getString("question");
                int subject = rs.getInt("qSubject");
                boolean status = rs.getBoolean("qStatus");
                QuestionsDTO dto = new QuestionsDTO(id, question, subject, status, null);
                result.add(dto);
            }

            if (result.size() > 0) {
                for (QuestionsDTO q : result) {
                    String qSql = "SELECT id as answerId, answer_content as answer, is_correct as isCorrect FROM Answers WHERE question_id = ?";
                    stm = conn.prepareStatement(qSql);
                    stm.setInt(1, q.getId());
                    rs = stm.executeQuery();
                    List<AnswersDTO> answers = new ArrayList();
                    while (rs.next()) {
                        int id = rs.getInt("answerId");
                        String answer = rs.getString("answer");
                        boolean isCorrect = rs.getBoolean("isCorrect");
                        AnswersDTO dto = new AnswersDTO(id, answer, isCorrect);
                        answers.add(dto);
                    }
                    q.setAnswers(answers);
                }
            }
        } finally {
            closeConnection();
        }
        return result;
    }
}
