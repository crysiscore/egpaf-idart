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
@Table(name = "provider", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provider.findAll", query = "SELECT p FROM Provider p"),
    @NamedQuery(name = "Provider.findByProviderId", query = "SELECT p FROM Provider p WHERE p.providerId = :providerId"),
    @NamedQuery(name = "Provider.findByName", query = "SELECT p FROM Provider p WHERE p.name = :name"),
    @NamedQuery(name = "Provider.findByIdentifier", query = "SELECT p FROM Provider p WHERE p.identifier = :identifier"),
    @NamedQuery(name = "Provider.findByDateCreated", query = "SELECT p FROM Provider p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "Provider.findByDateChanged", query = "SELECT p FROM Provider p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "Provider.findByRetired", query = "SELECT p FROM Provider p WHERE p.retired = :retired"),
    @NamedQuery(name = "Provider.findByDateRetired", query = "SELECT p FROM Provider p WHERE p.dateRetired = :dateRetired"),
    @NamedQuery(name = "Provider.findByRetireReason", query = "SELECT p FROM Provider p WHERE p.retireReason = :retireReason"),
    @NamedQuery(name = "Provider.findByUuid", query = "SELECT p FROM Provider p WHERE p.uuid = :uuid"),
})
public class Provider implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "provider_id", nullable = false)
    private Integer providerId;
    @Column(name = "name", length = 255)
    private String name;
    @Column(name = "identifier", length = 255)
    private String identifier;
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
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "providerId")
    private List<EncounterProvider> encounterProviderList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderer")
    private List<Orders> ordersList;

    public Provider() {
    }

    public Provider(Integer providerId) {
        this.providerId = providerId;
    }

    public Provider(Integer providerId, Date dateCreated, boolean retired, String uuid, long changedById, long creatorId, long usersByRetiredById) {
        this.providerId = providerId;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
      
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Users getRetiredBy() {
        return retiredBy;
    }

    public void setRetiredBy(Users retiredBy) {
        this.retiredBy = retiredBy;
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
        hash += (providerId != null ? providerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provider)) {
            return false;
        }
        Provider other = (Provider) object;
        if ((this.providerId == null && other.providerId != null) || (this.providerId != null && !this.providerId.equals(other.providerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Provider[ providerId=" + providerId + " ]";
    }
    
}
