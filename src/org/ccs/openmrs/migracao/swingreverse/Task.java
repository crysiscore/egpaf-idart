/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.util.List;
import java.util.Random;
import javax.swing.SwingWorker;
import model.manager.AdministrationManager;
import model.manager.PatientManager;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.PatientIdentifier;
import org.ccs.openmrs.migracao.entidades.PatientProgram;
import org.ccs.openmrs.migracao.entidades.Person;
import org.ccs.openmrs.migracao.entidades.PersonAddress;
import org.ccs.openmrs.migracao.entidades.PersonName;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientAttributeImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientIdentifierImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientProgramService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.ObsService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientIdentifierService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PersonAddressService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PersonNameService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PersonService;
import org.celllife.idart.database.hibernate.AttributeType;
import org.celllife.idart.database.hibernate.Clinic;
import org.celllife.idart.database.hibernate.IdentifierType;
import org.celllife.idart.database.hibernate.Patient;

class Task
        extends SwingWorker<String, Void> {

    private final Random rnd = new Random();

    Task() {
    }

    @Override
    public String doInBackground() {
        System.err.println("AGUARDE UM MOMENTO ....");
        try {
            PatientService patientService = new PatientService();
            PersonService personService = new PersonService();
            PersonNameService personNameService = new PersonNameService();
            PersonAddressService personAddressService = new PersonAddressService();
            PatientIdentifierService patientIdentifierService = new PatientIdentifierService();
            ObsService obsService = new ObsService();
            PatientProgramService patientProgramService = new PatientProgramService();
            PatientImportService patientImportService = new PatientImportService();
            PatientIdentifierImportService patientIdentifierImportService = new PatientIdentifierImportService();
            PatientAttributeImportService patientAttributeImportService = new PatientAttributeImportService();

            // Alterado por levar tempo no carregamento das consultas        
            //Carrega todos pacientes novos no Programa TARV - Criar na base de dados OpenMRS a coluna idart natabela patient-program
            List<PatientProgram> patientProgramlist = patientProgramService.findAll();

            Clinic clinic = AdministrationManager.getMainClinic(patientImportService.patientImportDao().openCurrentSessionwithTransaction());
            IdentifierType identifierType = AdministrationManager.getNationalIdentifierType(patientImportService.patientImportDao().openCurrentSessionwithTransaction());
            AttributeType attributeType = PatientManager.getAttributeTypeObject(patientImportService.patientImportDao().openCurrentSessionwithTransaction(), "ARV Start Date");

            int current = 0;
            int lengthOfTask = patientProgramlist.size();
            System.err.println("PROCESSANDO ....");
            while (current <= lengthOfTask && !this.isCancelled()) {
                try {
                    Thread.sleep(this.rnd.nextInt(50) + 1);
                    if (lengthOfTask == 0) {
                        System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.err.println("#### Sem Pacientes Listados para a Migracao ####");
                        return "Done";
                    }
                    for (PatientProgram patientProgram : patientProgramlist) {
                        ++current;
                        String dataTarv = null;
                        // if (patientImportService.findByPatientId(patientProgram.getPatientId().getPatientId().toString()) != null && (patientImportService.findByPatientId(patientProgram.getPatientId().getPatientId().toString()) == null))
                        //     continue;
                        this.setProgress(100 * current / lengthOfTask);
                        PersonName personName = personNameService.findByPersonId(patientProgram.getPatientId().getPatientId().toString());
                        PersonAddress personAddress = personAddressService.findByPersonId(patientProgram.getPatientId().getPatientId().toString());
                        PatientIdentifier patientIdentifier = patientIdentifierService.findByPatientId(patientProgram.getPatientId().getPatientId().toString());
                        Person person = personService.findById(patientProgram.getPatientId().getPatientId().toString());

                        Patient patient = DadosPaciente.InserePaciente(patientIdentifier.getIdentifier(), person, personName, personAddress, clinic, patientImportService, patientProgram.getPatientId().getPatientId().toString());

                        DadosPaciente.InserePatientIdentifier(patient, identifierType, patientIdentifier.getIdentifier(), patientIdentifierImportService);

                        DadosPaciente.InserePatientAttribute(patient, patientProgram.getDateEnrolled().toGMTString(), attributeType, patientAttributeImportService);

                        PatientProgramService patientProgramServiceActualiza = new PatientProgramService();
                        patientProgram.setIdart("Imported");
                        patientProgramServiceActualiza.update(patientProgram);
                        //   ObsService obsServiceActualizacao = new ObsService();
                        //   obj.setComments("Imported");
                        //   obsServiceActualizacao.update(obj);
                        System.err.println(personName.getGivenName() + " " + personName.getFamilyName() + " Paciente com o NID " + patientIdentifier.getIdentifier() + " Inserido com Sucesso");
                    }
                } catch (InterruptedException ie) {
                    return "Interrupted";
                }
                System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.err.println("" + lengthOfTask + " Pacientes Importados do OpenMRS para o IDART com sucesso!!!!!!");
                hibernateConection.getInstanceLocal().close();
                hibernateConection.getInstanceRemote().close();
                current = lengthOfTask * 2;
            }
        } catch (Exception e) {
            System.err.println("ACONTECEU UM ERRO INESPERADO, Ligue o Servidor OpenMRS e Tente Novamente ou Contacte o Administrador \n" + e);
        }
        return "Done";
    }
}
