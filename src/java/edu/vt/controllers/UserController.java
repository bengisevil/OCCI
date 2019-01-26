/*
Created by Chengen Li on 2018.09.25
Copyright © 2018 Chengen Li. All rights reserved.
*/
package edu.vt.controllers;

import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserPhoto;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserPhotoFacade;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;
import edu.vt.globals.Password;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/*
---------------------------------------------------------------------------
The @Named (javax.inject.Named) annotation indicates that the objects
instantiated from this class will be managed by the Contexts and Dependency
Injection (CDI) container. The name "userController" is used within
Expression Language (EL) expressions in JSF (XHTML) facelets pages to
access the properties and invoke methods of this class.
---------------------------------------------------------------------------
 */
@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    private String username;
    private String password;
    private String confirmPassword;

    private String firstName;
    private String middleName;
    private String lastName;

    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    private String imageUrl;
    
    private int isOrganizer;
    private String organizationName;
    private String organizationDescription;
    
    
    private List<User> userList = null;

    private int securityQuestionNumber;
    private String answerToSecurityQuestion;

    private String email;

    private Map<String, Object> security_questions;

    private User selected;
    private User tempSelected;
    
    
    private List <User> users;
    
    private User newUser;
    
    @EJB
    private UserFacade userFacade;

    @EJB
    private UserPhotoFacade userPhotoFacade;

    /*
    ==================
    Constructor Method
    ==================
     */
    public UserController() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    
    /*
    ***************************************************************
    Return the List of Surveys Created by the Signed-In User
    ***************************************************************
     */
    public List<User> getUserList() {
        if (userList == null) {
            /*
            user_id (database primary key) was put into the SessionMap in the
            initializeSessionMap() method in LoginManager upon user's sign in.
             */

            userList = getUserFacade().findAllUsers();
        }
        return userList;
    }

    public void setUserList(List<User> items) {
        this.userList = items;
    }

    public void setOrganizer(int eventOrganizer) {
        this.isOrganizer = eventOrganizer;
    }
    
    public int getOrganizer() {
        return isOrganizer;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationDescription() {
        return organizationDescription;
    }

    public void setOrganizationDescription(String organizationDescription) {
        this.organizationDescription = organizationDescription;
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public int getSecurityQuestionNumber() {
        return securityQuestionNumber;
    }

    public void setSecurityQuestionNumber(int securityQuestionNumber) {
        this.securityQuestionNumber = securityQuestionNumber;
    }

    public String getAnswerToSecurityQuestion() {
        return answerToSecurityQuestion;
    }

    public void setAnswerToSecurityQuestion(String answerToSecurityQuestion) {
        this.answerToSecurityQuestion = answerToSecurityQuestion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Map<String, Object> getSecurity_questions() {

        if (security_questions == null) {
            /*
            Difference between HashMap and LinkedHashMap:
            HashMap stores key-value pairings in no particular order. 
                Values are retrieved based on their corresponding Keys.
            LinkedHashMap stores and retrieves key-value pairings
                in the order they were put into the map.
             */
            security_questions = new LinkedHashMap<>();

            for (int i = 0; i < Constants.QUESTIONS.length; i++) {
                security_questions.put(Constants.QUESTIONS[i], i);
            }
        }
        return security_questions;
    }

    public void setSecurity_questions(Map<String, Object> security_questions) {
        this.security_questions = security_questions;
    }

    public User getSelected() {
        if (selected == null) {
            // Store the object reference of the signed-in User into the instance variable selected.
            selected = (User) Methods.sessionMap().get("user");
        }
        // Return the object reference of the selected (i.e., signed-in) User object
        return selected;
    }

    public void setSelected(User selected) {
        this.selected = selected;
    }

    public User getTempSelected() {
        return tempSelected;
    }

    public void setTempSelected(User tempSelected) {
        this.tempSelected = tempSelected;
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

    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserPhotoFacade getUserPhotoFacade() {
        return userPhotoFacade;
    }

    public void setUserPhotoFacade(UserPhotoFacade userPhotoFacade) {
        this.userPhotoFacade = userPhotoFacade;
    }

    /*
    ================
    Instance Methods
    ================

    **********************************
    Return True if a User is Signed In
    **********************************
     */
    public boolean isLoggedIn() {
        /*
        The username of a signed-in user is put into the SessionMap in the
        initializeSessionMap() method in LoginManager upon user's sign in.
        If there is a username, that means, there is a signed-in user.
         */
        return Methods.sessionMap().get("username") != null;
    }
    
    public boolean isAdministrator()
    {
        if (Methods.sessionMap().get("username") != null)
        {
            return Methods.sessionMap().get("username").equals("Administrator");    
        }
        return false;
    }
    
    public boolean isEventOrganizer()
    {
        User usr = (User)Methods.sessionMap().get("user");
        if (usr != null)
        {
            return (usr.getOrganizer() == 1);    
        }
        return false;
    }
    
    public boolean isParticipant()
    {
        return (!isEventOrganizer() && !isAdministrator() && isLoggedIn());
    }

    /*
    **************************************
    Return List of U.S. State Postal Codes
    **************************************
     */
    public String[] listOfStates() {
        return Constants.STATES;
    }

    /*
    ****************************************
    Return the Security Question Selected by
    the User at the Time of Account Creation
    ****************************************
     */
    public String getSelectedSecurityQuestion() {
        /*
        'user', the object reference of the signed-in user, was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */
        User signedInUser = (User) Methods.sessionMap().get("user");

        // Obtain the number of the security question selected by the user
        int questionNumber = signedInUser.getSecurityQuestionNumber();

        // Return the security question corresponding to the question number
        return Constants.QUESTIONS[questionNumber];
    }

    /*
    **********************************************************
    Create User's Account and Redirect to Show the SignIn Page
    **********************************************************
     */
    public String createAccount() {
        
        if (!password.equals(confirmPassword)) {
            Methods.showMessage("Fatal Error", "Unmatched Passwords!",
                    "Password and Confirm Password must Match!");
            return "";
        }
        Methods.preserveMessages();

        User aUser = getUserFacade().findByUsername(username);

        if (aUser != null) {
            username = "";
            Methods.showMessage("Fatal Error", "Username Already Exists!", "Please Select a Different One!");
            return "";
        }
        try {
            newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setMiddleName(middleName);
            newUser.setLastName(lastName);
            newUser.setAddress1(address1);
            newUser.setAddress2(address2);
            newUser.setCity(city);
            newUser.setState(state);
            newUser.setZipcode(zipcode);
            newUser.setSecurityQuestionNumber(securityQuestionNumber);
            newUser.setSecurityAnswer(answerToSecurityQuestion);
            newUser.setEmail(email);
            newUser.setUsername(username);
            newUser.setOrganizer(isOrganizer);
            newUser.setEventsList("");
            newUser.setImageUrl("");
            String parts = Password.createHash(password);
            newUser.setPassword(parts);
            if (isOrganizer == 0){
                newUser.setOrganizationName(" ");
                newUser.setOrganizationDescription(" ");
                getUserFacade().create(newUser);
                Methods.showMessage("Information", "Success!", "User Account is Successfully Created!");
                return "/signIn/SignIn.xhtml?faces-redirect=true";
            }else{
                return "/userAccount/CreateOrganizerAccount.xhtml?faces-redirect=true";
            }
        } catch (EJBException | Password.CannotPerformOperationException ex) {
            username = "";
            Methods.showMessage("Fatal Error", "Something went wrong while creating user's account!",
                    "See: " + ex.getMessage());
            return "";
        }
       
    }
    
    public String setupOrganizerAccount(){
        Methods.preserveMessages();
        try {
            newUser.setOrganizationName(organizationName);
            newUser.setOrganizationDescription(organizationDescription);
            getUserFacade().create(newUser);
            Methods.showMessage("Information", "Success!", "Organizer Account is Successfully Created!");
        }catch (EJBException ex) {
            username = "";
            Methods.showMessage("Fatal Error", "Something went wrong while creating user's account!",
                    "See: " + ex.getMessage());
            return "";
        }
        return "/signIn/SignIn.xhtml?faces-redirect=true";
    }

    /*
    ***********************************************************
    Update User's Account and Redirect to Show the Profile Page
    ***********************************************************
     */
    public String updateAccount() {
        Methods.preserveMessages();
        User editUser = (User) Methods.sessionMap().get("user");

        try {
            editUser.setFirstName(this.selected.getFirstName());
            editUser.setMiddleName(this.selected.getMiddleName());
            editUser.setLastName(this.selected.getLastName());

            editUser.setAddress1(this.selected.getAddress1());
            editUser.setAddress2(this.selected.getAddress2());
            editUser.setCity(this.selected.getCity());
            editUser.setState(this.selected.getState());
            editUser.setZipcode(this.selected.getZipcode());
            editUser.setEmail(this.selected.getEmail());
            if (this.selected.getOrganizer() == 1){
                editUser.setOrganizationName(this.selected.getOrganizationName());
                editUser.setOrganizationDescription(this.selected.getOrganizationDescription());
            }
            // Store the changes in the database
            getUserFacade().edit(editUser);

            Methods.showMessage("Information", "Success!", "User's Account is Successfully Updated!");

        } catch (EJBException ex) {
            username = "";
            Methods.showMessage("Fatal Error", "Something went wrong while updating user's profile!",
                    "See: " + ex.getMessage());
            return "";
        }

        if (isAdministrator()){
            return "/adminMenu/AdminProfile.xhtml?faces-redirect=true";
        }
        if (isEventOrganizer()){
            return "/eventOrganizer/OrganizerProfile.xhtml?faces-redirect=true";
        }
        return "/participant/ParticipantProfile.xhtml?faces-redirect=true";
    }

    /*
    *********************************************
    Process Submitted Answer to Security Question
    *********************************************
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
            // Answer to the security question is correct; Delete the user's account.
            deleteAccount();

        } else {
            Methods.showMessage("Error", "Answer to the Security Question is Incorrect!", "");
        }
    }

    /*
    *****************************************************************
    Delete User's Account, Logout, and Redirect to Show the Home Page
    *****************************************************************
     */
    public String deleteAccount() {

        // Since we will redirect to show the home page, invoke preserveMessages()
        Methods.preserveMessages();

        /*
        The database primary key of the signed-in User object was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */
        int userPrimaryKey = (int) Methods.sessionMap().get("user_id");

        try {
            // Delete all of the photo files associated with the signed-in user whose primary key is userPrimaryKey
            deleteAllUserPhotos(userPrimaryKey);

            // Delete the User entity, whose primary key is user_id, from the database
            getUserFacade().deleteUser(userPrimaryKey);

            Methods.showMessage("Information", "Success!", "Your Account is Successfully Deleted!");

        } catch (EJBException ex) {
            username = "";
            Methods.showMessage("Fatal Error", "Something went wrong while deleting user's account!",
                    "See: " + ex.getMessage());
            return "";
        }

        // Execute the logout() method given below
        logout();

        // Redirect to show the index (home) page
        return "/index.xhtml?faces-redirect=true";
    }
    
    public List<User> getUsers() {
        users = userFacade.findAll();
        return users;
    }

    /*
    **********************************************
    Logout User and Redirect to Show the Home Page
    **********************************************
     */
    public void logout() {

        // Clear the signed-in User's session map
        Methods.sessionMap().clear();

        // Reset the signed-in User's properties
        username = password = confirmPassword = "";
        firstName = middleName = lastName = "";
        address1 = address2 = city = state = zipcode = "";
        securityQuestionNumber = 0;
        answerToSecurityQuestion = email = "";
        selected = null;
        isOrganizer = 0;
        Methods.preserveMessages();

        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            externalContext.invalidateSession();
            String redirectPageURI = externalContext.getRequestContextPath() + "/index.xhtml";

            // Redirect to show the index (home) page
            externalContext.redirect(redirectPageURI);

            /*
            NOTE: We cannot use: return "/index?faces-redirect=true";
            here because the user's session is invalidated.
             */
        } catch (IOException ex) {
            Methods.showMessage("Fatal Error", "Unable to redirect to the index (home) page!",
                    "See: " + ex.getMessage());
        }
    }
    
    public String googleInfo(){
        if (firstName != null){
            username = email;
            User user = getUserFacade().findByUsername(username);
            if (user == null) {
                return "/signIn/GoogleSignIn.xhtml?faces-redirect=true";
            } else {
                String actualUsername = user.getUsername();
                if (!actualUsername.equals(username)) {
                    return "/signIn/GoogleSignIn.xhtml?faces-redirect=true";
                }
                initializeSessionMap(user);
                return "/participant/ParticipantProfile.xhtml?faces-redirect=true";
            }
        }else{
            Methods.showMessage("Fatal Error", "Something went wrong while creating user's account!", "");
            return null;
        }
    }
    
    public String signUpWithGoogle(){
        try {
            newUser = new User();
            newUser.setFirstName(firstName);
            newUser.setMiddleName(" ");
            newUser.setLastName(lastName);
            newUser.setAddress1(" ");
            newUser.setAddress2(" ");
            newUser.setCity(" ");
            newUser.setState(" ");
            newUser.setZipcode(" ");
            newUser.setSecurityQuestionNumber(securityQuestionNumber);
            newUser.setSecurityAnswer(answerToSecurityQuestion);
            newUser.setEmail(email);
            newUser.setEventsList("");
            username = email;
            newUser.setUsername(username);
            newUser.setPassword(" ");
            newUser.setImageUrl(imageUrl);
            newUser.setOrganizer(0);
            newUser.setOrganizationName(" ");
            newUser.setOrganizationDescription(" ");
            System.out.println(firstName+" "+lastName+" "+securityQuestionNumber+" "+answerToSecurityQuestion+" "+email+" "+username+" "+imageUrl);
            getUserFacade().create(newUser);
            initializeSessionMap(newUser);
            
            return "/participant/ParticipantProfile.xhtml?faces-redirect=true";
        } catch (Exception e) {
            Methods.showMessage("Fatal Error", "Something went wrong while creating user's account!",
                    "See: " + e.getMessage());
            return "";
        }
    }
    
    public boolean isGoogleSignIn(){
        User user = getUserFacade().getUser((Integer)Methods.sessionMap().get("user_id"));
        return user.getImageUrl() != null;
    }
    
    public void initializeSessionMap(User user) {
        // Store the object reference of the signed-in user
        Methods.sessionMap().put("user", user);

        // Store the First Name of the signed-in user
        Methods.sessionMap().put("first_name", user.getFirstName());

        // Store the Last Name of the signed-in user
        Methods.sessionMap().put("last_name", user.getLastName());

        // Store the Username of the signed-in user
        Methods.sessionMap().put("username", username);

        // Store signed-in user's Primary Key in the database
        Methods.sessionMap().put("user_id", user.getId());
    }
    

   

    /*
    *********************************************************
    Return Signed-In User's Thumbnail Photo Relative Filepath
    *********************************************************
     */
    public String userPhoto() {
        Integer primaryKey = (Integer) Methods.sessionMap().get("user_id");

        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserPrimaryKey(primaryKey);
        if (photoList.isEmpty()){
            try
            {   
                URL url = new URL(getUserFacade().getUser(primaryKey).getImageUrl());
                url.toURI();
                return getUserFacade().getUser(primaryKey).getImageUrl();
            } catch (MalformedURLException | URISyntaxException e)
            {
                return Constants.DEFAULT_PHOTO_RELATIVE_PATH;
            }
        }
        String thumbnailFileName = photoList.get(0).getThumbnailFileName();

        return Constants.PHOTOS_RELATIVE_PATH + thumbnailFileName;
    }
    
    
    /*
    *********************************************************
    Return Signed-In User's Thumbnail Photo Relative Filepath
    *********************************************************
     */
    public String userPhoto(Integer primaryKey) {

        /*
        The database primary key of the signed-in User object was put into the SessionMap
        in the initializeSessionMap() method in LoginManager upon user's sign in.
         */

        
        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserPrimaryKey(primaryKey);

        if (photoList.isEmpty()) {
            // No user photo exists. Return defaultUserPhoto.png from the UserPhotoStorage directory.
            return Constants.DEFAULT_PHOTO_RELATIVE_PATH;
        }

        /*
        photoList.get(0) returns the object reference of the first Photo object in the list.
        getThumbnailFileName() message is sent to that Photo object to retrieve its
        thumbnail image file name, e.g., 5_thumbnail.jpeg
         */
        String thumbnailFileName = photoList.get(0).getThumbnailFileName();

        return Constants.PHOTOS_RELATIVE_PATH + thumbnailFileName;
    }

    /*
    *********************************************************
    Delete the photo and thumbnail image files that belong to
    the User object whose database primary key is primaryKey
    *********************************************************
     */
    public void deleteAllUserPhotos(int primaryKey) {

        /*
        Obtain the list of Photo objects that belong to the User whose
        database primary key is userId.
         */
        List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserPrimaryKey(primaryKey);

        // photoList.isEmpty implies that the user has never uploaded a photo file
        if (!photoList.isEmpty()) {

            // Obtain the object reference of the first Photo object in the list.
            UserPhoto photo = photoList.get(0);
            try {
                /*
                NOTE: Files and Paths are imported as
                        import java.nio.file.Files;
                        import java.nio.file.Paths;

                 Delete the user's photo if it exists.
                 getFilePath() is given in UserPhoto.java file.
                 */
                Files.deleteIfExists(Paths.get(photo.getPhotoFilePath()));

                /*
                 Delete the user's thumbnail image if it exists.
                 getThumbnailFilePath() is given in UserPhoto.java file.
                 */
                Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath()));

                // Delete the photo file record from the database.
                // UserPhotoFacade inherits the remove() method from AbstractFacade.
                getUserPhotoFacade().remove(photo);

            } catch (IOException ex) {
                Methods.showMessage("Fatal Error",
                        "Something went wrong while deleting user's photo!",
                        "See: " + ex.getMessage());
            }
        }
    }

}