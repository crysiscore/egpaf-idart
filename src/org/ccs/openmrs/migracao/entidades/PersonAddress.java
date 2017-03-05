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
@Table(name = "person_address", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonAddress.findAll", query = "SELECT p FROM PersonAddress p"),
    @NamedQuery(name = "PersonAddress.findByPersonAddressId", query = "SELECT p FROM PersonAddress p WHERE p.personAddressId = :personAddressId"),
    @NamedQuery(name = "PersonAddress.findByPreferred", query = "SELECT p FROM PersonAddress p WHERE p.preferred = :preferred"),
    @NamedQuery(name = "PersonAddress.findByAddress1", query = "SELECT p FROM PersonAddress p WHERE p.address1 = :address1"),
    @NamedQuery(name = "PersonAddress.findByAddress2", query = "SELECT p FROM PersonAddress p WHERE p.address2 = :address2"),
    @NamedQuery(name = "PersonAddress.findByCityVillage", query = "SELECT p FROM PersonAddress p WHERE p.cityVillage = :cityVillage"),
    @NamedQuery(name = "PersonAddress.findByStateProvince", query = "SELECT p FROM PersonAddress p WHERE p.stateProvince = :stateProvince"),
    @NamedQuery(name = "PersonAddress.findByPostalCode", query = "SELECT p FROM PersonAddress p WHERE p.postalCode = :postalCode"),
    @NamedQuery(name = "PersonAddress.findByCountry", query = "SELECT p FROM PersonAddress p WHERE p.country = :country"),
    @NamedQuery(name = "PersonAddress.findByLatitude", query = "SELECT p FROM PersonAddress p WHERE p.latitude = :latitude"),
    @NamedQuery(name = "PersonAddress.findByLongitude", query = "SELECT p FROM PersonAddress p WHERE p.longitude = :longitude"),
    @NamedQuery(name = "PersonAddress.findByDateCreated", query = "SELECT p FROM PersonAddress p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "PersonAddress.findByVoided", query = "SELECT p FROM PersonAddress p WHERE p.voided = :voided"),
    @NamedQuery(name = "PersonAddress.findByDateVoided", query = "SELECT p FROM PersonAddress p WHERE p.dateVoided = :dateVoided"),
    @NamedQuery(name = "PersonAddress.findByVoidReason", query = "SELECT p FROM PersonAddress p WHERE p.voidReason = :voidReason"),
    @NamedQuery(name = "PersonAddress.findByCountyDistrict", query = "SELECT p FROM PersonAddress p WHERE p.countyDistrict = :countyDistrict"),
    @NamedQuery(name = "PersonAddress.findByAddress3", query = "SELECT p FROM PersonAddress p WHERE p.address3 = :address3"),
    @NamedQuery(name = "PersonAddress.findByAddress6", query = "SELECT p FROM PersonAddress p WHERE p.address6 = :address6"),
    @NamedQuery(name = "PersonAddress.findByAddress5", query = "SELECT p FROM PersonAddress p WHERE p.address5 = :address5"),
    @NamedQuery(name = "PersonAddress.findByAddress4", query = "SELECT p FROM PersonAddress p WHERE p.address4 = :address4"),
    @NamedQuery(name = "PersonAddress.findByUuid", query = "SELECT p FROM PersonAddress p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "PersonAddress.findByDateChanged", query = "SELECT p FROM PersonAddress p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "PersonAddress.findByStartDate", query = "SELECT p FROM PersonAddress p WHERE p.startDate = :startDate"),
    @NamedQuery(name = "PersonAddress.findByEndDate", query = "SELECT p FROM PersonAddress p WHERE p.endDate = :endDate")})
public class PersonAddress implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "person_address_id", nullable = false)
    private Integer personAddressId;
    @Basic(optional = false)
    @Column(name = "preferred", nullable = false)
    private boolean preferred;
    @Column(name = "address1", length = 255)
    private String address1;
    @Column(name = "address2", length = 255)
    private String address2;
    @Column(name = "city_village", length = 255)
    private String cityVillage;
    @Column(name = "state_province", length = 255)
    private String stateProvince;
    @Column(name = "postal_code", length = 50)
    private String postalCode;
    @Column(name = "country", length = 50)
    private String country;
    @Column(name = "latitude", length = 50)
    private String latitude;
    @Column(name = "longitude", length = 50)
    private String longitude;
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
    @Column(name = "county_district", length = 255)
    private String countyDistrict;
    @Column(name = "address3", length = 255)
    private String address3;
    @Column(name = "address6", length = 255)
    private String address6;
    @Column(name = "address5", length = 255)
    private String address5;
    @Column(name = "address4", length = 255)
    private String address4;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    @ManyToOne
    private Person personId;

    public PersonAddress() {
    }

    public PersonAddress(Integer personAddressId) {
        this.personAddressId = personAddressId;
    }

    public PersonAddress(Integer personAddressId, boolean preferred, Date dateCreated, boolean voided, String uuid) {
        this.personAddressId = personAddressId;
        this.preferred = preferred;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getPersonAddressId() {
        return personAddressId;
    }

    public void setPersonAddressId(Integer personAddressId) {
        this.personAddressId = personAddressId;
    }

    public boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCityVillage() {
        return cityVillage;
    }

    public void setCityVillage(String cityVillage) {
        this.cityVillage = cityVillage;
    }

    public String getStateProvince() {
        return stateProvince;
    }

    public void setStateProvince(String stateProvince) {
        this.stateProvince = stateProvince;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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

    public String getCountyDistrict() {
        return countyDistrict;
    }

    public void setCountyDistrict(String countyDistrict) {
        this.countyDistrict = countyDistrict;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress6() {
        return address6;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public String getAddress5() {
        return address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress4() {
        return address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personAddressId != null ? personAddressId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonAddress)) {
            return false;
        }
        PersonAddress other = (PersonAddress) object;
        if ((this.personAddressId == null && other.personAddressId != null) || (this.personAddressId != null && !this.personAddressId.equals(other.personAddressId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PersonAddress[ personAddressId=" + personAddressId + " ]";
    }
    
}
