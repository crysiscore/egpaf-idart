/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
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
 * @author ColacoVM
 */
@Entity
@Table(name = "program", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Program.findAll", query = "SELECT p FROM Program p"),
    @NamedQuery(name = "Program.findByProgramId", query = "SELECT p FROM Program p WHERE p.programId = :programId"),
    @NamedQuery(name = "Program.findByDateCreated", query = "SELECT p FROM Program p WHERE p.dateCreated = :dateCreated"),
    @NamedQuery(name = "Program.findByDateChanged", query = "SELECT p FROM Program p WHERE p.dateChanged = :dateChanged"),
    @NamedQuery(name = "Program.findByRetired", query = "SELECT p FROM Program p WHERE p.retired = :retired"),
    @NamedQuery(name = "Program.findByName", query = "SELECT p FROM Program p WHERE p.name = :name"),
    @NamedQuery(name = "Program.findByDescription", query = "SELECT p FROM Program p WHERE p.description = :description"),
    @NamedQuery(name = "Program.findByUuid", query = "SELECT p FROM Program p WHERE p.uuid = :uuid"),
//    @NamedQuery(name = "Program.findByConceptByConceptIdId", query = "SELECT p FROM Program p WHERE p.conceptByConceptIdId = :conceptByConceptIdId")
})
public class Program implements Serializable {

    @JoinColumn(name = "changed_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users changedBy;
    @JoinColumn(name = "outcomes_concept_id", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept outcomesConceptId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id")
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "concept_id", referencedColumnName = "concept_id")
    @ManyToOne(optional = false)
    private Concept conceptId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "program_id", nullable = false)
    private Integer programId;
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
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "description", length = 500)
    private String description;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
//    @Column(name = "concept_by_concept_id_id")
//    private BigInteger conceptByConceptIdId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "programId")
    private Collection<PatientProgram> patientProgramCollection;

    public Program() {
    }

    public Program(Integer programId) {
        this.programId = programId;
    }

    public Program(Integer programId, Date dateCreated, boolean retired, String name, String uuid, long changedById, long creatorId) {
        this.programId = programId;
        this.dateCreated = dateCreated;
        this.retired = retired;
        this.name = name;
        this.uuid = uuid;
    }

    public Integer getProgramId() {
        return programId;
    }

    public void setProgramId(Integer programId) {
        this.programId = programId;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


//    public BigInteger getConceptByConceptIdId() {
//        return conceptByConceptIdId;
//    }
//
//    public void setConceptByConceptIdId(BigInteger conceptByConceptIdId) {
//        this.conceptByConceptIdId = conceptByConceptIdId;
//    }

    @XmlTransient
    public Collection<PatientProgram> getPatientProgramCollection() {
        return patientProgramCollection;
    }

    public void setPatientProgramCollection(Collection<PatientProgram> patientProgramCollection) {
        this.patientProgramCollection = patientProgramCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (programId != null ? programId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Program)) {
            return false;
        }
        Program other = (Program) object;
        if ((this.programId == null && other.programId != null) || (this.programId != null && !this.programId.equals(other.programId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ccs.openmrs.migracao.entidades.Program[ programId=" + programId + " ]";
    }

    public Users getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(Users changedBy) {
        this.changedBy = changedBy;
    }

    public Concept getOutcomesConceptId() {
        return outcomesConceptId;
    }

    public void setOutcomesConceptId(Concept outcomesConceptId) {
        this.outcomesConceptId = outcomesConceptId;
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
    
}
