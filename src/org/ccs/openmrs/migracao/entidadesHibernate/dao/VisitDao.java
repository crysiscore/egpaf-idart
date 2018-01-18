/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Query
 *  org.hibernate.Session
 *  org.hibernate.Transaction
 */
package org.ccs.openmrs.migracao.entidadesHibernate.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.Visit;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.VisitDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VisitDao
implements VisitDaoInterface<Visit, String> {
    public Session currentSession;
    public Transaction currentTransaction;

    public Session openCurrentSession() {
        this.currentSession = hibernateConection.getInstanceRemote();
        return this.currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        this.currentSession = hibernateConection.getInstanceRemote();
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
    public void persist(Visit entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Visit entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Visit findById(String id) {
        Visit visit = (Visit)this.getCurrentSession().get((Class)Visit.class, (Serializable)((Object)id));
        return visit;
    }

    public Visit findByPatientAndPickupDate(Integer id,Date pickupDate) {
        Visit visit =null;
        List <Visit> visitlist = this.getCurrentSession().createQuery("from Visit v where v.patientId = " + id+" AND v.dateStarted = '"+pickupDate+"' AND visitTypeId = 8 ").list();
       if(!visitlist.isEmpty()){
           visit = visitlist.get(0);
        }
        return visit;
    }

    @Override
    public void delete(Visit entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Visit> findAll() {
        List visits = this.getCurrentSession().createQuery("from Visit").list();
        return visits;
    }

    @Override
    public void deleteAll() {
        List<Visit> entityList = this.findAll();
        for (Visit entity : entityList) {
            this.delete(entity);
        }
    }
}

