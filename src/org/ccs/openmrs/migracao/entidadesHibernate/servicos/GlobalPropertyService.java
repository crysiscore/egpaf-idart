/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.GlobalProperty;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.GlobalPropertyDao;

/**
 *
 * @author colaco
 */
public class GlobalPropertyService {
 private static GlobalPropertyDao globalPropertyDao;

    public GlobalPropertyService() {
        globalPropertyDao = new GlobalPropertyDao();
    }

    public void persist(GlobalProperty entity) {
        globalPropertyDao.openCurrentSessionwithTransaction();
        globalPropertyDao.persist(entity);
        globalPropertyDao.closeCurrentSessionwithTransaction();
    }

    public void update(GlobalProperty entity) {
        globalPropertyDao.openCurrentSessionwithTransaction();
        globalPropertyDao.update(entity);
        globalPropertyDao.closeCurrentSessionwithTransaction();
    }

    public GlobalProperty findById(String id) {
        globalPropertyDao.openCurrentSession();
        GlobalProperty obs = globalPropertyDao.findById(id);
        globalPropertyDao.closeCurrentSession();
        return obs;
    }

     public GlobalProperty findByDefaultName() {
        globalPropertyDao.openCurrentSession();
        GlobalProperty obs = globalPropertyDao.findByDefaultName();
        globalPropertyDao.closeCurrentSession();
        return obs;
    }
    
    public void delete(String id) {
        globalPropertyDao.openCurrentSessionwithTransaction();
        GlobalProperty obs = globalPropertyDao.findById(id);
        globalPropertyDao.delete(obs);
        globalPropertyDao.closeCurrentSessionwithTransaction();
    }

    public List<GlobalProperty> findAll() {
        globalPropertyDao.openCurrentSession();
        List<GlobalProperty> obss = globalPropertyDao.findAll();
        globalPropertyDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        globalPropertyDao.openCurrentSessionwithTransaction();
        globalPropertyDao.deleteAll();
        globalPropertyDao.closeCurrentSessionwithTransaction();
    }

    public GlobalPropertyDao globalPropertyDao() {
        return globalPropertyDao;
    }
}


