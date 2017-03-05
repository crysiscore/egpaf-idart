/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.RegimeTerapeuticoInterface;
import org.celllife.idart.database.hibernate.RegimeTerapeutico;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author ColacoVM
 */
public class RegimeTerapeuticoExportDao implements RegimeTerapeuticoInterface<RegimeTerapeutico, String>{
   public Session currentSession;
    public Transaction currentTransaction;

    public Session openCurrentSession() {
        this.currentSession = hibernateConection.getInstanceLocal();
        return this.currentSession;
    }

    public Session openCurrentSessionwithTransaction() {
        this.currentSession = hibernateConection.getInstanceLocal();
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
    public void persist(RegimeTerapeutico entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(RegimeTerapeutico entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public RegimeTerapeutico findById(String id) {
        RegimeTerapeutico regimeTerapeutico = (RegimeTerapeutico)this.getCurrentSession().get((Class)RegimeTerapeutico.class, (Serializable)((Object)id));
        return regimeTerapeutico;
    }

    public RegimeTerapeutico findByRegimeId(String id) {
        RegimeTerapeutico regimeTerapeutico = (RegimeTerapeutico)this.getCurrentSession().createQuery("from RegimeTerapeutico r where r.regimeid = " + Integer.parseInt(id)).uniqueResult();
        return regimeTerapeutico;
    }

    @Override
    public void delete(RegimeTerapeutico entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<RegimeTerapeutico> findAll() {
        List regimeTerapeuticos = this.getCurrentSession().createQuery("from RegimeTerapeutico").list();
        return regimeTerapeuticos;
    }

    @Override
    public void deleteAll() {
        List<RegimeTerapeutico> entityList = this.findAll();
        for (RegimeTerapeutico entity : entityList) {
            this.delete(entity);
        }
    }
}
