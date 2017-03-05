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
import org.ccs.openmrs.migracao.entidades.Person;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PersonDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PersonDao
implements PersonDaoInterface<Person, String> {
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
    public void persist(Person entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Person entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Person findById(String id) {
        Person person = (Person)this.getCurrentSession().get((Class)Person.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return person;
    }

    @Override
    public void delete(Person entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Person> findAll() {
        List persons = this.getCurrentSession().createQuery("from Person").list();
        return persons;
    }

    @Override
    public void deleteAll() {
        List<Person> entityList = this.findAll();
        for (Person entity : entityList) {
            this.delete(entity);
        }
    }
}

