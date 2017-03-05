/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
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
 * @author shy
 */
@Entity
@Table(name = "patient_identifier", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientIdentifier.findAll", query = "SELECT p FROM PatientIdentifier p"),
    @NamedQuery(name = "PatientIdentifier.findByPatientIdentifierId", query = "SELECT p FROM PatientIdentifier p WHERE p.patientIdentifierId = :patientIdentifierId"),
    @NamedQuery(name = "PatientIdentifier.findByIdentifier", query = "SELECT p FROM PatientIdentifier p WHERE p.identifier = :identifier"),
    @NamedQuery(name = "PatientIdentifier.findByPreferred", query = "SELECT p FROM PatientIdentifier p WHERE p.preferred = :preferred"),
    @NamedQuery(name = "PatientIdentifier.findByDateCreated", query = "SELECT p FROM PatientIdentifier p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "PatientIdentifier.findByVoided", query = "SELECT p FROM PatientIdentifier p WHERE p.voided = :voided"),
    @NamedQuery(name = "PatientIdentifier.findByDateVoided", query = "SELECT p FROM PatientIdentifier p WHERE p.dateVoided = :dateVoided"),
    @NamedQuery(name = "PatientIdentifier.findByVoidReason", query = "SELECT p FROM PatientIdentifier p WHERE p.voidReason = :voidReason"),
    @NamedQuery(name = "PatientIdentifier.findByUuid", query = "SELECT p FROM PatientIdentifier p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "PatientIdentifier.findByDateChanged", query = "SELECT p FROM PatientIdentifier p WHERE p.dateChanged = :dateChanged")})
public class PatientIdentifier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_identifier_id", nullable = false)
    private Integer patientIdentifierId;
    @Basic(optional = false)
    @Column(name = "identifier", nullable = false, length = 50)
    private String identifier;
    @Basic(optional = false)
    @Column(name = "preferred", nullable = false)
    private boolean preferred;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
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
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "identifier_type", referencedColumnName = "patient_identifier_type_id", nullable = false)
    @ManyToOne(optional = false)
    private PatientIdentifierType identifierType;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne
    private Location locationId;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;

    public PatientIdentifier() {
    }

    public PatientIdentifier(Integer patientIdentifierId) {
        this.patientIdentifierId = patientIdentifierId;
    }

    public PatientIdentifier(Integer patientIdentifierId, String identifier, boolean preferred, Date dateCreated, boolean voided, String uuid) {
        this.patientIdentifierId = patientIdentifierId;
        this.identifier = identifier;
        this.preferred = preferred;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getPatientIdentifierId() {
        return patientIdentifierId;
    }

    public void setPatientIdentifierId(Integer patientIdentifierId) {
        this.patientIdentifierId = patientIdentifierId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public PatientIdentifierType getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(PatientIdentifierType identifierType) {
        this.identifierType = identifierType;
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
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

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientIdentifierId != null ? patientIdentifierId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientIdentifier)) {
            return false;
        }
        PatientIdentifier other = (PatientIdentifier) object;
        if ((this.patientIdentifierId == null && other.patientIdentifierId != null) || (this.patientIdentifierId != null && !this.patientIdentifierId.equals(other.patientIdentifierId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PatientIdentifier[ patientIdentifierId=" + patientIdentifierId + " ]";
    }
    
}
