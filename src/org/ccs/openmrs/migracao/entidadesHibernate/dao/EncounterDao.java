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
import org.ccs.openmrs.migracao.entidades.Encounter;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.EncounterDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EncounterDao
implements EncounterDaoInterface<Encounter, String> {
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
    public void persist(Encounter entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Encounter entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Encounter findById(String id) {
        Encounter encounter = (Encounter)this.getCurrentSession().get((Class)Encounter.class, (Serializable)((Object)id));
        return encounter;
    }

    public Encounter findByVisitAndPickupDate(Integer id,Date pickupDate) {
        Encounter encounter =null;
        List<Encounter> listencounter = this.getCurrentSession().createQuery("from Encounter e where e.visitId = " + id+" AND e.dateCreated = '"+pickupDate+"'" ).list();
        if(!listencounter.isEmpty()){
        encounter= listencounter.get(0);
        }
        return encounter;
    }

    @Override
    public void delete(Encounter entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Encounter> findAll() {
        List encounters = this.getCurrentSession().createQuery("from Encounter").list();
        return encounters;
    }

    @Override
    public void deleteAll() {
        List<Encounter> entityList = this.findAll();
        for (Encounter entity : entityList) {
            this.delete(entity);
        }
    }
}

