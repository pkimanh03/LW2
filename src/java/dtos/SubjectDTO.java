/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author pkimanh03
 */
public class SubjectDTO implements Serializable {
    private int id;
    private String subjectName;
    private int numberOfQuestion;
    private int timer;

    public SubjectDTO(int id, String subjectName, int numberOfQuestion, int timer) {
        this.id = id;
        this.subjectName = subjectName;
        this.numberOfQuestion = numberOfQuestion;
        this.timer = timer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
    
    
}
