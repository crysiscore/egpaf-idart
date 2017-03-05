/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.importPatient;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.ImportDao.PatientAttributeImportDao;
import org.celllife.idart.database.hibernate.PatientAttribute;
import org.hibernate.Session;

public class PatientAttributeImportService {
    private static PatientAttributeImportDao patientAttributeImportDao;

    public PatientAttributeImportService() {
        patientAttributeImportDao = new PatientAttributeImportDao();
    }

    public void persist(PatientAttribute entity) {
        patientAttributeImportDao.openCurrentSessionwithTransaction();
        patientAttributeImportDao.persist(entity);
        patientAttributeImportDao.closeCurrentSessionwithTransaction();
    }

    public void update(PatientAttribute entity) {
        patientAttributeImportDao.openCurrentSessionwithTransaction();
        patientAttributeImportDao.update(entity);
        patientAttributeImportDao.closeCurrentSessionwithTransaction();
    }

    public PatientAttribute findById(String id) {
        patientAttributeImportDao.openCurrentSession();
        PatientAttribute patientAttribute = patientAttributeImportDao.findById(id);
        patientAttributeImportDao.closeCurrentSession();
        return patientAttribute;
    }

    public PatientAttribute findByPatientId(String id) {
        patientAttributeImportDao.openCurrentSession();
        PatientAttribute patientAttribute = patientAttributeImportDao.findByPatientId(id);
        patientAttributeImportDao.closeCurrentSession();
        return patientAttribute;
    }

    public void delete(String id) {
        patientAttributeImportDao.openCurrentSessionwithTransaction();
        PatientAttribute patientIdentifier = patientAttributeImportDao.findById(id);
        patientAttributeImportDao.delete(patientIdentifier);
        patientAttributeImportDao.closeCurrentSessionwithTransaction();
    }

    public List<PatientAttribute> findAll() {
        patientAttributeImportDao.openCurrentSession();
        List<PatientAttribute> patientAttributes = patientAttributeImportDao.findAll();
        patientAttributeImportDao.closeCurrentSession();
        return patientAttributes;
    }

    public void deleteAll() {
        patientAttributeImportDao.openCurrentSessionwithTransaction();
        patientAttributeImportDao.deleteAll();
        patientAttributeImportDao.closeCurrentSessionwithTransaction();
    }

    public PatientAttributeImportDao patientImportDao() {
        return patientAttributeImportDao;
    }
}

