/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pkimanh03
 */
public class HistoryDTO implements Serializable {
    private int id;
    private String student;
    private String questions;
    private int subjectId;
    private float result;
    private long createdAt;
    private String createdAtString;

    public HistoryDTO(String student, String questions, int subjectId, float result) {
        this.student = student;
        this.questions = questions;
        this.subjectId = subjectId;
        this.result = result;
        this.createdAt = System.currentTimeMillis();
    }

    public HistoryDTO() {
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public float getResult() {
        return result;
    }

    public void setResult(float result) {
        this.result = result;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getCreatedAtString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return this.createdAtString = simpleDateFormat.format(new Date(this.createdAt));
    }
    
    public void setCreatedAtString(String createdAtString) {
        this.createdAtString = createdAtString;
    }
}
