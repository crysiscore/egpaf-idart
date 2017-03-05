/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shy
 */
@Entity
@Table(name = "person", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findByPersonId", query = "SELECT p FROM Person p WHERE p.personId = :personId"),
    @NamedQuery(name = "Person.findByGender", query = "SELECT p FROM Person p WHERE p.gender = :gender"),
    @NamedQuery(name = "Person.findByBirthdate", query = "SELECT p FROM Person p WHERE p.birthdate = :birthdate"),
    @NamedQuery(name = "Person.findByBirthdateEstimated", query = "SELECT p FROM Person p WHERE p.birthdateEstimated = :birthdateEstimated"),
    @NamedQuery(name = "Person.findByDead", query = "SELECT p FROM Person p WHERE p.dead = :dead"),
    @NamedQuery(name = "Person.findByDeathDate", query = "SELECT p FROM Person p WHERE p.deathDate = :deathDate"),
    @NamedQuery(name = "Person.findByDateCreated", query = "SELECT p FROM Person p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "Person.findByDateChanged", query = "SELECT p FROM Person p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "Person.findByVoided", query = "SELECT p FROM Person p WHERE p.voided = :voided"),
    @NamedQuery(name = "Person.findByDateVoided", query = "SELECT p FROM Person p WHERE p.dateVoided = :dateVoided"),
    @NamedQuery(name = "Person.findByVoidReason", query = "SELECT p FROM Person p WHERE p.voidReason = :voidReason"),
    @NamedQuery(name = "Person.findByUuid", query = "SELECT p FROM Person p WHERE p.uuid = :uuid")})
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "person_id", nullable = false)
    private Integer personId;
    @Column(name = "gender", length = 50)
    private String gender;
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Basic(optional = false)
    @Column(name = "birthdate_estimated", nullable = false)
    private boolean birthdateEstimated;
    @Basic(optional = false)
    @Column(name = "dead", nullable = false)
    private boolean dead;
    @Column(name = "death_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deathDate;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Basic(optional = false)
    @Column(name = "voided", nullable = false)
    private boolean voided;
    @Column(name = "date_voided")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVoided;
    @Column(name = "void_reason", length = 255)
    private String voidReason;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<Users> usersList;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "cause_of_death", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept causeOfDeath;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<PersonName> personNameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "personId")
    private List<Obs> obsList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "person")
    private Patient patient;
    @OneToMany(mappedBy = "personId")
    private List<Provider> providerList;
    @OneToMany(mappedBy = "personId")
    private List<PersonAddress> personAddressList;

    public Person() {
    }

    public Person(Integer personId) {
        this.personId = personId;
    }

    public Person(Integer personId, boolean birthdateEstimated, boolean dead, Date dateCreated, boolean voided, String uuid) {
        this.personId = personId;
        this.birthdateEstimated = birthdateEstimated;
        this.dead = dead;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean getBirthdateEstimated() {
        return birthdateEstimated;
    }

    public void setBirthdateEstimated(boolean birthdateEstimated) {
        this.birthdateEstimated = birthdateEstimated;
    }

    public boolean getDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(Date deathDate) {
        this.deathDate = deathDate;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public boolean getVoided() {
        return voided;
    }

    public void setVoided(boolean voided) {
        this.voided = voided;
    }

    public Date getDateVoided() {
        return dateVoided;
    }

    public void setDateVoided(Date dateVoided) {
        this.dateVoided = dateVoided;
    }

    public String getVoidReason() {
        return voidReason;
    }

    public void setVoidReason(String voidReason) {
        this.voidReason = voidReason;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @XmlTransient
    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public Concept getCauseOfDeath() {
        return causeOfDeath;
    }

    public void setCauseOfDeath(Concept causeOfDeath) {
        this.causeOfDeath = causeOfDeath;
    }

    @XmlTransient
    public List<PersonName> getPersonNameList() {
        return personNameList;
    }

    public void setPersonNameList(List<PersonName> personNameList) {
        this.personNameList = personNameList;
    }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @XmlTransient
    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    @XmlTransient
    public List<PersonAddress> getPersonAddressList() {
        return personAddressList;
    }

    public void setPersonAddressList(List<PersonAddress> personAddressList) {
        this.personAddressList = personAddressList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personId != null ? personId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.personId == null && other.personId != null) || (this.personId != null && !this.personId.equals(other.personId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Person[ personId=" + personId + " ]";
    }
    
}
