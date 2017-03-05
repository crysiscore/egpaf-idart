/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.PersonAddress;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PersonAddressDao;
import org.hibernate.Session;

public class PersonAddressService {
    private static PersonAddressDao personAddressDao;

    public PersonAddressService() {
        personAddressDao = new PersonAddressDao();
    }

    public void persist(PersonAddress entity) {
        personAddressDao.openCurrentSessionwithTransaction();
        personAddressDao.persist(entity);
        personAddressDao.closeCurrentSessionwithTransaction();
    }

    public void update(PersonAddress entity) {
        personAddressDao.openCurrentSessionwithTransaction();
        personAddressDao.update(entity);
        personAddressDao.closeCurrentSessionwithTransaction();
    }

    public PersonAddress findById(String id) {
        personAddressDao.openCurrentSession();
        PersonAddress personAddress = personAddressDao.findById(id);
        personAddressDao.closeCurrentSession();
        return personAddress;
    }

    public void delete(String id) {
        personAddressDao.openCurrentSessionwithTransaction();
        PersonAddress personAddress = personAddressDao.findById(id);
        personAddressDao.delete(personAddress);
        personAddressDao.closeCurrentSessionwithTransaction();
    }

    public List<PersonAddress> findAll() {
        personAddressDao.openCurrentSession();
        List<PersonAddress> personAddresss = personAddressDao.findAll();
        personAddressDao.closeCurrentSession();
        return personAddresss;
    }

    public PersonAddress findByPersonId(String id) {
        personAddressDao.openCurrentSession();
        PersonAddress personAddress = personAddressDao.findByPersonId(id);
        personAddressDao.closeCurrentSession();
        return personAddress;
    }

    public void deleteAll() {
        personAddressDao.openCurrentSessionwithTransaction();
        personAddressDao.deleteAll();
        personAddressDao.closeCurrentSessionwithTransaction();
    }

    public PersonAddressDao personAddressDao() {
        return personAddressDao;
    }
}

