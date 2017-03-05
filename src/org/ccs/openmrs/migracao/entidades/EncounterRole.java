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
@Table(name = "encounter_role", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncounterRole.findAll", query = "SELECT e FROM EncounterRole e"),
    @NamedQuery(name = "EncounterRole.findByEncounterRoleId", query = "SELECT e FROM EncounterRole e WHERE e.encounterRoleId = :encounterRoleId"),
    @NamedQuery(name = "EncounterRole.findByName", query = "SELECT e FROM EncounterRole e WHERE e.name = :name"),
    @NamedQuery(name = "EncounterRole.findByDescription", query = "SELECT e FROM EncounterRole e WHERE e.description = :description"),
    @NamedQuery(name = "EncounterRole.findByDateCreated", query = "SELECT e FROM EncounterRole e WHERE e.dateCreated = :dateCreated"),
    @NamedQuery(name = "EncounterRole.findByDateChanged", query = "SELECT e FROM EncounterRole e WHERE e.dateChanged = :dateChanged"),
    @NamedQuery(name = "EncounterRole.findByRetired", query = "SELECT e FROM EncounterRole e WHERE e.retired = :retired"),
    @NamedQuery(name = "EncounterRole.findByDateRetired", query = "SELECT e FROM EncounterRole e WHERE e.dateRetired = :dateRetired"),
    @NamedQuery(name = "EncounterRole.findByRetireReason", query = "SELECT e FROM EncounterRole e WHERE e.retireReason = :retireReason"),
    @NamedQuery(name = "EncounterRole.findByUuid", query = "SELECT e FROM EncounterRole e WHERE e.uuid = :uuid"),

})
public class EncounterRole implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "encounter_role_id", nullable = false)
    private Integer encounterRoleId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", length = 1024)
    private String description;
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
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encounterRoleId")
    private List<EncounterProvider> encounterProviderList;

    public EncounterRole() {
    }

    public EncounterRole(Integer encounterRoleId) {
        this.encounterRoleId = encounterRoleId;
    }

    public EncounterRole(Integer encounterRoleId, String name, Date dateCreated, boolean retired, String uuid, long changedById, long creatorId, long usersByRetiredById) {
        this.encounterRoleId = encounterRoleId;
        this.name = name;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getEncounterRoleId() {
        return encounterRoleId;
    }

    public void setEncounterRoleId(Integer encounterRoleId) {
        this.encounterRoleId = encounterRoleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Users getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Users retiredBy) {
        this.retiredBy = retiredBy;
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
    public List<EncounterProvider> getEncounterProviderList() {
        return encounterProviderList;
    }

    public void setEncounterProviderList(List<EncounterProvider> encounterProviderList) {
        this.encounterProviderList = encounterProviderList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encounterRoleId != null ? encounterRoleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncounterRole)) {
            return false;
        }
        EncounterRole other = (EncounterRole) object;
        if ((this.encounterRoleId == null && other.encounterRoleId != null) || (this.encounterRoleId != null && !this.encounterRoleId.equals(other.encounterRoleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.EncounterRole[ encounterRoleId=" + encounterRoleId + " ]";
    }
    
}
