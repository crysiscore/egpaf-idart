/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.importPatient;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.ImportDao.PatientIdentifierImportDao;
import org.celllife.idart.database.hibernate.PatientIdentifier;
import org.hibernate.Session;

public class PatientIdentifierImportService {
    private static PatientIdentifierImportDao patientIdentifierImportDao;

    public PatientIdentifierImportService() {
        patientIdentifierImportDao = new PatientIdentifierImportDao();
    }

    public void persist(PatientIdentifier entity) {
        patientIdentifierImportDao.openCurrentSessionwithTransaction();
        patientIdentifierImportDao.persist(entity);
        patientIdentifierImportDao.closeCurrentSessionwithTransaction();
    }

    public void update(PatientIdentifier entity) {
        patientIdentifierImportDao.openCurrentSessionwithTransaction();
        patientIdentifierImportDao.update(entity);
        patientIdentifierImportDao.closeCurrentSessionwithTransaction();
    }

    public PatientIdentifier findById(String id) {
        patientIdentifierImportDao.openCurrentSession();
        PatientIdentifier patientIdentifier = patientIdentifierImportDao.findById(id);
        patientIdentifierImportDao.closeCurrentSession();
        return patientIdentifier;
    }

    public PatientIdentifier findByPatientId(String id) {
        patientIdentifierImportDao.openCurrentSession();
        PatientIdentifier patientIdentifier = patientIdentifierImportDao.findByPatientId(id);
        patientIdentifierImportDao.closeCurrentSession();
        return patientIdentifier;
    }

    public PatientIdentifier findByIdentifier(String identifier) {
        patientIdentifierImportDao.openCurrentSession();
        PatientIdentifier patientIdentifier = patientIdentifierImportDao.findByIdentifier(identifier);
        patientIdentifierImportDao.closeCurrentSession();
        return patientIdentifier;
    }

    public void delete(String id) {
        patientIdentifierImportDao.openCurrentSessionwithTransaction();
        PatientIdentifier patientIdentifier = patientIdentifierImportDao.findById(id);
        patientIdentifierImportDao.delete(patientIdentifier);
        patientIdentifierImportDao.closeCurrentSessionwithTransaction();
    }

    public List<PatientIdentifier> findAll() {
        patientIdentifierImportDao.openCurrentSession();
        List<PatientIdentifier> patientsIdentifiers = patientIdentifierImportDao.findAll();
        patientIdentifierImportDao.closeCurrentSession();
        return patientsIdentifiers;
    }

    public void deleteAll() {
        patientIdentifierImportDao.openCurrentSessionwithTransaction();
        patientIdentifierImportDao.deleteAll();
        patientIdentifierImportDao.closeCurrentSessionwithTransaction();
    }

    public PatientIdentifierImportDao patientImportDao() {
        return patientIdentifierImportDao;
    }
}

