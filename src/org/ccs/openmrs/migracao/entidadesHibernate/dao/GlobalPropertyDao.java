/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.GlobalProperty;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.GlobalPropertyDaoInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author colaco
 */
public class GlobalPropertyDao implements GlobalPropertyDaoInterface<GlobalProperty, String> {
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
    public void persist(GlobalProperty entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(GlobalProperty entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public GlobalProperty findById(String id) {
        GlobalProperty globalProperty = (GlobalProperty)this.getCurrentSession().get((Class)GlobalProperty.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return globalProperty;
    }

    public GlobalProperty findByDefaultName() {
        GlobalProperty globalProperty = (GlobalProperty)this.getCurrentSession().createQuery("from GlobalProperty gp where gp.property = 'default_location'").uniqueResult();
        return globalProperty;
    }

    @Override
    public void delete(GlobalProperty entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<GlobalProperty> findAll() {
        List globalPropertys = this.getCurrentSession().createQuery("from GlobalProperty").list();
        return globalPropertys;
    }

    @Override
    public void deleteAll() {
        List<GlobalProperty> entityList = this.findAll();
        for (GlobalProperty entity : entityList) {
            this.delete(entity);
        }
    }
}