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
@Table(name = "person_name", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonName.findAll", query = "SELECT p FROM PersonName p"),
    @NamedQuery(name = "PersonName.findByPersonNameId", query = "SELECT p FROM PersonName p WHERE p.personNameId = :personNameId"),
    @NamedQuery(name = "PersonName.findByPreferred", query = "SELECT p FROM PersonName p WHERE p.preferred = :preferred"),
    @NamedQuery(name = "PersonName.findByPrefix", query = "SELECT p FROM PersonName p WHERE p.prefix = :prefix"),
    @NamedQuery(name = "PersonName.findByGivenName", query = "SELECT p FROM PersonName p WHERE p.givenName = :givenName"),
    @NamedQuery(name = "PersonName.findByMiddleName", query = "SELECT p FROM PersonName p WHERE p.middleName = :middleName"),
    @NamedQuery(name = "PersonName.findByFamilyNamePrefix", query = "SELECT p FROM PersonName p WHERE p.familyNamePrefix = :familyNamePrefix"),
    @NamedQuery(name = "PersonName.findByFamilyName", query = "SELECT p FROM PersonName p WHERE p.familyName = :familyName"),
    @NamedQuery(name = "PersonName.findByFamilyName2", query = "SELECT p FROM PersonName p WHERE p.familyName2 = :familyName2"),
    @NamedQuery(name = "PersonName.findByFamilyNameSuffix", query = "SELECT p FROM PersonName p WHERE p.familyNameSuffix = :familyNameSuffix"),
    @NamedQuery(name = "PersonName.findByDegree", query = "SELECT p FROM PersonName p WHERE p.degree = :degree"),
    @NamedQuery(name = "PersonName.findByDateCreated", query = "SELECT p FROM PersonName p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "PersonName.findByVoided", query = "SELECT p FROM PersonName p WHERE p.voided = :voided"),
    @NamedQuery(name = "PersonName.findByDateVoided", query = "SELECT p FROM PersonName p WHERE p.dateVoided = :dateVoided"),
    @NamedQuery(name = "PersonName.findByVoidReason", query = "SELECT p FROM PersonName p WHERE p.voidReason = :voidReason"),
    @NamedQuery(name = "PersonName.findByDateChanged", query = "SELECT p FROM PersonName p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "PersonName.findByUuid", query = "SELECT p FROM PersonName p WHERE p.uuid = :uuid")})
public class PersonName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "person_name_id", nullable = false)
    private Integer personNameId;
    @Basic(optional = false)
    @Column(name = "preferred", nullable = false)
    private boolean preferred;
    @Column(name = "prefix", length = 50)
    private String prefix;
    @Column(name = "given_name", length = 50)
    private String givenName;
    @Column(name = "middle_name", length = 50)
    private String middleName;
    @Column(name = "family_name_prefix", length = 50)
    private String familyNamePrefix;
    @Column(name = "family_name", length = 50)
    private String familyName;
    @Column(name = "family_name2", length = 50)
    private String familyName2;
    @Column(name = "family_name_suffix", length = 50)
    private String familyNameSuffix;
    @Column(name = "degree", length = 50)
    private String degree;
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
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;

    public PersonName() {
    }

    public PersonName(Integer personNameId) {
        this.personNameId = personNameId;
    }

    public PersonName(Integer personNameId, boolean preferred, Date dateCreated, boolean voided, String uuid) {
        this.personNameId = personNameId;
        this.preferred = preferred;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getPersonNameId() {
        return personNameId;
    }

    public void setPersonNameId(Integer personNameId) {
        this.personNameId = personNameId;
    }

    public boolean getPreferred() {
        return preferred;
    }

    public void setPreferred(boolean preferred) {
        this.preferred = preferred;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFamilyNamePrefix() {
        return familyNamePrefix;
    }

    public void setFamilyNamePrefix(String familyNamePrefix) {
        this.familyNamePrefix = familyNamePrefix;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyName2() {
        return familyName2;
    }

    public void setFamilyName2(String familyName2) {
        this.familyName2 = familyName2;
    }

    public String getFamilyNameSuffix() {
        return familyNameSuffix;
    }

    public void setFamilyNameSuffix(String familyNameSuffix) {
        this.familyNameSuffix = familyNameSuffix;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (personNameId != null ? personNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonName)) {
            return false;
        }
        PersonName other = (PersonName) object;
        if ((this.personNameId == null && other.personNameId != null) || (this.personNameId != null && !this.personNameId.equals(other.personNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.PersonName[ personNameId=" + personNameId + " ]";
    }
    
}
