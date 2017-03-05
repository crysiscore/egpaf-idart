/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.servicos;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.Patient;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PatientDao;
import org.hibernate.Session;

public class PatientService {
    private static PatientDao patientDao;

    public PatientService() {
        patientDao = new PatientDao();
    }

    public void persist(Patient entity) {
        patientDao.openCurrentSessionwithTransaction();
        patientDao.persist(entity);
        patientDao.closeCurrentSessionwithTransaction();
    }

    public void update(Patient entity) {
        patientDao.openCurrentSessionwithTransaction();
        patientDao.update(entity);
        patientDao.closeCurrentSessionwithTransaction();
    }

    public Patient findById(String id) {
        patientDao.openCurrentSession();
        Patient patient = patientDao.findById(id);
        patientDao.closeCurrentSession();
        return patient;
    }

    public void delete(String id) {
        patientDao.openCurrentSessionwithTransaction();
        Patient patient = patientDao.findById(id);
        patientDao.delete(patient);
        patientDao.closeCurrentSessionwithTransaction();
    }

    public List<Patient> findAll() {
        patientDao.openCurrentSession();
        List<Patient> patients = patientDao.findAll();
        patientDao.closeCurrentSession();
        return patients;
    }

    public void deleteAll() {
        patientDao.openCurrentSessionwithTransaction();
        patientDao.deleteAll();
        patientDao.closeCurrentSessionwithTransaction();
    }

    public PatientDao patientDao() {
        return patientDao;
    }
}

