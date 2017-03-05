/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author ColacoVM
 */
@Entity
@Table(name = "patient_program", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientProgram.findAll", query = "SELECT p FROM PatientProgram p"),
    @NamedQuery(name = "PatientProgram.findByPatientProgramId", query = "SELECT p FROM PatientProgram p WHERE p.patientProgramId = :patientProgramId"),
    @NamedQuery(name = "PatientProgram.findByDateEnrolled", query = "SELECT p FROM PatientProgram p WHERE p.dateEnrolled = :dateEnrolled"),
    @NamedQuery(name = "PatientProgram.findByDateCompleted", query = "SELECT p FROM PatientProgram p WHERE p.dateCompleted = :dateCompleted"),
    @NamedQuery(name = "PatientProgram.findByDateCreated", query = "SELECT p FROM PatientProgram p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "PatientProgram.findByDateChanged", query = "SELECT p FROM PatientProgram p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "PatientProgram.findByVoided", query = "SELECT p FROM PatientProgram p WHERE p.voided = :voided"),
    @NamedQuery(name = "PatientProgram.findByDateVoided", query = "SELECT p FROM PatientProgram p WHERE p.dateVoided = :dateVoided"),
    @NamedQuery(name = "PatientProgram.findByVoidReason", query = "SELECT p FROM PatientProgram p WHERE p.voidReason = :voidReason"),
    @NamedQuery(name = "PatientProgram.findByUuid", query = "SELECT p FROM PatientProgram p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "PatientProgram.findByidart", query = "SELECT p FROM PatientProgram p WHERE p.idart = :idart")})
public class PatientProgram implements Serializable {

    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "outcome_concept_id", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept outcomeConceptId;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne
    private Location locationId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id")
    @ManyToOne(optional = false)
    private Patient patientId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_program_id", nullable = false)
    private Integer patientProgramId;
    @Column(name = "date_enrolled")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnrolled;
    @Column(name = "date_completed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCompleted;
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
    @JoinColumn(name = "program_id", referencedColumnName = "program_id", nullable = false)
    @ManyToOne(optional = false)
    private Program programId;

    @Basic(optional = false)
    @Column(name = "idart", nullable = true, length = 38)
    private String idart;
    
    public PatientProgram() {
    }

    public PatientProgram(Integer patientProgramId) {
        this.patientProgramId = patientProgramId;
    }

    public PatientProgram(Integer patientProgramId, Date dateCreated, boolean voided, String uuid) {
        this.patientProgramId = patientProgramId;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getPatientProgramId() {
        return patientProgramId;
    }

    public void setPatientProgramId(Integer patientProgramId) {
        this.patientProgramId = patientProgramId;
    }

    public Date getDateEnrolled() {
        return dateEnrolled;
    }

    public void setDateEnrolled(Date dateEnrolled) {
        this.dateEnrolled = dateEnrolled;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
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

    public String getIdart() {
        return idart;
    }

    public void setIdart(String idart) {
        this.idart = idart;
    }
    

    public Program getProgramId() {
        return programId;
    }

    public void setProgramId(Program programId) {
        this.programId = programId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientProgramId != null ? patientProgramId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientProgram)) {
            return false;
        }
        PatientProgram other = (PatientProgram) object;
        if ((this.patientProgramId == null && other.patientProgramId != null) || (this.patientProgramId != null && !this.patientProgramId.equals(other.patientProgramId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ccs.openmrs.migracao.entidades.PatientProgram[ patientProgramId=" + patientProgramId + " ]";
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public Concept getOutcomeConceptId() {
        return outcomeConceptId;
    }

    public void setOutcomeConceptId(Concept outcomeConceptId) {
        this.outcomeConceptId = outcomeConceptId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }
    
}
