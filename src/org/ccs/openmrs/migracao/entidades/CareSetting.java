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
@Table(name = "care_setting", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"}),
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CareSetting.findAll", query = "SELECT c FROM CareSetting c"),
    @NamedQuery(name = "CareSetting.findByCareSettingId", query = "SELECT c FROM CareSetting c WHERE c.careSettingId = :careSettingId"),
    @NamedQuery(name = "CareSetting.findByName", query = "SELECT c FROM CareSetting c WHERE c.name = :name"),
    @NamedQuery(name = "CareSetting.findByDescription", query = "SELECT c FROM CareSetting c WHERE c.description = :description"),
    @NamedQuery(name = "CareSetting.findByCareSettingType", query = "SELECT c FROM CareSetting c WHERE c.careSettingType = :careSettingType"),
    @NamedQuery(name = "CareSetting.findByDateCreated", query = "SELECT c FROM CareSetting c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "CareSetting.findByRetired", query = "SELECT c FROM CareSetting c WHERE c.retired = :retired"),
    @NamedQuery(name = "CareSetting.findByDateRetired", query = "SELECT c FROM CareSetting c WHERE c.dateRetired = :dateRetired"),
    @NamedQuery(name = "CareSetting.findByRetireReason", query = "SELECT c FROM CareSetting c WHERE c.retireReason = :retireReason"),
    @NamedQuery(name = "CareSetting.findByDateChanged", query = "SELECT c FROM CareSetting c WHERE c.dateChanged = :dateChanged"),
    @NamedQuery(name = "CareSetting.findByUuid", query = "SELECT c FROM CareSetting c WHERE c.uuid = :uuid")})
public class CareSetting implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "care_setting_id", nullable = false)
    private Integer careSettingId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", length = 255)
    private String description;
    @Basic(optional = false)
    @Column(name = "care_setting_type", nullable = false, length = 50)
    private String careSettingType;
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
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "careSetting")
    private List<Orders> ordersList;

    public CareSetting() {
    }

    public CareSetting(Integer careSettingId) {
        this.careSettingId = careSettingId;
    }

    public CareSetting(Integer careSettingId, String name, String careSettingType, Date dateCreated, boolean retired, String uuid) {
        this.careSettingId = careSettingId;
        this.name = name;
        this.careSettingType = careSettingType;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getCareSettingId() {
        return careSettingId;
    }

    public void setCareSettingId(Integer careSettingId) {
        this.careSettingId = careSettingId;
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

    public String getCareSettingType() {
        return careSettingType;
    }

    public void setCareSettingType(String careSettingType) {
        this.careSettingType = careSettingType;
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

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
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
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (careSettingId != null ? careSettingId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CareSetting)) {
            return false;
        }
        CareSetting other = (CareSetting) object;
        if ((this.careSettingId == null && other.careSettingId != null) || (this.careSettingId != null && !this.careSettingId.equals(other.careSettingId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.CareSetting[ careSettingId=" + careSettingId + " ]";
    }
    
}
