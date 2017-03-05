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
@Table(name = "concept_name", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"concept_name_id"}),
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptName.findAll", query = "SELECT c FROM ConceptName c"),
    @NamedQuery(name = "ConceptName.findByName", query = "SELECT c FROM ConceptName c WHERE c.name = :name"),
    @NamedQuery(name = "ConceptName.findByLocale", query = "SELECT c FROM ConceptName c WHERE c.locale = :locale"),
    @NamedQuery(name = "ConceptName.findByDateCreated", query = "SELECT c FROM ConceptName c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "ConceptName.findByConceptNameId", query = "SELECT c FROM ConceptName c WHERE c.conceptNameId = :conceptNameId"),
    @NamedQuery(name = "ConceptName.findByVoided", query = "SELECT c FROM ConceptName c WHERE c.voided = :voided"),
    @NamedQuery(name = "ConceptName.findByDateVoided", query = "SELECT c FROM ConceptName c WHERE c.dateVoided = :dateVoided"),
    @NamedQuery(name = "ConceptName.findByVoidReason", query = "SELECT c FROM ConceptName c WHERE c.voidReason = :voidReason"),
    @NamedQuery(name = "ConceptName.findByUuid", query = "SELECT c FROM ConceptName c WHERE c.uuid = :uuid"),
    @NamedQuery(name = "ConceptName.findByConceptNameType", query = "SELECT c FROM ConceptName c WHERE c.conceptNameType = :conceptNameType"),
    @NamedQuery(name = "ConceptName.findByLocalePreferred", query = "SELECT c FROM ConceptName c WHERE c.localePreferred = :localePreferred"),
})
public class ConceptName implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @Column(name = "locale", nullable = false, length = 50)
    private String locale;
    @Basic(optional = false)
    @Column(name = "date_created", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "concept_name_id", nullable = false)
    private Integer conceptNameId;
    @Basic(optional = false)
    @Column(name = "voided", nullable = false)
    private boolean voided;
    @Column(name = "date_voided")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateVoided;
    @Column(name = "void_reason", length = 255)
    private String voidReason;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @Column(name = "concept_name_type", length = 50)
    private String conceptNameType;
    @Column(name = "locale_preferred")
    private Boolean localePreferred;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "concept_id", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept conceptId;
    @OneToMany(mappedBy = "valueCodedNameId")
    private List<Obs> obsList;

    public ConceptName() {
    }

    public ConceptName(Integer conceptNameId) {
        this.conceptNameId = conceptNameId;
    }

    public ConceptName(Integer conceptNameId, String name, String locale, Date dateCreated, boolean voided, String uuid, long creatorId, long voidedById) {
        this.conceptNameId = conceptNameId;
        this.name = name;
        this.locale = locale;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
      
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Integer getConceptNameId() {
        return conceptNameId;
    }

    public void setConceptNameId(Integer conceptNameId) {
        this.conceptNameId = conceptNameId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getConceptNameType() {
        return conceptNameType;
    }

    public void setConceptNameType(String conceptNameType) {
        this.conceptNameType = conceptNameType;
    }

    public Boolean getLocalePreferred() {
        return localePreferred;
    }

    public void setLocalePreferred(Boolean localePreferred) {
        this.localePreferred = localePreferred;
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

    public Concept getConceptId() {
        return conceptId;
    }

    public void setConceptId(Concept conceptId) {
        this.conceptId = conceptId;
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
        hash += (conceptNameId != null ? conceptNameId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptName)) {
            return false;
        }
        ConceptName other = (ConceptName) object;
        if ((this.conceptNameId == null && other.conceptNameId != null) || (this.conceptNameId != null && !this.conceptNameId.equals(other.conceptNameId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ConceptName[ conceptNameId=" + conceptNameId + " ]";
    }
    
}
