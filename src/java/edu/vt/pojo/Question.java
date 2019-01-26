
/*
 * Created by Bengi Sevil on 2018.10.23  * 
 * Copyright Â© 2018 Bengi Sevil. All rights reserved. * 
 */

package edu.vt.pojo;

// This class provides a Plain Old Java Object (POJO) representing a Survey question

import javax.json.JsonArray;
import org.primefaces.json.JSONArray;

public class Question {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    Integer questionNumber;
    String questionName;
    String questionAnswer;
    
    public Question() {
    }

    public Question(Integer questionNumber, String questionName, String questionAnswer) {
     
        this.questionNumber = questionNumber;
        this.questionName = questionName;
        this.questionAnswer = questionAnswer;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionAnswer() {
        return questionAnswer;
    }

    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    

}

