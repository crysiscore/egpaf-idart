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
@Table(name = "concept_datatype", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptDatatype.findAll", query = "SELECT c FROM ConceptDatatype c"),
    @NamedQuery(name = "ConceptDatatype.findByConceptDatatypeId", query = "SELECT c FROM ConceptDatatype c WHERE c.conceptDatatypeId = :conceptDatatypeId"),
    @NamedQuery(name = "ConceptDatatype.findByName", query = "SELECT c FROM ConceptDatatype c WHERE c.name = :name"),
    @NamedQuery(name = "ConceptDatatype.findByDescription", query = "SELECT c FROM ConceptDatatype c WHERE c.description = :description"),
    @NamedQuery(name = "ConceptDatatype.findByDateCreated", query = "SELECT c FROM ConceptDatatype c WHERE c.dateCreated = :dateCreated"),
    @NamedQuery(name = "ConceptDatatype.findByRetired", query = "SELECT c FROM ConceptDatatype c WHERE c.retired = :retired"),
    @NamedQuery(name = "ConceptDatatype.findByDateRetired", query = "SELECT c FROM ConceptDatatype c WHERE c.dateRetired = :dateRetired"),
    @NamedQuery(name = "ConceptDatatype.findByRetireReason", query = "SELECT c FROM ConceptDatatype c WHERE c.retireReason = :retireReason"),
    @NamedQuery(name = "ConceptDatatype.findByUuid", query = "SELECT c FROM ConceptDatatype c WHERE c.uuid = :uuid"),
    @NamedQuery(name = "ConceptDatatype.findByhl7_Abbreviation", query = "SELECT c FROM ConceptDatatype c WHERE c.hl7_Abbreviation = :hl7_Abbreviation")
})
public class ConceptDatatype implements Serializable {

//    @Column(name = "hl7abbreviation")
//    private String hl7abbreviation;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "concept_datatype_id", nullable = false)
    private Integer conceptDatatypeId;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @Column(name = "hl7_abbreviation", length = 3)
    private String hl7_Abbreviation;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "datatypeId")
    private List<Concept> conceptList;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;

    public ConceptDatatype() {
    }

    public ConceptDatatype(Integer conceptDatatypeId) {
        this.conceptDatatypeId = conceptDatatypeId;
    }

    public ConceptDatatype(Integer conceptDatatypeId, String name, String description, Date dateCreated, boolean retired, String uuid) {
        this.conceptDatatypeId = conceptDatatypeId;
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getConceptDatatypeId() {
        return conceptDatatypeId;
    }

    public void setConceptDatatypeId(Integer conceptDatatypeId) {
        this.conceptDatatypeId = conceptDatatypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHl7Abbreviation() {
        return hl7_Abbreviation;
    }

    public void setHl7Abbreviation(String hl7Abbreviation) {
        this.hl7_Abbreviation = hl7Abbreviation;
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
    public List<Concept> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<Concept> conceptList) {
        this.conceptList = conceptList;
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
        hash += (conceptDatatypeId != null ? conceptDatatypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptDatatype)) {
            return false;
        }
        ConceptDatatype other = (ConceptDatatype) object;
        if ((this.conceptDatatypeId == null && other.conceptDatatypeId != null) || (this.conceptDatatypeId != null && !this.conceptDatatypeId.equals(other.conceptDatatypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ConceptDatatype[ conceptDatatypeId=" + conceptDatatypeId + " ]";
    }

//    public String getHl7abbreviation() {
//        return hl7abbreviation;
//    }
//
//    public void setHl7abbreviation(String hl7abbreviation) {
//        this.hl7abbreviation = hl7abbreviation;
//    }
    
}
