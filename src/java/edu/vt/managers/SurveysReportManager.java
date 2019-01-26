
/*
 * Created by Bengi Sevil on 2018.10.33  * 
 * Copyright Â© 2018 Bengi Sevil. All rights reserved. * 
 */
package edu.vt.managers;

import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserSurvey;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserSurveyFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.json.JSONArray;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.PieChartModel;

@Named(value = "surveysReportManager")
@SessionScoped

public class SurveysReportManager implements Serializable {

    // Instance variables holding bar chart models for each question 
    private PieChartModel question1PieModel;
    private PieChartModel question2PieModel;
    private BarChartModel question3BarModel;
    private BarChartModel question4BarModel;
    private BarChartModel question5BarModel;
    private BarChartModel question6BarModel;

    // Statistics data
    private int numberOfUsers;
    private int numberOfTotalSurveys;
    private List<UserSurvey> surveys;
    private List<String> answers;
    private String answer;
    String survey;

    /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private UserFacade userFacade;
    @EJB
    private UserSurveyFacade userSurveyFacade;

    @PostConstruct
    public void init() {
        answers = new ArrayList<>();
        surveys = new ArrayList<>();
        createPieModel1();
        createPieModel2();
        createBarModel3();
        createBarModel4();
        createBarModel5();
        createBarModel6();
    }

    public PieChartModel getQuestion1PieModel() {
        return question1PieModel;
    }

    public void setQuestion1PieModel(PieChartModel question1PieModel) {
        this.question1PieModel = question1PieModel;
    }

    public PieChartModel getQuestion2PieModel() {
        return question2PieModel;
    }

    public void setQuestion2PieModel(PieChartModel question2PieModel) {
        this.question2PieModel = question2PieModel;
    }

    public BarChartModel getQuestion3BarModel() {
        return question3BarModel;
    }

    public void setQuestion3BarModel(BarChartModel question3BarModel) {
        this.question3BarModel = question3BarModel;
    }

    public BarChartModel getQuestion4BarModel() {
        return question4BarModel;
    }

    public void setQuestion4BarModel(BarChartModel question4BarModel) {
        this.question4BarModel = question4BarModel;
    }

    public BarChartModel getQuestion5BarModel() {
        return question5BarModel;
    }

    public void setQuestion5BarModel(BarChartModel question5BarModel) {
        this.question5BarModel = question5BarModel;
    }

    public BarChartModel getQuestion6BarModel() {
        return question6BarModel;
    }

    public void setQuestion6BarModel(BarChartModel question6BarModel) {
        this.question6BarModel = question6BarModel;
    }


    private void procesSurveys() {

    }

    public String generateSurveysReport() {
        return "/admin/GenerateReport?faces-redirect=true";
    }

    public int getNumberOfUsers() {
        List<User> users = userFacade.findAll();
        numberOfUsers = users.size();
        return numberOfUsers;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public int getNumberOfTotalSurveys() {
        surveys = userSurveyFacade.findAll();
        numberOfTotalSurveys = surveys.size();
        return numberOfTotalSurveys;
    }

    public void setNumberOfTotalSurveys(int numberOfTotalSurveys) {
        this.numberOfTotalSurveys = numberOfTotalSurveys;
    }

    /**
     * Bar chart model for question 1
     *
     * @return model
     */
    private PieChartModel initPieModel1() {
        // Question 1: Retrieve answer statistics for question 1 from all surveys

        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(0).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "Yes":
                    numOfAnswer1++;
                    break;
                case "No":
                    numOfAnswer2++;
                    break;
            }
        }

        // Creates new Pie Chart and populates it with the cumulative survey data.
        PieChartModel model = new PieChartModel();
 
        model.set("Yes", numOfAnswer1);
        model.set("No", numOfAnswer2);
        model.setLegendPosition("ne");
        model.setTitle("Question 1: Have you or someone in your immediate family been affected by the crisis?");
        model.setDiameter(400);

        return model;
    }

    /**
     * Constructs the bar chart properties for question 1
     */
    private void createPieModel1() {
        question1PieModel = initPieModel1();
        question1PieModel.setShowDataLabels(true);
    }

     /**
     * Bar chart model for question 1
     *
     * @return model
     */
    private PieChartModel initPieModel2() {
        // Question 2: Retrieve answer statistics for question 1 from all surveys

        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(1).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "Yes":
                    numOfAnswer1++;
                    break;
                case "No":
                    numOfAnswer2++;
                    break;
            }
        }
     
        // Creates new Pie Chart and populates it with the cumulative survey data.
        PieChartModel model = new PieChartModel();
        model.set("Yes", numOfAnswer1);
        model.set("No", numOfAnswer2);
        model.setLegendPosition("ne");
        model.setTitle("Question 2: Do you know someone outside your family that has been affected by the crisis?");
        model.setDiameter(400);

        return model;
    }

    /**
     * Constructs the bar chart properties for question 2
     */
    private void createPieModel2() {
        question2PieModel = initPieModel2();
        question2PieModel.setShowDataLabels(true);     
    }

    private BarChartModel initBarModel3() {
        // Question3: Retrieve answer statistics for question 8 from all surveys
        answers = new ArrayList<>();
        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(2).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;
        int numOfAnswer3 = 0;
        int numOfAnswer4 = 0;
        int numOfAnswer5 = 0;
        int numOfAnswer6 = 0;
        int numOfAnswer7 = 0;
        int numOfAnswer8 = 0;
        int numOfAnswer9 = 0;
        int numOfAnswer10 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "1":
                    numOfAnswer1++;
                    break;
                case "2":
                    numOfAnswer2++;
                    break;
                case "3":
                    numOfAnswer3++;
                    break;
                case "4":
                    numOfAnswer4++;
                    break;
                case "5":
                    numOfAnswer5++;
                    break;
                case "6":
                    numOfAnswer6++;
                    break;
                case "7":
                    numOfAnswer7++;
                    break;
                case "8":
                    numOfAnswer8++;
                    break;
                case "9":
                    numOfAnswer9++;
                    break;
                case "10":
                    numOfAnswer10++;
                    break;
            }
        }
        
        double percentage1 = 0.0;
        double percentage2 = 0.0;
        double percentage3 = 0.0;
        double percentage4 = 0.0;
        double percentage5 = 0.0;
        double percentage6 = 0.0;
        double percentage7 = 0.0;
        double percentage8 = 0.0;
        double percentage9 = 0.0;
        double percentage10 = 0.0;

        //Compute percentages
        if (numberOfUsers != 0) {
            percentage1 = (((double) numOfAnswer1) / ((double) numberOfUsers)) * 100;
            percentage2 = (((double) numOfAnswer2) / ((double) numberOfUsers)) * 100;
            percentage3 = (((double) numOfAnswer3) / ((double) numberOfUsers)) * 100;
            percentage4 = (((double) numOfAnswer4) / ((double) numberOfUsers)) * 100;
            percentage5 = (((double) numOfAnswer5) / ((double) numberOfUsers)) * 100;
            percentage6 = (((double) numOfAnswer6) / ((double) numberOfUsers)) * 100;
            percentage7 = (((double) numOfAnswer7) / ((double) numberOfUsers)) * 100;
            percentage8 = (((double) numOfAnswer8) / ((double) numberOfUsers)) * 100;
            percentage9 = (((double) numOfAnswer9) / ((double) numberOfUsers)) * 100;
            percentage10 = (((double) numOfAnswer10) / ((double) numberOfUsers)) * 100;
        }
        
        // Creates new Bar Chart and populates it with the cumulative survey data.        
        BarChartModel model = new BarChartModel();
        model.setBarWidth(20);

        ChartSeries communityResponse = new ChartSeries();
        communityResponse.setLabel("Question 3");
        communityResponse.set("1", percentage1);
        communityResponse.set("2", percentage2);
        communityResponse.set("3", percentage3);
        communityResponse.set("4", percentage4);
        communityResponse.set("5", percentage5);
        communityResponse.set("6", percentage6);
        communityResponse.set("7", percentage7);
        communityResponse.set("8", percentage8);
        communityResponse.set("9", percentage9);
        communityResponse.set("10", percentage10);

        model.addSeries(communityResponse);
// JavaScript function "barChartExtender" in GenerateReport.xhtml is used to style the chart
        model.setExtender("barChartExtender");
        return model;
    }

    /**
     * Constructs the bar chart properties for question 8
     */
    private void createBarModel3() {
        question3BarModel = initBarModel3();

        question3BarModel.setLegendPosition("ne");

        Axis xAxis = question3BarModel.getAxis(AxisType.X);
        xAxis.setLabel("Question 3: How well do you feel that your community is responding to the crisis?");
        xAxis.setTickAngle(-30);
        Axis yAxis = question3BarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Percent of Respondents");
        yAxis.setTickFormat("%'.3f");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private BarChartModel initBarModel4() {
        // Question4: Retrieve answer statistics for question 8 from all surveys
        answers = new ArrayList<>();
        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(3).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;
        int numOfAnswer3 = 0;
        int numOfAnswer4 = 0;
        int numOfAnswer5 = 0;
        int numOfAnswer6 = 0;
        int numOfAnswer7 = 0;
        int numOfAnswer8 = 0;
        int numOfAnswer9 = 0;
        int numOfAnswer10 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "1":
                    numOfAnswer1++;
                    break;
                case "2":
                    numOfAnswer2++;
                    break;
                case "3":
                    numOfAnswer3++;
                    break;
                case "4":
                    numOfAnswer4++;
                    break;
                case "5":
                    numOfAnswer5++;
                    break;
                case "6":
                    numOfAnswer6++;
                    break;
                case "7":
                    numOfAnswer7++;
                    break;
                case "8":
                    numOfAnswer8++;
                    break;
                case "9":
                    numOfAnswer9++;
                    break;
                case "10":
                    numOfAnswer10++;
                    break;
            }
        }
        
        double percentage1 = 0.0;
        double percentage2 = 0.0;
        double percentage3 = 0.0;
        double percentage4 = 0.0;
        double percentage5 = 0.0;
        double percentage6 = 0.0;
        double percentage7 = 0.0;
        double percentage8 = 0.0;
        double percentage9 = 0.0;
        double percentage10 = 0.0;

        //Compute percentages
        if (numberOfUsers != 0) {
            percentage1 = (((double) numOfAnswer1) / ((double) numberOfUsers)) * 100;
            percentage2 = (((double) numOfAnswer2) / ((double) numberOfUsers)) * 100;
            percentage3 = (((double) numOfAnswer3) / ((double) numberOfUsers)) * 100;
            percentage4 = (((double) numOfAnswer4) / ((double) numberOfUsers)) * 100;
            percentage5 = (((double) numOfAnswer5) / ((double) numberOfUsers)) * 100;
            percentage6 = (((double) numOfAnswer6) / ((double) numberOfUsers)) * 100;
            percentage7 = (((double) numOfAnswer7) / ((double) numberOfUsers)) * 100;
            percentage8 = (((double) numOfAnswer8) / ((double) numberOfUsers)) * 100;
            percentage9 = (((double) numOfAnswer9) / ((double) numberOfUsers)) * 100;
            percentage10 = (((double) numOfAnswer10) / ((double) numberOfUsers)) * 100;
        }
        
        // Creates new Bar Chart and populates it with the cumulative survey data.        
        BarChartModel model = new BarChartModel();
        model.setBarWidth(20);

        ChartSeries communityResponse = new ChartSeries();
        communityResponse.setLabel("Question 4");
        communityResponse.set("1", percentage1);
        communityResponse.set("2", percentage2);
        communityResponse.set("3", percentage3);
        communityResponse.set("4", percentage4);
        communityResponse.set("5", percentage5);
        communityResponse.set("6", percentage6);
        communityResponse.set("7", percentage7);
        communityResponse.set("8", percentage8);
        communityResponse.set("9", percentage9);
        communityResponse.set("10", percentage10);

        model.addSeries(communityResponse);
// JavaScript function "barChartExtender" in GenerateReport.xhtml is used to style the chart
        model.setExtender("barChartExtender");
        return model;
    }

    /**
     * Constructs the bar chart properties for question 8
     */
    private void createBarModel4() {
        question4BarModel = initBarModel4();

        question4BarModel.setLegendPosition("ne");

        Axis xAxis = question4BarModel.getAxis(AxisType.X);
        xAxis.setLabel("Question 4: How knowledgeable do you feel about the steps your community has taken in response to the crisis?");
        xAxis.setTickAngle(-30);
        Axis yAxis = question4BarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Percent of Respondents");
        yAxis.setTickFormat("%'.3f");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }

    private BarChartModel initBarModel5() {
        // Question5: Retrieve answer statistics for question 8 from all surveys
        answers = new ArrayList<>();
        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(4).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;
        int numOfAnswer3 = 0;
        int numOfAnswer4 = 0;
        int numOfAnswer5 = 0;
        int numOfAnswer6 = 0;
        int numOfAnswer7 = 0;
        int numOfAnswer8 = 0;
        int numOfAnswer9 = 0;
        int numOfAnswer10 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "1":
                    numOfAnswer1++;
                    break;
                case "2":
                    numOfAnswer2++;
                    break;
                case "3":
                    numOfAnswer3++;
                    break;
                case "4":
                    numOfAnswer4++;
                    break;
                case "5":
                    numOfAnswer5++;
                    break;
                case "6":
                    numOfAnswer6++;
                    break;
                case "7":
                    numOfAnswer7++;
                    break;
                case "8":
                    numOfAnswer8++;
                    break;
                case "9":
                    numOfAnswer9++;
                    break;
                case "10":
                    numOfAnswer10++;
                    break;
            }
        }
        
        double percentage1 = 0.0;
        double percentage2 = 0.0;
        double percentage3 = 0.0;
        double percentage4 = 0.0;
        double percentage5 = 0.0;
        double percentage6 = 0.0;
        double percentage7 = 0.0;
        double percentage8 = 0.0;
        double percentage9 = 0.0;
        double percentage10 = 0.0;

        //Compute percentages
        if (numberOfUsers != 0) {
            percentage1 = (((double) numOfAnswer1) / ((double) numberOfUsers)) * 100;
            percentage2 = (((double) numOfAnswer2) / ((double) numberOfUsers)) * 100;
            percentage3 = (((double) numOfAnswer3) / ((double) numberOfUsers)) * 100;
            percentage4 = (((double) numOfAnswer4) / ((double) numberOfUsers)) * 100;
            percentage5 = (((double) numOfAnswer5) / ((double) numberOfUsers)) * 100;
            percentage6 = (((double) numOfAnswer6) / ((double) numberOfUsers)) * 100;
            percentage7 = (((double) numOfAnswer7) / ((double) numberOfUsers)) * 100;
            percentage8 = (((double) numOfAnswer8) / ((double) numberOfUsers)) * 100;
            percentage9 = (((double) numOfAnswer9) / ((double) numberOfUsers)) * 100;
            percentage10 = (((double) numOfAnswer10) / ((double) numberOfUsers)) * 100;
        }
        
        // Creates new Pie Chart and populates it with the cumulative survey data.        
        BarChartModel model = new BarChartModel();
        model.setBarWidth(20);

        ChartSeries communityResponse = new ChartSeries();
        communityResponse.setLabel("Question 5");
        communityResponse.set("1", percentage1);
        communityResponse.set("2", percentage2);
        communityResponse.set("3", percentage3);
        communityResponse.set("4", percentage4);
        communityResponse.set("5", percentage5);
        communityResponse.set("6", percentage6);
        communityResponse.set("7", percentage7);
        communityResponse.set("8", percentage8);
        communityResponse.set("9", percentage9);
        communityResponse.set("10", percentage10);

        model.addSeries(communityResponse);
// JavaScript function "barChartExtender" in GenerateReport.xhtml is used to style the chart
        model.setExtender("barChartExtender");
        return model;
    }

    /**
     * Constructs the bar chart properties for question 8
     */
    private void createBarModel5() {
        question5BarModel = initBarModel5();

        question5BarModel.setLegendPosition("ne");

        Axis xAxis = question5BarModel.getAxis(AxisType.X);
        xAxis.setLabel("Question 5: How involved are you in your community's response to the crisis?");
        xAxis.setTickAngle(-30);
        Axis yAxis = question5BarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Percent of Respondents");
        yAxis.setTickFormat("%'.3f");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }
    
   private BarChartModel initBarModel6() {
        // Question6: Retrieve answer statistics for question 8 from all surveys
        answers = new ArrayList<>();
        surveys = userSurveyFacade.findAll();
        // Since one user can take multiple surveys, "numberOfUsers" is the number of total surveys here
        numberOfUsers = surveys.size();

        surveys.forEach((userSurvey) -> {
            survey = userSurvey.getSurvey();
            JSONArray jsonArr = new JSONArray(survey);
            answer = jsonArr.getJSONObject(5).optString("questionAnswer");
            answers.add(answer);
        });

        //Place holders for computation 
        int numOfAnswer1 = 0;
        int numOfAnswer2 = 0;
        int numOfAnswer3 = 0;
        int numOfAnswer4 = 0;
        int numOfAnswer5 = 0;
        int numOfAnswer6 = 0;
        int numOfAnswer7 = 0;
        int numOfAnswer8 = 0;
        int numOfAnswer9 = 0;
        int numOfAnswer10 = 0;

        for (int i = 0; i < answers.size(); i++) {

            switch (answers.get(i)) {
                case "1":
                    numOfAnswer1++;
                    break;
                case "2":
                    numOfAnswer2++;
                    break;
                case "3":
                    numOfAnswer3++;
                    break;
                case "4":
                    numOfAnswer4++;
                    break;
                case "5":
                    numOfAnswer5++;
                    break;
                case "6":
                    numOfAnswer6++;
                    break;
                case "7":
                    numOfAnswer7++;
                    break;
                case "8":
                    numOfAnswer8++;
                    break;
                case "9":
                    numOfAnswer9++;
                    break;
                case "10":
                    numOfAnswer10++;
                    break;
            }
        }
        
        double percentage1 = 0.0;
        double percentage2 = 0.0;
        double percentage3 = 0.0;
        double percentage4 = 0.0;
        double percentage5 = 0.0;
        double percentage6 = 0.0;
        double percentage7 = 0.0;
        double percentage8 = 0.0;
        double percentage9 = 0.0;
        double percentage10 = 0.0;

        //Compute percentages
        if (numberOfUsers != 0) {
            percentage1 = (((double) numOfAnswer1) / ((double) numberOfUsers)) * 100;
            percentage2 = (((double) numOfAnswer2) / ((double) numberOfUsers)) * 100;
            percentage3 = (((double) numOfAnswer3) / ((double) numberOfUsers)) * 100;
            percentage4 = (((double) numOfAnswer4) / ((double) numberOfUsers)) * 100;
            percentage5 = (((double) numOfAnswer5) / ((double) numberOfUsers)) * 100;
            percentage6 = (((double) numOfAnswer6) / ((double) numberOfUsers)) * 100;
            percentage7 = (((double) numOfAnswer7) / ((double) numberOfUsers)) * 100;
            percentage8 = (((double) numOfAnswer8) / ((double) numberOfUsers)) * 100;
            percentage9 = (((double) numOfAnswer9) / ((double) numberOfUsers)) * 100;
            percentage10 = (((double) numOfAnswer10) / ((double) numberOfUsers)) * 100;
        }
        
        // Creates new Pie Chart and populates it with the cumulative survey data.        
        BarChartModel model = new BarChartModel();
        model.setBarWidth(20);

        ChartSeries communityResponse = new ChartSeries();
        communityResponse.setLabel("Question 6");
        communityResponse.set("1", percentage1);
        communityResponse.set("2", percentage2);
        communityResponse.set("3", percentage3);
        communityResponse.set("4", percentage4);
        communityResponse.set("5", percentage5);
        communityResponse.set("6", percentage6);
        communityResponse.set("7", percentage7);
        communityResponse.set("8", percentage8);
        communityResponse.set("9", percentage9);
        communityResponse.set("10", percentage10);

        model.addSeries(communityResponse);
// JavaScript function "barChartExtender" in GenerateReport.xhtml is used to style the chart
        model.setExtender("barChartExtender");
        return model;
    }

    /**
     * Constructs the bar chart properties for question 8
     */
    private void createBarModel6() {
        question6BarModel = initBarModel6();

        question6BarModel.setLegendPosition("ne");

        Axis xAxis = question6BarModel.getAxis(AxisType.X);
        xAxis.setLabel("Question 6: How thorough was the press coverage of the crisis in your community?");
        xAxis.setTickAngle(-30);
        Axis yAxis = question6BarModel.getAxis(AxisType.Y);
        yAxis.setLabel("Percent of Respondents");
        yAxis.setTickFormat("%'.3f");
        yAxis.setMin(0);
        yAxis.setMax(100);
    }
    
}
