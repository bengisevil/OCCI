package edu.vt.EntityBeans;

import edu.vt.EntityBeans.UserPhoto;
import edu.vt.EntityBeans.UserSurvey;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-04T21:49:49")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> eventsList;
    public static volatile SingularAttribute<User, String> address2;
    public static volatile SingularAttribute<User, String> city;
    public static volatile SingularAttribute<User, String> securityAnswer;
    public static volatile SingularAttribute<User, String> address1;
    public static volatile CollectionAttribute<User, UserSurvey> userSurveyCollection;
    public static volatile CollectionAttribute<User, UserPhoto> userPhotoCollection;
    public static volatile SingularAttribute<User, String> owningEvents;
    public static volatile SingularAttribute<User, String> zipcode;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, Integer> organizer;
    public static volatile SingularAttribute<User, String> imageUrl;
    public static volatile SingularAttribute<User, Integer> securityQuestionNumber;
    public static volatile SingularAttribute<User, String> organizerDes;
    public static volatile SingularAttribute<User, String> organizerName;
    public static volatile SingularAttribute<User, String> middleName;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> state;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}