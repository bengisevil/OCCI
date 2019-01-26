/*
 * Created by Matthew Jehnke on 2018.11.25  * 
 * Copyright © 2018 Matthew Jehnke. All rights reserved. * 
 */
package edu.vt.FacadeBeans;

import edu.vt.EntityBeans.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jehnk
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "OCCIPU")
    private EntityManager em;

    // @Override annotation indicates that the super class AbstractFacade's getEntityManager() method is overridden.
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /* 
    This constructor method invokes the parent abstract class AbstractFacade.java's
    constructor method AbstractFacade, which in turn initializes its entityClass instance
    variable with the User class object reference returned by the User.class.
     */
    public UserFacade() {
        super(User.class);
    }
    
    public List<User> findAllUsers() {
        /*
        The following @NamedQuery is defined in UserSurvey.java entity class file:
        @NamedQuery(name = "UserSurvey.findSurveysByUserPrimaryKey", 
            query = "SELECT u FROM UserSurvey u WHERE u.userId.id = :primaryKey")
        
        The following statement obtaines the results from the named database query.
         */
        List<User> userSurveys = em.createNamedQuery("User.findAll")
                .getResultList();

        return userSurveys;
    }
    
    /*
    ***********************************************************
    The following methods are added to the auto generated code.
    ***********************************************************
     */
    /**
     * @param id is the Primary Key of the User entity in a table row in the database.
     * @return object reference of the User object whose primary key is id
     */
    public User getUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(User.class, id);
    }

    /**
     * @param username is the username attribute (column) value of the user
     * @return object reference of the User entity whose user name is username
     */
    public User findByUsername(String username) {
        if (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                .setParameter("username", username)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT c FROM User c WHERE c.username = :username")
                    .setParameter("username", username)
                    .getSingleResult());
        }
    }
    
    public User findByUserId(Integer id){
        if (em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                .setParameter("id", id)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT u FROM User u WHERE u.id = :id")
                    .setParameter("id", id)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the User entity whose primary key is id
     * @param id is the Primary Key of the User entity in a table row in the database.
     */
    public void deleteUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        User user = em.find(User.class, id);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(user); 
    }
    
}
