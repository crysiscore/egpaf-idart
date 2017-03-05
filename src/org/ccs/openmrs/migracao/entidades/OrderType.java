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
@Table(name = "order_type", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"}),
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderType.findAll", query = "SELECT o FROM OrderType o"),
    @NamedQuery(name = "OrderType.findByOrderTypeId", query = "SELECT o FROM OrderType o WHERE o.orderTypeId = :orderTypeId"),
    @NamedQuery(name = "OrderType.findByName", query = "SELECT o FROM OrderType o WHERE o.name = :name"),
    @NamedQuery(name = "OrderType.findByDescription", query = "SELECT o FROM OrderType o WHERE o.description = :description"),
    @NamedQuery(name = "OrderType.findByDateCreated", query = "SELECT o FROM OrderType o WHERE o.dateCreated = :dateCreated"),
    @NamedQuery(name = "OrderType.findByRetired", query = "SELECT o FROM OrderType o WHERE o.retired = :retired"),
    @NamedQuery(name = "OrderType.findByDateRetired", query = "SELECT o FROM OrderType o WHERE o.dateRetired = :dateRetired"),
    @NamedQuery(name = "OrderType.findByRetireReason", query = "SELECT o FROM OrderType o WHERE o.retireReason = :retireReason"),
    @NamedQuery(name = "OrderType.findByUuid", query = "SELECT o FROM OrderType o WHERE o.uuid = :uuid"),
    @NamedQuery(name = "OrderType.findByJavaClassName", query = "SELECT o FROM OrderType o WHERE o.javaClassName = :javaClassName"),
    @NamedQuery(name = "OrderType.findByDateChanged", query = "SELECT o FROM OrderType o WHERE o.dateChanged = :dateChanged"),
})
public class OrderType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_type_id", nullable = false)
    private Integer orderTypeId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @Column(name = "description", nullable = false, length = 255)
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
    @Basic(optional = false)
    @Column(name = "java_class_name", nullable = false, length = 255)
    private String javaClassName;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderTypeId")
    private List<Orders> ordersList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "parent", referencedColumnName = "order_id")
    @ManyToOne
    private Orders parent;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;

    public OrderType() {
    }

    public OrderType(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
    }

    public OrderType(Integer orderTypeId, String name, String description, Date dateCreated, boolean retired, String uuid, String javaClassName, long creatorId, long usersByRetiredById) {
        this.orderTypeId = orderTypeId;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
        this.javaClassName = javaClassName;

    }

    public Integer getOrderTypeId() {
        return orderTypeId;
    }

    public void setOrderTypeId(Integer orderTypeId) {
        this.orderTypeId = orderTypeId;
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

    public String getJavaClassName() {
        return javaClassName;
    }

    public void setJavaClassName(String javaClassName) {
        this.javaClassName = javaClassName;
    }

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
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

    public Orders getParent() {
        return parent;
    }

    public void setParent(Orders parent) {
        this.parent = parent;
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
        hash += (orderTypeId != null ? orderTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderType)) {
            return false;
        }
        OrderType other = (OrderType) object;
        if ((this.orderTypeId == null && other.orderTypeId != null) || (this.orderTypeId != null && !this.orderTypeId.equals(other.orderTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.OrderType[ orderTypeId=" + orderTypeId + " ]";
    }
    
}
