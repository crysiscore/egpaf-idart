/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidades;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "orders", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findByOrderId", query = "SELECT o FROM Orders o WHERE o.orderId = :orderId"),
    @NamedQuery(name = "Orders.findByDateActivated", query = "SELECT o FROM Orders o WHERE o.dateActivated = :dateActivated"),
    @NamedQuery(name = "Orders.findByAutoExpireDate", query = "SELECT o FROM Orders o WHERE o.autoExpireDate = :autoExpireDate"),
    @NamedQuery(name = "Orders.findByDateStopped", query = "SELECT o FROM Orders o WHERE o.dateStopped = :dateStopped"),
    @NamedQuery(name = "Orders.findByDateCreated", query = "SELECT o FROM Orders o WHERE o.dateCreated = :dateCreated"),
    @NamedQuery(name = "Orders.findByVoided", query = "SELECT o FROM Orders o WHERE o.voided = :voided"),
    @NamedQuery(name = "Orders.findByDateVoided", query = "SELECT o FROM Orders o WHERE o.dateVoided = :dateVoided"),
    @NamedQuery(name = "Orders.findByVoidReason", query = "SELECT o FROM Orders o WHERE o.voidReason = :voidReason"),
    @NamedQuery(name = "Orders.findByAccessionNumber", query = "SELECT o FROM Orders o WHERE o.accessionNumber = :accessionNumber"),
    @NamedQuery(name = "Orders.findByUuid", query = "SELECT o FROM Orders o WHERE o.uuid = :uuid"),
    @NamedQuery(name = "Orders.findByOrderReasonNonCoded", query = "SELECT o FROM Orders o WHERE o.orderReasonNonCoded = :orderReasonNonCoded"),
    @NamedQuery(name = "Orders.findByUrgency", query = "SELECT o FROM Orders o WHERE o.urgency = :urgency"),
    @NamedQuery(name = "Orders.findByOrderNumber", query = "SELECT o FROM Orders o WHERE o.orderNumber = :orderNumber"),
    @NamedQuery(name = "Orders.findByOrderAction", query = "SELECT o FROM Orders o WHERE o.orderAction = :orderAction"),
    @NamedQuery(name = "Orders.findByCommentToFulfiller", query = "SELECT o FROM Orders o WHERE o.commentToFulfiller = :commentToFulfiller"),
    @NamedQuery(name = "Orders.findByScheduledDate", query = "SELECT o FROM Orders o WHERE o.scheduledDate = :scheduledDate"),
 //   @NamedQuery(name = "Orders.findByDiscontinued", query = "SELECT o FROM Orders o WHERE o.discontinued = :discontinued"),
   /// @NamedQuery(name = "Orders.findByDiscontinuedDate", query = "SELECT o FROM Orders o WHERE o.discontinuedDate = :discontinuedDate"),
    //@NamedQuery(name = "Orders.findByDiscontinuedReasonNonCoded", query = "SELECT o FROM Orders o WHERE o.discontinuedReasonNonCoded = :discontinuedReasonNonCoded"),
   // @NamedQuery(name = "Orders.findByDrugOrderId", query = "SELECT o FROM Orders o WHERE o.drugOrderId = :drugOrderId"),
  //  @NamedQuery(name = "Orders.findByStartDate", query = "SELECT o FROM Orders o WHERE o.startDate = :startDate"),
   // @NamedQuery(name = "Orders.findByTestOrderId", query = "SELECT o FROM Orders o WHERE o.testOrderId = :testOrderId"),
  //  @NamedQuery(name = "Orders.findByDiscontinuedBy", query = "SELECT o FROM Orders o WHERE o.discontinuedBy = :discontinuedBy")
})
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Lob
    @Column(name = "instructions", length = 65535)
    private String instructions;
    @Column(name = "date_activated")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateActivated;
    @Column(name = "auto_expire_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date autoExpireDate;
    @Column(name = "date_stopped")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStopped;
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
    @Column(name = "accession_number", length = 255)
    private String accessionNumber;
    @Basic(optional = false)
    @Column(name = "uuid", nullable = false, length = 38)
    private String uuid;
    @Column(name = "order_reason_non_coded", length = 255)
    private String orderReasonNonCoded;
    @Basic(optional = false)
    @Column(name = "urgency", nullable = false, length = 50)
    private String urgency;
    @Basic(optional = false)
    @Column(name = "order_number", nullable = false, length = 50)
    private String orderNumber;
    @Basic(optional = false)
    @Column(name = "order_action", nullable = false, length = 50)
    private String orderAction;
    @Column(name = "comment_to_fulfiller", length = 1024)
    private String commentToFulfiller;
    @Column(name = "scheduled_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scheduledDate;
   // @Basic(optional = false)
   // @Column(name = "discontinued", nullable = false)
   // private boolean discontinued;
  //  @Column(name = "discontinued_date")
   // @Temporal(TemporalType.TIMESTAMP)
    //private Date discontinuedDate;
    //@Column(name = "discontinued_reason_non_coded", length = 255)
    //private String discontinuedReasonNonCoded;
   // @Column(name = "drug_order_id")
    //private BigInteger drugOrderId;
 //   @Column(name = "start_date")
 //   @Temporal(TemporalType.TIMESTAMP)
 //   private Date startDate;
   // @Column(name = "test_order_id")
   // private BigInteger testOrderId;
 //   @Basic(optional = false)
 //   @Column(name = "discontinued_By", nullable = false)
 //   private long discontinuedBy;
    @OneToMany(mappedBy = "orderId")
    private List<Obs> obsList;
    @JoinColumn(name = "voided_by", referencedColumnName = "user_id")
    @ManyToOne
    private Users voidedBy;
    @JoinColumn(name = "order_type_id", referencedColumnName = "order_type_id", nullable = false)
    @ManyToOne(optional = false)
    private OrderType orderTypeId;
   // @OneToMany(mappedBy = "previousOrderId")
   // private List<Orders> ordersList;
    //@JoinColumn(name = "previous_order_id", referencedColumnName = "order_id")
   // @ManyToOne
   // private Orders previousOrderId;
    @JoinColumn(name = "encounter_id", referencedColumnName = "encounter_id", nullable = false)
    @ManyToOne(optional = false)
    private Encounter encounterId;
    @JoinColumn(name = "care_setting", referencedColumnName = "care_setting_id", nullable = false)
    @ManyToOne(optional = false)
    private CareSetting careSetting;
    @JoinColumn(name = "patient_id", referencedColumnName = "patient_id", nullable = false)
    @ManyToOne(optional = false)
    private Patient patientId;
    @JoinColumn(name = "creator", referencedColumnName = "user_id", nullable = false)
    @ManyToOne(optional = false)
    private Users creator;
    @JoinColumn(name = "orderer", referencedColumnName = "provider_id", nullable = false)
    @ManyToOne(optional = false)
    private Provider orderer;
    @JoinColumn(name = "order_reason", referencedColumnName = "concept_id")
    @ManyToOne
    private Concept orderReason;
    @JoinColumn(name = "concept_id", referencedColumnName = "concept_id", nullable = false)
    @ManyToOne(optional = false)
    private Concept conceptId;
    @OneToMany(mappedBy = "parent")
    private List<OrderType> orderTypeList;

    public Orders() {
    }

    public Orders(Integer orderId) {
        this.orderId = orderId;
    }

    public Orders(Integer orderId, Date dateCreated, boolean voided, String uuid, String urgency, String orderNumber, String orderAction, long creatorId, boolean discontinued, long usersByDiscontinuedById, long usersByOrdererId, long voidedById, long discontinuedBy) {
        this.orderId = orderId;
        this.dateCreated = dateCreated;
        this.voided = voided;
        this.uuid = uuid;
        this.urgency = urgency;
        this.orderNumber = orderNumber;
        this.orderAction = orderAction;
    //    this.discontinued = discontinued;
   //     this.discontinuedBy = discontinuedBy;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDateActivated() {
        return dateActivated;
    }

    public void setDateActivated(Date dateActivated) {
        this.dateActivated = dateActivated;
    }

    public Date getAutoExpireDate() {
        return autoExpireDate;
    }

    public void setAutoExpireDate(Date autoExpireDate) {
        this.autoExpireDate = autoExpireDate;
    }

    public Date getDateStopped() {
        return dateStopped;
    }

    public void setDateStopped(Date dateStopped) {
        this.dateStopped = dateStopped;
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

    public String getAccessionNumber() {
        return accessionNumber;
    }

    public void setAccessionNumber(String accessionNumber) {
        this.accessionNumber = accessionNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderReasonNonCoded() {
        return orderReasonNonCoded;
    }

    public void setOrderReasonNonCoded(String orderReasonNonCoded) {
        this.orderReasonNonCoded = orderReasonNonCoded;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderAction() {
        return orderAction;
    }

    public void setOrderAction(String orderAction) {
        this.orderAction = orderAction;
    }

    public String getCommentToFulfiller() {
        return commentToFulfiller;
    }

    public void setCommentToFulfiller(String commentToFulfiller) {
        this.commentToFulfiller = commentToFulfiller;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

 //   public boolean getDiscontinued() {
 //       return discontinued;
 //   }

 //   public void setDiscontinued(boolean discontinued) {
  //      this.discontinued = discontinued;
 //   }

    //public Date getDiscontinuedDate() {
    //    return discontinuedDate;
    //}

   // public void setDiscontinuedDate(Date discontinuedDate) {
   //     this.discontinuedDate = discontinuedDate;
    //}

   /// public String getDiscontinuedReasonNonCoded() {
   //     return discontinuedReasonNonCoded;
   /// }

   // public void setDiscontinuedReasonNonCoded(String discontinuedReasonNonCoded) {
   //     this.discontinuedReasonNonCoded = discontinuedReasonNonCoded;
   // }

//    public BigInteger getDrugOrderId() {
//        return drugOrderId;
//    }
//
//    public void setDrugOrderId(BigInteger drugOrderId) {
//        this.drugOrderId = drugOrderId;
//    }

//    public Date getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(Date startDate) {
//        this.startDate = startDate;
//    }

//    public BigInteger getTestOrderId() {
//        return testOrderId;
//    }
//
//    public void setTestOrderId(BigInteger testOrderId) {
//        this.testOrderId = testOrderId;
//    }

//   public long getDiscontinuedBy() {
//        return discontinuedBy;
//    }

//    public void setDiscontinuedBy(long discontinuedBy) {
 //       this.discontinuedBy = discontinuedBy;
  //  }

    @XmlTransient
    public List<Obs> getObsList() {
        return obsList;
    }

    public void setObsList(List<Obs> obsList) {
        this.obsList = obsList;
    }

    public Users getVoidedBy() {
        return voidedBy;
    }

    public void setVoidedBy(Users voidedBy) {
        this.voidedBy = voidedBy;
    }

//    public OrderType getOrderTypeId() {
//        return orderTypeId;
//    }
//
//    public void setOrderTypeId(OrderType orderTypeId) {
//        this.orderTypeId = orderTypeId;
//    }
//
//    @XmlTransient
//    public List<Orders> getOrdersList() {
//        return ordersList;
//    }
//
//    public void setOrdersList(List<Orders> ordersList) {
//        this.ordersList = ordersList;
//    }
//
//    public Orders getPreviousOrderId() {
//        return previousOrderId;
//    }
//
//    public void setPreviousOrderId(Orders previousOrderId) {
//        this.previousOrderId = previousOrderId;
//    }

    public Encounter getEncounterId() {
        return encounterId;
    }

    public void setEncounterId(Encounter encounterId) {
        this.encounterId = encounterId;
    }

    public CareSetting getCareSetting() {
        return careSetting;
    }

    public void setCareSetting(CareSetting careSetting) {
        this.careSetting = careSetting;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Users getCreator() {
        return creator;
    }

    public void setCreator(Users creator) {
        this.creator = creator;
    }

    public Provider getOrderer() {
        return orderer;
    }

    public void setOrderer(Provider orderer) {
        this.orderer = orderer;
    }

    public Concept getOrderReason() {
        return orderReason;
    }

    public void setOrderReason(Concept orderReason) {
        this.orderReason = orderReason;
    }

    public Concept getConceptId() {
        return conceptId;
    }

    public void setConceptId(Concept conceptId) {
        this.conceptId = conceptId;
    }

    @XmlTransient
    public List<OrderType> getOrderTypeList() {
        return orderTypeList;
    }

    public void setOrderTypeList(List<OrderType> orderTypeList) {
        this.orderTypeList = orderTypeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Orders[ orderId=" + orderId + " ]";
    }
    
}
