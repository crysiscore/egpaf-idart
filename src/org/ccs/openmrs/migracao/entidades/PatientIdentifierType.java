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
@Table(name = "patient_identifier_type", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PatientIdentifierType.findAll", query = "SELECT p FROM PatientIdentifierType p"),
    @NamedQuery(name = "PatientIdentifierType.findByPatientIdentifierTypeId", query = "SELECT p FROM PatientIdentifierType p WHERE p.patientIdentifierTypeId = :patientIdentifierTypeId"),
    @NamedQuery(name = "PatientIdentifierType.findByName", query = "SELECT p FROM PatientIdentifierType p WHERE p.name = :name"),
    @NamedQuery(name = "PatientIdentifierType.findByFormat", query = "SELECT p FROM PatientIdentifierType p WHERE p.format = :format"),
    @NamedQuery(name = "PatientIdentifierType.findByCheckDigit", query = "SELECT p FROM PatientIdentifierType p WHERE p.checkDigit = :checkDigit"),
    @NamedQuery(name = "PatientIdentifierType.findByDateCreated", query = "SELECT p FROM PatientIdentifierType p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "PatientIdentifierType.findByRequired", query = "SELECT p FROM PatientIdentifierType p WHERE p.required = :required"),
    @NamedQuery(name = "PatientIdentifierType.findByFormatDescription", query = "SELECT p FROM PatientIdentifierType p WHERE p.formatDescription = :formatDescription"),
    @NamedQuery(name = "PatientIdentifierType.findByValidator", query = "SELECT p FROM PatientIdentifierType p WHERE p.validator = :validator"),
    @NamedQuery(name = "PatientIdentifierType.findByRetired", query = "SELECT p FROM PatientIdentifierType p WHERE p.retired = :retired"),
    @NamedQuery(name = "PatientIdentifierType.findByDateRetired", query = "SELECT p FROM PatientIdentifierType p WHERE p.dateRetired = :dateRetired"),
    @NamedQuery(name = "PatientIdentifierType.findByRetireReason", query = "SELECT p FROM PatientIdentifierType p WHERE p.retireReason = :retireReason"),
    @NamedQuery(name = "PatientIdentifierType.findByUuid", query = "SELECT p FROM PatientIdentifierType p WHERE p.uuid = :uuid"),
    @NamedQuery(name = "PatientIdentifierType.findByLocationBehavior", query = "SELECT p FROM PatientIdentifierType p WHERE p.locationBehavior = :locationBehavior"),
})
public class PatientIdentifierType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "patient_identifier_type_id", nullable = false)
    private Integer patientIdentifierTypeId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic(optional = false)
    @Lob
    @Column(name = "description", nullable = false, length = 65535)
    private String description;
    @Column(name = "format", length = 255)
    private String format;
    @Basic(optional = false)
    @Column(name = "check_digit", nullable = false)
    private boolean checkDigit;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Basic(optional = false)
    @Column(name = "required", nullable = false)
    private boolean required;
    @Column(name = "format_description", length = 255)
    private String formatDescription;
    @Column(name = "validator", length = 200)
    private String validator;
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
    @Column(name = "location_behavior", length = 50)
    private String locationBehavior;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "identifierType")
    private List<PatientIdentifier> patientIdentifierList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;

    public PatientIdentifierType() {
    }

    public PatientIdentifierType(Integer patientIdentifierTypeId) {
        this.patientIdentifierTypeId = patientIdentifierTypeId;
    }

    public PatientIdentifierType(Integer patientIdentifierTypeId, String name, String description, boolean checkDigit, Date dateCreated, boolean required, boolean retired, String uuid, long creatorId, long retiredById) {
        this.patientIdentifierTypeId = patientIdentifierTypeId;
        this.name = name;
        this.description = description;
        this.checkDigit = checkDigit;
        this.dateCreated = dateCreated;
        this.required = required;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getPatientIdentifierTypeId() {
        return patientIdentifierTypeId;
    }

    public void setPatientIdentifierTypeId(Integer patientIdentifierTypeId) {
        this.patientIdentifierTypeId = patientIdentifierTypeId;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(boolean checkDigit) {
        this.checkDigit = checkDigit;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean getRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getFormatDescription() {
        return formatDescription;
    }

    public void setFormatDescription(String formatDescription) {
        this.formatDescription = formatDescription;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
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

    public String getLocationBehavior() {
        return locationBehavior;
    }

    public void setLocationBehavior(String locationBehavior) {
        this.locationBehavior = locationBehavior;
    }

    @XmlTransient
    public List<PatientIdentifier> getPatientIdentifierList() {
        return patientIdentifierList;
    }

    public void setPatientIdentifierList(List<PatientIdentifier> patientIdentifierList) {
        this.patientIdentifierList = patientIdentifierList;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (patientIdentifierTypeId != null ? patientIdentifierTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PatientIdentifierType)) {
            return false;
        }
        PatientIdentifierType other = (PatientIdentifierType) object;
        if ((this.patientIdentifierTypeId == null && other.patientIdentifierTypeId != null) || (this.patientIdentifierTypeId != null && !this.patientIdentifierTypeId.equals(other.patientIdentifierTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PatientIdentifierType[ patientIdentifierTypeId=" + patientIdentifierTypeId + " ]";
    }
    
}
