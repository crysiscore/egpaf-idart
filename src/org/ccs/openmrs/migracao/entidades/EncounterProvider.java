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
@Table(name = "encounter_provider", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncounterProvider.findAll", query = "SELECT e FROM EncounterProvider e"),
    @NamedQuery(name = "EncounterProvider.findByEncounterProviderId", query = "SELECT e FROM EncounterProvider e WHERE e.encounterProviderId = :encounterProviderId"),
    @NamedQuery(name = "EncounterProvider.findByDateCreated", query = "SELECT e FROM EncounterProvider e WHERE e.dateCreated = :dateCreated"),
    @NamedQuery(name = "EncounterProvider.findByDateChanged", query = "SELECT e FROM EncounterProvider e WHERE e.dateChanged = :dateChanged"),
    @NamedQuery(name = "EncounterProvider.findByVoided", query = "SELECT e FROM EncounterProvider e WHERE e.voided = :voided"),
    @NamedQuery(name = "EncounterProvider.findByDateVoided", query = "SELECT e FROM EncounterProvider e WHERE e.dateVoided = :dateVoided"),
    @NamedQuery(name = "EncounterProvider.findByVoidReason", query = "SELECT e FROM EncounterProvider e WHERE e.voidReason = :voidReason"),
    @NamedQuery(name = "EncounterProvider.findByUuid", query = "SELECT e FROM EncounterProvider e WHERE e.uuid = :uuid")})
public class EncounterProvider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "encounter_provider_id", nullable = false)
    private Integer encounterProviderId;
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
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "encounter_role_id", referencedColumnName = "encounter_role_id", nullable = false)
    @ManyToOne(optional = false)
    private EncounterRole encounterRoleId;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "encounter_id", referencedColumnName = "encounter_id", nullable = false)
    @ManyToOne(optional = false)
    private Encounter encounterId;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "provider_id", referencedColumnName = "provider_id", nullable = false)
    @ManyToOne(optional = false)
    private Provider providerId;

    public EncounterProvider() {
    }

    public EncounterProvider(Integer encounterProviderId) {
        this.encounterProviderId = encounterProviderId;
    }

    public EncounterProvider(Integer encounterProviderId, Date dateCreated, boolean voided, String uuid) {
        this.encounterProviderId = encounterProviderId;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getEncounterProviderId() {
        return encounterProviderId;
    }

    public void setEncounterProviderId(Integer encounterProviderId) {
        this.encounterProviderId = encounterProviderId;
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

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public EncounterRole getEncounterRoleId() {
        return encounterRoleId;
    }

    public void setEncounterRoleId(EncounterRole encounterRoleId) {
        this.encounterRoleId = encounterRoleId;
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
    }

    public Encounter getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Encounter encounterId) {
        this.encounterId = encounterId;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public Provider getProviderId() {
        return providerId;
    }

    public void setProviderId(Provider providerId) {
        this.providerId = providerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encounterProviderId != null ? encounterProviderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncounterProvider)) {
            return false;
        }
        EncounterProvider other = (EncounterProvider) object;
        if ((this.encounterProviderId == null && other.encounterProviderId != null) || (this.encounterProviderId != null && !this.encounterProviderId.equals(other.encounterProviderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.EncounterProvider[ encounterProviderId=" + encounterProviderId + " ]";
    }
    
}
