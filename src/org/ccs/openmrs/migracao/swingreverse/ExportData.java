/*
 * Decompiled with CFR 0_114.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ccs.openmrs.migracao.entidades.Concept;
import org.ccs.openmrs.migracao.entidades.ConceptName;
import org.ccs.openmrs.migracao.entidades.Encounter;
import org.ccs.openmrs.migracao.entidades.EncounterProvider;
import org.ccs.openmrs.migracao.entidades.EncounterRole;
import org.ccs.openmrs.migracao.entidades.EncounterType;
import org.ccs.openmrs.migracao.entidades.Form;
import org.ccs.openmrs.migracao.entidades.Location;
import org.ccs.openmrs.migracao.entidades.Obs;
import org.ccs.openmrs.migracao.entidades.Patient;
import org.ccs.openmrs.migracao.entidades.Provider;
import org.ccs.openmrs.migracao.entidades.Users;
import org.ccs.openmrs.migracao.entidades.Visit;
import org.ccs.openmrs.migracao.entidades.VisitType;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense.PackageDrugInfoExportService;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense.PrescriptionExportService;
import org.ccs.openmrs.migracao.entidadesHibernate.ExportDispense.RegimeTerapeuticoExportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ConceptNameService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ConceptService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterProviderService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.EncounterService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ObsService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.VisitService;
import org.celllife.idart.database.hibernate.Prescription;
import org.celllife.idart.database.hibernate.RegimeTerapeutico;
import org.celllife.idart.database.hibernate.tmp.PackageDrugInfo;

public class ExportData {

    public static Visit InsereVisitas(Patient patient, PackageDrugInfo packageDrugInfo, VisitType visitType, Location location, Concept concept, Users users, VisitService visitService) {

        if (patient == null) {
            return null;
        }

        Visit tipoVerifica = visitService.findByPatientAndPickupDate(patient.getPatientId(), new java.sql.Date(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()).getTime()));
        Visit temp = tipoVerifica;
        String uuid = "";
        if (temp == null) {
            tipoVerifica = new Visit();
            DadosPaciente dadosPaciente = new DadosPaciente();
            tipoVerifica.setUuid(dadosPaciente.devolveUuid());
        }
        tipoVerifica.setPatientId(patient);
        tipoVerifica.setDateCreated(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()));
        tipoVerifica.setDateChanged(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()));
        tipoVerifica.setVoided(false);
        tipoVerifica.setDateVoided(null);
        tipoVerifica.setVoidReason(null);
        tipoVerifica.setVisitTypeId(visitType);
        tipoVerifica.setVoidedBy(users);
        tipoVerifica.setCreator(users);
        tipoVerifica.setChangedBy(users);
        tipoVerifica.setDateStarted(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()));
        tipoVerifica.setDateStopped(ExportData.devolveDataEndVisit(packageDrugInfo.getDispenseDate()));
        tipoVerifica.setLocationId(location);
        if (temp == null) {
            visitService.persist(tipoVerifica);
        } else {
            visitService.update(tipoVerifica);
        }
        return tipoVerifica;
    }

    public static Encounter InsereEncounter(Visit visit, PackageDrugInfo packageDrugInfo, EncounterType encounterType, Users users, Location location, Form form, Patient patient, EncounterService encounterService) {

        if (patient == null) {
            return null;
        }

        Encounter encounter = encounterService.findByVisitAndPickupDate(visit.getVisitId(), new java.sql.Date(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()).getTime()));
        Encounter temp = encounter;
        String uuid = "";
        if (temp == null) {
            encounter = new Encounter();
            DadosPaciente dadosPaciente = new DadosPaciente();
            encounter.setUuid(dadosPaciente.devolveUuid());
        }
        encounter.setEncounterDatetime(packageDrugInfo.getDispenseDate());
        encounter.setDateCreated(packageDrugInfo.getDispenseDate());
        encounter.setVoided(false);
        encounter.setDateVoided(null);
        encounter.setVoidReason(null);
        encounter.setDateChanged(packageDrugInfo.getDispenseDate());
        encounter.setPatientId(patient);
        encounter.setVoidedBy(users);
        encounter.setCreator(users);
        encounter.setVisitId(visit);
        encounter.setChangedBy(users);
        encounter.setLocationId(location);
        encounter.setFormId(form);
        encounter.setEncounterType(encounterType);
        if (temp == null) {
            encounterService.persist(encounter);
        } else {
            encounterService.update(encounter);
        }
        return encounter;
    }

    public static EncounterProvider InsereEncounterProvider(PackageDrugInfo packageDrugInfo, Encounter encounter, Users users, EncounterRole encounterRole, Provider provider, EncounterProviderService encounterProviderService) {
        if (encounter == null) {
            return null;
        }

        EncounterProvider encounterProvider = encounterProviderService.findByEncounterAndPickupDate(encounter.getEncounterId(), new java.sql.Date(ExportData.devolveDataPick(packageDrugInfo.getDispenseDate()).getTime()));
        EncounterProvider temp = encounterProvider;
        String uuid = "";
        if (temp == null) {
            encounterProvider = new EncounterProvider();
            DadosPaciente dadosPaciente = new DadosPaciente();
            encounterProvider.setUuid(dadosPaciente.devolveUuid());
        }

        encounterProvider.setCreator(users);
        encounterProvider.setDateCreated(packageDrugInfo.getDispenseDate());
        encounterProvider.setChangedBy(null);
        encounterProvider.setDateChanged(null);
        encounterProvider.setVoided(false);
        encounterProvider.setDateVoided(null);
        encounterProvider.setVoidedBy(null);
        encounterProvider.setVoidReason(null);
        encounterProvider.setEncounterRoleId(encounterRole);
        encounterProvider.setProviderId(provider);
        encounterProvider.setEncounterId(encounter);
        if (temp == null) {
            encounterProviderService.persist(encounterProvider);
        } else {
            encounterProviderService.update(encounterProvider);
        }
        return encounterProvider;
    }

    public static void InsereObs(Patient patient, PackageDrugInfo packageDrugInfo, Location location, Concept concept, Users users, Encounter encounter, ObsService obsService, PackageDrugInfoExportService packageDrugInfoExportService) {
        Obs obs;
        Obs temp = obs = obsService.findByEncounterAndConcept(encounter, concept);
        String esquema = "";
        String uuid = "";
        ConceptService conceptService = new ConceptService();
        ConceptNameService conceptNameService = new ConceptNameService();
        PrescriptionExportService prescriptionExportService = new PrescriptionExportService();
        RegimeTerapeuticoExportService regimeTerapeuticoExportService = new RegimeTerapeuticoExportService();
        PatientImportService importService = new PatientImportService();
        RegimeTerapeutico regimeTerapeutico;
        Prescription prescription = null;
        ConceptName conceptName1Linha = null;
        ConceptName conceptName2Linha = null;
        ConceptName conceptName3Linha = null;
        int somaDispensedQty = 0;

        org.celllife.idart.database.hibernate.Patient p = importService.findByPatientId(packageDrugInfo.getPatientId() + "");

        if (p != null) {
            // carregar a prescricao exacta da dispensa
            prescription = packageDrugInfo.getPackagedDrug().getParentPackage().getPrescription();

            //load the last prescription
            if (prescription == null) {
                prescription = prescriptionExportService.findByPrescricaoId(p.getId() + "", packageDrugInfo.getPatientId());
            }

            if (prescription.getRegimeTerapeutico() != null) {
                regimeTerapeutico = regimeTerapeuticoExportService.findByRegimeId("" + prescription.getRegimeTerapeutico().getRegimeid());
                if (regimeTerapeutico != null) {
                    esquema = regimeTerapeutico.getRegimeesquema();
                    esquema = esquema.replace("30", "");
                    esquema = esquema.replace("50", "");
                    esquema = esquema.replace("60", "");
                    esquema = esquema.replace("200", "");
                    esquema = esquema.replace("100/25", "");
                    esquema = esquema.replace("40/10", "");
                    esquema = esquema.replace("80/20", "");
                    esquema = esquema.replace("(Baby)", "");
                    esquema = esquema.replace("()", "");
                }
            }

            if (temp == null) {
                obs = new Obs();
                DadosPaciente dadosPaciente = new DadosPaciente();
                obs.setUuid(dadosPaciente.devolveUuid());
            }
            obs.setObsDatetime(packageDrugInfo.getDispenseDate());
            String linhaterapeutica = prescriptionExportService.findByLinhaId(prescription).getLinhanome();

            if (concept.getConceptId() == 5096) {
                obs.setValueDatetime(ExportData.devolveData(packageDrugInfo.getDateExpectedString()));
            }

            if (concept.getConceptId() == 1715) {
                somaDispensedQty = devolveQuantidadeAviada(packageDrugInfo.getPatientId(), packageDrugInfo.getDispenseDate(), packageDrugInfoExportService);
                if (somaDispensedQty == 0) {
                    somaDispensedQty = packageDrugInfo.getDispensedQty();
                }
                obs.setValueNumeric(Double.parseDouble("" + somaDispensedQty + ""));
            }

            if (concept.getConceptId() == 1711) {
                somaDispensedQty = devolveQuantidadeAviada(packageDrugInfo.getPatientId(), packageDrugInfo.getDispenseDate(), packageDrugInfoExportService);
                if (somaDispensedQty == 0) {
                    somaDispensedQty = packageDrugInfo.getDispensedQty();
                }
                if (somaDispensedQty / (packageDrugInfo.getWeeksSupply() / 4) <= 30) {
                    obs.setValueText("0-0-1");
                } else {
                    obs.setValueText("1-0-1");
                }
            }
            if (concept.getConceptId() == 1088) {
                conceptName3Linha = conceptNameService.findByName3Line(esquema.trim());
                conceptName2Linha = conceptNameService.findByName2Line(esquema.trim());
                conceptName1Linha = conceptNameService.findByName(esquema.trim());

                if (linhaterapeutica.contains("3")) {
                    if (conceptName3Linha != null) {
                        obs.setValueCoded(conceptName3Linha.getConceptId());
                    } else if (conceptName2Linha != null) {
                        obs.setValueCoded(conceptName2Linha.getConceptId());
                    } else if (conceptName1Linha != null) {
                        obs.setValueCoded(conceptName1Linha.getConceptId());
                    } else {
                        obs.setValueCoded(conceptService.findById("5424"));
                    }
                } else {
                    if (linhaterapeutica.contains("2")) {
                        if (conceptName2Linha != null) {
                            obs.setValueCoded(conceptName2Linha.getConceptId());
                        } else if (conceptName3Linha != null) {
                            obs.setValueCoded(conceptName3Linha.getConceptId());
                        } else if (conceptName1Linha != null) {
                            obs.setValueCoded(conceptName1Linha.getConceptId());
                        } else {
                            obs.setValueCoded(conceptService.findById("5424"));
                        }
                    } else {
                        if (conceptName1Linha != null) {
                            obs.setValueCoded(conceptName1Linha.getConceptId());
                        } else if (conceptName2Linha != null) {
                            obs.setValueCoded(conceptName2Linha.getConceptId());
                        } else if (conceptName3Linha != null) {
                            obs.setValueCoded(conceptName3Linha.getConceptId());
                        } else {
                            obs.setValueCoded(conceptService.findById("5424"));
                        }
                    }
                }
                
                if(conceptName1Linha == null && conceptName2Linha == null && conceptName3Linha == null)
                    obs.setValueCoded(conceptService.findById("5424"));
            }
            obs.setComments("Imported by IDART");
            obs.setDateCreated(encounter.getDateCreated());
            obs.setVoided(false);
            obs.setDateVoided(null);
            obs.setVoidReason(null);
            obs.setVoidedBy(users);
            obs.setEncounterId(encounter);
            obs.setCreator(users);
            obs.setConceptId(concept);
            obs.setLocationId(location);
            obs.setPersonId(patient.getPerson());
            if (temp == null) {
                obsService.persist(obs);
            } else {
                obsService.update(obs);
            }
        }
    }

    public static Date devolveData(String data) {
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(data);
        } catch (ParseException ex) {
            Logger.getLogger(ExportData.class.getName()).log(Level.SEVERE, null, ex);
        }
        return date;
    }

    public static int devolveQuantidadeAviada(String identifier, Date dataDispensa, PackageDrugInfoExportService packageDrugInfoExportService) {
        int somaDispensedQty = 0;

        List<PackageDrugInfo> packageDrugInfos = packageDrugInfoExportService.findAllbyNid(identifier, dataDispensa);

        if (!packageDrugInfos.isEmpty()) {
            for (PackageDrugInfo pinfo : packageDrugInfos) {
                somaDispensedQty = somaDispensedQty + pinfo.getDispensedQty();
            }
        }
        return somaDispensedQty;

    }

    public static Date devolveDataPick(Date data) {
        Date date = data;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        date = cal.getTime();
        System.out.println(date);
        return date;
    }

    public static Date devolveDataEndVisit(Date data) {
        Date date = data;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(11, 23);
        cal.set(12, 50);
        cal.set(13, 50);
        cal.set(14, 0);
        date = cal.getTime();
        return date;
    }
}
