/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Query
 *  org.hibernate.Session
 *  org.hibernate.Transaction
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ImportDao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PatientIdentifierDaoInterface;
import org.celllife.idart.database.hibernate.PatientIdentifier;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PatientIdentifierImportDao
implements PatientIdentifierDaoInterface<PatientIdentifier, String> {
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
    public void persist(PatientIdentifier entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(PatientIdentifier entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public PatientIdentifier findById(String id) {
        PatientIdentifier patientIdentifier = (PatientIdentifier)this.getCurrentSession().get((Class)PatientIdentifier.class, (Serializable)((Object)id));
        return patientIdentifier;
    }

    @Override
     public List<PatientIdentifier> findByAllIdentifierLike(String id) {
        List patientIdentifiers = this.getCurrentSession().createQuery("from PatientIdentifier p where p.identifier like '%"+id+"%'").list();
        return patientIdentifiers;
    }
    
    @Override
    public PatientIdentifier findByPatientId(String id) {
        PatientIdentifier patientIdentifier = (PatientIdentifier)this.getCurrentSession().createQuery("from PatientIdentifier p where AND p.patient = " + Integer.parseInt(id)).uniqueResult();
        return patientIdentifier;
    }
    
    public PatientIdentifier findByIdentifier(String identifier) {
         PatientIdentifier patientIdentifier = (PatientIdentifier)this.getCurrentSession().createQuery("from PatientIdentifier p where p.value = '" + identifier + "' AND p.type = 0").uniqueResult();
        return patientIdentifier;
    }

    @Override
    public void delete(PatientIdentifier entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<PatientIdentifier> findAll() {
        List patientIdentifiers = this.getCurrentSession().createQuery("from PatientIdentifier").list();
        return patientIdentifiers;
    }

    @Override
    public void deleteAll() {
        List<PatientIdentifier> entityList = this.findAll();
        for (PatientIdentifier entity : entityList) {
            this.delete(entity);
        }
    }

    @Override
    public List<PatientIdentifier> findByNidAndNameAndSurname(String var1, String var2, String var3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PatientIdentifier> findAllByNidLikeAndNameLikeAndSurnameLike(String var1, String var2, String var3) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

