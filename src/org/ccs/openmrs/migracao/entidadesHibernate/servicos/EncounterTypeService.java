/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.EncounterType;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.EncounterTypeDao;
import org.hibernate.Session;

public class EncounterTypeService {
    private static EncounterTypeDao encounterTypeDao;

    public EncounterTypeService() {
        encounterTypeDao = new EncounterTypeDao();
    }

    public void persist(EncounterType entity) {
        encounterTypeDao.openCurrentSessionwithTransaction();
        encounterTypeDao.persist(entity);
        encounterTypeDao.closeCurrentSessionwithTransaction();
    }

    public void update(EncounterType entity) {
        encounterTypeDao.openCurrentSessionwithTransaction();
        encounterTypeDao.update(entity);
        encounterTypeDao.closeCurrentSessionwithTransaction();
    }

    public EncounterType findById(String id) {
        encounterTypeDao.openCurrentSession();
        EncounterType obs = encounterTypeDao.findById(id);
        encounterTypeDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        encounterTypeDao.openCurrentSessionwithTransaction();
        EncounterType obs = encounterTypeDao.findById(id);
        encounterTypeDao.delete(obs);
        encounterTypeDao.closeCurrentSessionwithTransaction();
    }

    public List<EncounterType> findAll() {
        encounterTypeDao.openCurrentSession();
        List<EncounterType> obss = encounterTypeDao.findAll();
        encounterTypeDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        encounterTypeDao.openCurrentSessionwithTransaction();
        encounterTypeDao.deleteAll();
        encounterTypeDao.closeCurrentSessionwithTransaction();
    }

    public EncounterTypeDao encounterTypeDao() {
        return encounterTypeDao;
    }
}

