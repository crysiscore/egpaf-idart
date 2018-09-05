/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celllife.idart.database.hibernate;

import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author agnaldo
 */
@Entity
@Table(name = "gaacmember")
public class GaacMember {

	@Id
	@GeneratedValue
	private Integer id;

        @ManyToOne
	@JoinColumn(name = "gaac")
	private Gaac gaac;
        
     	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient")
     	private Patient patient;

        private Date startdate;

	private int leaving;
	private String reasonleaving;
        private Date datecreated;
        
        private int voided;
        private String voidedreason;
        private String voidedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

  
    public int getLeaving() {
        return leaving;
    }

    public void setLeaving(int leaving) {
        this.leaving = leaving;
    }

    public String getReasonleaving() {
        return reasonleaving;
    }

    public void setReasonleaving(String reasonleaving) {
        this.reasonleaving = reasonleaving;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public int getVoided() {
        return voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }

    public String getVoidedreason() {
        return voidedreason;
    }

    public void setVoidedreason(String voidedreason) {
        this.voidedreason = voidedreason;
    }

    public String getVoidedby() {
        return voidedby;
    }

    public void setVoidedby(String voidedby) {
        this.voidedby = voidedby;
    }

    public Gaac getGaac() {
        return gaac;
    }

    public void setGaac(Gaac gaac) {
        this.gaac = gaac;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }


        
        
}
