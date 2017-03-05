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
import org.ccs.openmrs.migracao.entidades.ConceptName;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.ConceptNameDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ConceptNameDao
implements ConceptNameDaoInterface<ConceptName, String> {
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
    public void persist(ConceptName entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(ConceptName entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public ConceptName findById(String id) {
        ConceptName conceptName = (ConceptName)this.getCurrentSession().get((Class)ConceptName.class, (Serializable)((Object)id));
        return conceptName;
    }

    public ConceptName findByName(String regime) {
        ConceptName conceptName = (ConceptName)this.getCurrentSession().createQuery("from ConceptName cn where cn.locale = 'pt' AND cn.name = '" + regime + "'").uniqueResult();
        return conceptName;
    }

    @Override
    public void delete(ConceptName entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<ConceptName> findAll() {
        List conceptNames = this.getCurrentSession().createQuery("from ConceptName").list();
        return conceptNames;
    }

    @Override
    public void deleteAll() {
        List<ConceptName> entityList = this.findAll();
        for (ConceptName entity : entityList) {
            this.delete(entity);
        }
    }
}

