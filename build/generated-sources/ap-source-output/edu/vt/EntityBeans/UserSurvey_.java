package edu.vt.EntityBeans;

import edu.vt.EntityBeans.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-04T21:49:49")
@StaticMetamodel(UserSurvey.class)
public class UserSurvey_ { 

    public static volatile SingularAttribute<UserSurvey, Date> dateEntered;
    public static volatile SingularAttribute<UserSurvey, String> survey;
    public static volatile SingularAttribute<UserSurvey, Integer> id;
    public static volatile SingularAttribute<UserSurvey, User> userId;

}