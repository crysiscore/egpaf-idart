/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDao.PrescriptionExportDao;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDao.RegimeTerapeuticoExportDao;
import org.celllife.idart.database.hibernate.Prescription;
import org.celllife.idart.database.hibernate.RegimeTerapeutico;

/**
 *
 * @author ColacoVM
 */
public class RegimeTerapeuticoExportService {
    private static RegimeTerapeuticoExportDao regimeTerapeuticoExportDao;

    public RegimeTerapeuticoExportService() {
        regimeTerapeuticoExportDao = new RegimeTerapeuticoExportDao();
    }

    public void persist(RegimeTerapeutico entity) {
        regimeTerapeuticoExportDao.openCurrentSessionwithTransaction();
        regimeTerapeuticoExportDao.persist(entity);
        regimeTerapeuticoExportDao.closeCurrentSessionwithTransaction();
    }

    public void update(RegimeTerapeutico entity) {
        regimeTerapeuticoExportDao.openCurrentSessionwithTransaction();
        regimeTerapeuticoExportDao.update(entity);
        regimeTerapeuticoExportDao.closeCurrentSessionwithTransaction();
    }

    public RegimeTerapeutico findById(String id) {
        regimeTerapeuticoExportDao.openCurrentSession();
        RegimeTerapeutico regimeTerapeutico = regimeTerapeuticoExportDao.findById(id);
        regimeTerapeuticoExportDao.closeCurrentSession();
        return regimeTerapeutico;
    }

    public RegimeTerapeutico findByRegimeId(String id) {
        regimeTerapeuticoExportDao.openCurrentSession();
        RegimeTerapeutico regimeTerapeutico = regimeTerapeuticoExportDao.findByRegimeId(id);
        regimeTerapeuticoExportDao.closeCurrentSession();
        return regimeTerapeutico;
    }

    public void delete(String id) {
        regimeTerapeuticoExportDao.openCurrentSessionwithTransaction();
        RegimeTerapeutico regimeTerapeutico = regimeTerapeuticoExportDao.findById(id);
        regimeTerapeuticoExportDao.delete(regimeTerapeutico);
        regimeTerapeuticoExportDao.closeCurrentSessionwithTransaction();
    }

    public List<RegimeTerapeutico> findAll() {
        regimeTerapeuticoExportDao.openCurrentSession();
        List<RegimeTerapeutico> regimeTerapeuticos = regimeTerapeuticoExportDao.findAll();
        regimeTerapeuticoExportDao.closeCurrentSession();
        return regimeTerapeuticos;
    }

    public void deleteAll() {
        regimeTerapeuticoExportDao.openCurrentSessionwithTransaction();
        regimeTerapeuticoExportDao.deleteAll();
        regimeTerapeuticoExportDao.closeCurrentSessionwithTransaction();
    }

    public RegimeTerapeuticoExportDao regimeTerapeuticoExportDao() {
        return regimeTerapeuticoExportDao;
    }
}

