/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.sql.Date;
import java.util.List;
import org.ccs.openmrs.migracao.entidades.Visit;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.VisitDao;
import org.hibernate.Session;

public class VisitService {
    private static VisitDao visitDao;

    public VisitService() {
        visitDao = new VisitDao();
    }

    public void persist(Visit entity) {
        visitDao.openCurrentSessionwithTransaction();
        visitDao.persist(entity);
        visitDao.closeCurrentSessionwithTransaction();
    }

    public void update(Visit entity) {
        visitDao.openCurrentSessionwithTransaction();
        visitDao.update(entity);
        visitDao.closeCurrentSessionwithTransaction();
    }

    public Visit findById(String id) {
        visitDao.openCurrentSession();
        Visit obs = visitDao.findById(id);
        visitDao.closeCurrentSession();
        return obs;
    }

    public Visit findByPatientAndPickupDate(Integer id,Date pickupDate) {
        visitDao.openCurrentSession();
        Visit obs = visitDao.findByPatientAndPickupDate(id,pickupDate);
        visitDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        visitDao.openCurrentSessionwithTransaction();
        Visit obs = visitDao.findById(id);
        visitDao.delete(obs);
        visitDao.closeCurrentSessionwithTransaction();
    }

    public List<Visit> findAll() {
        visitDao.openCurrentSession();
        List<Visit> obss = visitDao.findAll();
        visitDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        visitDao.openCurrentSessionwithTransaction();
        visitDao.deleteAll();
        visitDao.closeCurrentSessionwithTransaction();
    }

    public VisitDao visitDao() {
        return visitDao;
    }
}

