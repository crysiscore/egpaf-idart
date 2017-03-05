/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shy
 */
@Entity
@Table(name = "users", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserId", query = "SELECT u FROM Users u WHERE u.userId = :userId"),
    @NamedQuery(name = "Users.findBySystemId", query = "SELECT u FROM Users u WHERE u.systemId = :systemId"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findBySalt", query = "SELECT u FROM Users u WHERE u.salt = :salt"),
    @NamedQuery(name = "Users.findBySecretQuestion", query = "SELECT u FROM Users u WHERE u.secretQuestion = :secretQuestion"),
    @NamedQuery(name = "Users.findBySecretAnswer", query = "SELECT u FROM Users u WHERE u.secretAnswer = :secretAnswer"),
    @NamedQuery(name = "Users.findByDateCreated", query = "SELECT u FROM Users u WHERE u.dateCreated = :dateCreated"),
    @NamedQuery(name = "Users.findByDateChanged", query = "SELECT u FROM Users u WHERE u.dateChanged = :dateChanged"),
    @NamedQuery(name = "Users.findByRetired", query = "SELECT u FROM Users u WHERE u.retired = :retired"),
    @NamedQuery(name = "Users.findByDateRetired", query = "SELECT u FROM Users u WHERE u.dateRetired = :dateRetired"),
    @NamedQuery(name = "Users.findByRetireReason", query = "SELECT u FROM Users u WHERE u.retireReason = :retireReason"),
    @NamedQuery(name = "Users.findByUuid", query = "SELECT u FROM Users u WHERE u.uuid = :uuid"),
})
public class Users implements Serializable {

    @OneToMany(mappedBy = "changedBy")
    private Collection<Program> programCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private Collection<Program> programCollection1;
    @OneToMany(mappedBy = "voidedBy")
    private Collection<PatientProgram> patientProgramCollection;
    @OneToMany(mappedBy = "changedBy")
    private Collection<PatientProgram> patientProgramCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private Collection<PatientProgram> patientProgramCollection2;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id", nullable = false)
    private Integer userId;
    @Basic(optional = false)
    @Column(name = "system_id", nullable = false, length = 50)
    private String systemId;
    @Column(name = "username", length = 50)
    private String username;
    @Column(name = "password", length = 128)
    private String password;
    @Column(name = "salt", length = 128)
    private String salt;
    @Column(name = "secret_question", length = 255)
    private String secretQuestion;
    @Column(name = "secret_answer", length = 255)
    private String secretAnswer;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Basic(optional = false)
    @Column(name = "retired", nullable = false)
    private boolean retired;
    @Column(name = "date_retired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRetired;
    @Column(name = "retire_reason", length = 255)
    private String retireReason;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @OneToMany(mappedBy = "retiredBy")
    private List<Users> usersList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @OneToMany(mappedBy = "changedBy")
    private List<Users> usersList1;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Users> usersList2;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;
    @OneToMany(mappedBy = "voidedBy")
    private List<Person> personList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Person> personList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Person> personList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<Concept> conceptList;
    @OneToMany(mappedBy = "changedBy")
    private List<Concept> conceptList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Concept> conceptList2;
    @OneToMany(mappedBy = "voidedBy")
    private List<PersonName> personNameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<PersonName> personNameList1;
    @OneToMany(mappedBy = "changedBy")
    private List<PersonName> personNameList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<Location> locationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Location> locationList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Location> locationList2;
    @OneToMany(mappedBy = "voidedBy")
    private List<ConceptName> conceptNameList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<ConceptName> conceptNameList1;
    @OneToMany(mappedBy = "retiredBy")
    private List<EncounterRole> encounterRoleList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<EncounterRole> encounterRoleList1;
    @OneToMany(mappedBy = "changedBy")
    private List<EncounterRole> encounterRoleList2;
    @OneToMany(mappedBy = "voidedBy")
    private List<Visit> visitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Visit> visitList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Visit> visitList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Encounter> encounterList;
    @OneToMany(mappedBy = "voidedBy")
    private List<Encounter> encounterList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Encounter> encounterList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<PatientIdentifier> patientIdentifierList;
    @OneToMany(mappedBy = "voidedBy")
    private List<PatientIdentifier> patientIdentifierList1;
    @OneToMany(mappedBy = "changedBy")
    private List<PatientIdentifier> patientIdentifierList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<EncounterType> encounterTypeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<EncounterType> encounterTypeList1;
    @OneToMany(mappedBy = "retiredBy")
    private List<ConceptDatatype> conceptDatatypeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<ConceptDatatype> conceptDatatypeList1;
    @OneToMany(mappedBy = "voidedBy")
    private List<Obs> obsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Obs> obsList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Patient> patientList;
    @OneToMany(mappedBy = "voidedBy")
    private List<Patient> patientList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Patient> patientList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<PatientIdentifierType> patientIdentifierTypeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<PatientIdentifierType> patientIdentifierTypeList1;
    @OneToMany(mappedBy = "retiredBy")
    private List<VisitType> visitTypeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<VisitType> visitTypeList1;
    @OneToMany(mappedBy = "changedBy")
    private List<VisitType> visitTypeList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<Drug> drugList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Drug> drugList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Drug> drugList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<CareSetting> careSettingList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<CareSetting> careSettingList1;
    @OneToMany(mappedBy = "changedBy")
    private List<CareSetting> careSettingList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<Form> formList;
    @OneToMany(mappedBy = "changedBy")
    private List<Form> formList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Form> formList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Provider> providerList;
    @OneToMany(mappedBy = "retiredBy")
    private List<Provider> providerList1;
    @OneToMany(mappedBy = "changedBy")
    private List<Provider> providerList2;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<EncounterProvider> encounterProviderList;
    @OneToMany(mappedBy = "voidedBy")
    private List<EncounterProvider> encounterProviderList1;
    @OneToMany(mappedBy = "changedBy")
    private List<EncounterProvider> encounterProviderList2;
    @OneToMany(mappedBy = "voidedBy")
    private List<Orders> ordersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<Orders> ordersList1;
    @OneToMany(mappedBy = "changedBy")
    private List<PersonAddress> personAddressList;
    @OneToMany(mappedBy = "voidedBy")
    private List<PersonAddress> personAddressList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<PersonAddress> personAddressList2;
    @OneToMany(mappedBy = "retiredBy")
    private List<ConceptClass> conceptClassList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<ConceptClass> conceptClassList1;
    @OneToMany(mappedBy = "retiredBy")
    private List<OrderType> orderTypeList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private List<OrderType> orderTypeList1;
    @OneToMany(mappedBy = "changedBy")
    private List<OrderType> orderTypeList2;

    public Users() {
    }

    public Users(Integer userId) {
        this.userId = userId;
    }

    public Users(Integer userId, String systemId, Date dateCreated, boolean retired, String uuid) {
        this.userId = userId;
        this.systemId = systemId;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSecretQuestion() {
        return secretQuestion;
    }

    public void setSecretQuestion(String secretQuestion) {
        this.secretQuestion = secretQuestion;
    }

    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
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

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public Date getDateRetired() {
        return dateRetired;
    }

    public void setDateRetired(Date dateRetired) {
        this.dateRetired = dateRetired;
    }

    public String getRetireReason() {
        return retireReason;
    }

    public void setRetireReason(String retireReason) {
        this.retireReason = retireReason;
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

    public Users getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Users retiredBy) {
        this.retiredBy = retiredBy;
    }

    @XmlTransient
    public List<Users> getUsersList1() {
        return usersList1;
    }

    public void setUsersList1(List<Users> usersList1) {
        this.usersList1 = usersList1;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    @XmlTransient
    public List<Users> getUsersList2() {
        return usersList2;
    }

    public void setUsersList2(List<Users> usersList2) {
        this.usersList2 = usersList2;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    @XmlTransient
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @XmlTransient
    public List<Person> getPersonList1() {
        return personList1;
    }

    public void setPersonList1(List<Person> personList1) {
        this.personList1 = personList1;
    }

    @XmlTransient
    public List<Person> getPersonList2() {
        return personList2;
    }

    public void setPersonList2(List<Person> personList2) {
        this.personList2 = personList2;
    }

    @XmlTransient
    public List<Concept> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<Concept> conceptList) {
        this.conceptList = conceptList;
    }

    @XmlTransient
    public List<Concept> getConceptList1() {
        return conceptList1;
    }

    public void setConceptList1(List<Concept> conceptList1) {
        this.conceptList1 = conceptList1;
    }

    @XmlTransient
    public List<Concept> getConceptList2() {
        return conceptList2;
    }

    public void setConceptList2(List<Concept> conceptList2) {
        this.conceptList2 = conceptList2;
    }

    @XmlTransient
    public List<PersonName> getPersonNameList() {
        return personNameList;
    }

    public void setPersonNameList(List<PersonName> personNameList) {
        this.personNameList = personNameList;
    }

    @XmlTransient
    public List<PersonName> getPersonNameList1() {
        return personNameList1;
    }

    public void setPersonNameList1(List<PersonName> personNameList1) {
        this.personNameList1 = personNameList1;
    }

    @XmlTransient
    public List<PersonName> getPersonNameList2() {
        return personNameList2;
    }

    public void setPersonNameList2(List<PersonName> personNameList2) {
        this.personNameList2 = personNameList2;
    }

    @XmlTransient
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @XmlTransient
    public List<Location> getLocationList1() {
        return locationList1;
    }

    public void setLocationList1(List<Location> locationList1) {
        this.locationList1 = locationList1;
    }

    @XmlTransient
    public List<Location> getLocationList2() {
        return locationList2;
    }

    public void setLocationList2(List<Location> locationList2) {
        this.locationList2 = locationList2;
    }

    @XmlTransient
    public List<ConceptName> getConceptNameList() {
        return conceptNameList;
    }

    public void setConceptNameList(List<ConceptName> conceptNameList) {
        this.conceptNameList = conceptNameList;
    }

    @XmlTransient
    public List<ConceptName> getConceptNameList1() {
        return conceptNameList1;
    }

    public void setConceptNameList1(List<ConceptName> conceptNameList1) {
        this.conceptNameList1 = conceptNameList1;
    }

    @XmlTransient
    public List<EncounterRole> getEncounterRoleList() {
        return encounterRoleList;
    }

    public void setEncounterRoleList(List<EncounterRole> encounterRoleList) {
        this.encounterRoleList = encounterRoleList;
    }

    @XmlTransient
    public List<EncounterRole> getEncounterRoleList1() {
        return encounterRoleList1;
    }

    public void setEncounterRoleList1(List<EncounterRole> encounterRoleList1) {
        this.encounterRoleList1 = encounterRoleList1;
    }

    @XmlTransient
    public List<EncounterRole> getEncounterRoleList2() {
        return encounterRoleList2;
    }

    public void setEncounterRoleList2(List<EncounterRole> encounterRoleList2) {
        this.encounterRoleList2 = encounterRoleList2;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    @XmlTransient
    public List<Visit> getVisitList1() {
        return visitList1;
    }

    public void setVisitList1(List<Visit> visitList1) {
        this.visitList1 = visitList1;
    }

    @XmlTransient
    public List<Visit> getVisitList2() {
        return visitList2;
    }

    public void setVisitList2(List<Visit> visitList2) {
        this.visitList2 = visitList2;
    }

    @XmlTransient
    public List<Encounter> getEncounterList() {
        return encounterList;
    }

    public void setEncounterList(List<Encounter> encounterList) {
        this.encounterList = encounterList;
    }

    @XmlTransient
    public List<Encounter> getEncounterList1() {
        return encounterList1;
    }

    public void setEncounterList1(List<Encounter> encounterList1) {
        this.encounterList1 = encounterList1;
    }

    @XmlTransient
    public List<Encounter> getEncounterList2() {
        return encounterList2;
    }

    public void setEncounterList2(List<Encounter> encounterList2) {
        this.encounterList2 = encounterList2;
    }

    @XmlTransient
    public List<PatientIdentifier> getPatientIdentifierList() {
        return patientIdentifierList;
    }

    public void setPatientIdentifierList(List<PatientIdentifier> patientIdentifierList) {
        this.patientIdentifierList = patientIdentifierList;
    }

    @XmlTransient
    public List<PatientIdentifier> getPatientIdentifierList1() {
        return patientIdentifierList1;
    }

    public void setPatientIdentifierList1(List<PatientIdentifier> patientIdentifierList1) {
        this.patientIdentifierList1 = patientIdentifierList1;
    }

    @XmlTransient
    public List<PatientIdentifier> getPatientIdentifierList2() {
        return patientIdentifierList2;
    }

    public void setPatientIdentifierList2(List<PatientIdentifier> patientIdentifierList2) {
        this.patientIdentifierList2 = patientIdentifierList2;
    }

    @XmlTransient
    public List<EncounterType> getEncounterTypeList() {
        return encounterTypeList;
    }

    public void setEncounterTypeList(List<EncounterType> encounterTypeList) {
        this.encounterTypeList = encounterTypeList;
    }

    @XmlTransient
    public List<EncounterType> getEncounterTypeList1() {
        return encounterTypeList1;
    }

    public void setEncounterTypeList1(List<EncounterType> encounterTypeList1) {
        this.encounterTypeList1 = encounterTypeList1;
    }

    @XmlTransient
    public List<ConceptDatatype> getConceptDatatypeList() {
        return conceptDatatypeList;
    }

    public void setConceptDatatypeList(List<ConceptDatatype> conceptDatatypeList) {
        this.conceptDatatypeList = conceptDatatypeList;
    }

    @XmlTransient
    public List<ConceptDatatype> getConceptDatatypeList1() {
        return conceptDatatypeList1;
    }

    public void setConceptDatatypeList1(List<ConceptDatatype> conceptDatatypeList1) {
        this.conceptDatatypeList1 = conceptDatatypeList1;
    }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    @XmlTransient
    public List<Obs> getObsList1() {
        return obsList1;
    }

    public void setObsList1(List<Obs> obsList1) {
        this.obsList1 = obsList1;
    }

    @XmlTransient
    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @XmlTransient
    public List<Patient> getPatientList1() {
        return patientList1;
    }

    public void setPatientList1(List<Patient> patientList1) {
        this.patientList1 = patientList1;
    }

    @XmlTransient
    public List<Patient> getPatientList2() {
        return patientList2;
    }

    public void setPatientList2(List<Patient> patientList2) {
        this.patientList2 = patientList2;
    }

    @XmlTransient
    public List<PatientIdentifierType> getPatientIdentifierTypeList() {
        return patientIdentifierTypeList;
    }

    public void setPatientIdentifierTypeList(List<PatientIdentifierType> patientIdentifierTypeList) {
        this.patientIdentifierTypeList = patientIdentifierTypeList;
    }

    @XmlTransient
    public List<PatientIdentifierType> getPatientIdentifierTypeList1() {
        return patientIdentifierTypeList1;
    }

    public void setPatientIdentifierTypeList1(List<PatientIdentifierType> patientIdentifierTypeList1) {
        this.patientIdentifierTypeList1 = patientIdentifierTypeList1;
    }

    @XmlTransient
    public List<VisitType> getVisitTypeList() {
        return visitTypeList;
    }

    public void setVisitTypeList(List<VisitType> visitTypeList) {
        this.visitTypeList = visitTypeList;
    }

    @XmlTransient
    public List<VisitType> getVisitTypeList1() {
        return visitTypeList1;
    }

    public void setVisitTypeList1(List<VisitType> visitTypeList1) {
        this.visitTypeList1 = visitTypeList1;
    }

    @XmlTransient
    public List<VisitType> getVisitTypeList2() {
        return visitTypeList2;
    }

    public void setVisitTypeList2(List<VisitType> visitTypeList2) {
        this.visitTypeList2 = visitTypeList2;
    }

    @XmlTransient
    public List<Drug> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<Drug> drugList) {
        this.drugList = drugList;
    }

    @XmlTransient
    public List<Drug> getDrugList1() {
        return drugList1;
    }

    public void setDrugList1(List<Drug> drugList1) {
        this.drugList1 = drugList1;
    }

    @XmlTransient
    public List<Drug> getDrugList2() {
        return drugList2;
    }

    public void setDrugList2(List<Drug> drugList2) {
        this.drugList2 = drugList2;
    }

    @XmlTransient
    public List<CareSetting> getCareSettingList() {
        return careSettingList;
    }

    public void setCareSettingList(List<CareSetting> careSettingList) {
        this.careSettingList = careSettingList;
    }

    @XmlTransient
    public List<CareSetting> getCareSettingList1() {
        return careSettingList1;
    }

    public void setCareSettingList1(List<CareSetting> careSettingList1) {
        this.careSettingList1 = careSettingList1;
    }

    @XmlTransient
    public List<CareSetting> getCareSettingList2() {
        return careSettingList2;
    }

    public void setCareSettingList2(List<CareSetting> careSettingList2) {
        this.careSettingList2 = careSettingList2;
    }

    @XmlTransient
    public List<Form> getFormList() {
        return formList;
    }

    public void setFormList(List<Form> formList) {
        this.formList = formList;
    }

    @XmlTransient
    public List<Form> getFormList1() {
        return formList1;
    }

    public void setFormList1(List<Form> formList1) {
        this.formList1 = formList1;
    }

    @XmlTransient
    public List<Form> getFormList2() {
        return formList2;
    }

    public void setFormList2(List<Form> formList2) {
        this.formList2 = formList2;
    }

    @XmlTransient
    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

    @XmlTransient
    public List<Provider> getProviderList1() {
        return providerList1;
    }

    public void setProviderList1(List<Provider> providerList1) {
        this.providerList1 = providerList1;
    }

    @XmlTransient
    public List<Provider> getProviderList2() {
        return providerList2;
    }

    public void setProviderList2(List<Provider> providerList2) {
        this.providerList2 = providerList2;
    }

    @XmlTransient
    public List<EncounterProvider> getEncounterProviderList() {
        return encounterProviderList;
    }

    public void setEncounterProviderList(List<EncounterProvider> encounterProviderList) {
        this.encounterProviderList = encounterProviderList;
    }

    @XmlTransient
    public List<EncounterProvider> getEncounterProviderList1() {
        return encounterProviderList1;
    }

    public void setEncounterProviderList1(List<EncounterProvider> encounterProviderList1) {
        this.encounterProviderList1 = encounterProviderList1;
    }

    @XmlTransient
    public List<EncounterProvider> getEncounterProviderList2() {
        return encounterProviderList2;
    }

    public void setEncounterProviderList2(List<EncounterProvider> encounterProviderList2) {
        this.encounterProviderList2 = encounterProviderList2;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @XmlTransient
    public List<Orders> getOrdersList1() {
        return ordersList1;
    }

    public void setOrdersList1(List<Orders> ordersList1) {
        this.ordersList1 = ordersList1;
    }


    @XmlTransient
    public List<PersonAddress> getPersonAddressList() {
        return personAddressList;
    }

    public void setPersonAddressList(List<PersonAddress> personAddressList) {
        this.personAddressList = personAddressList;
    }

    @XmlTransient
    public List<PersonAddress> getPersonAddressList1() {
        return personAddressList1;
    }

    public void setPersonAddressList1(List<PersonAddress> personAddressList1) {
        this.personAddressList1 = personAddressList1;
    }

    @XmlTransient
    public List<PersonAddress> getPersonAddressList2() {
        return personAddressList2;
    }

    public void setPersonAddressList2(List<PersonAddress> personAddressList2) {
        this.personAddressList2 = personAddressList2;
    }

    @XmlTransient
    public List<ConceptClass> getConceptClassList() {
        return conceptClassList;
    }

    public void setConceptClassList(List<ConceptClass> conceptClassList) {
        this.conceptClassList = conceptClassList;
    }

    @XmlTransient
    public List<ConceptClass> getConceptClassList1() {
        return conceptClassList1;
    }

    public void setConceptClassList1(List<ConceptClass> conceptClassList1) {
        this.conceptClassList1 = conceptClassList1;
    }

    @XmlTransient
    public List<OrderType> getOrderTypeList() {
        return orderTypeList;
    }

    public void setOrderTypeList(List<OrderType> orderTypeList) {
        this.orderTypeList = orderTypeList;
    }

    @XmlTransient
    public List<OrderType> getOrderTypeList1() {
        return orderTypeList1;
    }

    public void setOrderTypeList1(List<OrderType> orderTypeList1) {
        this.orderTypeList1 = orderTypeList1;
    }

    @XmlTransient
    public List<OrderType> getOrderTypeList2() {
        return orderTypeList2;
    }

    public void setOrderTypeList2(List<OrderType> orderTypeList2) {
        this.orderTypeList2 = orderTypeList2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Users[ userId=" + userId + " ]";
    }

    @XmlTransient
    public Collection<Program> getProgramCollection() {
        return programCollection;
    }

    public void setProgramCollection(Collection<Program> programCollection) {
        this.programCollection = programCollection;
    }

    @XmlTransient
    public Collection<Program> getProgramCollection1() {
        return programCollection1;
    }

    public void setProgramCollection1(Collection<Program> programCollection1) {
        this.programCollection1 = programCollection1;
    }

    @XmlTransient
    public Collection<PatientProgram> getPatientProgramCollection() {
        return patientProgramCollection;
    }

    public void setPatientProgramCollection(Collection<PatientProgram> patientProgramCollection) {
        this.patientProgramCollection = patientProgramCollection;
    }

    @XmlTransient
    public Collection<PatientProgram> getPatientProgramCollection1() {
        return patientProgramCollection1;
    }

    public void setPatientProgramCollection1(Collection<PatientProgram> patientProgramCollection1) {
        this.patientProgramCollection1 = patientProgramCollection1;
    }

    @XmlTransient
    public Collection<PatientProgram> getPatientProgramCollection2() {
        return patientProgramCollection2;
    }

    public void setPatientProgramCollection2(Collection<PatientProgram> patientProgramCollection2) {
        this.patientProgramCollection2 = patientProgramCollection2;
    }
    
}
