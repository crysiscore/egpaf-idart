/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.sql.Date;
import java.util.List;
import org.ccs.openmrs.migracao.entidades.Encounter;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.EncounterDao;
import org.hibernate.Session;

public class EncounterService {
    private static EncounterDao encounterDao;

    public EncounterService() {
        encounterDao = new EncounterDao();
    }

    public void persist(Encounter entity) {
        encounterDao.openCurrentSessionwithTransaction();
        encounterDao.persist(entity);
        encounterDao.closeCurrentSessionwithTransaction();
    }

    public void update(Encounter entity) {
        encounterDao.openCurrentSessionwithTransaction();
        encounterDao.update(entity);
        encounterDao.closeCurrentSessionwithTransaction();
    }

    public Encounter findById(String id) {
        encounterDao.openCurrentSession();
        Encounter obs = encounterDao.findById(id);
        encounterDao.closeCurrentSession();
        return obs;
    }

    public Encounter findByVisitAndPickupDate(Integer id,Date pickupDate) {
        encounterDao.openCurrentSession();
        Encounter obs = encounterDao.findByVisitAndPickupDate(id,pickupDate);
        encounterDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        encounterDao.openCurrentSessionwithTransaction();
        Encounter obs = encounterDao.findById(id);
        encounterDao.delete(obs);
        encounterDao.closeCurrentSessionwithTransaction();
    }

    public List<Encounter> findAll() {
        encounterDao.openCurrentSession();
        List<Encounter> obss = encounterDao.findAll();
        encounterDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        encounterDao.openCurrentSessionwithTransaction();
        encounterDao.deleteAll();
        encounterDao.closeCurrentSessionwithTransaction();
    }

    public EncounterDao encounterDao() {
        return encounterDao;
    }
}

