/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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
@Table(name = "location", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Location.findAll", query = "SELECT l FROM Location l"),
    @NamedQuery(name = "Location.findByLocationId", query = "SELECT l FROM Location l WHERE l.locationId = :locationId"),
    @NamedQuery(name = "Location.findByName", query = "SELECT l FROM Location l WHERE l.name = :name"),
    @NamedQuery(name = "Location.findByDescription", query = "SELECT l FROM Location l WHERE l.description = :description"),
    @NamedQuery(name = "Location.findByAddress1", query = "SELECT l FROM Location l WHERE l.address1 = :address1"),
    @NamedQuery(name = "Location.findByAddress2", query = "SELECT l FROM Location l WHERE l.address2 = :address2"),
    @NamedQuery(name = "Location.findByCityVillage", query = "SELECT l FROM Location l WHERE l.cityVillage = :cityVillage"),
    @NamedQuery(name = "Location.findByStateProvince", query = "SELECT l FROM Location l WHERE l.stateProvince = :stateProvince"),
    @NamedQuery(name = "Location.findByPostalCode", query = "SELECT l FROM Location l WHERE l.postalCode = :postalCode"),
    @NamedQuery(name = "Location.findByCountry", query = "SELECT l FROM Location l WHERE l.country = :country"),
    @NamedQuery(name = "Location.findByLatitude", query = "SELECT l FROM Location l WHERE l.latitude = :latitude"),
    @NamedQuery(name = "Location.findByLongitude", query = "SELECT l FROM Location l WHERE l.longitude = :longitude"),
    @NamedQuery(name = "Location.findByDateCreated", query = "SELECT l FROM Location l WHERE l.dateCreated = :dateCreated"),
    @NamedQuery(name = "Location.findByCountyDistrict", query = "SELECT l FROM Location l WHERE l.countyDistrict = :countyDistrict"),
    @NamedQuery(name = "Location.findByAddress3", query = "SELECT l FROM Location l WHERE l.address3 = :address3"),
    @NamedQuery(name = "Location.findByAddress6", query = "SELECT l FROM Location l WHERE l.address6 = :address6"),
    @NamedQuery(name = "Location.findByAddress5", query = "SELECT l FROM Location l WHERE l.address5 = :address5"),
    @NamedQuery(name = "Location.findByAddress4", query = "SELECT l FROM Location l WHERE l.address4 = :address4"),
    @NamedQuery(name = "Location.findByRetired", query = "SELECT l FROM Location l WHERE l.retired = :retired"),
    @NamedQuery(name = "Location.findByDateRetired", query = "SELECT l FROM Location l WHERE l.dateRetired = :dateRetired"),
    @NamedQuery(name = "Location.findByRetireReason", query = "SELECT l FROM Location l WHERE l.retireReason = :retireReason"),
    @NamedQuery(name = "Location.findByUuid", query = "SELECT l FROM Location l WHERE l.uuid = :uuid"),
    @NamedQuery(name = "Location.findByDateChanged", query = "SELECT l FROM Location l WHERE l.dateChanged = :dateChanged")})
public class Location implements Serializable {

    @OneToMany(mappedBy = "locationId")
    private Collection<PatientProgram> patientProgramCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "location_id", nullable = false)
    private Integer locationId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "description", length = 255)
    private String description;
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
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @OneToMany(mappedBy = "parentLocation")
    private List<Location> locationList;
    @JoinColumn(name = "parent_location", referencedColumnName = "location_id")
    @ManyToOne
    private Location parentLocation;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @OneToMany(mappedBy = "locationId")
    private List<Visit> visitList;
    @OneToMany(mappedBy = "locationId")
    private List<Encounter> encounterList;
    @OneToMany(mappedBy = "locationId")
    private List<PatientIdentifier> patientIdentifierList;
    @OneToMany(mappedBy = "locationId")
    private List<Obs> obsList;

    public Location() {
    }

    public Location(Integer locationId) {
        this.locationId = locationId;
    }

    public Location(Integer locationId, String name, Date dateCreated, boolean retired, String uuid) {
        this.locationId = locationId;
        this.name = name;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
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

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
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
    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    @XmlTransient
    public List<Visit> getVisitList() {
        return visitList;
    }

    public void setVisitList(List<Visit> visitList) {
        this.visitList = visitList;
    }

    @XmlTransient
    public List<Encounter> getEncounterList() {
        return encounterList;
    }

    public void setEncounterList(List<Encounter> encounterList) {
        this.encounterList = encounterList;
    }

    @XmlTransient
    public List<PatientIdentifier> getPatientIdentifierList() {
        return patientIdentifierList;
    }

    public void setPatientIdentifierList(List<PatientIdentifier> patientIdentifierList) {
        this.patientIdentifierList = patientIdentifierList;
    }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locationId != null ? locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.locationId == null && other.locationId != null) || (this.locationId != null && !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Location[ locationId=" + locationId + " ]";
    }

    @XmlTransient
    public Collection<PatientProgram> getPatientProgramCollection() {
        return patientProgramCollection;
    }

    public void setPatientProgramCollection(Collection<PatientProgram> patientProgramCollection) {
        this.patientProgramCollection = patientProgramCollection;
    }
    
}
