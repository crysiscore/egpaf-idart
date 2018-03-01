/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Query
 *  org.hibernate.Session
 *  org.hibernate.Transaction
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDao;

import java.io.Serializable;
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.PackageDrugInfoInterface;
import org.celllife.idart.database.hibernate.tmp.PackageDrugInfo;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PackageDrugInfoExportDao
implements PackageDrugInfoInterface<PackageDrugInfo, String> {
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
    public void persist(PackageDrugInfo entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(PackageDrugInfo entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public PackageDrugInfo findById(String id) {
        PackageDrugInfo packageDrugInfo = (PackageDrugInfo)this.getCurrentSession().get((Class)PackageDrugInfo.class, (Serializable)((Object)id));
        return packageDrugInfo;
    }

    public PackageDrugInfo findByPatientId(String id) {
        PackageDrugInfo packageDrugInfo = (PackageDrugInfo)this.getCurrentSession().createQuery("from PackageDrugInfo p where p.patientId = '" +id+"'").uniqueResult();
        return packageDrugInfo;
    }

    @Override
    public void delete(PackageDrugInfo entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<PackageDrugInfo> findAll() {
        List packageDrugInfos = this.getCurrentSession().createQuery("from PackageDrugInfo p where p.notes <> 'Exported' OR p.notes IS NULL ORDER BY p.dispenseDate desc ").list();
        return packageDrugInfos;
    }

    @Override
    public List<PackageDrugInfo> findAllbyPatientID(String identifier) {
        List packageDrugInfos = this.getCurrentSession().createQuery("from PackageDrugInfo p where p.patientId = '" +identifier+"'").list();
        return packageDrugInfos;
    }
    
    @Override
    public void deleteAll() {
        List<PackageDrugInfo> entityList = this.findAll();
        for (PackageDrugInfo entity : entityList) {
            this.delete(entity);
        }
    }
  
}

