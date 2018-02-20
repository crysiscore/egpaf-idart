/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.PatientIdentifier;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PatientIdentifierDao;

public class PatientIdentifierService {
    private static PatientIdentifierDao patientIdentifierDao;

    public PatientIdentifierService() {
        patientIdentifierDao = new PatientIdentifierDao();
    }

    public void persist(PatientIdentifier entity) {
        patientIdentifierDao.openCurrentSessionwithTransaction();
        patientIdentifierDao.persist(entity);
        patientIdentifierDao.closeCurrentSessionwithTransaction();
    }

    public void update(PatientIdentifier entity) {
        patientIdentifierDao.openCurrentSessionwithTransaction();
        patientIdentifierDao.update(entity);
        patientIdentifierDao.closeCurrentSessionwithTransaction();
    }

    public PatientIdentifier findById(String id) {
        patientIdentifierDao.openCurrentSession();
        PatientIdentifier patientIdentifier = patientIdentifierDao.findById(id);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifier;
    }

    public PatientIdentifier findByPatientId(String id) {
        patientIdentifierDao.openCurrentSession();
        PatientIdentifier patientIdentifier = patientIdentifierDao.findByPatientId(id);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifier;
    }

    public void delete(String id) {
        patientIdentifierDao.openCurrentSessionwithTransaction();
        PatientIdentifier patientIdentifier = patientIdentifierDao.findById(id);
        patientIdentifierDao.delete(patientIdentifier);
        patientIdentifierDao.closeCurrentSessionwithTransaction();
    }

    public List<PatientIdentifier> findAll() {
        patientIdentifierDao.openCurrentSession();
        List<PatientIdentifier> patientIdentifiers = patientIdentifierDao.findAll();
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifiers;
    }

    public List<PatientIdentifier> findByAllIdentifierLike(String identifier) {
        patientIdentifierDao.openCurrentSession();
        List<PatientIdentifier> patientIdentifiers = patientIdentifierDao.findByAllIdentifierLike(identifier);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifiers;
    }

      public List<PatientIdentifier> findByNidAndNameAndSurname(String identifier,String name,String surname) {
        patientIdentifierDao.openCurrentSession();
          List<PatientIdentifier> patientIdentifier = patientIdentifierDao.findByNidAndNameAndSurname(identifier, name, surname);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifier;
    }
    
       public List<PatientIdentifier> findByPatientUuid(String uuid) {
        patientIdentifierDao.openCurrentSession();
          List<PatientIdentifier> patientIdentifier = patientIdentifierDao.findByPatientUuid(uuid);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifier;
    }
      
    public List<PatientIdentifier> findAllByNidLikeAndNameLikeAndSurnameLike(String identifier,String name,String surname) {
        patientIdentifierDao.openCurrentSession();
        List<PatientIdentifier> patientIdentifier = patientIdentifierDao.findAllByNidLikeAndNameLikeAndSurnameLike(identifier, name, surname);
        patientIdentifierDao.closeCurrentSession();
        return patientIdentifier;
    }
      
    public void deleteAll() {
        patientIdentifierDao.openCurrentSessionwithTransaction();
        patientIdentifierDao.deleteAll();
        patientIdentifierDao.closeCurrentSessionwithTransaction();
    }

    public PatientIdentifierDao patientIdentifierDao() {
        return patientIdentifierDao;
    }
}

