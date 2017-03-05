/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ccs.openmrs.migracao.swingreverse;

import java.util.List;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientIdentifierImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.importPatient.PatientImportService;
import org.ccs.openmrs.migracao.entidadesHibernate.servicos.PatientIdentifierService;
import org.celllife.idart.database.hibernate.Patient;
import org.celllife.idart.database.hibernate.PatientIdentifier;

/**
 *
 * @author ColacoVM
 */
public class OptmizazNids {
    
     @SuppressWarnings("empty-statement")
     public static void main(String[] args) {
        try {
            
            PatientIdentifierImportService patientIdentifierImportServiceIdart = new PatientIdentifierImportService();
          
            List<PatientIdentifier> patientIdentifiersIdart = patientIdentifierImportServiceIdart.findAll();
            
            int current = 0;
            int lengthOfTask = patientIdentifiersIdart.size();
          
            if (lengthOfTask == 0) {
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("#### Sem Pacientes Listados para a Migracao ####");
                System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println("     NID IDART   +     NID OPENMRS    ");
            }
            
            for (PatientIdentifier patientIdentifier : patientIdentifiersIdart) {
                ++current;
                String nseq = null;
                String ano = null;
                String nidIdartNovo= null;
                PatientIdentifierService identifierServiceOpenmrs = new PatientIdentifierService();
                PatientImportService importService = new PatientImportService();
                DadosPaciente dadosPaciente = new DadosPaciente();
                String nidIdart = patientIdentifier.getValue().trim();

                Patient patientIdart = importService.findById(patientIdentifier.getPatient().getId());
                
                String name = "";
                String surname = "";
                
                if(patientIdart.getFirstNames().trim().length() > 4)
                    name = patientIdart.getFirstNames().substring(0, 3).replace("'", "");
                else
                    name = patientIdart.getFirstNames().replace("'", "");
                
                if(patientIdart.getLastname().trim().length() > 4)
                    surname =  patientIdart.getLastname().substring(0, 3).replace("'", "");
                else
                    surname =  patientIdart.getLastname().replace("'", ""); 
                
                List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrsFase1 = identifierServiceOpenmrs.findByNidAndNameAndSurname(nidIdart, name, surname);
                
                if(!patientIdentifierOpenmrsFase1.isEmpty()){
                    // Verifica se o nid existe no OpenmRS
                    dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase1.get(0).getIdentifier(),patientIdentifier);
                  //     System.out.println(nidIdart+"       +        "+patientIdentifierOpenmrsFase1.getIdentifier()+" - Matched Nids");
                }else{
                     List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrsFase2 = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdart, name, surname);
                                   
                     if(!patientIdentifierOpenmrsFase2.isEmpty()){
                         dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrsFase2.get(0).getIdentifier(),patientIdentifier); 
                   //      System.out.println(nidIdart+"       +        "+patientIdentifierOpenmrsFase2.get(0).getIdentifier()+" - Matched Second Fase List Nids");
                     }else{
                    // verifica se encontra lista de pacientes do mesmo ano e numero de Sequencia
                    if(nidIdart.contains("/")){
                        nseq= nidIdart.substring(0, nidIdart.indexOf('/'));
                        ano = nidIdart.substring(nidIdart.indexOf('/')+1, nidIdart.length());
                        
                        if(ano.length() >= 3)
                        ano = ano.substring(0,2);
                            
                        nidIdartNovo = ano+"/%"+nseq;
                    }else{
                          nidIdartNovo =  nidIdart;
                    }
                        List<org.ccs.openmrs.migracao.entidades.PatientIdentifier> patientIdentifierOpenmrs = identifierServiceOpenmrs.findAllByNidLikeAndNameLikeAndSurnameLike(nidIdartNovo, name, surname);
                         if(!patientIdentifierOpenmrs.isEmpty()){
                              dadosPaciente.actualizaNidIdart(nidIdart, patientIdentifierOpenmrs.get(0).getIdentifier(),patientIdentifier); 
                         //   System.out.println(nidIdart+"       +        "+patientIdentifierOpenmrs.get(0).getIdentifier()+" - First Macth on List Third Fase");
                         //   Actualiza as tabelas do Idart que contenha o nidIdart patientIdentifierOpenmrs2.get(0);
                         }else{
                             System.out.println(nidIdart+", "+ patientIdart.getFirstNames().trim() +" "+patientIdart.getLastname().trim()+"- Not Match on List"); 
                         }
                    }
                }
            }
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("" + lengthOfTask + " Pacientes Optimizados do OpenMRS para o IDART com sucesso!!!!!!");
        }
        catch (Exception e) {
            System.err.println("ACONTECEU UM ERRO INESPERADO, Ligue o Servidor OpenMRS e Tente Novamente ou Contacte o Administrador \n" + e.getMessage());
        }
    }
     
     
    
}
