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
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shy
 */
@Entity
@Table(name = "visit", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Visit.findAll", query = "SELECT v FROM Visit v"),
    @NamedQuery(name = "Visit.findByVisitId", query = "SELECT v FROM Visit v WHERE v.visitId = :visitId"),
    @NamedQuery(name = "Visit.findByDateStarted", query = "SELECT v FROM Visit v WHERE v.dateStarted = :dateStarted"),
    @NamedQuery(name = "Visit.findByDateStopped", query = "SELECT v FROM Visit v WHERE v.dateStopped = :dateStopped"),
    @NamedQuery(name = "Visit.findByDateCreated", query = "SELECT v FROM Visit v WHERE v.dateCreated = :dateCreated"),
    @NamedQuery(name = "Visit.findByDateChanged", query = "SELECT v FROM Visit v WHERE v.dateChanged = :dateChanged"),
    @NamedQuery(name = "Visit.findByVoided", query = "SELECT v FROM Visit v WHERE v.voided = :voided"),
    @NamedQuery(name = "Visit.findByDateVoided", query = "SELECT v FROM Visit v WHERE v.dateVoided = :dateVoided"),
    @NamedQuery(name = "Visit.findByVoidReason", query = "SELECT v FROM Visit v WHERE v.voidReason = :voidReason"),
    @NamedQuery(name = "Visit.findByUuid", query = "SELECT v FROM Visit v WHERE v.uuid = :uuid")})
public class Visit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visit_id", nullable = false)
    private Integer visitId;
    @Basic(optional = false)
    @Column(name = "date_started", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStarted;
    @Column(name = "date_stopped")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStopped;
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
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "visit_type_id", referencedColumnName = "visit_type_id", nullable = false)
    @ManyToOne(optional = false)
    private VisitType visitTypeId;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne
    private Location locationId;
    @JoinColumn(name = "indication_concept_id", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept indicationConceptId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @OneToMany(mappedBy = "visitId")
    private List<Encounter> encounterList;

    public Visit() {
    }

    public Visit(Integer visitId) {
        this.visitId = visitId;
    }

    public Visit(Integer visitId, Date dateStarted, Date dateCreated, boolean voided, String uuid) {
        this.visitId = visitId;
        this.dateStarted = dateStarted;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public Date getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
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

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
    }

    public VisitType getVisitTypeId() {
        return visitTypeId;
    }

    public void setVisitTypeId(VisitType visitTypeId) {
        this.visitTypeId = visitTypeId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Concept getIndicationConceptId() {
        return indicationConceptId;
    }

    public void setIndicationConceptId(Concept indicationConceptId) {
        this.indicationConceptId = indicationConceptId;
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

    @XmlTransient
    public List<Encounter> getEncounterList() {
        return encounterList;
    }

    public void setEncounterList(List<Encounter> encounterList) {
        this.encounterList = encounterList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitId != null ? visitId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Visit)) {
            return false;
        }
        Visit other = (Visit) object;
        if ((this.visitId == null && other.visitId != null) || (this.visitId != null && !this.visitId.equals(other.visitId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Visit[ visitId=" + visitId + " ]";
    }
    
}
