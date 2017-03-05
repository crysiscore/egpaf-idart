/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "concept", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concept.findAll", query = "SELECT c FROM Concept c"),
    @NamedQuery(name = "Concept.findByConceptId", query = "SELECT c FROM Concept c WHERE c.conceptId = :conceptId"),
    @NamedQuery(name = "Concept.findByRetired", query = "SELECT c FROM Concept c WHERE c.retired = :retired"),
    @NamedQuery(name = "Concept.findByShortName", query = "SELECT c FROM Concept c WHERE c.shortName = :shortName"),
    @NamedQuery(name = "Concept.findByIsSet", query = "SELECT c FROM Concept c WHERE c.isSet = :isSet"),
    @NamedQuery(name = "Concept.findByDateCreated", query = "SELECT c FROM Concept c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "Concept.findByVersion", query = "SELECT c FROM Concept c WHERE c.version = :version"),
    @NamedQuery(name = "Concept.findByDateChanged", query = "SELECT c FROM Concept c WHERE c.dateChanged = :dateChanged"),
    @NamedQuery(name = "Concept.findByDateRetired", query = "SELECT c FROM Concept c WHERE c.dateRetired = :dateRetired"),
    @NamedQuery(name = "Concept.findByRetireReason", query = "SELECT c FROM Concept c WHERE c.retireReason = :retireReason"),
    @NamedQuery(name = "Concept.findByUuid", query = "SELECT c FROM Concept c WHERE c.uuid = :uuid"),
})
public class Concept implements Serializable {

  
    @OneToMany(mappedBy = "outcomesConceptId")
    private Collection<Program> programCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptId")
    private Collection<Program> programCollection1;
    @OneToMany(mappedBy = "outcomeConceptId")
    private Collection<PatientProgram> patientProgramCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "concept_id", nullable = false)
    private Integer conceptId;
    @Basic(optional = false)
    @Column(name = "retired", nullable = false)
    private boolean retired;
    @Column(name = "short_name", length = 255)
    private String shortName;
    @Lob
    @Column(name = "description", length = 65535)
    private String description;
    @Lob
    @Column(name = "form_text", length = 65535)
    private String formText;
    @Basic(optional = false)
    @Column(name = "is_set", nullable = false)
    private boolean isSet;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "version", length = 50)
    private String version;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Column(name = "date_retired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRetired;
    @Column(name = "retire_reason", length = 255)
    private String retireReason;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @OneToMany(mappedBy = "causeOfDeath")
    private List<Person> personList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "datatype_id", referencedColumnName = "concept_datatype_id", nullable = false)
    @ManyToOne(optional = false)
    private ConceptDatatype datatypeId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "class_id", referencedColumnName = "concept_class_id", nullable = false)
    @ManyToOne(optional = false)
    private ConceptClass classId;
    @OneToMany(mappedBy = "conceptId")
    private List<ConceptName> conceptNameList;
    @OneToMany(mappedBy = "indicationConceptId")
    private List<Visit> visitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptId")
    private List<Obs> obsList;
    @OneToMany(mappedBy = "valueCoded")
    private List<Obs> obsList1;
    @OneToMany(mappedBy = "route")
    private List<Drug> drugList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptId")
    private List<Drug> drugList1;
    @OneToMany(mappedBy = "dosageForm")
    private List<Drug> drugList2;
    @OneToMany(mappedBy = "orderReason")
    private List<Orders> ordersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptId")
    private List<Orders> ordersList1;

    public Concept() {
    }

    public Concept(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public Concept(Integer conceptId, boolean retired, boolean isSet, Date dateCreated, String uuid, long changedById, long creatorId, long retiredById) {
        this.conceptId = conceptId;
        this.retired = retired;
        this.isSet = isSet;
        this.dateCreated = dateCreated;
        this.uuid = uuid;
    }

    public Integer getConceptId() {
        return conceptId;
    }

    public void setConceptId(Integer conceptId) {
        this.conceptId = conceptId;
    }

    public boolean getRetired() {
        return retired;
    }

    public void setRetired(boolean retired) {
        this.retired = retired;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFormText() {
        return formText;
    }

    public void setFormText(String formText) {
        this.formText = formText;
    }

    public boolean getIsSet() {
        return isSet;
    }

    public void setIsSet(boolean isSet) {
        this.isSet = isSet;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
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
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    public Users getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Users retiredBy) {
        this.retiredBy = retiredBy;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public ConceptDatatype getDatatypeId() {
        return datatypeId;
    }

    public void setDatatypeId(ConceptDatatype datatypeId) {
        this.datatypeId = datatypeId;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public ConceptClass getClassId() {
        return classId;
    }

    public void setClassId(ConceptClass classId) {
        this.classId = classId;
    }

    @XmlTransient
    public List<ConceptName> getConceptNameList() {
        return conceptNameList;
    }

    public void setConceptNameList(List<ConceptName> conceptNameList) {
        this.conceptNameList = conceptNameList;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptId != null ? conceptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concept)) {
            return false;
        }
        Concept other = (Concept) object;
        if ((this.conceptId == null && other.conceptId != null) || (this.conceptId != null && !this.conceptId.equals(other.conceptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Concept[ conceptId=" + conceptId + " ]";
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
    
}
