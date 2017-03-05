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
import org.ccs.openmrs.migracao.entidades.Form;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.FormDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FormDao
implements FormDaoInterface<Form, String> {
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
    public void persist(Form entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Form entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Form findById(String id) {
        Form form = (Form)this.getCurrentSession().get((Class)Form.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return form;
    }

    @Override
    public void delete(Form entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Form> findAll() {
        List forms = this.getCurrentSession().createQuery("from Form").list();
        return forms;
    }

    @Override
    public void deleteAll() {
        List<Form> entityList = this.findAll();
        for (Form entity : entityList) {
            this.delete(entity);
        }
    }
}

