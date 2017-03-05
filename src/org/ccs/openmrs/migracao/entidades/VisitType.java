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
@Table(name = "visit_type", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisitType.findAll", query = "SELECT v FROM VisitType v"),
    @NamedQuery(name = "VisitType.findByVisitTypeId", query = "SELECT v FROM VisitType v WHERE v.visitTypeId = :visitTypeId"),
    @NamedQuery(name = "VisitType.findByName", query = "SELECT v FROM VisitType v WHERE v.name = :name"),
    @NamedQuery(name = "VisitType.findByDescription", query = "SELECT v FROM VisitType v WHERE v.description = :description"),
    @NamedQuery(name = "VisitType.findByUuid", query = "SELECT v FROM VisitType v WHERE v.uuid = :uuid"),
    @NamedQuery(name = "VisitType.findByDateCreated", query = "SELECT v FROM VisitType v WHERE v.dateCreated = :dateCreated"),
    @NamedQuery(name = "VisitType.findByDateChanged", query = "SELECT v FROM VisitType v WHERE v.dateChanged = :dateChanged"),
    @NamedQuery(name = "VisitType.findByRetired", query = "SELECT v FROM VisitType v WHERE v.retired = :retired"),
    @NamedQuery(name = "VisitType.findByDateRetired", query = "SELECT v FROM VisitType v WHERE v.dateRetired = :dateRetired"),
    @NamedQuery(name = "VisitType.findByRetireReason", query = "SELECT v FROM VisitType v WHERE v.retireReason = :retireReason")})
public class VisitType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visit_type_id", nullable = false)
    private Integer visitTypeId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", length = 1024)
    private String description;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Basic(optional = false)
    @Column(name = "retired", nullable = false)
    private short retired;
    @Column(name = "date_retired")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRetired;
    @Column(name = "retire_reason", length = 255)
    private String retireReason;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "visitTypeId")
    private List<Visit> visitList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;

    public VisitType() {
    }

    public VisitType(Integer visitTypeId) {
        this.visitTypeId = visitTypeId;
    }

    public VisitType(Integer visitTypeId, String name, String uuid, Date dateCreated, short retired) {
        this.visitTypeId = visitTypeId;
        this.name = name;
        this.uuid = uuid;
        this.dateCreated = dateCreated;
        this.retired = retired;
    }

    public Integer getVisitTypeId() {
        return visitTypeId;
    }

    public void setVisitTypeId(Integer visitTypeId) {
        this.visitTypeId = visitTypeId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    public short getRetired() {
        return retired;
    }

    public void setRetired(short retired) {
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

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visitTypeId != null ? visitTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisitType)) {
            return false;
        }
        VisitType other = (VisitType) object;
        if ((this.visitTypeId == null && other.visitTypeId != null) || (this.visitTypeId != null && !this.visitTypeId.equals(other.visitTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.VisitType[ visitTypeId=" + visitTypeId + " ]";
    }
    
}
