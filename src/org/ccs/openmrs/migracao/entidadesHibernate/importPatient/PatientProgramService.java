/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.importPatient;

import java.util.List;
import org.ccs.openmrs.migracao.entidades.PatientProgram;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PatientProgramDao;

/**
 *
 * @author ColacoVM
 */
public class PatientProgramService {
    private static PatientProgramDao patientProgramDao;

    public PatientProgramService() {
        patientProgramDao = new PatientProgramDao();
    }

    public void persist(PatientProgram entity) {
        patientProgramDao.openCurrentSessionwithTransaction();
        patientProgramDao.persist(entity);
        patientProgramDao.closeCurrentSessionwithTransaction();
    }

    public void update(PatientProgram entity) {
        patientProgramDao.openCurrentSessionwithTransaction();
        patientProgramDao.update(entity);
        patientProgramDao.closeCurrentSessionwithTransaction();
    }

    public PatientProgram findById(String id) {
        patientProgramDao.openCurrentSession();
        PatientProgram patient = patientProgramDao.findById(id);
        patientProgramDao.closeCurrentSession();
        return patient;
    }

//    public PatientProgram findByPatientId(String id) {
//        patientProgramDao.openCurrentSession();
//        PatientProgram patient = patientProgramDao.findByPatientId(id);
//        patientProgramDao.closeCurrentSession();
//        return patient;
//    }

    public void delete(String id) {
        patientProgramDao.openCurrentSessionwithTransaction();
        PatientProgram patient = patientProgramDao.findById(id);
        patientProgramDao.delete(patient);
        patientProgramDao.closeCurrentSessionwithTransaction();
    }

    public List<PatientProgram> findAll() {
        patientProgramDao.openCurrentSession();
        List<PatientProgram> patients = patientProgramDao.findAll();
        patientProgramDao.closeCurrentSession();
        return patients;
    }

    public void deleteAll() {
        patientProgramDao.openCurrentSessionwithTransaction();
        patientProgramDao.deleteAll();
        patientProgramDao.closeCurrentSessionwithTransaction();
    }

    public PatientProgramDao patientProgramDao() {
        return patientProgramDao;
    }
}

