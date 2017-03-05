/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.sql.Date;
import java.util.List;
import org.ccs.openmrs.migracao.entidades.EncounterProvider;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.EncounterProviderDao;
import org.hibernate.Session;

public class EncounterProviderService {
    private static EncounterProviderDao encounterProviderDao;

    public EncounterProviderService() {
        encounterProviderDao = new EncounterProviderDao();
    }

    public void persist(EncounterProvider entity) {
        encounterProviderDao.openCurrentSessionwithTransaction();
        encounterProviderDao.persist(entity);
        encounterProviderDao.closeCurrentSessionwithTransaction();
    }

    public void update(EncounterProvider entity) {
        encounterProviderDao.openCurrentSessionwithTransaction();
        encounterProviderDao.update(entity);
        encounterProviderDao.closeCurrentSessionwithTransaction();
    }

    public EncounterProvider findById(String id) {
        encounterProviderDao.openCurrentSession();
        EncounterProvider obs = encounterProviderDao.findById(id);
        encounterProviderDao.closeCurrentSession();
        return obs;
    }

    public EncounterProvider findByEncounterAndPickupDate(Integer id,Date pickupDate) {
        encounterProviderDao.openCurrentSession();
        EncounterProvider obs = encounterProviderDao.findByEncounterAndPickupDate(id,pickupDate);
        encounterProviderDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        encounterProviderDao.openCurrentSessionwithTransaction();
        EncounterProvider obs = encounterProviderDao.findById(id);
        encounterProviderDao.delete(obs);
        encounterProviderDao.closeCurrentSessionwithTransaction();
    }

    public List<EncounterProvider> findAll() {
        encounterProviderDao.openCurrentSession();
        List<EncounterProvider> obss = encounterProviderDao.findAll();
        encounterProviderDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        encounterProviderDao.openCurrentSessionwithTransaction();
        encounterProviderDao.deleteAll();
        encounterProviderDao.closeCurrentSessionwithTransaction();
    }

    public EncounterProviderDao encounterProviderDao() {
        return encounterProviderDao;
    }
}

