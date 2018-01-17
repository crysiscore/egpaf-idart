/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.dao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.PatientProgram;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PatientProgramDaoInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ColacoVM
 */
public class PatientProgramDao implements PatientProgramDaoInterface<PatientProgram, String> {
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
    public void persist(PatientProgram entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(PatientProgram entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public PatientProgram findById(String id) {
        PatientProgram concept = (PatientProgram)this.getCurrentSession().get((Class)PatientProgram.class, (Serializable)Integer.valueOf(Integer.parseInt(id)));
        return concept;
    }

    @Override
    public void delete(PatientProgram entity) {
        this.getCurrentSession().delete((Object)entity);
    }
  //TODO
  //Review - colaco
    @Override
    public List<PatientProgram> findAll() {
        List patientPrograms = this.getCurrentSession().createQuery("from PatientProgram p "
                                                                    + "where p.programId = 2 and "
                                                                    + "p.idart is null and "
                                                                    + "p.dateEnrolled is not null and "
                                                                    + "p.dateCompleted is null").list();
        return patientPrograms;
    }

    @Override
    public void deleteAll() {
        List<PatientProgram> entityList = this.findAll();
        for (PatientProgram entity : entityList) {
            this.delete(entity);
        }
    }
}

