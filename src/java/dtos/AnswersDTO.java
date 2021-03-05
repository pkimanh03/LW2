/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author pkimanh03
 * id INT IDENTITY(1,1),
    answer_content VARCHAR(MAX) NOT NULL,
    question_id INT NOT NULL,
    is_correct BIT NOT NULL DEFAULT 0,
    answer_status BIT NOT NULL DEFAULT 1,
    created_at INT NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at INT NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
 */
public class AnswersDTO {
    private int id;
    private String answerContent;
    private int questionId;
    private boolean isCorrect;
    private int createdAt, updatedAt;
    private int createdBy, updatedBy;

    public AnswersDTO(int id, String answerContent, boolean isCorrect) {
        this.id = id;
        this.answerContent = answerContent;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(boolean isCorrect) {
        this.isCorrect = isCorrect;
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
    
}
