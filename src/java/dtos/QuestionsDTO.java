/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author pkimanh03
 * id INT IDENTITY(1,1),
 * question_content VARCHAR(MAX) NOT NULL,
 * subject_id INT NOT NULL,
 * question_status BIT NOT NULL DEFAULT 1,
 * created_at INT NOT NULL,
 * created_by VARCHAR(255) NOT NULL,
 * updated_at INT NOT NULL,
 * updated_by VARCHAR(255) NOT NULL,
 */
public class QuestionsDTO implements Serializable {
    private int id;
    private String questionContent;
    private int subjectId;
    private boolean questionStatus;
    private int createdAt, updatedAt, createdBy, updatedBy;
    private List<AnswersDTO> answers;

    public QuestionsDTO(int id, String questionContent, int subjectId, boolean questionStatus, List<AnswersDTO> answers) {
        this.id = id;
        this.questionContent = questionContent;
        this.subjectId = subjectId;
        this.questionStatus = questionStatus;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public boolean isQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(boolean questionStatus) {
        this.questionStatus = questionStatus;
    }

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }

    public int getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(int updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public int getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(int updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<AnswersDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswersDTO> answers) {
        this.answers = answers;
    }
    
}
