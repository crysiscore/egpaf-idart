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
@Table(name = "drug", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Drug.findAll", query = "SELECT d FROM Drug d"),
    @NamedQuery(name = "Drug.findByDrugId", query = "SELECT d FROM Drug d WHERE d.drugId = :drugId"),
    @NamedQuery(name = "Drug.findByName", query = "SELECT d FROM Drug d WHERE d.name = :name"),
    @NamedQuery(name = "Drug.findByCombination", query = "SELECT d FROM Drug d WHERE d.combination = :combination"),
    @NamedQuery(name = "Drug.findByMaximumDailyDose", query = "SELECT d FROM Drug d WHERE d.maximumDailyDose = :maximumDailyDose"),
    @NamedQuery(name = "Drug.findByMinimumDailyDose", query = "SELECT d FROM Drug d WHERE d.minimumDailyDose = :minimumDailyDose"),
    @NamedQuery(name = "Drug.findByDateCreated", query = "SELECT d FROM Drug d WHERE d.dateCreated = :dateCreated"),
    @NamedQuery(name = "Drug.findByRetired", query = "SELECT d FROM Drug d WHERE d.retired = :retired"),
    @NamedQuery(name = "Drug.findByDateRetired", query = "SELECT d FROM Drug d WHERE d.dateRetired = :dateRetired"),
    @NamedQuery(name = "Drug.findByRetireReason", query = "SELECT d FROM Drug d WHERE d.retireReason = :retireReason"),
    @NamedQuery(name = "Drug.findByUuid", query = "SELECT d FROM Drug d WHERE d.uuid = :uuid"),
    @NamedQuery(name = "Drug.findByDateChanged", query = "SELECT d FROM Drug d WHERE d.dateChanged = :dateChanged"),
    @NamedQuery(name = "Drug.findByStrength", query = "SELECT d FROM Drug d WHERE d.strength = :strength"),
//    @NamedQuery(name = "Drug.findByDoseStrength", query = "SELECT d FROM Drug d WHERE d.doseStrength = :doseStrength"),
 //   @NamedQuery(name = "Drug.findByUnits", query = "SELECT d FROM Drug d WHERE d.units = :units"),
})
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "drug_id", nullable = false)
    private Integer drugId;
    @Column(name = "name", length = 255)
    private String name;
    @Basic(optional = false)
    @Column(name = "combination", nullable = false)
    private boolean combination;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "maximum_daily_dose", precision = 22)
    private Double maximumDailyDose;
    @Column(name = "minimum_daily_dose", precision = 22)
    private Double minimumDailyDose;
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
    @Column(name = "date_changed")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateChanged;
    @Column(name = "strength", length = 255)
    private String strength;
  //  @Column(name = "dose_strength", precision = 22)
 //   private Double doseStrength;
 //   @Column(name = "units", length = 50)
 //   private String units;
    
    @OneToMany(mappedBy = "valueDrug")
    private List<Obs> obsList;
    @JoinColumn(name = "route", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept route;
    @JoinColumn(name = "concept_id", referencedColumnName = "concept_id", nullable = false)
    @ManyToOne(optional = false)
    private Concept conceptId;
    @JoinColumn(name = "retired_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users retiredBy;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "dosage_form", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept dosageForm;

    public Drug() {
    }

    public Drug(Integer drugId) {
        this.drugId = drugId;
    }

    public Drug(Integer drugId, boolean combination, Date dateCreated, boolean retired, String uuid, long changedById, long conceptByConceptIdId, long conceptByDosageFormId, long conceptByRouteId, long creatorId, long usersByRetiredById) {
        this.drugId = drugId;
        this.combination = combination;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.uuid = uuid;
    }

    public Integer getDrugId() {
        return drugId;
    }

    public void setDrugId(Integer drugId) {
        this.drugId = drugId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getCombination() {
        return combination;
    }

    public void setCombination(boolean combination) {
        this.combination = combination;
    }

    public Double getMaximumDailyDose() {
        return maximumDailyDose;
    }

    public void setMaximumDailyDose(Double maximumDailyDose) {
        this.maximumDailyDose = maximumDailyDose;
    }

    public Double getMinimumDailyDose() {
        return minimumDailyDose;
    }

    public void setMinimumDailyDose(Double minimumDailyDose) {
        this.minimumDailyDose = minimumDailyDose;
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

    public Date getDateChanged() {
        return dateChanged;
    }

    public void setDateChanged(Date dateChanged) {
        this.dateChanged = dateChanged;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

//    public Double getDoseStrength() {
//        return doseStrength;
//    }
//
//    public void setDoseStrength(Double doseStrength) {
//        this.doseStrength = doseStrength;
//    }

//    public String getUnits() {
//        return units;
//    }
//
//    public void setUnits(String units) {
//        this.units = units;
//    }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    public Concept getRoute() {
        return route;
    }

    public void setRoute(Concept route) {
        this.route = route;
    }

    public Concept getConceptId() {
        return conceptId;
    }

    public void setConceptId(Concept conceptId) {
        this.conceptId = conceptId;
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

    public Concept getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(Concept dosageForm) {
        this.dosageForm = dosageForm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (drugId != null ? drugId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Drug)) {
            return false;
        }
        Drug other = (Drug) object;
        if ((this.drugId == null && other.drugId != null) || (this.drugId != null && !this.drugId.equals(other.drugId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Drug[ drugId=" + drugId + " ]";
    }
    
}
