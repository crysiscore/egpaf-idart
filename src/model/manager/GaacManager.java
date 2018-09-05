/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager;

/**
 *
 * @author agnaldo
 */

import java.util.ArrayList;
import java.util.List;
import model.nonPersistent.PatientIdAndName;
import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.Gaac;
import org.celllife.idart.database.hibernate.GaacMember;
import org.celllife.idart.database.hibernate.Patient;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 */
public class GaacManager {

    private static Logger log = Logger.getLogger(GaacManager.class);

    public static List<Gaac> getAllGaacs(Session sess) {
        @SuppressWarnings("unchecked")
        List<Gaac> gaacs = sess.createQuery("from gaac order by datecreated desc ")
                .list();
        return gaacs;
    }

    /**
     * Returns all getGaacMembers
     *
     * @param sess Session
     * @param gaac
     * @return List<Patient> &#064;throws HibernateException
     *
     */
    public static List<Patient> getAllGaacMembers(Session sess, Gaac gaac)
            throws HibernateException {
        @SuppressWarnings("unchecked")
        List<GaacMember> result = sess.createQuery(
                "from gaacmember as p where p.gaacid = :gid ")
                .setInteger("gid", gaac.getId()).list();
        List<Patient> patients = null;

        if (!result.isEmpty()) {
            for (int i = 0; i <= result.size(); i++) {
                patients.add(result.get(i).getPatient());
            }

        }

        return patients;
    }

    /**
     * Method getGaac.
     *
     * @param session Session
     * @param c String
     * @return Gaac
     * @throws HibernateException
     */
    public static Gaac getGaac(Session session, String c)
            throws HibernateException {
        @SuppressWarnings("unchecked")
        Gaac gac = null;
        gac = (Gaac) session
                .createQuery(
                        "select gaac from gaac as g where g.name = :gaacname")
                .setString("gaacname", c)
                .setMaxResults(1)
                .uniqueResult();

        return gac;
    }

    /**
     * Method getGaac.
     *
     * @param session Session
     * @param c int
     * @return Gaac
     * @throws HibernateException
     */
    public static Gaac getGaac(Session session, int c)
            throws HibernateException {
        @SuppressWarnings("unchecked")
        Gaac gac = null;
        gac = (Gaac) session
                .createQuery(
                        "select gaac from gaac as g where g.id = :gaacID")
                .setInteger("gaacID", c)
                .setMaxResults(1)
                .uniqueResult();

        return gac;
    }

    /**
     * Method getAllPatientsWithScripts.
     *
     * @param sess Session
     * @param clinicId int
     * @return List<PatientIdAndName>
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public static List<PatientIdAndName> getAllPatientsWithScripts(
            Session sess, int clinicId) throws HibernateException {
        List<PatientIdAndName> returnList = new ArrayList<PatientIdAndName>();

        List<Object[]> result = sess
                .createQuery(
                        "select pre.id, pre.patient.patientId, pre.patient.firstNames, pre.patient.lastname "
                        + "from Prescription pre"
                        + " where pre.patient.accountStatus=true"
                        + " and pre.current = 'T' and "
                        + "pre.patient.clinic.id =:clinicId")
                .setInteger("clinicId", clinicId).list();

        if (result != null) {
            for (Object[] obj : result) {

                returnList.add(new PatientIdAndName((Integer) obj[0], (String) obj[1],
                        (String) obj[3] + ", " + (String) obj[2]));
            }
        }
        return returnList;
    }

    /**
     * Saves a Gaac.
     *
     * @param sess Session
     * @param g Gaac
     * @throws HibernateException
     */
    public static void saveGaac(Session sess, Gaac g)
            throws HibernateException {

        sess.save(g);

    }
    
    /**
     * Add a patient to Gaac.
     *
     * @param sess Session
     * @param gm GaacMember
     * @throws HibernateException
     */
    
         @SuppressWarnings("unchecked")
    public static void addMemberToGacc(Session sess,  GaacMember gm) 
            throws HibernateException {
        sess.save(gm);
    }
}
