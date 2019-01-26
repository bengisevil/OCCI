package edu.vt.controllers;

import edu.vt.EntityBeans.OrganizerEvent;
import edu.vt.EntityBeans.User;
import edu.vt.FacadeBeans.OrganizerEventFacade;
import edu.vt.FacadeBeans.UserFacade;
import edu.vt.FacadeBeans.UserPhotoFacade;
import edu.vt.controllers.util.JsfUtil;
import edu.vt.globals.Methods;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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

@Named("organizerEventController")
@SessionScoped
public class OrganizerEventController implements Serializable {

    @EJB
    private OrganizerEventFacade organizerEventFacade;
    
    @EJB
    private UserFacade userFacade;
    
     @EJB
    private UserPhotoFacade userPhotoFacade;
    
    private List<OrganizerEvent> organizerEvents = null;
    
    private OrganizerEvent organizerEvent;
    
    private UserFacade organizerFacade;

    public OrganizerEventController() {
    }

    public OrganizerEventFacade getOrganizerEventFacade() {
        return organizerEventFacade;
    }

    public void setOrganizerEventFacade(OrganizerEventFacade organizerEventFacade) {
        this.organizerEventFacade = organizerEventFacade;
    }

    public UserFacade getOrganizerFacade() {
        return organizerFacade;
    }

    public void setOrganizerFacade(UserFacade organizerFacade) {
        this.organizerFacade = organizerFacade;
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
    
    

    public List<OrganizerEvent> getOrganizerEvents() {
        Integer key = (Integer)Methods.sessionMap().get("user_id");
        if (organizerEvents == null){
            organizerEvents = getOrganizerEventFacade().findByEventOrganizer(key);
        }
        return organizerEvents;
    }

    public void setOrganizerEvents(List<OrganizerEvent> organizerEvents) {
        this.organizerEvents = organizerEvents;
    }

    public OrganizerEvent getOrganizerEvent() {
        return organizerEvent;
    }
    
    public OrganizerEvent getOrganizerEvent(java.lang.Integer id) {
        return getOrganizerEventFacade().find(id);
    }

    public void setOrganizerEvent(OrganizerEvent organizerEvent) {
        this.organizerEvent = organizerEvent;
    }
    
    public OrganizerEvent prepareCreate(){
        organizerEvent = new OrganizerEvent();
        System.out.println("prepare creation");
        return organizerEvent;
    }
    
    
    public void create() {
        System.out.println(" "+Methods.sessionMap().get("user_id")+"     ");
        organizerEvent.setOrganizerId((Integer)Methods.sessionMap().get("user_id"));
        organizerEvent.setPeopleNum(0);
        organizerEvent.setOrganizer(((User)Methods.sessionMap().get("user")).getOrganizationName());
        
        persist(JsfUtil.PersistAction.CREATE, "New Event was Successfully Created");
        if (!JsfUtil.isValidationFailed()) {
            organizerEvent = null;
            organizerEvents = null;
        }
    }
    public void edit() {
        persist(JsfUtil.PersistAction.UPDATE, "Event was Successfully Updated");
        organizerEvent = null;
        organizerEvents = null;
    }
    
    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, "Event was Cancelled");
        if (!JsfUtil.isValidationFailed()) {
            organizerEvent = null;
            organizerEvents = null;
        }
    }
    
     private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (organizerEvent != null) {
            try {
                switch (persistAction) {
                    case CREATE:
                    case UPDATE:
                        getOrganizerEventFacade().edit(organizerEvent);
                        break;
                    default:
                        getOrganizerEventFacade().remove(organizerEvent);
                        break;
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

    @FacesConverter(forClass = OrganizerEvent.class)
    public static class UserEventControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            OrganizerEventController controller = (OrganizerEventController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userEventController");
            return controller.getOrganizerEvent(getKey(value));
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, 
                        "object {0} is of type {1}; expected type: {2}", 
                        new Object[]{object, object.getClass().getName(), OrganizerEvent.class.getName()});
                return null;
            }
        }

    }
    
   public Date getToday() {
        Calendar c = Calendar.getInstance(); 
        return c.getTime();
    }

}