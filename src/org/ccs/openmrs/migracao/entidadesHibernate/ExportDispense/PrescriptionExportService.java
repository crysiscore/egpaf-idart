/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDao.PrescriptionExportDao;
import org.celllife.idart.database.hibernate.Prescription;

/**
 *
 * @author ColacoVM
 */
public class PrescriptionExportService {
    private static PrescriptionExportDao prescriptionExportDao;

    public PrescriptionExportService() {
        prescriptionExportDao = new PrescriptionExportDao();
    }

    public void persist(Prescription entity) {
        prescriptionExportDao.openCurrentSessionwithTransaction();
        prescriptionExportDao.persist(entity);
        prescriptionExportDao.closeCurrentSessionwithTransaction();
    }

    public void update(Prescription entity) {
        prescriptionExportDao.openCurrentSessionwithTransaction();
        prescriptionExportDao.update(entity);
        prescriptionExportDao.closeCurrentSessionwithTransaction();
    }

    public Prescription findById(String id) {
        prescriptionExportDao.openCurrentSession();
        Prescription prescription = prescriptionExportDao.findById(id);
        prescriptionExportDao.closeCurrentSession();
        return prescription;
    }

    public Prescription findByPrescricaoId(String id, String nid) {
        prescriptionExportDao.openCurrentSession();
        Prescription prescription = prescriptionExportDao.findByPrescricaoId(id,nid);
        prescriptionExportDao.closeCurrentSession();
        return prescription;
    }

    public void delete(String id) {
        prescriptionExportDao.openCurrentSessionwithTransaction();
        Prescription packageDrugInfo = prescriptionExportDao.findById(id);
        prescriptionExportDao.delete(packageDrugInfo);
        prescriptionExportDao.closeCurrentSessionwithTransaction();
    }

    public List<Prescription> findAll() {
        prescriptionExportDao.openCurrentSession();
        List<Prescription> prescriptions = prescriptionExportDao.findAll();
        prescriptionExportDao.closeCurrentSession();
        return prescriptions;
    }

    public void deleteAll() {
        prescriptionExportDao.openCurrentSessionwithTransaction();
        prescriptionExportDao.deleteAll();
        prescriptionExportDao.closeCurrentSessionwithTransaction();
    }

    public PrescriptionExportDao prescriptionExportDao() {
        return prescriptionExportDao;
    }
}

