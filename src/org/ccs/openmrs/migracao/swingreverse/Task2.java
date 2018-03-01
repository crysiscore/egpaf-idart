/*
 * Decompiled with CFR 0_114.
 * 
 * Could not load the following classes:
 *  org.hibernate.Session
 */

 /* *********** *********** ***********  Moficacoes  *************************  *********** *********** ******** 
* @Data : 14-03-2017
* @colaborador: Agnaldo
* @Changes: Linha 76 , Abri um block try-catch para manter o ciclo a executar quando ocorrer uma exception
* @Changes: Linha 171 : funcao que retorna o path do logFile
************* *********** *********** *********** ********************** *********** *********** ***********  */
package org.ccs.openmrs.migracao.swingreverse;

import java.io.File;
import java.io.IOException;
import static java.rmi.server.LogStream.log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.SwingWorker;
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidades.Person;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientIdentifierImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientIdentifierService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PersonService;
import static org.ccs.openmrs.migracao.swingreverse.Task1.logFileLocations;
import org.celllife.idart.database.hibernate.Patient;

class Task2
        extends SwingWorker<String, Void> {

    private final Random rnd = new Random();

    // Esta classe vai ler e escrever um logFile  com os detalhe das excecpiotns que podem ocorrer 
    // durante o processo de uniao de nids. O ficheiro deve ser criado na pasta de instalacao do idart que pode ser
    // C:\\idart ou C:\\Program Files\\idart ou C:\\Program Files (x86)\\idart.
    ReadWriteTextFile rwTextFile = new ReadWriteTextFile();

    //Lista dos possiveis Localizacao do logFile
    final static List<String> logFileLocations = new ArrayList<>();
    final static String logFileName = "uniaoNidsLogFile.txt";
     String logFile ;
    
    Task2() {

        logFileLocations.add("C:\\iDART_V2017");
        logFileLocations.add("C:\\Program Files\\idart");
        logFileLocations.add("C:\\Program Files (x86)\\idart");      
        logFileLocations.add("C:\\idart");
        logFileLocations.add("C:\\Idart");
        logFileLocations.add("C:\\IDART");

    }

    @Override
    public String doInBackground() {

        System.err.println("AGUARDE UM MOMENTO ....");
        try {
            PatientIdentifierImportService patientIdentifierImportServiceIdart = new PatientIdentifierImportService();
            //Esvazia o log file
            logFile = getLogFileLocation();
           // rwTextFile.writeSmallTextFile(new ArrayList<String>(), logFile);
            
            List<org.celllife.idart.database.hibernate.PatientIdentifier> patientIdentifiersIdart = patientIdentifierImportServiceIdart.findAll();

            int current = 0;
            int descont = 0;
            int lengthOfTask = patientIdentifiersIdart.size();

            while (current <= lengthOfTask && !this.isCancelled()) {
                try {
                    Thread.sleep(this.rnd.nextInt(50) + 1);
                    if (lengthOfTask == 0) {
                        System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                        System.err.println("#### Sem Pacientes Listados para a Uniao ####");
                        return "Done";
                    }

                    for (org.celllife.idart.database.hibernate.PatientIdentifier patientIdentifier : patientIdentifiersIdart) {
                        // Abrimos um block try-catch para manter o ciclo a executar quando ocorrer uma exception
                        try {
                            ++current;
                            this.setProgress(100 * current / lengthOfTask);
                            String nseq = null;
                            String ano = null;
                            String nidIdartNovo = null;
                            PatientIdentifierService identifierServiceOpenmrs = new PatientIdentifierService();
                            PatientImportService importService = new PatientImportService();
                            DadosPaciente dadosPaciente = new DadosPaciente();
                            String nidIdart = patientIdentifier.getValue().trim();
                            Patient patientIdart = importService.findById(patientIdentifier.getPatient().getId());
                            PersonService personService = new PersonService();
                            Person person = null;

                            String name = "";
                            String surname = "";

                            if (patientIdart.getFirstNames().trim().length() > 5) {
                                name = patientIdart.getFirstNames().substring(0, 4).replace("'", "");
                            } else {
                                name = patientIdart.getFirstNames().replace("'", "");
                            }

                            if (patientIdart.getLastname().trim().length() > 6) {
                                surname = patientIdart.getLastname().substring(0, 5).replace("'", "");
                            } else {
                                surname = patientIdart.getLastname().replace("'", "");
                            }

                            List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrsFase1 = identifierServiceOpenmrs.findByNidAndNameAndSurname(nidIdart, name, surname);

                            if (!patientIdentifierOpenmrsFase1.isEmpty()) {
                                // Verifica se o nid existe no OpenmRS
                                person = personService.findById(patientIdentifierOpenmrsFase1.get(0).getPatientId().getPerson().getPersonId().toString());
                                dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase1.get(0).getIdentifier(),person.getUuid(), patientIdentifier);
                            } else {
                                List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrsFase2 = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdart, name, surname);

                                if (!patientIdentifierOpenmrsFase2.isEmpty()) {
                                    person = personService.findById(patientIdentifierOpenmrsFase2.get(0).getPatientId().getPerson().getPersonId().toString());
                                    dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase2.get(0).getIdentifier(),person.getUuid(), patientIdentifier);
                                } else {                                  
                                    nidIdart = nidIdart.replaceFirst("/", ":");                                    
                                    if (nidIdart.contains("/")) {
                                        ano = nidIdart.substring(nidIdart.indexOf(':') + 1, nidIdart.indexOf('/'));
                                        nseq = nidIdart.substring(nidIdart.indexOf('/') + 1, nidIdart.length());
                                       
                                    }else{
                                         if(nidIdart.contains(":")){
                                             nseq = nidIdart.substring(0, nidIdart.indexOf(':'));
                                        ano = nidIdart.substring(nidIdart.indexOf(':') + 1, nidIdart.length());
                                        }
                                    }                                   
                                    if( ano != null && nseq != null){
                                     
                                        if (ano.length() >= 3) 
                                            ano = ano.substring(0, 2);
                                        
                                        nidIdartNovo = ano + "/%" + nseq;
                                    }else
                                        nidIdartNovo = nidIdart;
                                    }
                                }                                  
                                    List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrs = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdartNovo, name, surname);
                                   
                                      nidIdart = nidIdart.replaceFirst(":", "/");  
                                      
                                    if (!patientIdentifierOpenmrs.isEmpty()) {
                                        person = personService.findById(patientIdentifierOpenmrs.get(0).getPatientId().getPerson().getPersonId().toString());
                                        dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrs.get(0).getIdentifier(), person.getUuid(),patientIdentifier);
                                    } else {
                                         patientIdentifierOpenmrs = identifierServiceOpenmrs.findAllByNid(nidIdart);
                                        if (!patientIdentifierOpenmrs.isEmpty()) {
                                        person = personService.findById(patientIdentifierOpenmrs.get(0).getPatientId().getPerson().getPersonId().toString());
                                        dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrs.get(0).getIdentifier(), person.getUuid(),patientIdentifier);
                                    } else {
                                        System.err.println(nidIdart + ", " + patientIdart.getFirstNames().trim() + " " + patientIdart.getLastname().trim() + "- Not Match on List");
                                        descont++;
                                        }
                                    }               
                        } catch (Exception e) {
                            
                            //Podem ocorrer diferentes tipos de exceptions, coomo nao podemos prever todas vamos escreve-las
                            //num logfile e continuar com a execucao ciclo   
                            List<String> listNidsProblematicos = new ArrayList<>();
                            listNidsProblematicos.add("---------------------------------------------------------------------- ----------------------------------------------------------------");
                            listNidsProblematicos.add("NID: "+ patientIdentifier.getValue().trim() );
                            listNidsProblematicos.add("NOME: "+ patientIdentifier.getPatient().getFirstNames());
                            listNidsProblematicos.add("APELIDO: "+ patientIdentifier.getPatient().getLastname());
                            listNidsProblematicos.add("ERRO: "+ e.getMessage() );
                            listNidsProblematicos.add("CAUSA: "+ e.getCause().toString() );
                            rwTextFile.writeSmallTextFile(listNidsProblematicos, logFile);
                     
                        }

            } 
                } catch (InterruptedException ie) {
                    return "Interrupted";
                }
                System.err.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.err.println("" + lengthOfTask + " Total de Pacientes no IDART !!!!!!");
                System.err.println("" + (lengthOfTask - descont) + " Total de Pacientes Unidos do OpenMRS para o IDART com sucesso!!!!!!");
                System.err.println("" + descont + " Total de Pacientes Noao encontrados no OpenMRS!!!!!!");
                hibernateConection.getInstanceLocal().close();
                hibernateConection.getInstanceRemote().close();
                current = lengthOfTask * 2;
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ACONTECEU UM ERRO INESPERADO, Ligue o Servidor OpenMRS e Tente Novamente ou Contacte o Administrador \n" + e.getMessage());

        }
        return "Done";
    }

    public String getLogFileLocation() {

        String fileLocation = "";

        for (int i = 0; i < logFileLocations.size(); i++) {

            File dir = new File(logFileLocations.get(i));

            //Se o Ficheiro nao e encontrado em nenhuma das locations criar o ficheiro
            // No directorio que existir
            if (dir.exists()) {
                File logFile = new File(logFileLocations.get(i) + "\\" + logFileName);
                if (logFile.exists()) {
                    try {
                        logFile.delete();
                        logFile.createNewFile();
                        fileLocation = logFile.getPath();
                        break;
                    } catch (IOException e) {
                           System.out.println("cannot create log file" +e.getMessage());
                    }
                } //create new file
                else {
                    try {

                        logFile.createNewFile();
                        fileLocation = logFile.getPath();
                        System.out.println(fileLocation + ":  Criado");
                        break;

                    } catch (IOException e) {
                        System.out.println("cannot create log file" +e.getMessage());
                    }

                }

            }

        }

        return fileLocation;

    }
}
