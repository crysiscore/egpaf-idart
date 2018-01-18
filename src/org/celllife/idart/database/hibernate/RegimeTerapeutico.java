package org.celllife.idart.database.hibernate;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;

@Entity
public class RegimeTerapeutico {

    @Id
    @GeneratedValue
    private int regimeid;
    private String regimeesquema;
    private boolean active;
    private boolean adult;
    
 /*
 * 
 * Adicionei 2 atributos: linhaT & regimeDrugs
 * Metodo para buscar regime terapeutico - Idart antigo 
 * Modified by : Agnaldo Samuel
 * Modifica date: 27/03/2017
 */      
    @ManyToOne
    @JoinColumn(name = "linhaid")
    private LinhaT linhaT;

    
    @OneToMany(mappedBy = "regime")
    @Cascade({org.hibernate.annotations.CascadeType.ALL,
        org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    private Set<RegimeTerapeuticoDrugs> regimeDrugs;

    public int getRegimeid() {
        return regimeid;
    }

    public void setRegimeid(int regimeid) {
        this.regimeid = regimeid;
    }

    public String getRegimeesquema() {
        return regimeesquema;
    }

    public void setRegimeesquema(String regimeesquema) {
        this.regimeesquema = regimeesquema;
    }

    public RegimeTerapeutico(int regimeid, String regimeesquema, boolean active) {
        super();
        this.regimeid = regimeid;
        this.regimeesquema = regimeesquema;
        this.active = active;
    }

    public RegimeTerapeutico() {
        super();
        // TODO Auto-generated constructor stub
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public LinhaT getLinhaT() {
        return linhaT;
    }

    public void setLinhaT(LinhaT linhaT) {
        this.linhaT = linhaT;
    }


    public Set<RegimeTerapeuticoDrugs> getRegimeDrugs() {
        return regimeDrugs;
    }

    public void setRegimeDrugs(Set<RegimeTerapeuticoDrugs> regimeDrugs) {
        this.regimeDrugs = regimeDrugs;
    }
}
