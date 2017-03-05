/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shy
 */
@Entity
@Table(name = "tribe", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tribe.findAll", query = "SELECT t FROM Tribe t"),
    @NamedQuery(name = "Tribe.findByTribeId", query = "SELECT t FROM Tribe t WHERE t.tribeId = :tribeId"),
    @NamedQuery(name = "Tribe.findByRetired", query = "SELECT t FROM Tribe t WHERE t.retired = :retired"),
    @NamedQuery(name = "Tribe.findByName", query = "SELECT t FROM Tribe t WHERE t.name = :name")})
public class Tribe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "tribe_id", nullable = false)
    private Integer tribeId;
    @Basic(optional = false)
    @Column(name = "retired", nullable = false)
    private boolean retired;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "tribe")
    private List<Patient> patientList;

    public Tribe() {
    }

    public Tribe(Integer tribeId) {
        this.tribeId = tribeId;
    }

    public Tribe(Integer tribeId, boolean retired, String name) {
        this.tribeId = tribeId;
        this.retired = retired;
        this.name = name;
    }

    public Integer getTribeId() {
        return tribeId;
    }

    public void setTribeId(Integer tribeId) {
        this.tribeId = tribeId;
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

    @XmlTransient
    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tribeId != null ? tribeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tribe)) {
            return false;
        }
        Tribe other = (Tribe) object;
        if ((this.tribeId == null && other.tribeId != null) || (this.tribeId != null && !this.tribeId.equals(other.tribeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Tribe[ tribeId=" + tribeId + " ]";
    }
    
}
