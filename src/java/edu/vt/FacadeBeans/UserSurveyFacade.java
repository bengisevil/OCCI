/*
 * Created by Matthew Jehnke on 2018.11.25  * 
 * Copyright © 2018 Matthew Jehnke. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.UserSurvey;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jehnk
 */
@Stateless
public class UserSurveyFacade extends AbstractFacade<UserSurvey> {

    @PersistenceContext(unitName = "OCCIPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserSurveyFacade() {
        super(UserSurvey.class);
    }
    
    public List<UserSurvey> findUserSurveysByUserPrimaryKey(Integer dbPrimaryKey) {
        List<UserSurvey> userSurveys = em.createNamedQuery("UserSurvey.findSurveysByUserPrimaryKey")
                .setParameter("primaryKey", dbPrimaryKey)
                .getResultList();

        return userSurveys;
    }
    
    public List<UserSurvey> findAllUserSurveys() {
        List<UserSurvey> userSurveys = em.createNamedQuery("UserSurvey.findAll")
                .getResultList();

        return userSurveys;
    }
    
}
