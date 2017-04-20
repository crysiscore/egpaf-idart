/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.SwingWorker;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.Concept;
import org.ccs.openmrs.migracao.entidades.Encounter;
import org.ccs.openmrs.migracao.entidades.EncounterProvider;
import org.ccs.openmrs.migracao.entidades.EncounterRole;
import org.ccs.openmrs.migracao.entidades.EncounterType;
import org.ccs.openmrs.migracao.entidades.Form;
import org.ccs.openmrs.migracao.entidades.Location;
import org.ccs.openmrs.migracao.entidades.Patient;
import org.ccs.openmrs.migracao.entidades.Provider;
import org.ccs.openmrs.migracao.entidades.Users;
import org.ccs.openmrs.migracao.entidades.Visit;
import org.ccs.openmrs.migracao.entidades.VisitType;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense.PackageDrugInfoExportService;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense.PatientExportService;
import org.ccs.openmrs.migracao.entidadesHibernate.dao.PatientIdentifierDao;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ConceptService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterProviderService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterRoleService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterTypeService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.FormService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.LocationService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ObsService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientIdentifierService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ProviderService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.UsersService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.VisitService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.VisitTypeService;
import org.celllife.idart.database.hibernate.tmp.PackageDrugInfo;

class Task1
        extends SwingWorker<String, Void> {

    private final Random rnd = new Random();

    Task1() {
    }

    @Override
    public String doInBackground() {
        System.err.println("AGUARDE UM MOMENTO ....");
        try {
            ConceptService conceptService = new ConceptService();
            EncounterProviderService encounterProviderService = new EncounterProviderService();
            EncounterService encounterService = new EncounterService();
            EncounterTypeService encounterTypeService = new EncounterTypeService();
            FormService formService = new FormService();
            ObsService obsService = new ObsService();
            PatientIdentifierService patientIdentifierService = new PatientIdentifierService();
            PatientExportService patientExportService = new PatientExportService();
            UsersService usersService = new UsersService();
            VisitTypeService visitTypeService = new VisitTypeService();
            VisitService visitService = new VisitService();
            ProviderService providerService = new ProviderService();
            EncounterRoleService encounterRoleService = new EncounterRoleService();
            LocationService locationService = new LocationService();
            PackageDrugInfoExportService packageDrugInfoExportService = new PackageDrugInfoExportService();
            System.err.println("PROCESSANDO ....");
            List<PackageDrugInfo> packageDrugInfos = packageDrugInfoExportService.findAll();
            int current = 0;
            int contanonSend = 0;
            int lengthOfTask = packageDrugInfos.size();

            while (current <= lengthOfTask && !this.isCancelled()) {
                try {
                    Thread.sleep(this.rnd.nextInt(50) + 1);
                    if (lengthOfTask == 0) {
                        System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.err.println("#### Sem Dispensas Listadas para a Migracao ####");
                        return "Done";
                    }
                    Users users = usersService.findById("1");
                    Concept concept = conceptService.findById("311");
                    VisitType visitType = visitTypeService.findById("8");
                    EncounterType encounterType = encounterTypeService.findById("18");
                    Form form = formService.findById("130");
                    Provider provider = providerService.findById("1");
                    EncounterRole encounterRole = encounterRoleService.findById("1");
                    Location location = locationService.findById("212");
                    List<Concept> conceptsFarmacia = conceptService.findAll();

                    String prescricao = "";
                    Date dataPrescricao = null;
                    for (PackageDrugInfo packageDrugInfo : packageDrugInfos) {
                        ++current;
                      
                        this.setProgress(100 * current / lengthOfTask);

                        String name = "";
                        String surname = "";

                        if (packageDrugInfo.getPatientFirstName().trim().length() > 4) {
                            name = packageDrugInfo.getPatientFirstName().substring(0, 3).replace("'", "");
                        } else {
                            name = packageDrugInfo.getPatientFirstName().replace("'", "");
                        }

                        if (packageDrugInfo.getPatientLastName().trim().length() > 4) {
                            surname = packageDrugInfo.getPatientLastName().substring(0, 3).replace("'", "");
                        } else {
                            surname = packageDrugInfo.getPatientLastName().replace("'", "");
                        }

                        PatientIdentifierService identifierDao = new PatientIdentifierService();
                        List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrs = identifierDao.findByNidAndNameAndSurname(packageDrugInfo.getPatientId(), name, surname);

                        Patient patient = null;
                        if (!patientIdentifierOpenmrs.isEmpty()) {
                            patient = patientExportService.findById(patientIdentifierOpenmrs.get(0).getPatientId().getPatientId() + "");
                            // patient = (Patient)this.getCurrentSession().createQuery("from Patient p where p.patientId = " + patientIdentifier.getPatientId().getPatientId()).uniqueResult();
                            System.err.println("**********************************************************************************************************************************************************************************");
                            System.err.println(" Dispensa do Paciente " + packageDrugInfo.getPatientFirstName() + " " + packageDrugInfo.getPatientLastName() + " com o nid NID " + packageDrugInfo.getPatientId() + " Enviado para o OpenMRS.");
                            System.err.println("**********************************************************************************************************************************************************************************");
                        } else {
                            System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                            System.err.println("Paciente " + packageDrugInfo.getPatientFirstName() + " " + packageDrugInfo.getPatientLastName() + " com o nid NID " + packageDrugInfo.getPatientId() + " nao foi encontrado no OpenMRS. Verifique o NID no OpenMRS ou Contacte o Administrador");
                            System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        }

                        if (patient != null) {
                            if (prescricao.equalsIgnoreCase(packageDrugInfo.getPackageId()) && dataPrescricao == packageDrugInfo.getDispenseDate()) {
                                System.err.println(" INFO: Mais de 1 Pacote foi dispensado para esta Prescricao -> " + packageDrugInfo.getPackageId());
                            } else {
                                Visit visit = ExportData.InsereVisitas(patient, packageDrugInfo, visitType, location, concept, users, visitService);
                                Encounter encounter = ExportData.InsereEncounter(visit, packageDrugInfo, encounterType, users, location, form, patient, encounterService);
                                EncounterProvider encounterProvider = ExportData.InsereEncounterProvider(packageDrugInfo, encounter, users, encounterRole, provider, encounterProviderService);

                                for (Concept concept1 : conceptsFarmacia) {
                                    ExportData.InsereObs(patient, packageDrugInfo, location, concept1, users, encounter, obsService);
                                }
                                prescricao = packageDrugInfo.getPackageId();
                                dataPrescricao = packageDrugInfo.getDispenseDate();
                            }

                            PackageDrugInfoExportService rapidSave = new PackageDrugInfoExportService();
                            packageDrugInfo.setNotes("Exported");
                            rapidSave.update(packageDrugInfo);
                        } else {
                            contanonSend++;
                        }
                    }
                } catch (InterruptedException ie) {
                    return "Interrupted";
                }
                System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.err.println("" + lengthOfTask + " Dispensas enviadas para OpenMRS!!!!!!");
                if (contanonSend != 0) {
                    System.err.println("" + contanonSend + " Dispensas nao actualizadas no OpenMRS!!!!!!");
                }
                if ((lengthOfTask - contanonSend) != 0) {
                    System.err.println("" + (lengthOfTask - contanonSend) + " Dispensas Actualizadas no OpenMRS com Sucesso!!!!!!");
                }
                hibernateConection.getInstanceLocal().close();
                hibernateConection.getInstanceRemote().close();
                current = lengthOfTask * 2;
            }

        } catch (Exception e) {
            System.err.println("ACONTECEU UM ERRO INESPERADO, Ligue o Servidor OpenMRS e Tente Novamente ou Contacte o Administrador \n" + e.getMessage());
        }
        return "Done";
    }
}
