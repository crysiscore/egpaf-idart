/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.Patient;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDao.PatientExportDao;

public class PatientExportService {
    private static PatientExportDao patientExportDao;

    public PatientExportService() {
        patientExportDao = new PatientExportDao();
    }

    public void persist(Patient entity) {
        patientExportDao.openCurrentSessionwithTransaction();
        patientExportDao.persist(entity);
        patientExportDao.closeCurrentSessionwithTransaction();
    }

    public void update(Patient entity) {
        patientExportDao.openCurrentSession();
        patientExportDao.update(entity);
        patientExportDao.closeCurrentSession();
    }

    public Patient findById(String id) {
        patientExportDao.openCurrentSession();
        Patient patient = patientExportDao.findById(id);
        patientExportDao.closeCurrentSession();
        return patient;
    }

    public Patient findByPatientId(String id) {
        patientExportDao.openCurrentSession();
        Patient patient = patientExportDao.findByPatientId(id);
        patientExportDao.closeCurrentSession();
        return patient;
    }

    public void delete(String id) {
        patientExportDao.openCurrentSessionwithTransaction();
        Patient patient = patientExportDao.findById(id);
        patientExportDao.delete(patient);
        patientExportDao.closeCurrentSessionwithTransaction();
    }

    public List<Patient> findAll() {
        patientExportDao.openCurrentSession();
        List<Patient> patients = patientExportDao.findAll();
        patientExportDao.closeCurrentSession();
        return patients;
    }

    public void deleteAll() {
        patientExportDao.openCurrentSessionwithTransaction();
        patientExportDao.deleteAll();
        patientExportDao.closeCurrentSessionwithTransaction();
    }

    public PatientExportDao patientExportDao() {
        return patientExportDao;
    }
}

