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
import org.ccs.openmrs.migracao.entidades.EncounterRole;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.EncounterRoleDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class EncounterRoleDao
implements EncounterRoleDaoInterface<EncounterRole, String> {
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
    public void persist(EncounterRole entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(EncounterRole entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public EncounterRole findById(String id) {
        EncounterRole encounterRole = (EncounterRole)this.getCurrentSession().get((Class)EncounterRole.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return encounterRole;
    }

    public EncounterRole findByPersonId(String id) {
        EncounterRole encounterRole = (EncounterRole)this.getCurrentSession().createQuery("from EncounterRole p where p.person = " + Integer.parseInt(id)).uniqueResult();
        return encounterRole;
    }

    @Override
    public void delete(EncounterRole entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<EncounterRole> findAll() {
        List encounterRoles = this.getCurrentSession().createQuery("from EncounterRole").list();
        return encounterRoles;
    }

    @Override
    public void deleteAll() {
        List<EncounterRole> entityList = this.findAll();
        for (EncounterRole entity : entityList) {
            this.delete(entity);
        }
    }
}

