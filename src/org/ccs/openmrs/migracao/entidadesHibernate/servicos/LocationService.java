/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.Location;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.LocationDao;
import org.hibernate.Session;

public class LocationService {
    private static LocationDao locationDao;

    public LocationService() {
        locationDao = new LocationDao();
    }

    public void persist(Location entity) {
        locationDao.openCurrentSessionwithTransaction();
        locationDao.persist(entity);
        locationDao.closeCurrentSessionwithTransaction();
    }

    public void update(Location entity) {
        locationDao.openCurrentSessionwithTransaction();
        locationDao.update(entity);
        locationDao.closeCurrentSessionwithTransaction();
    }

    public Location findById(String id) {
        locationDao.openCurrentSession();
        Location obs = locationDao.findById(id);
        locationDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        locationDao.openCurrentSessionwithTransaction();
        Location obs = locationDao.findById(id);
        locationDao.delete(obs);
        locationDao.closeCurrentSessionwithTransaction();
    }

    public List<Location> findAll() {
        locationDao.openCurrentSession();
        List<Location> obss = locationDao.findAll();
        locationDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        locationDao.openCurrentSessionwithTransaction();
        locationDao.deleteAll();
        locationDao.closeCurrentSessionwithTransaction();
    }

    public LocationDao locationDao() {
        return locationDao;
    }
}

