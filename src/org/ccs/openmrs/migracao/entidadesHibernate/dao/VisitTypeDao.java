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
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.VisitType;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.VisitTypeDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class VisitTypeDao
implements VisitTypeDaoInterface<VisitType, String> {
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
    public void persist(VisitType entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(VisitType entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public VisitType findById(String id) {
        VisitType visitType = (VisitType)this.getCurrentSession().get((Class)VisitType.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return visitType;
    }

    @Override
    public void delete(VisitType entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<VisitType> findAll() {
        List visitTypes = this.getCurrentSession().createQuery("from VisitType").list();
        return visitTypes;
    }

    @Override
    public void deleteAll() {
        List<VisitType> entityList = this.findAll();
        for (VisitType entity : entityList) {
            this.delete(entity);
        }
    }
}

