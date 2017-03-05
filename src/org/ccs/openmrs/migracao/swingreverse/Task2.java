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
import org.ccs.openmrs.migracao.connection.hibernateConection;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientIdentifierImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientIdentifierService;
import org.celllife.idart.database.hibernate.Patient;

class Task2
        extends SwingWorker<String, Void> {

    private final Random rnd = new Random();

    Task2() {
    }

    @Override
    public String doInBackground() {

        System.err.println("AGUARDE UM MOMENTO ....");
        try {
            PatientIdentifierImportService patientIdentifierImportServiceIdart = new PatientIdentifierImportService();

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
                            dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase1.get(0).getIdentifier(), patientIdentifier);
                        } else {
                            List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrsFase2 = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdart, name, surname);

                            if (!patientIdentifierOpenmrsFase2.isEmpty()) {
                                dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase2.get(0).getIdentifier(), patientIdentifier);
                            } else {
                                if (nidIdart.contains("/")) {
                                    nseq = nidIdart.substring(0, nidIdart.indexOf('/'));
                                    ano = nidIdart.substring(nidIdart.indexOf('/') + 1, nidIdart.length());

                                    if (ano.length() >= 3) {
                                        ano = ano.substring(0, 2);
                                    }
                                    nidIdartNovo = ano + "/%" + nseq;
                                } else {
                                    nidIdartNovo = nidIdart;
                                }
                                List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrs = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdartNovo, name, surname);
                                if (!patientIdentifierOpenmrs.isEmpty()) {
                                    dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrs.get(0).getIdentifier(), patientIdentifier);
                                } else {
                                    System.err.println(nidIdart + ", " + patientIdart.getFirstNames().trim() + " " + patientIdart.getLastname().trim() + "- Not Match on List");
                                    descont++;
                                }
                            }
                        }
                    }
                } catch (InterruptedException ie) {
                    return "Interrupted";
                }
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("" + lengthOfTask + " Total de Pacientes no IDART !!!!!!");
                System.out.println("" + (lengthOfTask - descont) + " Total de Pacientes Unidos do OpenMRS para o IDART com sucesso!!!!!!");
                System.out.println("" + descont + " Total de Pacientes Noao encontrados no OpenMRS!!!!!!");
                hibernateConection.getInstanceLocal().close();
                hibernateConection.getInstanceRemote().close();
                current = lengthOfTask*2;
            }

        } catch (Exception e) {
            System.err.println("ACONTECEU UM ERRO INESPERADO, Ligue o Servidor OpenMRS e Tente Novamente ou Contacte o Administrador \n" + e.getMessage());
        }
        return "Done";
    }
}
