/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.ConceptName;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.ConceptNameDao;
import org.hibernate.Session;

public class ConceptNameService {
    private static ConceptNameDao conceptNameDao;

    public ConceptNameService() {
        conceptNameDao = new ConceptNameDao();
    }

    public void persist(ConceptName entity) {
        conceptNameDao.openCurrentSessionwithTransaction();
        conceptNameDao.persist(entity);
        conceptNameDao.closeCurrentSessionwithTransaction();
    }

    public void update(ConceptName entity) {
        conceptNameDao.openCurrentSessionwithTransaction();
        conceptNameDao.update(entity);
        conceptNameDao.closeCurrentSessionwithTransaction();
    }

    public ConceptName findById(String id) {
        conceptNameDao.openCurrentSession();
        ConceptName obs = conceptNameDao.findById(id);
        conceptNameDao.closeCurrentSession();
        return obs;
    }

    public ConceptName findByName(String regime) {
        conceptNameDao.openCurrentSession();
        ConceptName conceptName = conceptNameDao.findByName(regime);
        conceptNameDao.closeCurrentSession();
        return conceptName;
    }

    public ConceptName findByName2Line(String regime) {
        conceptNameDao.openCurrentSession();
        ConceptName conceptName = conceptNameDao.findByName2Line(regime);
        conceptNameDao.closeCurrentSession();
        return conceptName;
    }
    
    public ConceptName findByName3Line(String regime) {
        conceptNameDao.openCurrentSession();
        ConceptName conceptName = conceptNameDao.findByName3Line(regime);
        conceptNameDao.closeCurrentSession();
        return conceptName;
    }
    
    
    public void delete(String id) {
        conceptNameDao.openCurrentSessionwithTransaction();
        ConceptName conceptName = conceptNameDao.findById(id);
        conceptNameDao.delete(conceptName);
        conceptNameDao.closeCurrentSessionwithTransaction();
    }

    public List<ConceptName> findAll() {
        conceptNameDao.openCurrentSession();
        List<ConceptName> obss = conceptNameDao.findAll();
        conceptNameDao.closeCurrentSession();
        return obss;
    }

    public void deleteAll() {
        conceptNameDao.openCurrentSessionwithTransaction();
        conceptNameDao.deleteAll();
        conceptNameDao.closeCurrentSessionwithTransaction();
    }

    public ConceptNameDao conceptNameDao() {
        return conceptNameDao;
    }
}
