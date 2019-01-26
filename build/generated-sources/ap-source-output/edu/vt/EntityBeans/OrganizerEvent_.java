package edu.vt.EntityBeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-04T21:49:49")
@StaticMetamodel(OrganizerEvent.class)
public class OrganizerEvent_ { 

    public static volatile SingularAttribute<OrganizerEvent, Date> date;
    public static volatile SingularAttribute<OrganizerEvent, String> zipcode;
    public static volatile SingularAttribute<OrganizerEvent, Integer> organizerId;
    public static volatile SingularAttribute<OrganizerEvent, String> organizer;
    public static volatile SingularAttribute<OrganizerEvent, String> name;
    public static volatile SingularAttribute<OrganizerEvent, String> description;
    public static volatile SingularAttribute<OrganizerEvent, String> location;
    public static volatile SingularAttribute<OrganizerEvent, Integer> id;
    public static volatile SingularAttribute<OrganizerEvent, Integer> peopleNum;
    public static volatile SingularAttribute<OrganizerEvent, Integer> capacity;

}