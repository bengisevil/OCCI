package edu.vt.controllers;

import edu.vt.EntityBeans.OrganizerEvent;
import edu.vt.EntityBeans.UserPhoto;
import edu.vt.FacadeBeans.OrganizerEventFacade;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserPhotoFacade;
import edu.vt.globals.Constants;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("adminEventController")
@SessionScoped
public class AdminEventController implements Serializable {

    @EJB
    private OrganizerEventFacade adminEventFacade;
    
     @EJB
    private UserFacade userFacade;

    @EJB
    private UserPhotoFacade userPhotoFacade;
    
    private List<OrganizerEvent> adminEvents = null;
    
    private OrganizerEvent adminEvent;

    public AdminEventController() {
    }

    public OrganizerEvent getAdminrEvent(java.lang.Integer id) {
        return getAdminEventFacade().find(id);
    }

    public List<OrganizerEvent> getAdminEvents() {
        if (adminEvents == null) {
            adminEvents = getAdminEventFacade().findAll();
        }
        return adminEvents;
    }

    public OrganizerEventFacade getAdminEventFacade() {
        return adminEventFacade;
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
    
    

    public void setAdminEventFacade(OrganizerEventFacade adminEventFacade) {
        this.adminEventFacade = adminEventFacade;
    }

    public OrganizerEvent getAdminEvent() {
         return adminEvent;
    }

    public void setAdminEvent(OrganizerEvent adminEvent) {
        this.adminEvent = adminEvent;
    }
    
    
    @FacesConverter(forClass = OrganizerEvent.class)
    public static class UserEventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            AdminEventController controller = (AdminEventController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userEventController");
            return controller.getAdminrEvent(getKey(value));
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", 
                        new Object[]{object, object.getClass().getName(), OrganizerEvent.class.getName()});
                return null;
            }
        }

    }

}