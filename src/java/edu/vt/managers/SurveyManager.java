
/*
 * Created by Bengi Sevil on 2018.10.23  * 
 * Copyright © 2018 Bengi Sevil. All rights reserved. * 
 */
package edu.vt.managers;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import edu.vt.pojo.Question;
import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.globals.Methods;

import edu.vt.controllers.UserSurveyController;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.json.JSONArray;

@Named(value = "surveyManager")
@SessionScoped

public class SurveyManager implements Serializable {

    /*
    ==================
    Instance Variables
    ==================
     */
    // Inject (store) the UserFacade object reference into userFacade after it is instantiated at runtime
    @EJB
    private UserFacade userFacade;

    @Inject
    private UserSurveyController userSurveyController;

    // 'items' is a List containing the object references of Question objects
    private List<Question> items = null;

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    String questionName;
    String question1;
    String question2;
    Integer question3;
    Integer question4;
    Integer question5;
    Integer question6;


    /*
    ==================
    Constructor Method
    ==================
     */
    public SurveyManager() {
        question1 = "";
        question2 = "";
        question3 = 0;
        question4 = 0;
        question5 = 0;
        question6 = 0;

    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public UserFacade getUserFacade() {
        return userFacade;
    }

    public UserSurveyController getUserSurveyController() {
        return userSurveyController;
    }

    public List<Question> getItems() {
        return items;
    }

    public void setItems(List<Question> items) {
        this.items = items;
    }

    

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public Integer getQuestion3() {
        return question3;
    }

    public void setQuestion3(Integer question3) {
        this.question3 = question3;
    }

    public Integer getQuestion4() {
        return question4;
    }

    public void setQuestion4(Integer question4) {
        this.question4 = question4;
    }

    public Integer getQuestion5() {
        return question5;
    }

    public void setQuestion5(Integer question5) {
        this.question5 = question5;
    }

    public Integer getQuestion6() {
        return question6;
    }

    public void setQuestion6(Integer question6) {
        this.question6 = question6;
    }

   

    /*
    ================
    Instance Methods
    ================

    ***********************************
    Process the Submitted Survey
    ***********************************
     */
    public String processSurvey() {

        // Instantiate a list to hold all questions
        List<String> questions;
        questions = new ArrayList<>();
        questions.add("Have you or someone in your immediate family been affected by the crisis?");
        questions.add("Do you know someone outside your family that has been affected by the crisis?");
        questions.add("How well do you feel that your community is responding to the crisis?");
        questions.add("How knowledgeable do you feel about the steps your community has taken in response to the crisis?");
        questions.add("How involved are you in your community's response to the crisis?");
        questions.add("How thorough was the press coverage of the crisis in your community?");
        

        // Instantiate a new 'items' List to contain the object references of the Question objects
        items = new ArrayList<>();

        /*
        The UserSurvey Table in HealthSurveyDB database has the following attributes to set:
            Integer id (The DB Primary Key id value is generated and set at the time of UserSurvey object creation)
            Date    dateEntered
            String  survey (Stored as MEDIUMTEXT in HealthSurveyDB containing the JSON Array of all of the 10 Health Questions)
            User    userId
         */
        //--------------------------------------
        // Construct a new UserSurvey object
        //--------------------------------------
        // Create question objects with question number and name associated with the answers
        Question q1 = new Question(1, questions.get(0), question1);
        Question q2 = new Question(2, questions.get(1), question2);
        Question q3 = new Question(3, questions.get(2), question3.toString());
        Question q4 = new Question(4, questions.get(3), question4.toString());
        Question q5 = new Question(5, questions.get(4), question5.toString());
        Question q6 = new Question(6, questions.get(5), question6.toString());

        //Add all questions to items
        items.add(q1);
        items.add(q2);
        items.add(q3);
        items.add(q4);
        items.add(q5);
        items.add(q6);

        // Convert items into a JSONArray
        JSONArray jsonArr = new JSONArray(items);
        // Convert json array into a string to store as the survey object in the UserSurvey

        String survey = jsonArr.toString();
        

        // Set the survey attribute value
        userSurveyController.prepareCreate();
        
         //-----------------------
        // Set id attribute value 
        //-----------------------
        /*
        The database Primary Key id value is generated and set at the time of UserQuestionnaire object creation.
         */
        //--------------------------------
        // Set dateEntered attribute value 
        //--------------------------------
        LocalDate localDate = java.time.LocalDate.now();
        Date currentDate = java.sql.Date.valueOf(localDate);

        userSurveyController.getSelected().setDateEntered(currentDate);
        
        userSurveyController.getSelected().setSurvey(survey);

        //---------------------------
        // Set userId attribute value 
        //---------------------------
        // Obtain the object reference of the signed-in user
        User signedInUser = (User) Methods.sessionMap().get("user");
        
        userSurveyController.getSelected().setUserId(signedInUser);

        //----------------------------------------------------------
        // Store the newly created UserSurvey in the database
        //----------------------------------------------------------
        userSurveyController.create();

        //-----------------------------------------------------------
        // Clear the survey content without displaying message
        //-----------------------------------------------------------
        clearSurvey(false);

        return "/index?faces-redirect=true";

    }

    /*
    ************************************************
    Clear All of the Selections in the Survey
    ************************************************
     */
    public String clearSurvey(Boolean displayMessage) {

        return "/userSurvey/TakeSurvey?faces-redirect=true";
    }
    
    /*
    *******************************************
    Pre-process the PDF File to be in Landscape 
    Orientation on Letter Size Paper
    *******************************************
     */
    public void preProcessPDF(Object document) {
        Document pdf = (Document) document;
        pdf.setPageSize(PageSize.LETTER.rotate());
        pdf.open();
    }
    
    

}
