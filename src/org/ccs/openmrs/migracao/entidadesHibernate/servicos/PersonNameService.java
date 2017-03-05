/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.PersonName;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PersonNameDao;
import org.hibernate.Session;

public class PersonNameService {
    private static PersonNameDao personNameDao;

    public PersonNameService() {
        personNameDao = new PersonNameDao();
    }

    public void persist(PersonName entity) {
        personNameDao.openCurrentSessionwithTransaction();
        personNameDao.persist(entity);
        personNameDao.closeCurrentSessionwithTransaction();
    }

    public void update(PersonName entity) {
        personNameDao.openCurrentSessionwithTransaction();
        personNameDao.update(entity);
        personNameDao.closeCurrentSessionwithTransaction();
    }

    public PersonName findById(String id) {
        personNameDao.openCurrentSession();
        PersonName personName = personNameDao.findById(id);
        personNameDao.closeCurrentSession();
        return personName;
    }

    public PersonName findByPersonId(String id) {
        personNameDao.openCurrentSession();
        PersonName personName = personNameDao.findByPersonId(id);
        personNameDao.closeCurrentSession();
        return personName;
    }

    public void delete(String id) {
        personNameDao.openCurrentSessionwithTransaction();
        PersonName personName = personNameDao.findById(id);
        personNameDao.delete(personName);
        personNameDao.closeCurrentSessionwithTransaction();
    }

    public List<PersonName> findAll() {
        personNameDao.openCurrentSession();
        List<PersonName> personNames = personNameDao.findAll();
        personNameDao.closeCurrentSession();
        return personNames;
    }

     public List<PersonName> findByAllGivenNameLike(String name) {
        personNameDao.openCurrentSession();
        List<PersonName> personNames = personNameDao.findByAllGivenNameLike(name);
        personNameDao.closeCurrentSession();
        return personNames;
    }
     
      public List<PersonName> findByAllFamilyNameLike(String surname) {
        personNameDao.openCurrentSession();
        List<PersonName> personNames = personNameDao.findByAllFamilyNameLike(surname);
        personNameDao.closeCurrentSession();
        return personNames;
    }
    
    public void deleteAll() {
        personNameDao.openCurrentSessionwithTransaction();
        personNameDao.deleteAll();
        personNameDao.closeCurrentSessionwithTransaction();
    }

    public PersonNameDao personNameDao() {
        return personNameDao;
    }
}

