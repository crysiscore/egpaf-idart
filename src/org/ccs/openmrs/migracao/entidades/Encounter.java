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
@Table(name = "encounter", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encounter.findAll", query = "SELECT e FROM Encounter e"),
    @NamedQuery(name = "Encounter.findByEncounterId", query = "SELECT e FROM Encounter e WHERE e.encounterId = :encounterId"),
    @NamedQuery(name = "Encounter.findByEncounterDatetime", query = "SELECT e FROM Encounter e WHERE e.encounterDatetime = :encounterDatetime"),
    @NamedQuery(name = "Encounter.findByDateCreated", query = "SELECT e FROM Encounter e WHERE e.dateCreated = :dateCreated"),
    @NamedQuery(name = "Encounter.findByVoided", query = "SELECT e FROM Encounter e WHERE e.voided = :voided"),
    @NamedQuery(name = "Encounter.findByDateVoided", query = "SELECT e FROM Encounter e WHERE e.dateVoided = :dateVoided"),
    @NamedQuery(name = "Encounter.findByVoidReason", query = "SELECT e FROM Encounter e WHERE e.voidReason = :voidReason"),
    @NamedQuery(name = "Encounter.findByUuid", query = "SELECT e FROM Encounter e WHERE e.uuid = :uuid"),
    @NamedQuery(name = "Encounter.findByDateChanged", query = "SELECT e FROM Encounter e WHERE e.dateChanged = :dateChanged")})
public class Encounter implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "encounter_id", nullable = false)
    private Integer encounterId;
    @Basic(optional = false)
    @Column(name = "encounter_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date encounterDatetime;
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
    @JoinColumn(name = "form_id", referencedColumnName = "form_id")
    @ManyToOne
    private Form formId;
    @JoinColumn(name = "encounter_type", referencedColumnName = "encounter_type_id", nullable = false)
    @ManyToOne(optional = false)
    private EncounterType encounterType;
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
    @JoinColumn(name = "visit_id", referencedColumnName = "visit_id")
    @ManyToOne
    private Visit visitId;
    @OneToMany(mappedBy = "encounterId")
    private List<Obs> obsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encounterId")
    private List<EncounterProvider> encounterProviderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encounterId")
    private List<Orders> ordersList;

    public Encounter() {
    }

    public Encounter(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public Encounter(Integer encounterId, Date encounterDatetime, Date dateCreated, boolean voided, String uuid) {
        this.encounterId = encounterId;
        this.encounterDatetime = encounterDatetime;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Integer encounterId) {
        this.encounterId = encounterId;
    }

    public Date getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(Date encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
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

    public Form getFormId() {
        return formId;
    }

    public void setFormId(Form formId) {
        this.formId = formId;
    }

    public EncounterType getEncounterType() {
        return encounterType;
    }

    public void setEncounterType(EncounterType encounterType) {
        this.encounterType = encounterType;
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

    public Visit getVisitId() {
        return visitId;
    }

    public void setVisitId(Visit visitId) {
        this.visitId = visitId;
    }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    @XmlTransient
    public List<EncounterProvider> getEncounterProviderList() {
        return encounterProviderList;
    }

    public void setEncounterProviderList(List<EncounterProvider> encounterProviderList) {
        this.encounterProviderList = encounterProviderList;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encounterId != null ? encounterId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encounter)) {
            return false;
        }
        Encounter other = (Encounter) object;
        if ((this.encounterId == null && other.encounterId != null) || (this.encounterId != null && !this.encounterId.equals(other.encounterId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Encounter[ encounterId=" + encounterId + " ]";
    }
    
}
