
/**
 *
 * @author agnaldo
 */
package org.celllife.idart.database.hibernate;

import java.util.Date;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;

/**
 */
@Entity
@Table(name = "gaac")
public class Gaac {

	private String name;
	private String description;

	@Id
	@GeneratedValue
	private Integer id;

	private Date startDate;
        private Date endDate;

	private int focalpatient;

	private int crumbled;
	private String crumbledreason;
        private Date crumbleddate;
        
        private int voided;
	private Date voideddate;
        private String voidedreason;
        private String voidedby;
        
	private String affinitytype;
        
        @OneToMany(mappedBy = "gaac")
	@Cascade( { org.hibernate.annotations.CascadeType.ALL,
			org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<GaacMember> members;

	public Gaac() {
		super();

	}

	/**
	 * @param name
	 * @param description
	 * @param startDate
	 * @param focalpatient
	 * @param affinitytype
	 */
	public Gaac(String name, String description, Date startDate,
			int focalpatient, String affinitytype) {
		super();
		this.name = name;
		this.description = description;

		this.startDate = startDate;
		this.focalpatient = focalpatient;
		this.affinitytype = affinitytype;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getFocalpatient() {
        return focalpatient;
    }

    public void setFocalpatient(int focalpatient) {
        this.focalpatient = focalpatient;
    }

    public int getCrumbled() {
        return crumbled;
    }

    public void setCrumbled(int crumbled) {
        this.crumbled = crumbled;
    }

    public String getCrumbledreason() {
        return crumbledreason;
    }

    public void setCrumbledreason(String crumbledreason) {
        this.crumbledreason = crumbledreason;
    }

    public Date getCrumbleddate() {
        return crumbleddate;
    }

    public void setCrumbleddate(Date crumbleddate) {
        this.crumbleddate = crumbleddate;
    }

    public int getVoided() {
        return voided;
    }

    public void setVoided(int voided) {
        this.voided = voided;
    }

    public Date getVoideddate() {
        return voideddate;
    }

    public void setVoideddate(Date voideddate) {
        this.voideddate = voideddate;
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

    public String getAffinitytype() {
        return affinitytype;
    }

    public void setAffinitytype(String affinitytype) {
        this.affinitytype = affinitytype;
    }

	
	

}
