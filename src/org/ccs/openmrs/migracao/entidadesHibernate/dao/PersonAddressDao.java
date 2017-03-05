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
import org.ccs.openmrs.migracao.entidades.PersonAddress;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PersonAdressDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonAddressDao
implements PersonAdressDaoInterface<PersonAddress, String> {
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
    public void persist(PersonAddress entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(PersonAddress entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public PersonAddress findById(String id) {
        PersonAddress personAddress = (PersonAddress)this.getCurrentSession().get((Class)PersonAddress.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return personAddress;
    }

    @Override
    public PersonAddress findByPersonId(String id) {
        PersonAddress personAddress = null;
        personAddress = (PersonAddress)this.getCurrentSession().createQuery("from PersonAddress p where p.preferred = 1 AND p.personId = " + Integer.parseInt(id)).uniqueResult();
        if (personAddress == null) {
            personAddress = (PersonAddress)this.getCurrentSession().createQuery("from PersonAddress p where p.voidedBy IS NULL AND p.personId = " + Integer.parseInt(id)).uniqueResult();
        }
        return personAddress;
    }

    @Override
    public void delete(PersonAddress entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<PersonAddress> findAll() {
        List personAddress = this.getCurrentSession().createQuery("from PersonAddress").list();
        return personAddress;
    }

    @Override
    public void deleteAll() {
        List<PersonAddress> entityList = this.findAll();
        for (PersonAddress entity : entityList) {
            this.delete(entity);
        }
    }
}

