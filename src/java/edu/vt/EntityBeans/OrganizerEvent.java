/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chengen
 */
@Entity
@Table(name = "OrganizerEvent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrganizerEvent.findAll", query = "SELECT u FROM OrganizerEvent u")
    , @NamedQuery(name = "OrganizerEvent.findByOrganizerId", query = "SELECT o FROM OrganizerEvent o WHERE o.organizerId = :organizerId")
    , @NamedQuery(name = "OrganizerEvent.findById", query = "SELECT o FROM OrganizerEvent o WHERE o.id = :id")
    , @NamedQuery(name = "OrganizerEvent.findByName", query = "SELECT o FROM OrganizerEvent o WHERE o.name = :name")
    , @NamedQuery(name = "OrganizerEvent.findByOrganizer", query = "SELECT o FROM OrganizerEvent o WHERE o.organizer = :organizer")
    , @NamedQuery(name = "OrganizerEvent.findByDescription", query = "SELECT o FROM OrganizerEvent o WHERE o.description = :description")
    , @NamedQuery(name = "OrganizerEvent.findByDate", query = "SELECT o FROM OrganizerEvent o WHERE o.date = :date")
    , @NamedQuery(name = "OrganizerEvent.findByZipcode", query = "SELECT o FROM OrganizerEvent o WHERE o.zipcode = :zipcode")
    , @NamedQuery(name = "OrganizerEvent.findByLocation", query = "SELECT o FROM OrganizerEvent o WHERE o.location = :location")
    , @NamedQuery(name = "OrganizerEvent.findByCapacity", query = "SELECT o FROM OrganizerEvent o WHERE o.capacity = :capacity")
    , @NamedQuery(name = "OrganizerEvent.findByPeopleNum", query = "SELECT o FROM OrganizerEvent o WHERE o.peopleNum = :peopleNum")})
public class OrganizerEvent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "organizer_id")
    private int organizerId;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "organizer")
    private String organizer;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "zipcode")
    private String zipcode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Column(name = "capacity")
    private int capacity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "people_num")
    private int peopleNum;

    public OrganizerEvent() {
    }

    public OrganizerEvent(Integer id) {
        this.id = id;
    }

    public OrganizerEvent(Integer id, int organizerId, String name, String organizer, String description, Date date, String zipcode, String location, int capacity, int peopleNum) {
        this.id = id;
        this.organizerId = organizerId;
        this.name = name;
        this.organizer = organizer;
        this.description = description;
        this.date = date;
        this.zipcode = zipcode;
        this.location = location;
        this.capacity = capacity;
        this.peopleNum = peopleNum;
    }

    public int getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(int organizerId) {
        this.organizerId = organizerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return name;
    }

    public void setEventName(String name) {
        this.name = name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrganizerEvent)) {
            return false;
        }
        OrganizerEvent other = (OrganizerEvent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edu.vt.EntityBeans.OrganizerEvent[ id=" + id + " ]";
    }
    
}