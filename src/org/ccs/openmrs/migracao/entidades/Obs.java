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
@Table(name = "obs", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Obs.findAll", query = "SELECT o FROM Obs o"),
    @NamedQuery(name = "Obs.findByObsId", query = "SELECT o FROM Obs o WHERE o.obsId = :obsId"),
    @NamedQuery(name = "Obs.findByObsDatetime", query = "SELECT o FROM Obs o WHERE o.obsDatetime = :obsDatetime"),
    @NamedQuery(name = "Obs.findByAccessionNumber", query = "SELECT o FROM Obs o WHERE o.accessionNumber = :accessionNumber"),
    @NamedQuery(name = "Obs.findByValueGroupId", query = "SELECT o FROM Obs o WHERE o.valueGroupId = :valueGroupId"),
    @NamedQuery(name = "Obs.findByValueBoolean", query = "SELECT o FROM Obs o WHERE o.valueBoolean = :valueBoolean"),
    @NamedQuery(name = "Obs.findByValueDatetime", query = "SELECT o FROM Obs o WHERE o.valueDatetime = :valueDatetime"),
    @NamedQuery(name = "Obs.findByValueNumeric", query = "SELECT o FROM Obs o WHERE o.valueNumeric = :valueNumeric"),
    @NamedQuery(name = "Obs.findByValueModifier", query = "SELECT o FROM Obs o WHERE o.valueModifier = :valueModifier"),
    @NamedQuery(name = "Obs.findByComments", query = "SELECT o FROM Obs o WHERE o.comments = :comments"),
    @NamedQuery(name = "Obs.findByDateCreated", query = "SELECT o FROM Obs o WHERE o.dateCreated = :dateCreated"),
    @NamedQuery(name = "Obs.findByVoided", query = "SELECT o FROM Obs o WHERE o.voided = :voided"),
    @NamedQuery(name = "Obs.findByDateVoided", query = "SELECT o FROM Obs o WHERE o.dateVoided = :dateVoided"),
    @NamedQuery(name = "Obs.findByVoidReason", query = "SELECT o FROM Obs o WHERE o.voidReason = :voidReason"),
    @NamedQuery(name = "Obs.findByValueComplex", query = "SELECT o FROM Obs o WHERE o.valueComplex = :valueComplex"),
    @NamedQuery(name = "Obs.findByUuid", query = "SELECT o FROM Obs o WHERE o.uuid = :uuid")})
public class Obs implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "obs_id", nullable = false)
    private Integer obsId;
    @Basic(optional = false)
    @Column(name = "obs_datetime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date obsDatetime;
    @Column(name = "accession_number", length = 255)
    private String accessionNumber;
    @Column(name = "value_group_id")
    private Integer valueGroupId;
    @Column(name = "value_boolean")
    private Boolean valueBoolean;
    @Column(name = "value_datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date valueDatetime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "value_numeric", precision = 22)
    private Double valueNumeric;
    @Column(name = "value_modifier", length = 2)
    private String valueModifier;
    @Lob
    @Column(name = "value_text", length = 65535)
    private String valueText;
    @Column(name = "comments", length = 255)
    private String comments;
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
    @Column(name = "value_complex", length = 255)
    private String valueComplex;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @OneToMany(mappedBy = "previousVersion")
    private List<Obs> obsList;
    @JoinColumn(name = "previous_version", referencedColumnName = "obs_id")
    @ManyToOne
    private Obs previousVersion;
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", nullable = false)
    @ManyToOne(optional = false)
    private Person personId;
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    @ManyToOne
    private Orders orderId;
    @JoinColumn(name = "value_coded_name_id", referencedColumnName = "concept_name_id")
    @ManyToOne
    private ConceptName valueCodedNameId;
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    @ManyToOne
    private Location locationId;
    @OneToMany(mappedBy = "obsGroupId")
    private List<Obs> obsList1;
    @JoinColumn(name = "obs_group_id", referencedColumnName = "obs_id")
    @ManyToOne
    private Obs obsGroupId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "concept_id", referencedColumnName = "concept_id", nullable = false)
    @ManyToOne(optional = false)
    private Concept conceptId;
    @JoinColumn(name = "encounter_id", referencedColumnName = "encounter_id")
    @ManyToOne
    private Encounter encounterId;
    @JoinColumn(name = "value_drug", referencedColumnName = "drug_id")
    @ManyToOne
    private Drug valueDrug;
    @JoinColumn(name = "value_coded", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept valueCoded;

    public Obs() {
    }

    public Obs(Integer obsId) {
        this.obsId = obsId;
    }

    public Obs(Integer obsId, Date obsDatetime, Date dateCreated, boolean voided, String uuid) {
        this.obsId = obsId;
        this.obsDatetime = obsDatetime;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
    }

    public Integer getObsId() {
        return obsId;
    }

    public void setObsId(Integer obsId) {
        this.obsId = obsId;
    }

    public Date getObsDatetime() {
        return obsDatetime;
    }

    public void setObsDatetime(Date obsDatetime) {
        this.obsDatetime = obsDatetime;
    }

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public Integer getValueGroupId() {
        return valueGroupId;
    }

    public void setValueGroupId(Integer valueGroupId) {
        this.valueGroupId = valueGroupId;
    }

    public Boolean getValueBoolean() {
        return valueBoolean;
    }

    public void setValueBoolean(Boolean valueBoolean) {
        this.valueBoolean = valueBoolean;
    }

    public Date getValueDatetime() {
        return valueDatetime;
    }

    public void setValueDatetime(Date valueDatetime) {
        this.valueDatetime = valueDatetime;
    }

    public Double getValueNumeric() {
        return valueNumeric;
    }

    public void setValueNumeric(Double valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    public String getValueModifier() {
        return valueModifier;
    }

    public void setValueModifier(String valueModifier) {
        this.valueModifier = valueModifier;
    }

    public String getValueText() {
        return valueText;
    }

    public void setValueText(String valueText) {
        this.valueText = valueText;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getValueComplex() {
        return valueComplex;
    }

    public void setValueComplex(String valueComplex) {
        this.valueComplex = valueComplex;
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

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    public Obs getPreviousVersion() {
        return previousVersion;
    }

    public void setPreviousVersion(Obs previousVersion) {
        this.previousVersion = previousVersion;
    }

    public Person getPersonId() {
        return personId;
    }

    public void setPersonId(Person personId) {
        this.personId = personId;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public ConceptName getValueCodedNameId() {
        return valueCodedNameId;
    }

    public void setValueCodedNameId(ConceptName valueCodedNameId) {
        this.valueCodedNameId = valueCodedNameId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
    }

    @XmlTransient
    public List<Obs> getObsList1() {
        return obsList1;
    }

    public void setObsList1(List<Obs> obsList1) {
        this.obsList1 = obsList1;
    }

    public Obs getObsGroupId() {
        return obsGroupId;
    }

    public void setObsGroupId(Obs obsGroupId) {
        this.obsGroupId = obsGroupId;
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

    public Encounter getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Encounter encounterId) {
        this.encounterId = encounterId;
    }

    public Drug getValueDrug() {
        return valueDrug;
    }

    public void setValueDrug(Drug valueDrug) {
        this.valueDrug = valueDrug;
    }

    public Concept getValueCoded() {
        return valueCoded;
    }

    public void setValueCoded(Concept valueCoded) {
        this.valueCoded = valueCoded;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (obsId != null ? obsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Obs)) {
            return false;
        }
        Obs other = (Obs) object;
        if ((this.obsId == null && other.obsId != null) || (this.obsId != null && !this.obsId.equals(other.obsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Obs[ obsId=" + obsId + " ]";
    }
    
}
