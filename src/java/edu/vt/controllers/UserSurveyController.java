/*
 * Created by Bengi Sevil on 2018.10.23  * 
 * Copyright © 2018 Bengi Sevil. All rights reserved. * 
 */
package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserPhoto;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.EntityBeans.UserSurvey;
import edu.vt.FacadeBeans.UserPhotoFacade;
import edu.vt.FacadeBeans.UserSurveyFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.controllers.util.JsfUtil.PersistAction;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import edu.vt.pojo.Question;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;

@Named("userSurveyController")
@SessionScoped

public class UserSurveyController implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    @EJB
    private UserFacade userFacade;

    @EJB
    private UserSurveyFacade userSurveyFacade;
    
    @EJB 
    private UserPhotoFacade userPhotoFacade;

    // 'items' is a List containing the object references of UserSurvey objects
    private List<UserSurvey> items = null;
    
    private List<UserSurvey> userItems = null;

    // 'selected' contains the object reference of the selected UserSurvey object
    private UserSurvey selected;

    // 'adminItems' is a List containing the object references of Question objects
    private List<Question> adminItems = null;

    private String answerToSecurityQuestion;

    /*
    ==================
    Constructor Method
    ==================
     */
    public UserSurveyController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */

    public List<UserSurvey> getUserItems() {
        if (userItems == null) {
            
            userItems = getUserSurveyFacade().
                    findUserSurveysByUserPrimaryKey((Integer)Methods.sessionMap().get("user_id"));
                    
        }
        return userItems;
    }

    public void setUserItems(List<UserSurvey> userItems) {
        this.userItems = userItems;
    }
    
    
    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    private UserSurveyFacade getUserSurveyFacade() {
        return userSurveyFacade;
    }

    public void setUserSurveyFacade(UserSurveyFacade userSurveyFacade) {
        this.userSurveyFacade = userSurveyFacade;
    }

    /*
    ***************************************************************
    Return the List of Surveys Created by the Signed-In User
    ***************************************************************
     */
    public List<UserSurvey> getItems() {
        if (items == null) {
            
            items = getUserSurveyFacade().findAll();
        }
        return items;
    }

    public UserPhotoFacade getUserPhotoFacade() {
        return userPhotoFacade;
    }

    public void setUserPhotoFacade(UserPhotoFacade userPhotoFacade) {
        this.userPhotoFacade = userPhotoFacade;
    }
    

    public void setItems(List<UserSurvey> items) {
        this.items = items;
    }

    public UserSurvey getSelected() {
        return selected;
    }

    public void setSelected(UserSurvey selected) {
        adminItems = null;    // Invalidate list of Health items to trigger re-query.
        this.selected = selected;
    }

    public List<Question> getAdminItems() {
        if (adminItems == null) {

            adminItems = new ArrayList<>();

            String survey = selected.getSurvey();
            JSONArray jsonArray = new JSONArray(survey);

            jsonArray.forEach(object -> {
                // Typecast the object as JSONObject
                JSONObject jsonObject = (JSONObject) object;

                Integer questionNumber = jsonObject.getInt("questionNumber");
                String questionName = jsonObject.getString("questionName");
                String questionAnswer = jsonObject.getString("questionAnswer");

                Question question = new Question(questionNumber, questionName, questionAnswer);
                adminItems.add(question);
            });
        }
        return adminItems;
    }

    public void setAdminItems(List<Question> adminItems) {
        this.adminItems = adminItems;
    }

    public String getAnswerToSecurityQuestion() {
        return answerToSecurityQuestion;
    }

    public void setAnswerToSecurityQuestion(String answerToSecurityQuestion) {
        this.answerToSecurityQuestion = answerToSecurityQuestion;
    }
    
    public String getUserPhotoById(int userId){
        User aUser = getUserFacade().getUser(userId);

        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserPrimaryKey(aUser.getId());
        if (photoList.isEmpty()){
            try
            {   
                URL url = new URL(getUserFacade().getUser(userId).getImageUrl());
                url.toURI();
                return getUserFacade().getUser(userId).getImageUrl();
            } catch (MalformedURLException | URISyntaxException e)
            {
                return Constants.DEFAULT_PHOTO_RELATIVE_PATH;
            }
        }
        String thumbnailFileName = photoList.get(0).getThumbnailFileName();

        return Constants.PHOTOS_RELATIVE_PATH + thumbnailFileName;
    }

    /*
    ================
    Instance Methods
    ================

    *****************************************************
    Process the Submitted Answer to the Security Question
    *****************************************************
     */
    public void securityAnswerSubmit() {
        /*
        'user', the object reference of the signed-in user, was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */
        User signedInUser = (User) Methods.sessionMap().get("user");

        String actualSecurityAnswer = signedInUser.getSecurityAnswer();
        String enteredSecurityAnswer = getAnswerToSecurityQuestion();

        if (actualSecurityAnswer.equals(enteredSecurityAnswer)) {
            // Answer to the security question is correct. Delete the selected survey.
            deleteSurvey();

        } else {
            Methods.showMessage("Error", "Answer to the Security Question is Incorrect!", "");
        }
    }

    /*
    *************************************
    Prepare to Create a New Survey
    *************************************
     */
    public UserSurvey prepareCreate() {
        /*
        Instantiate a new UserSurvey object and store its object reference into instance
        variable 'selected'. The UserSurvey class is defined in UserSurvey.java
         */
        selected = new UserSurvey();

        // Return the object reference of the newly created UserSurvey object
        return selected;
    }

    /*
    ******************************************
    Create a New Survey in the Database
    ******************************************
     */
    public void create() {
        /*
        We need to preserve the messages since we will redirect to show a
        different JSF page after successful creation of the survey.
         */
        Methods.preserveMessages();
        /*
        Show the message "Thank You! Your Survey was Successfully Saved in the Database!"
        given in the Bundle.properties file under the UserSurveyCreated keyword.

        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserSurveyCreated"));
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The CREATE operation is successfully performed.
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            userItems = null;
        }
    }

    // We do not allow update of a survey.
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserSurveyUpdated"));
    }

    /*
    ***************************************************
    Delete the Selected Survey from the Database
    ***************************************************
     */
    public void deleteSurvey() {
        /*
        We need to preserve the messages since we will redirect to show a
        different JSF page after successful deletion of the survey.
         */
        Methods.preserveMessages();
        /*
        Show the message "The Survey was Successfully Deleted from the Database!"
        given in the Bundle.properties file under the UserSurveyDeleted keyword.
        
        Prevent displaying the same msg twice, one for Summary and one for Detail, by setting the 
        message Detail to "" in the addSuccessMessage(String msg) method in the jsfUtil.java file.
         */
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserSurveyDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            // No JSF validation error. The DELETE operation is successfully performed.
            
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
            
        }
    }

    /*
     ****************************************************************************
     *   Perform CREATE, EDIT (UPDATE), and DELETE Operations in the Database   *
     ****************************************************************************
     */
    /**
     * @param persistAction refers to CREATE, UPDATE (Edit) or DELETE action
     * @param successMessage displayed to inform the user about the result
     */
    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    /*
                     -------------------------------------------------
                     Perform CREATE or EDIT operation in the database.
                     -------------------------------------------------
                     The edit(selected) method performs the SAVE (STORE) operation of the "selected"
                     object in the database regardless of whether the object is a newly
                     created object (CREATE) or an edited (updated) object (EDIT or UPDATE).
                    
                     UserSurveyFacade inherits the edit(selected) method from the AbstractFacade class.
                     */
                    getUserSurveyFacade().edit(selected);
                } else {
                    /*
                     -----------------------------------------
                     Perform DELETE operation in the database.
                     -----------------------------------------
                     The remove method performs the DELETE operation in the database.
                    
                     UserSurveyFacade inherits the remove(selected) method from the AbstractFacade class.
                     */
                    getUserSurveyFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);

            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    /*
    ************************************************
    |   Other Auto Generated Methods by NetBeans   |
    ************************************************
     */
    public UserSurvey getUserSurvey(java.lang.Integer id) {
        return getUserSurveyFacade().find(id);

    }

    /* public String getUserPhoto(java.lang.Integer id){
        
       return getUserFacade().getUser(selected.getUserId().getId()).getUserPhoto();
 
    }
     */
    public List<UserSurvey> getItemsAvailableSelectMany() {
        return getUserSurveyFacade().findAll();
    }

    public List<UserSurvey> getItemsAvailableSelectOne() {
        return getUserSurveyFacade().findAll();
    }

    @FacesConverter(forClass = UserSurvey.class)
    public static class UserSurveyControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserSurveyController controller = (UserSurveyController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userSurveyController");
            return controller.getUserSurvey(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof UserSurvey) {
                UserSurvey o = (UserSurvey) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}",
                        new Object[]{object, object.getClass().getName(), UserSurvey.class.getName()});
                return null;
            }
        }

    }

    /*
    ***************************************************
    Type Converter Methods for PrimeFaces Data Exporter 
    Function to Export Data into PDF, Excel, and CSV
    ***************************************************
    
    PrimeFaces p:dataExporter requires the exported values to be of String type.
    These methods are called in SurveyDetails.xhtml.
     */
    public String convertIntToString(Integer value) {
        return Integer.toString(value);
    }

    public String convertDoubleToString(Double value) {
        return Double.toString(value);
    }
}
