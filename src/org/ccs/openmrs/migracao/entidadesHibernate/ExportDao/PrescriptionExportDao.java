/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PrescriptionInterface;
import org.celllife.idart.database.hibernate.Prescription;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ColacoVM
 */
public class PrescriptionExportDao implements PrescriptionInterface<Prescription, String>{
   public Session currentSession;
    public Transaction currentTransaction;

    public Session openCurrentSession() {
        this.currentSession = hibernateConection.getInstanceLocal();
        return this.currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        this.currentSession = hibernateConection.getInstanceLocal();
        this.currentTransaction = this.currentSession.beginTransaction();
        return this.currentSession;
    }

    public void closeCurrentSession() {
        this.currentSession.close();
    }

    public void closeCurrentSessionwithTransaction() {
        this.currentTransaction.commit();
        this.currentSession.close();
    }

    public Session getCurrentSession() {
        return this.currentSession;
    }

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return this.currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void persist(Prescription entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Prescription entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Prescription findById(String id) {
        Prescription prescription = (Prescription)this.getCurrentSession().get((Class)Prescription.class, (Serializable)((Object)id));
        return prescription;
    }

    public Prescription findByPrescricaoId(String id, String nid) {
        Prescription prescription = (Prescription)this.getCurrentSession().createQuery("from Prescription p where p.patient = '" +id+"' AND current = 'T'" + " AND prescriptionid like '%"+nid+"%'").uniqueResult();
        return prescription;
    }

    @Override
    public void delete(Prescription entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Prescription> findAll() {
        List prescriptions = this.getCurrentSession().createQuery("from Prescription").list();
        return prescriptions;
    }

    @Override
    public void deleteAll() {
        List<Prescription> entityList = this.findAll();
        for (Prescription entity : entityList) {
            this.delete(entity);
        }
    }
}
