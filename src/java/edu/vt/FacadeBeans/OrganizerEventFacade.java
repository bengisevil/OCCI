/*
 * Created by Chengen Li on 2018.11.25  * 
 * Copyright © 2018 Chengen Li. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.OrganizerEvent;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jehnk
 */
@Stateless
public class OrganizerEventFacade extends AbstractFacade<OrganizerEvent> {

    @PersistenceContext(unitName = "OCCIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrganizerEventFacade() {
        super(OrganizerEvent.class);
    }
    
    public OrganizerEvent findUserEventsById(Integer primaryKey) {
        return em.find(OrganizerEvent.class, primaryKey);
    }

    public List<OrganizerEvent> findByEventOrganizer(Integer key) {
 
        List<OrganizerEvent> userEvents = em.createNamedQuery("OrganizerEvent.findByOrganizerId")
                .setParameter("organizerId", key)
                .getResultList();
        return userEvents;
    }
    
}