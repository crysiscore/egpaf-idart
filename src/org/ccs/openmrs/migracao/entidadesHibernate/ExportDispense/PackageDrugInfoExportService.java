/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDao.PackageDrugInfoExportDao;
import org.celllife.idart.database.hibernate.tmp.PackageDrugInfo;

public class PackageDrugInfoExportService {
    private static PackageDrugInfoExportDao packageDrugInfoExportDao;

    public PackageDrugInfoExportService() {
        packageDrugInfoExportDao = new PackageDrugInfoExportDao();
    }

    public void persist(PackageDrugInfo entity) {
        packageDrugInfoExportDao.openCurrentSessionwithTransaction();
        packageDrugInfoExportDao.persist(entity);
        packageDrugInfoExportDao.closeCurrentSessionwithTransaction();
    }

    public void update(PackageDrugInfo entity) {
        packageDrugInfoExportDao.openCurrentSessionwithTransaction();
        packageDrugInfoExportDao.update(entity);
        packageDrugInfoExportDao.closeCurrentSessionwithTransaction();
    }

    public PackageDrugInfo findById(String id) {
        packageDrugInfoExportDao.openCurrentSession();
        PackageDrugInfo packageDrugInfo = packageDrugInfoExportDao.findById(id);
        packageDrugInfoExportDao.closeCurrentSession();
        return packageDrugInfo;
    }

    public PackageDrugInfo findByPatientId(String id) {
        packageDrugInfoExportDao.openCurrentSession();
        PackageDrugInfo packageDrugInfo = packageDrugInfoExportDao.findByPatientId(id);
        packageDrugInfoExportDao.closeCurrentSession();
        return packageDrugInfo;
    }

    public void delete(String id) {
        packageDrugInfoExportDao.openCurrentSessionwithTransaction();
        PackageDrugInfo packageDrugInfo = packageDrugInfoExportDao.findById(id);
        packageDrugInfoExportDao.delete(packageDrugInfo);
        packageDrugInfoExportDao.closeCurrentSessionwithTransaction();
    }

    public List<PackageDrugInfo> findAll() {
        packageDrugInfoExportDao.openCurrentSession();
        List<PackageDrugInfo> packageDrugInfos = packageDrugInfoExportDao.findAll();
        packageDrugInfoExportDao.closeCurrentSession();
        return packageDrugInfos;
    }
    

             public List<PackageDrugInfo> findAllbyPatientID(String identifier) {
        packageDrugInfoExportDao.openCurrentSession();
        List<PackageDrugInfo> packageDrugInfos = packageDrugInfoExportDao.findAllbyPatientID(identifier);
        packageDrugInfoExportDao.closeCurrentSession();
        return packageDrugInfos;
    }
            
    public void deleteAll() {
        packageDrugInfoExportDao.openCurrentSessionwithTransaction();
        packageDrugInfoExportDao.deleteAll();
        packageDrugInfoExportDao.closeCurrentSessionwithTransaction();
    }

    public PackageDrugInfoExportDao packageDrugInfoExportDao() {
        return packageDrugInfoExportDao;
    }
}

