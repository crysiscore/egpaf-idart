/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.Provider;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.ProviderDao;
import org.hibernate.Session;

public class ProviderService {
    private static ProviderDao providerDao;

    public ProviderService() {
        providerDao = new ProviderDao();
    }

    public void persist(Provider entity) {
        providerDao.openCurrentSessionwithTransaction();
        providerDao.persist(entity);
        providerDao.closeCurrentSessionwithTransaction();
    }

    public void update(Provider entity) {
        providerDao.openCurrentSessionwithTransaction();
        providerDao.update(entity);
        providerDao.closeCurrentSessionwithTransaction();
    }

    public Provider findById(String id) {
        providerDao.openCurrentSession();
        Provider obs = providerDao.findById(id);
        providerDao.closeCurrentSession();
        return obs;
    }

    public void delete(String id) {
        providerDao.openCurrentSessionwithTransaction();
        Provider obs = providerDao.findById(id);
        providerDao.delete(obs);
        providerDao.closeCurrentSessionwithTransaction();
    }

    public List<Provider> findAll() {
        providerDao.openCurrentSession();
        List<Provider> obss = providerDao.findAll();
        providerDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        providerDao.openCurrentSessionwithTransaction();
        providerDao.deleteAll();
        providerDao.closeCurrentSessionwithTransaction();
    }

    public ProviderDao providerDao() {
        return providerDao;
    }
}

