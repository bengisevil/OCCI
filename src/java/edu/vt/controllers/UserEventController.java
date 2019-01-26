package edu.vt.controllers;

import edu.vt.EntityBeans.OrganizerEvent;
import edu.vt.EntityBeans.User;
import edu.vt.EntityBeans.UserPhoto;
import edu.vt.FacadeBeans.OrganizerEventFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserPhotoFacade;
import edu.vt.globals.Constants;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Named("userEventController")
@SessionScoped
public class UserEventController implements Serializable {

    @EJB
    private UserFacade userFacade;
    
    @EJB
    private OrganizerEventFacade organizerEventFacade;
    
    @EJB
    private UserPhotoFacade userPhotoFacade;
    
    Properties emailServerProperties;   // java.util.Properties
    Session emailSession;               // javax.mail.Session
    MimeMessage htmlEmailMessage;  

    
    private List<OrganizerEvent> organizerEvents = null;
    private List<OrganizerEvent> myEvents = null;
    private List<OrganizerEvent> publicEvents = null;
    private OrganizerEvent myEvent;
    private OrganizerEvent publicEvent;
    private User organizerInfo;

    public UserEventController() {
    }

    public User getOrganizerInfo() {
        return organizerInfo;
    }

    public void setOrganizerInfo(User organizerInfo) {
        this.organizerInfo = organizerInfo;
    }
    
    
    public UserFacade getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public OrganizerEvent getPublicEvent() {
        return publicEvent;
    }

    public void setPublicEvent(OrganizerEvent publicEvent) {
        this.publicEvent = publicEvent;
    }
    
    public OrganizerEvent getUserEvent(java.lang.Integer id) {
        return getOrganizerEventFacade().find(id);
    }

    public UserPhotoFacade getUserPhotoFacade() {
        return userPhotoFacade;
    }

    public void setUserPhotoFacade(UserPhotoFacade userPhotoFacade) {
        this.userPhotoFacade = userPhotoFacade;
    }
    
    
    
    public void setOrganizerEventFacade(OrganizerEventFacade organizerEventFacade){
        this.organizerEventFacade = organizerEventFacade;
    }
    
    public OrganizerEventFacade getOrganizerEventFacade(){
        return organizerEventFacade;
    }

    public List<OrganizerEvent> getMyEvents() {
        if (myEvents == null) {
            myEvents  = new ArrayList<>();
            User signedInUser = (User) Methods.sessionMap().get("user");
            String eventStr = signedInUser.getEventsList();
            if (eventStr == null ||eventStr.equals("")){
                return myEvents;
            }
            String[] alist = eventStr.split(",");
            for (int i = 1; i < alist.length; i++) {
                Integer id = Integer.parseInt(alist[i]);
                myEvents.add(getOrganizerEventFacade().findUserEventsById(id));
            }
        }
        return myEvents;
    }

    public void setMyEvents(List<OrganizerEvent> myEvents) {
        this.myEvents = myEvents;
    }
    
    public List<OrganizerEvent> getPublicEvents() {
        if (publicEvents == null) {
            publicEvents = getOrganizerEventFacade().findAll();
        }
        return publicEvents;
    }

    public void setPublicEvents(List<OrganizerEvent> publicEvents) {
        this.publicEvents = publicEvents;
    }


    public OrganizerEvent getMyEvent() {
        return myEvent;
    }

    public void setMyEvent(OrganizerEvent myEvent) {
        this.myEvent = myEvent;
    }

    public void cancelMyEvent() {
        User signedInUser = (User) Methods.sessionMap().get("user");
        String eventStr = signedInUser.getEventsList();
        String newStr = " ";
        Integer id = myEvent.getId();
        String[] aList = eventStr.split(",");
        for (int i = 1; i < aList.length; i++) {
            if (Integer.parseInt(aList[i]) != id) {
                newStr += ","+aList[i];
            }
        }
        eventStr = newStr;
        signedInUser.setEventsList(eventStr);
        getUserFacade().edit(signedInUser);
        OrganizerEvent event = getOrganizerEventFacade().findUserEventsById(id);
        event.setPeopleNum(event.getPeopleNum()-1);
        getOrganizerEventFacade().edit(event);
        JsfUtil.addSuccessMessage("Event Has Canceled by User");
        myEvent = null;
        myEvents = null;
    }

    public void prepareRegister(){
        User signedInUser = (User) Methods.sessionMap().get("user");
        String eventStr = signedInUser.getEventsList();
        eventStr += ","+publicEvent.getId();
        User editUser = (User) Methods.sessionMap().get("user");
        editUser.setEventsList(eventStr);
        getUserFacade().edit(signedInUser);
        OrganizerEvent event = getOrganizerEventFacade().findUserEventsById(publicEvent.getId());
        event.setPeopleNum(event.getPeopleNum()+1);
        getOrganizerEventFacade().edit(event);
        Methods.preserveMessages();
        JsfUtil.addSuccessMessage("Event Has Signed Up by User");
    }
    
    public String signUp() throws MessagingException{
        getMyEvents();
        for (int i = 0; i < myEvents.size(); i++){
            if (Objects.equals(publicEvent.getId(), myEvents.get(i).getId())){
                JsfUtil.addErrorMessage("You have registed for this event!");
                return null;
            }
        }
        prepareRegister();
        sendEmail();
        publicEvent = null;
        myEvents = null;
        publicEvents = null;
        publicEvent = null;
        myEvent = null;
        return "/participant/MyEventsList?faces-redirect=true";
    }
    
    public String searchMore(){
        myEvent = null;
        publicEvent = null;
        myEvents = null;
        publicEvents = null;
        return "/participant/PublicEventsList?faces-redirect=true";

    }

    public List<OrganizerEvent> getOrganizerEvents() {
         if (organizerEvents == null){
            organizerEvents = getOrganizerEventFacade().findByEventOrganizer(organizerInfo.getId());
        }
        return organizerEvents;
    }

    public void setOrganizerEvents(List<OrganizerEvent> organizerEvents) {
        this.organizerEvents = organizerEvents;
    }
    
    
    
    public String getUserPhotoById(int userId){
        User aUser = getUserFacade().getUser(userId);
        organizerEvents = null;
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
    
    public String organizerPage(Integer id){
        User user = getUserFacade().findByUserId(id);
        organizerInfo = user;
        
        return "/participant/MainPage?faces-redirect=true";
    }
    
    @FacesConverter(forClass = OrganizerEvent.class)
    public static class UserEventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserEventController controller = (UserEventController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userEventController");
            return controller.getUserEvent(getKey(value));
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
            if (object instanceof OrganizerEvent) {
                OrganizerEvent o = (OrganizerEvent) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), OrganizerEvent.class.getName()});
                return null;
            }
        }

    }
    
    public void sendEmail() throws AddressException, MessagingException {

        // Obtain the email message content from the editorController object
        String emailBody = "<p>Congratulations, you have successfully signed up for the following event!</p>"
                + "<p>Name:&nbsp;"+publicEvent.getEventName()+"</p>"
                + "<p>Organizer:&nbsp;"+publicEvent.getOrganizer()+"</p>"
                + "<p>Date:&nbsp;"+publicEvent.getDate()+"</p>"
                + "<p>Description:&nbsp;"+publicEvent.getDescription()+"</p>";

        String emailTo = ((User) Methods.sessionMap().get("user")).getEmail();
        // Set Email Server Properties
        emailServerProperties = System.getProperties();
        emailServerProperties.put("mail.smtp.port", "587");
        emailServerProperties.put("mail.smtp.auth", "true");
        emailServerProperties.put("mail.smtp.starttls.enable", "true");

         try {
            // Create an email session using the email server properties set above
            emailSession = Session.getDefaultInstance(emailServerProperties, null);
            htmlEmailMessage = new MimeMessage(emailSession);
            htmlEmailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            htmlEmailMessage.setSubject("Event Signed Up");
            htmlEmailMessage.setContent(emailBody, "text/html");
            Transport transport = emailSession.getTransport("smtp");
            transport.connect("smtp.gmail.com", "noreplyOCCI@gmail.com", "CSD@VaTech-1872");
            transport.sendMessage(htmlEmailMessage, htmlEmailMessage.getAllRecipients());
            transport.close();

            Methods.showMessage("Information", "Success!", "Email Message is Sent!");

        } catch (AddressException ae) {
            Methods.showMessage("Fatal Error", "Email Address Exception Occurred!",
                    "See: " + ae.getMessage());

        } catch (MessagingException me) {
            Methods.showMessage("Fatal Error",
                    "Email Messaging Exception Occurred! Internet Connection Required!",
                    "See: " + me.getMessage());
        }
    }
}
