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
import java.util.List;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.Concept;
import org.ccs.openmrs.migracao.entidades.Encounter;
import org.ccs.openmrs.migracao.entidades.Obs;
import org.ccs.openmrs.migracao.entidadesHibernate.Interfaces.ObsDaoInterface;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ObsDao
implements ObsDaoInterface<Obs, String> {
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
    public void persist(Obs entity) {
        this.getCurrentSession().save((Object)entity);
    }

    @Override
    public void update(Obs entity) {
        this.getCurrentSession().update((Object)entity);
    }

    @Override
    public Obs findById(String id) {
        Obs patientsObs = (Obs)this.getCurrentSession().get((Class)Obs.class, (Serializable)((Object)id));
        return patientsObs;
    }

    public Obs findByUuId(String id) {
        Obs obs = (Obs)this.getCurrentSession().createQuery("from Obs o where o.uuid = " + Integer.parseInt(id)).uniqueResult();
        return obs;
    }

    public Obs findByEncounterAndConcept(Encounter encounter, Concept concept) {
        Obs obs = (Obs)this.getCurrentSession().createQuery("from Obs o where o.encounterId = " + encounter.getEncounterId() + " AND o.conceptId = " + concept.getConceptId()).uniqueResult();
        return obs;
    }

    @Override
    public void delete(Obs entity) {
        this.getCurrentSession().delete((Object)entity);
    }

    @Override
    public List<Obs> findAll() {
        List patientsObs = this.getCurrentSession().createQuery("from Obs").list();
        return patientsObs;
    }

    @Override
    public List<Obs> findAllByConcept(String id) {
        
        SQLQuery query = getCurrentSession().createSQLQuery("SELECT * FROM obs o WHERE "
                                                                   + "(o.concept_Id = " + Integer.parseInt(id) + " )" + " AND "
                                                                   + "(o.comments <> 'Imported' OR o.comments IS NULL) "
                                                                   + "GROUP by o.person_Id");
         query.addEntity(Obs.class);
        List<Obs> patientsObs = query.list();
//        List<Obs> patientsObs = getCurrentSession().createQuery("FROM Obs o WHERE "
//                                                                   + "(o.conceptId = " + Integer.parseInt(id) + " )" + " AND "
//                                                                   + "(o.comments <> 'Imported' OR o.comments IS NULL) "
//                                                                   + "GROUP by o.personId").list();
//       
        return patientsObs;
    }

    @Override
    public void deleteAll() {
        List<Obs> entityList = this.findAll();
        for (Obs entity : entityList) {
            this.delete(entity);
        }
    }
}

