/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.PersonName;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PersonNameDaoInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ColacoVM
 */
public class PersonNameExportDao implements PersonNameDaoInterface<PersonName, String> {
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
    public void persist(PersonName entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(PersonName entity) {
        this.getCurrentSession().update((Object)entity);
    }
   @Override
    public PersonName findById(String id) {
        PersonName personName = (PersonName)this.getCurrentSession().get((Class)PersonName.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return personName;
    }

    @Override
    public PersonName findByPersonId(String id) {
        PersonName personName = (PersonName)this.getCurrentSession().createQuery("from PersonName p where p.preferred = 1 AND p.personId = " + Integer.parseInt(id)).uniqueResult();
        if (personName == null) {
            personName = (PersonName)this.getCurrentSession().createQuery("from PersonName p where p.personId = " + Integer.parseInt(id)).list().get(0);
        }
        return personName;
    }

    @Override
    public List<PersonName> findByAllGivenNameLike(String name) {
        List personNames = this.getCurrentSession().createQuery("from PersonName p where p.preferred = 1 AND p.given_name like '%"+name+"%'").list();
        return personNames;
    }
    
         @Override
    public List<PersonName> findByAllFamilyNameLike(String surname) {
        List personNames = this.getCurrentSession().createQuery("from PersonName p where p.preferred = 1 AND p.family_name like '%"+surname+"%'").list();
        return personNames;
    }
    
    @Override
    public void delete(PersonName entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<PersonName> findAll() {
        List personNames = this.getCurrentSession().createQuery("from PersonName").list();
        return personNames;
    }

    @Override
    public void deleteAll() {
        List<PersonName> entityList = this.findAll();
        for (PersonName entity : entityList) {
            this.delete(entity);
        }
    }
}

