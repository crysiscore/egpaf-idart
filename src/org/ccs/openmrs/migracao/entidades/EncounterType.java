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
@Table(name = "encounter_type", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EncounterType.findAll", query = "SELECT e FROM EncounterType e"),
    @NamedQuery(name = "EncounterType.findByEncounterTypeId", query = "SELECT e FROM EncounterType e WHERE e.encounterTypeId = :encounterTypeId"),
    @NamedQuery(name = "EncounterType.findByName", query = "SELECT e FROM EncounterType e WHERE e.name = :name"),
    @NamedQuery(name = "EncounterType.findByDateCreated", query = "SELECT e FROM EncounterType e WHERE e.dateCreated = :dateCreated"),
    @NamedQuery(name = "EncounterType.findByRetired", query = "SELECT e FROM EncounterType e WHERE e.retired = :retired"),
    @NamedQuery(name = "EncounterType.findByDateRetired", query = "SELECT e FROM EncounterType e WHERE e.dateRetired = :dateRetired"),
    @NamedQuery(name = "EncounterType.findByRetireReason", query = "SELECT e FROM EncounterType e WHERE e.retireReason = :retireReason"),
    @NamedQuery(name = "EncounterType.findByUuid", query = "SELECT e FROM EncounterType e WHERE e.uuid = :uuid")})
public class EncounterType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "encounter_type_id", nullable = false)
    private Integer encounterTypeId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Lob
    @Column(name = "description", length = 65535)
    private String description;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "encounterType")
    private List<Encounter> encounterList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @OneToMany(mappedBy = "encounterType")
    private List<Form> formList;

    public EncounterType() {
    }

    public EncounterType(Integer encounterTypeId) {
        this.encounterTypeId = encounterTypeId;
    }

    public EncounterType(Integer encounterTypeId, String name, Date dateCreated, boolean retired, String uuid) {
        this.encounterTypeId = encounterTypeId;
        this.name = name;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getEncounterTypeId() {
        return encounterTypeId;
    }

    public void setEncounterTypeId(Integer encounterTypeId) {
        this.encounterTypeId = encounterTypeId;
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
    public List<Encounter> getEncounterList() {
        return encounterList;
    }

    public void setEncounterList(List<Encounter> encounterList) {
        this.encounterList = encounterList;
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

    @XmlTransient
    public List<Form> getFormList() {
        return formList;
    }

    public void setFormList(List<Form> formList) {
        this.formList = formList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (encounterTypeId != null ? encounterTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EncounterType)) {
            return false;
        }
        EncounterType other = (EncounterType) object;
        if ((this.encounterTypeId == null && other.encounterTypeId != null) || (this.encounterTypeId != null && !this.encounterTypeId.equals(other.encounterTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.EncounterType[ encounterTypeId=" + encounterTypeId + " ]";
    }
    
}
