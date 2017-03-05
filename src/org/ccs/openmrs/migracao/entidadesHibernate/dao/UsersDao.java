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
import org.ccs.openmrs.migracao.entidades.Users;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.UserDaoInterface;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsersDao
implements UserDaoInterface<Users, String> {
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
    public void persist(Users entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Users entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Users findById(String id) {
        Users users = (Users)this.getCurrentSession().get((Class)Users.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return users;
    }

    @Override
    public void delete(Users entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Users> findAll() {
        List userses = this.getCurrentSession().createQuery("from Users").list();
        return userses;
    }

    @Override
    public void deleteAll() {
        List<Users> entityList = this.findAll();
        for (Users entity : entityList) {
            this.delete(entity);
        }
    }
}

