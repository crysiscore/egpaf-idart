package org.celllife.idart.database.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.celllife.idart.commonobjects.Confidente;
import org.celllife.idart.commonobjects.Contacto;
import org.celllife.idart.commonobjects.Endereco;
import org.celllife.idart.commonobjects.iDartProperties;
import org.celllife.idart.database.hibernate.PrescriptionToPatient;
import org.celllife.idart.gui.alert.RiscoRoptura;
import org.celllife.idart.gui.sync.dispense.SyncLinha;
import org.celllife.idart.gui.sync.patients.SyncLinhaPatients;



/**
 * Esta classe efectua conexao com a BD postgres e tem metodo para a manipulacao dos dados
 * @author EdiasJambaia
 *
 */

public class ConexaoJDBC {
	
	private static Logger log = Logger.getLogger(ConexaoJDBC.class);
Connection conn_db;  // Conexão com o servidor de banco de dados
Statement st;   // Declaração para executar os comandos

/**
 * Conexao a base de dado
 * @param usr
 * @param pwd
 * @throws SQLException
 * @throws ClassNotFoundException
 */
	
	public void conecta(String usr, String pwd) throws SQLException, ClassNotFoundException 
{

		DOMConfigurator.configure("log4j.xml");
		
		//String url = "jdbc:postgresql://192.168.0.105/pharm?charSet=LATIN1";
		String url = iDartProperties.hibernateConnectionUrl;
		               
		
	//System.out.println(" url "+iDartProperties.hibernateConnectionUrl);	
		
		log.info("Conectando ao banco de dados\nURL = " + url);



// Carregar o driver
Class.forName("org.postgresql.Driver");

// Conectar com o servidor de banco de dados

conn_db = DriverManager.getConnection(url, usr, pwd);

 

log.info("Conectado...Criando a declaração");


st = conn_db.createStatement();


}
	

	
	/**
	 * Retorna a conexao com a base de dados
	 * @param usr
	 * @param pwd
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	
	public Connection retornaConexao(String usr, String pwd) throws SQLException, ClassNotFoundException 
{



String url = iDartProperties.hibernateConnectionUrl;

// Carregar o driver
Class.forName("org.postgresql.Driver");

// Conectar com o servidor de banco de dados


conn_db = DriverManager.getConnection(url, usr, pwd);


//st = conn_db.createStatement();

return conn_db;

}
	
	
	
/**
 * Devolve a lista de PrescriptionToPatient, na verdade so devolve lista de tamanho 1
 * @param patientid
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public List<PrescriptionToPatient> listPtP(String patientid ) throws ClassNotFoundException, SQLException{
	
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	
	String query=""
			+ "SELECT "
			+ "p.id, "
			+ "p.current, "
			+ "p.duration, "
			+ "p.reasonforupdate, "
			+ "p.notes, "
			+ "pt.patientid, "
			+ "rt.regimeesquema, "
			+" date_part(\'YEAR\',now())-date_part(\'YEAR\',pt.dateofbirth) as idade,  "
			+" p.motivomudanca AS motivomudanca, "
			+" p.datainicionoutroservico as datainicionoutroservico, "
			+ "lt.linhanome "
			+" FROM "
			+ "  patient pt, "
			+ "regimeterapeutico rt,  "
			+ "linhat lt, "
			+ "prescription AS p "
			+ "WHERE ("
			+ "(p.current = \'T\'::bpchar) "
			+ "AND "
			+ "(pt.id = p.patient) "
			+ "AND "
			+ "(pt.patientid=\'"+patientid+"\') "
			+ "AND "
			+ "(rt.regimeid=p.regimeid) "
			+ "AND "
			+ "(lt.linhaid=p.linhaid)) ";
	
	
	//ResultSet rs = st.executeQuery("select id, current, duration, reasonforupdate, notes, patientid from PrescriptioToPatient where patientid=\'"+patientid+"\'");
	List <PrescriptionToPatient> ptp = new ArrayList();
	ResultSet rs=st.executeQuery(query);
	if (rs != null)
        {
           
            while (rs.next())
            {

            	ptp.add(new PrescriptionToPatient(rs.getInt("id"), rs.getString("current"), rs.getInt("duration"),
            			rs.getString("reasonforupdate"),rs.getString("notes"), rs.getString("patientid"), rs.getString("regimeesquema"), rs.getInt("idade"), rs.getString("motivomudanca"),rs.getDate("datainicionoutroservico"),rs.getString("linhanome")));



           
            }
            rs.close(); // é necessário fechar o resultado ao terminar
        }
        
        st.close();
        conn_db.close();
        return ptp;
	}

public void updatePatientDetailsOnDispense(String newPatientid, String newpatientFirstname, String newpatientLastname,String oldPatiendid , String oldpatientFirstname, String oldpatientLastname) throws ClassNotFoundException, SQLException{

 String updateQuery = "UPDATE public.packagedruginfotmp "
                              + "SET patientid='"+ newPatientid+"'"
                              + " ,patientfirstname='"+newpatientFirstname+"'"
                              + " ,patientlastname='" +newpatientLastname+"'"+" "
                              + " WHERE  "
                              + "patientid='"+oldPatiendid+"'"+" and patientfirstname='"+oldpatientFirstname+"'"
                              + " and patientlastname='" +oldpatientLastname+"'";
 
                conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
		int rs=0;
                rs= st.executeUpdate(updateQuery);
                st.close();
      conn_db.close();
}
/**
 * Converte uma data para o formato DD Mon YYYY
 * @param date
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public Date converteData(String date) throws ClassNotFoundException, SQLException{
	
	Date data=new Date();
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	
	String query="select to_date(\'"+date+"\', \'DD Mon YYYY\')";
	ResultSet rs=st.executeQuery(query);
	
	rs.next();
	data=rs.getDate("to_date");
	
	  st.close();
      conn_db.close();
      return data;
}



/**
 * devolve um vector de todos medicamentos com seus AMC, SALDO E QUANTIDADE DE REQUISICAO
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public Vector<RiscoRoptura> selectRiscoDeRopturaStock() throws ClassNotFoundException, SQLException{

	
		
	
	String query="SELECT drugname, consumo_max_ult_3meses, saldos "
			+ "FROM "
			+ "alimenta_risco_roptura";
	
	
    Vector<RiscoRoptura> riscos=new Vector<RiscoRoptura>();
 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
   
	ResultSet rs=st.executeQuery(query);
	if (rs != null)
        {
           
            while (rs.next())
            {

RiscoRoptura rr=new RiscoRoptura(rs.getString("drugname"), rs.getInt("consumo_max_ult_3meses"), rs.getInt("saldos"), rs.getInt("consumo_max_ult_3meses")*3 - rs.getInt("saldos"));

riscos.add(rr);
System.out.println(" \n");

            } 
            rs.close(); // é necessário fechar o resultado ao terminar
        }
	
	
      
        st.close();
        conn_db.close();
return riscos;

	
}


/**
 * Total de pacientes que levantaram ARVs num periodo
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesFarmacia(String startDate, String endDate) throws ClassNotFoundException, SQLException{

      String query=""
			+ "SELECT  "
			+ " distinct packagedruginfotmp.patientid "
			+ " FROM  "
			+ " packagedruginfotmp "
			+ "  WHERE "
			+ "  packagedruginfotmp.dispensedate::timestamp::date >=  "
			+ "\'"+startDate+"\'"
			+ "AND packagedruginfotmp.dispensedate::timestamp::date <= "
			+ " \'"+endDate+"\'"
					+ " AND "
					+ " dispensedate IS NOT NULL"
;
     
	int total=0;

	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

	total++;

	

	            } 
	            rs.close(); //
	        }
    
	
	return total;
}

/**
 * Total de pacientes que iniciaram o tratamento de ARV num periodo
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesTransito(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	

	

 


			String query=" SELECT  DISTINCT "
					
+ "	 dispensa_packege.patientid  "
	+ "	FROM  "

	+ "	( "
	+ "	  SELECT  "

	+ "		prescription.id, package.packageid  "

+ "		 FROM  "

	+ "	 prescription,  "
 	+ "	package,  "
 
 	
+ " ( "
+ " 	select patient.id "
 + " 	from patient,episode"

 	+ "  WHERE"
 	+ "  patient.id=episode.patient"
 	+ "  and (episode.startreason like '%Transito%' OR episode.startreason like \'%aternidade%\')"

 	+ "  )as naotransito"
 	 
 		+ "  	 WHERE  "

 	+ "  naotransito.id =prescription.patient"
 			+ " AND "

	+ "	 prescription.id = package.prescription  "
	 
	+ "	 )as prescription_package,  "

	+ "	 (  "
	+ "	 SELECT  "
	

	+ "	 packagedruginfotmp.patientid,  "
	+ "	 packagedruginfotmp.packageid, "
	+ "	 packagedruginfotmp.dispensedate "
	 
+ "	 "
	+ "	 FROM "
	+ "	 package, packagedruginfotmp  "

	 + "	 WHERE  "

+ "		 package.packageid=packagedruginfotmp.packageid  "
+ "		 AND  "
 
	+ "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	+ " \'"+endDate+"\'::timestamp::date  "
	 + "	) as dispensa_packege  ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			
			
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}
/**
 * PARA MMIA PERSONALIZADO
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public int totalPacientesInicioP(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	

	
 


	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\'  "
							
			+" AND  "

			+" prescription.reasonforupdate=\'Inicia\' AND prescription.ptv=\'F\' AND prescription.tb=\'F\' "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
			 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid, "
			+ " packagedruginfotmp.dispensedate "

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes na manutencao de ARV num periodo
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesManter(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
 
	String query=" SELECT  DISTINCT "
+" dispensa_packege.patientid "
+"	FROM "

+"	(SELECT "

+"	prescription.id, package.packageid "

+" FROM "

+" prescription, "
+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

+" prescription.id = package.prescription "

+" AND "
+"  prescription.ppe=\'F\'  "
			
+" AND  "

+" (prescription.reasonforupdate=\'Manter\' OR prescription.reasonforupdate=\'Transfer de\' OR prescription.reasonforupdate=\'Reiniciar\')  "

+" )as prescription_package, "

+" ( "
+" SELECT "
 

+" packagedruginfotmp.patientid, "
+" packagedruginfotmp.packageid,"
+ "packagedruginfotmp.dispensedate "

+" FROM "
+" package, packagedruginfotmp "

+" WHERE "

+" package.packageid=packagedruginfotmp.packageid "
+" AND "

+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
	+ " \'"+endDate+"\'::timestamp::date"
+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * PARA MMIA PERSONALIZADO
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public int totalPacientesManterP(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid"
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package, "

		
+" 		("
				+" 		select patient.id"
				+" 		from patient,episode"

		+" 		WHERE"
	+" 		patient.id=episode.patient"
				+" 		and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\'"

	+" 		)as naotransito"
		 
		+" 		 	 WHERE  "

			 	+" 	 naotransito.id =prescription.patient"
				 +" 		AND "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.ptv=\'F\' AND prescription.tb=\'F\'  AND (prescription.reasonforupdate=\'Manter\' OR prescription.reasonforupdate=\'Transfer de\')"
							
			 
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
			 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ " packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 *  Total de pacientes na manutencao de ARV num periodo
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesAlterar(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;

	String query=" SELECT  DISTINCT "
+" dispensa_packege.patientid "
+"	FROM "

+"	(SELECT "

+"	prescription.id, package.packageid "

+" FROM "

+" prescription, "
+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

+" prescription.id = package.prescription "

+" AND "
+"  prescription.ppe=\'F\'  "
			
+" AND  "

+" prescription.reasonforupdate=\'Alterar\'  "

+" )as prescription_package, "

+" ( "
+" SELECT "
 

+" packagedruginfotmp.patientid, "
+" packagedruginfotmp.packageid,"
+ " packagedruginfotmp.dispensedate "

+" FROM "
+" package, packagedruginfotmp "

+" WHERE "

+" package.packageid=packagedruginfotmp.packageid "
+" AND "

+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
	+ " \'"+endDate+"\'::timestamp::date"
+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			


	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * PARA MMIA PERSONALIZADO
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesAlterarP(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	

	 


	
	String query=" SELECT  DISTINCT "
+" dispensa_packege.patientid"
+"	FROM "

+"	(SELECT "

+"	prescription.id, package.packageid "

+" FROM "

+" prescription, "
+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

+" prescription.id = package.prescription "

+" AND "
+"  prescription.ppe=\'F\' AND  prescription.ptv=\'F\' AND prescription.tb=\'F\'  "
			
+" AND  "

+" prescription.reasonforupdate=\'Alterar\'  "

+" )as prescription_package, "

+" ( "
+" SELECT "
 

+" packagedruginfotmp.patientid, "
+" packagedruginfotmp.packageid,"
+ "packagedruginfotmp.dispensedate "

+" FROM "
+" package, packagedruginfotmp "

+" WHERE "

+" package.packageid=packagedruginfotmp.packageid "
+" AND "

+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
	+ " \'"+endDate+"\'::timestamp::date"
+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}


/**
 * Total de pacientes PPE
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesPPE(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	

 


	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

			+"	(SELECT "

			+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
			+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'T\' AND  prescription.ptv=\'F\' AND prescription.tb=\'F\'  "
						
			 

			+" )as prescription_package, "

			+" ( "
			+" SELECT "
			 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ "packagedruginfotmp.dispensedate "

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
				+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes PTV iNICIO
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesPTVInicio(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	 


	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.ptv=\'T\'  "
							
			+" AND  "

			+" prescription.reasonforupdate=\'Inicia\'  "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
		 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ " packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes PTV Manter
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesPTVManter(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
 


	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.ptv=\'T\'  "
							
			+" AND  "

			+" prescription.reasonforupdate=\'Manter\'  "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ " packagedruginfotmp.dispensedate "

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes TB Alterar
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesTbAlterar(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	

 

	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.tb=\'T\'  "
							
			+" AND  "

			+" prescription.reasonforupdate=\'Alterar\'  "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "



			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ " packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			


	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Devolve o regime anterior de uma prescricao
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public String carregaRegime(int idpaciente) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " regimeterapeutico.regimeesquema "
			+ "  FROM "
			+ "  regimeterapeutico , "
			+ "  prescription "
			+ "  WHERE "
			+ "  prescription.regimeid =regimeterapeutico.regimeid "
			+ "  AND "
			+ "  prescription.patient="+idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

String regime="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


regime=rs.getString("regimeesquema");

            } 
            rs.close(); //
        }
		
		return regime;
		
		
}


/**
 * Devolve ppe  duma prescricao
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public String carregaPpe(int idpaciente) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " ppe "
			+ "  FROM "
			+ "   "
			+ "  prescription "
			+ "  WHERE "
			+ "   "
			+ "  "
			+ "  prescription.patient="+idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

	 String ppe="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


ppe=rs.getString("ppe");

            } 
            rs.close(); //
        }
		
		return ppe;
		
		
}

/**
 * Devolve a linha anterior duma prescricao
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public String carregaLinha(int idpaciente) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " linhat.linhanome "
			+ "  FROM "
			+ "  linhat , "
			+ "  prescription "
			+ "  WHERE "
			+ "  prescription.linhaid =linhat.linhaid "
			+ "  AND "
			+ "  prescription.patient="+idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

String linha="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


linha=rs.getString("linhanome");

            } 
            rs.close(); //
        }
		
		return linha;
		
		
}


public int carregaDispensaTrimestral(int idpaciente) throws ClassNotFoundException, SQLException{
    
    String query=" "
			+ " SELECT "
			+ "  dispensatrimestral "
			+ "  FROM "
			+ "  prescription "
			+ "  WHERE "
			+ "  prescription.patient="+ idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
    conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
    // 0 = nao
    // 1 = sim
    int dispensaTrimestral=0;
    ResultSet rs=st.executeQuery(query);
    if (rs != null)
        {
            while (rs.next())
            {
                dispensaTrimestral=rs.getInt("dispensatrimestral");
            } 
            rs.close(); //
        }
		
		return dispensaTrimestral;

}

/**
 * Devolve tb  duma prescricao
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public String carregaTb(int idpaciente) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " tb "
			+ "  FROM "
			+ "   "
			+ "  prescription "
			+ "  WHERE "
			+ "   "
			+ "  "
			+ "  prescription.patient="+idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

	 String tb="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


tb=rs.getString("tb");

            } 
            rs.close(); //
        }
		
		return tb;
		
		
}


/**
 * Devolve se um ARV é pediátrico ou adulto
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public String carregaPediatric(int iddrug) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " pediatric "
			+ "  FROM "
			+ "   "
			+ "  drug "
			+ "  WHERE "
			+ "   "
			+ "  "
			+ "  drug.id="+iddrug;
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

	 String pediatric="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


            	pediatric=rs.getString("pediatric");

            } 
            rs.close(); //
        }
		
		return pediatric;
		
		
}

/**
 * Devolve ptv  duma prescricao
 * 
 * @param idpaciente
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public String carregaPtv(int idpaciente) throws ClassNotFoundException, SQLException

{
	
	String query=" "
			+ " SELECT "
			+ " ptv "
			+ "  FROM "
			+ "   "
			+ "  prescription "
			+ "  WHERE "
			+ "   "
			+ "  "
			+ "  prescription.patient="+idpaciente
			+ "  AND "
			+ "  prescription.current=\'T\'"
			+ "";
	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);

	 String ptv="";
		ResultSet rs=st.executeQuery(query);
		
		if (rs != null)
        {
           
            while (rs.next())
            {


ptv=rs.getString("ptv");

            } 
            rs.close(); //
        }
		
		return ptv;
		
		
}

/**
 * Total de Meses Dispensados
 * @param startDate
 * @param endDate
 * @return
 * @throws SQLException
 */

public int mesesDispensados(String startDate, String endDate) throws SQLException{
	
	int meses=0;
	double somaSemanas=0;
	
	String query=" SELECT "
			+ " weekssupply, packageid"
			+ " FROM packagedruginfotmp "
			+ ""
			+ " WHERE "
			+ "  packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date "
					+ "AND packagedruginfotmp.dispensedate::timestamp::date <="
					+ " \'"+endDate+"\'::timestamp::date GROUP BY packageid, weekssupply";
	
	ResultSet rs=st.executeQuery(query);
	
	if (rs != null)
    {
       
        while (rs.next())
        {


somaSemanas+=rs.getInt("weekssupply");

        } 
        rs.close(); //
        
        meses=(int) Math.round(somaSemanas/4);
    }
	
	
	return meses;
}

/**
 * Insere pacientes que nao estao ainda no SESP
 * @param nid
 * @param nomes
 * @param apelido
 * @param dataderegisto
 * @throws ClassNotFoundException
 * @throws SQLException
 */

public void inserPacienteIdart(String nid, String nomes, String apelido, Date dataderegisto) throws ClassNotFoundException, SQLException
{
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	 
	 st.executeUpdate(""
	 		+ " INSERT INTO registadosnoidart (nid, nomes, apelido, dataderegisto) "
	 		+ "  VALUES( \'"
	 		+ nid
	 		+ "\',\'"
	 		+ nomes
	 		+ "\',\'"
	 		+ apelido
	 		+ "\',\'"
	 		+new SimpleDateFormat("yyyy-MM-dd").format(dataderegisto)
	 		+ "\')");
	
}


/**
 * VE se o paciente foi dispensado ARV no periodo
 * @param patientid
 * @return
 * @throws ClassNotFoundException
 */
public boolean dispensadonoperiodo(String patientid) throws ClassNotFoundException
{
	
	
	boolean foidispensado=false;
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	try {
		ResultSet rs= st.executeQuery(""
				+ " SELECT "
				+ "  patientid FROM  "
				+ "   packagedruginfotmp "
				+ "  WHERE "
				+ " to_timestamp(dateexpectedstring, \'DD Mon YYYY\')::DATE > now()::DATE "
				+ "  AND patientid = \'"+patientid
				+ ""
				+ "\'");
		
		
		if(rs!=null)
			while(rs.next())
				foidispensado=true;
				
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
			
	

	return foidispensado;
	

}


/**
 * Total de pacientes TB iNICIO
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesTbInicio(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	

 


	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid  "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.tb=\'T\'  "
							
			+" AND  "

			+" prescription.reasonforupdate=\'Inicia\'  "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
		 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ "packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			


	
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes TB Manter
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesTbManter(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	


	
	String query=" SELECT  DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\' AND prescription.tb=\'T\'  "
							
			+" AND  "

			+"  prescription.reasonforupdate=\'Manter\'   "
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
			 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid ,"
			+ "packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes PTV Alterar
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesPTVAlterar(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	
 

	String query=" SELECT  DISTINCT"
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\'  AND prescription.ptv=\'T\' AND prescription.reasonforupdate=\'Alterar\'"
							
		 
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
			 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ "packagedruginfotmp.dispensedate "

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

			

	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}

/**
 * Total de pacientes pvt sem discriminar
 * @param startDate
 * @param endDate
 * @return
 */
public int totalPacientesPTV(String startDate, String endDate) throws ClassNotFoundException, SQLException{
	int total=0;
	
	
	

 
	String query=" SELECT DISTINCT "
			+" dispensa_packege.patientid "
			+"	FROM "

		+"	(SELECT  "

		+"	prescription.id, package.packageid "

			+" FROM "

			+" prescription, "
		+" 	package "

+ ", ( "
+ " select patient.id "
+ " from patient,episode "

+ " WHERE "
+ " patient.id=episode.patient "
+ " and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\' "

+ " )as naotransito "
 
	 + " 	 WHERE  "

 + " naotransito.id =prescription.patient "
+ " AND  "

			+" prescription.id = package.prescription "

			+" AND "
			+"  prescription.ppe=\'F\'  AND prescription.ptv=\'T\'"
							
		 
			 
			+" )as prescription_package, "

			+" ( "
			+" SELECT "
		 

			+" packagedruginfotmp.patientid, "
			+" packagedruginfotmp.packageid,"
			+ "  packagedruginfotmp.dispensedate"

			+" FROM "
			+" package, packagedruginfotmp "

			+" WHERE "

			+" package.packageid=packagedruginfotmp.packageid "
			+" AND "

			+"				 packagedruginfotmp.dispensedate::timestamp::date >= "
			+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <= "
					+ " \'"+endDate+"\'::timestamp::date"
			+" ) as dispensa_packege ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			

			
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
}
public String getQueryHistoricoLevantamentos(boolean i, boolean m, boolean a, String startDate, String endDate){
	
	
	
	Vector<String> v = new Vector<String>();
	
	if (i) v.add("Inicia");
	if (m) v.add("Manter");
	if (a) v.add("Alterar");
	
	String condicao="(\'";
	
	if(v.size()==3){
	for(int j=0;j<v.size()-1;j++)
		
	condicao +=v.get(j)+"\' , \'";
	
	condicao +=v.get(v.size()-1)+"\')";
	}
	
	if(v.size()==2){
		for(int j=0;j<v.size()-1;j++)
			
			condicao +=v.get(j)+"\' , \'";
		
		condicao +=v.get(v.size()-1)+"\')";
		}
	
	if(v.size()==1){
	 
			
	  
		condicao +=v.get(0)+"\')";
		}



	String query=""
			
			
		+ " SELECT DISTINCT dispensas_e_prescricoes.nid, patient.firstnames as nome, patient.lastname as apelido, "
		+ " extract(year FROM age(current_date, patient.dateofbirth)) as idade, "
		+ " dispensas_e_prescricoes.tipotarv,  "
     + "   dispensas_e_prescricoes.regime, "
     + "    dispensas_e_prescricoes.datalevantamento, dispensas_e_prescricoes.weekssupply as dias, "
    + "    dispensas_e_prescricoes.dataproximolevantamento, dispensas_e_prescricoes.linha, dispensas_e_prescricoes.dispensedqty as qtd "
    + "    FROM "
+ "  (SELECT   "
					
 	+ "  dispensa_packege.nid , "
    + "    prescription_package.tipotarv,  "
    + "    prescription_package.regime, "
     + "    dispensa_packege.datalevantamento, "
     + "    dispensa_packege.dataproximolevantamento , prescription_package.linha, dispensa_packege.dispensedqty,dispensa_packege.weekssupply"
	 + " 	FROM  "

	 + " 	( "
	 	+ "   SELECT  "

	 + " 		prescription.id, "
	 + " package.packageid ,prescription.reasonforupdate as tipotarv, regimeterapeutico.regimeesquema as regime , linhat.linhanome as linha "

 	+ " 	 FROM  "

	 + " 	 prescription,  "
 	 	+ " package , regimeterapeutico , linhat"
 
	 	+ "  WHERE   "

	 	 + " prescription.id = package.prescription  "

	 	+ "  AND  "
	  + " 	 prescription.ppe=\'F\' "
      
  		+ " 	AND 	prescription.regimeid=regimeterapeutico.regimeid "
	  	+ " AND   "

	+ "  	 prescription.reasonforupdate IN "+condicao
	+ " AND prescription.linhaid=linhat.linhaid "
	 
	+ " 	 )as prescription_package,  "

	 + " 	 (  "
	 			+ " 	 SELECT  "
	

	 + " 	 packagedruginfotmp.patientid as nid,  "
	 + " 	 packagedruginfotmp.packageid,"
	 + " 	 packagedruginfotmp.dispensedate as datalevantamento, packagedruginfotmp.weekssupply,"
	+ "  	 to_date(packagedruginfotmp.dateexpectedstring, 'DD-Mon-YYYY') as dataproximolevantamento ,packagedruginfotmp.dispensedqty "
	 	  
	+ "  	 FROM "
	 	+ "  	 package, packagedruginfotmp  "

	  + " 	 WHERE  "

 	+ " 	 package.packageid=packagedruginfotmp.packageid  "
 	+ " 	 AND  "
 
	+ "  					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "  \'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	+ "   \'"+endDate+"\'::timestamp::date  "
	  + " 	) as dispensa_packege,"
      + "     ( "
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	  	+ "  WHERE  "

 		+ "  package.packageid=packagedruginfotmp.packageid  "
 	+ " 	 AND  "
 
	 		+ " 			 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "  \'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	+ "   \'"+endDate+"\'::timestamp::date  "
	  
	+ "   group by packagedruginfotmp.patientid "
 
   + "       ) as ultimadatahora  "

	+ "  	 WHERE  "
	+ "  	 dispensa_packege.packageid=prescription_package.packageid  "
	 	 
	 + " 	 and "
    + "    dispensa_packege.datalevantamento=ultimadatahora.lastdispense "
    + "    ) as dispensas_e_prescricoes "
   + "     ,"
       + "     patient "
       
    + "    where "
       
    + "    dispensas_e_prescricoes.nid=patient.patientid AND patient.id NOT IN  "
    + "     (select patient.id from patient inner join episode on patient.id=episode.patient  WHERE episode.startreason  like '%Transito%' OR episode.startreason like '%aternidade%'  ); ";
        //Alterado por Colaco Nhongo. Nao emprime o Ficheiro correcto       
        //  + "     (select patient.id  	from patient,episode  WHERE  patient.id=episode.patient  and episode.startreason  like '%Transito%' OR episode.startreason   like '%aternidade%'  ); ";
 

 System.out.println(query);
	return query;
	
}

public void insere_sync_temp_dispense(){
	
	delete_sync_temp_dispense();
	 try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	
	ConexaoODBC conn=new ConexaoODBC();
	
	ResultSet data=conn.result_for_sync_dispense();
	
	
	if(data!=null)
	{
		try {
			

			while (data.next())
			{
			
				st.executeUpdate(" INSERT INTO sync_temp_dispense(nid,ultimo_levantamento) values (\'"+data.getString("nid")+"\',\'"+data.getString("ultimo_lev")+"\')"); 
		 
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	else System.out.println("NULL NULL NULL NULL");
}

public int total_rows( ) {
	 
	ResultSet data=null;
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		data = st.executeQuery("SELECT  *   FROM  sync_view_dispense ");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	int rows=0;
	try {
		while (data.next())
		{
		rows++;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		data.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rows;
}
public void delete_sync_temp_dispense(){
	
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		st.execute("DELETE FROM sync_temp_dispense");
		st.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public  Vector <SyncLinha> sync_table_dispense() 
{
	dataProximoLevantamentoFormat() ;
	insere_sync_temp_dispense();
	Vector<SyncLinha> linha = new Vector<SyncLinha>() ;
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try
	{
		String query="SELECT "
				+ " sync_view_dispense.nid as a, "
				+ "  sync_view_dispense.ultimo_lev as b,  "
				+ "   sync_view_dispense.tipo_tarv as c, "
				+ "  sync_view_dispense.regime as d, "
				+ "   sync_view_dispense.linha as e, "
				+ " sync_view_dispense.ultimo_sesp as f, "
				+ " tabela.proximolev   as g  "
				+ ""
				+ " FROM  "
				+ "   ( SELECT "
				+ "   idart.nid, "
  + "   idart.ultimo_lev, "
  + "   idart.tipo_tarv, "
  + "   idart.regime, "
 + "    idart.linha, "
  + "   (idart.ultimo_lev + 30) AS dataproxima, "
  + "   sync_temp_dispense.ultimo_levantamento AS ultimo_sesp "
+ "   FROM (( SELECT "
+ "     dispensa.nid, "
  + "   dispensa.ultimo_lev, "
  + "   prescricao.tipo_tarv, "
  + "   prescricao.regime, "
  + "    prescricao.linha "
+ "   FROM ( SELECT "
 + "    packagedruginfotmp.patientid AS nid, "
  + "   (max(packagedruginfotmp.dispensedate))::date AS ultimo_lev "
+ "   FROM packagedruginfotmp "
+ "   GROUP BY "
 + "    packagedruginfotmp.patientid "
+ "   ORDER BY "
 + "    packagedruginfotmp.patientid) dispensa, ( SELECT DISTINCT "
		  + "   patient.patientid AS nid, "
  + "   prescription.reasonforupdate AS tipo_tarv, "
  + "   regimeterapeutico.regimeesquema AS regime, "
  + "   linhat.linhanome AS linha "
  + "   FROM patient, prescription, linhat, regimeterapeutico "
+ "   WHERE "
+ "     ( "
 + "      ("
 + "        ("
  + "         (patient.id = prescription.patient) AND"
    + "       (prescription.current = 'T'::bpchar) "
            + "      ) AND "
      + "      (prescription.regimeid = regimeterapeutico.regimeid) "
      + "      ) AND "
    + "       (prescription.linhaid = linhat.linhaid) "
    + "     )) prescricao  "
  + "   WHERE "
+ "    ( "
		  + "      (dispensa.nid)::text = (prescricao.nid)::text "
    + "    )) idart  "
  + "    LEFT JOIN sync_temp_dispense ON  "
  + "    ( "
    		+ "      ( "
    		  + "        (idart.nid)::text = (sync_temp_dispense.nid)::text "
        + "        ) "
      + "      )) "
+ "   ) as sync_view_dispense,"
				+ ""
				+ "(select patientid, max (date (packagedruginfotmp.dateexpectedstring)) proximolev from packagedruginfotmp "
	 
				+ ""
				+ ""
				+ "GROUP BY patientid ) as tabela  WHERE  "
				+ " sync_view_dispense.nid= tabela.patientid AND (sync_view_dispense.ultimo_lev > sync_view_dispense.ultimo_sesp OR sync_view_dispense.ultimo_sesp IS NULL)";
		
	ResultSet linhas =   st.executeQuery(query);
	
	
	
 
		
	
	//System.out.println(" Query: "+query );
			   

			

 


	
	while (linhas.next()){
		
 	 SyncLinha synclinha=new  SyncLinha(linhas.getString("a"),linhas.getString("b") ,linhas.getString("c") ,linhas.getString("d"),
	linhas.getString("e"),linhas.getString("f"), linhas.getString("g"));
 

	  System.out.println	( linhas.getString("a")+", "+linhas.getString("b") +", "+ linhas.getString("c") +", "+linhas.getString("d")+", "+
					 linhas.getString("e")+", "+ linhas.getString("f") +", dATA PROXIMA  "+ linhas.getString("g"));
				 
				
	 linha.add(synclinha);
        
	}
	
 
	
	}catch(SQLException  e){
		
		
	}
	
	System.out.println(" Vector size "+linha.size());
	
	 return linha;
 
}

public Vector <SyncLinhaPatients> sync_table_patients() {

	Vector<SyncLinhaPatients> linha = new Vector<SyncLinhaPatients>() ;
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try
	{
		
	ResultSet linhas =   st.executeQuery("SELECT "
			+ " sync_view_patients.nid as a, "
			+ "  sync_view_patients.datanasc  as b, "
			+ " sync_view_patients.pnomes as c, "
			+ " sync_view_patients.unome as d, "
			+ "  sync_view_patients.sexo as e, "
			+ "  sync_view_patients.dataabertura as f "
			+ "  FROM "
			+ " sync_view_patients ");
	
	while (linhas.next()){
		
		
		
		SyncLinhaPatients synclinha=new  SyncLinhaPatients(linhas.getString("a"),linhas.getString("b"), linhas.getString("c"), linhas.getString("d"), linhas.getString("e"), linhas.getString("f"));
 

	/*System.out.println	(linhas.getString("a")+" "+linhas.getString("b") +" "+ linhas.getString("c") +" "+linhas.getString("d")+" "+
					linhas.getString("e")+" "+ linhas.getString("f"));
				*/
				
	 linha.add(synclinha);
        
	}
	
 
	
	}catch(SQLException  e){
		
		
	}
	
	 return linha;
}

public void delete_sync_temp_patients() {
	// TODO Auto-generated method stub
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		st.execute("DELETE FROM sync_temp_patients");
		st.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public void insere_sync_temp_patients() {

	

	
	 try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} 
	
	ConexaoODBC conn=new ConexaoODBC();
	
	ResultSet data=conn.result_for_sync_patients();
	
 
	
	if(data!=null)
		try {
			

			while (data.next())
			{
				
			 String datanasc=data.getString("datanasc");
			 String dataabertura=data.getString("dataabertura");
			 String unomes=data.getString("apelido");
			 
			 if (unomes==null || contemInterrogacao(unomes) ) unomes ="  ";
			 if(datanasc==null) datanasc=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			 if(dataabertura==null) dataabertura=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

			 String pnomes=data.getString("nome");
			 
			 pnomes=pnomes.replace('\'', ' ');
			 unomes = unomes.replace('\'', ' ');
			 
			 
			 String query=" INSERT INTO sync_temp_patients(nid,datanasc,pnomes, unomes, sexo, dataabertura) values (\'"+data.getString("nid")+"\',"
						+ "\'"+datanasc+"\',"
						+ "\'"+pnomes+"\',"
						+ "\'"+unomes+"\',"
						+ "\'"+data.getString("sexo")+"\',"
						+ "\'"+dataabertura+"\')"
						+ ""; 
				System.out.println(query);

						st.executeUpdate(query);
 
		 
			}
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}


private boolean contemInterrogacao(String unomes) {
	boolean contem=false;
	
	if(unomes!=null)
		
	{
		
		for(int i=0;i<unomes.length();i++)
			if(unomes.charAt(i)=='?')
			{
				contem=true;
				break;
			}
	}
	return contem;
	
}



public void syncdata_patients(SyncLinhaPatients syncLinhaPatients) {
 
	String sexo=syncLinhaPatients.getSexo();
	 if (sexo.trim().equals("null")) sexo ="U";
	 String apelido =syncLinhaPatients.getUnomes();
	 
		 if (apelido.trim().equals("null")) apelido =" ";
		 
	 try {
			conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	 
		try{
			int id=nextval_hibernate_sequence();
			
			st.executeUpdate(""
					+ "INSERT INTO "
					+ " patient "
					+ " (id, accountstatus, dateofbirth, clinic, firstnames, lastname, modified, patientid, province , sex) "
					+ " VALUES ( "
					+ ""+ id+","
					+ " \'t\',"
					+ "\'"+ syncLinhaPatients.getDatanasc()+"\',"
					+ "2,"
					+ "\'"+syncLinhaPatients.getPnomes()+"\',"
					+ "\'"+apelido+"\',"
					+ "\'T\',"
					+ "\'"+syncLinhaPatients.getNid()+"\',"
					+ "\'Select a Province\',"
					+ "\'" +sexo.trim().charAt(0)+"\')");
			
			st.executeUpdate( ""
					+ "INSERT INTO episode "
					+ "(id, "
					+ "startdate, "
					+ "startreason, "
					+ "patient, "
					+ "index, "
					+ "clinic"
					+ ") "
					+ "VALUES "
					+ "("
					+ ""+nextval_hibernate_sequence()+","
					+ "\'"+syncLinhaPatients.getDataabertura()+"\',"
					+ "\'Novo Paciente\',"
					+ ""+id+","
					+ "0,2)");
			
			st.executeUpdate(" INSERT INTO "
					+ " patientidentifier "
					+ "("
					+ "id, "
					+ "value, "
					+ "patient_id,"
					+ "type_id"
					+ ") "
					+ "VALUES "
					+ ""
					+ "("
					+ ""+nextval_hibernate_sequence()+","
					+ "\'"+syncLinhaPatients.getNid()+"\',"
					+ ""+id+", 0)");
					 
 
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
	

}
private int nextval_hibernate_sequence(){
int id=0;
			
			 try {
					conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} 
	
	ResultSet rsId=null;
	try {
		rsId = st.executeQuery("SELECT nextval(\'hibernate_sequence\') as id");
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(rsId!=null)
		try {
			while(rsId.next()) id=rsId.getInt("id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	return id;
}



public void updateDispensas() {
	// TODO Auto-generated method stub
	
	
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	try {
		st.executeUpdate("UPDATE packagedruginfotmp SET "
				+ " dateexpectedstring= TO_CHAR((dispensedate::date) + 30 ,'dd Month yyyy')  "
				+ " WHERE dispensedate::date=(date (packagedruginfotmp.dateexpectedstring))");
		
		System.out.println();
		st.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
}



/**
 * Total de pacientes que iniciaram o tratamento de ARV num periodo
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesInicio(String startDate, String endDate)throws ClassNotFoundException, SQLException{
	int total=0;
	

	

 


			String query=" SELECT  DISTINCT "
					
+ "	 dispensa_packege.patientid  "
	+ "	FROM  "

	+ "	( "
	+ "	  SELECT  "

	+ "		prescription.id, package.packageid  "

+ "		 FROM  "

	+ "	 prescription,  "
 	+ "	package,  "
 
 	
+ " ( "
+ " 	select patient.id "
 + " 	from patient,episode"

 	+ "  WHERE"
 	+ "  patient.id=episode.patient"
 	+ "  and episode.startreason not like '%Transito%' and episode.startreason not like \'%aternidade%\'"

 	+ "  )as naotransito"
 	 
 		+ "  	 WHERE  "

 	+ "  naotransito.id =prescription.patient"
 			+ " AND "

	+ "	 prescription.id = package.prescription  "

	+ "	 AND  "
	 + "	 prescription.ppe=\'F\' "
      
  			
	 + "	AND   "

	+ "	 prescription.reasonforupdate=\'Inicia\'   "
	 
	+ "	 )as prescription_package,  "

	+ "	 (  "
	+ "	 SELECT  "
	

	+ "	 packagedruginfotmp.patientid,  "
	+ "	 packagedruginfotmp.packageid, "
	+ "	 packagedruginfotmp.dispensedate "
	 
+ "	 "
	+ "	 FROM "
	+ "	 package, packagedruginfotmp  "

	 + "	 WHERE  "

+ "		 package.packageid=packagedruginfotmp.packageid  "
+ "		 AND  "
 
	+ "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	+ " \'"+endDate+"\'::timestamp::date  "
	 + "	) as dispensa_packege  ,"
          
	 + " ("
      + "     select packagedruginfotmp.patientid,  "
	 	 
	 + " 	  max(packagedruginfotmp.dispensedate) as lastdispense"
	 
 	 
	 + " 	 FROM "
	 + " 	 package, packagedruginfotmp  "

	 + "  	 WHERE  "

 	 + "	 package.packageid=packagedruginfotmp.packageid  "
 	 + "	 AND  "
 
	  + "					 packagedruginfotmp.dispensedate::timestamp::date >=  "
	+ "\'"+startDate+"\'::timestamp::date  AND  packagedruginfotmp.dispensedate::timestamp::date <=  "
	 + " \'"+endDate+"\'::timestamp::date  "
	  
	 + "  group by packagedruginfotmp.patientid "
 
      + "     ) as ultimadatahora "

	+ "	 WHERE  "
	+ "	 dispensa_packege.packageid=prescription_package.packageid  "
     + "	  and  "
     + "  dispensa_packege.dispensedate=ultimadatahora.lastdispense";
			
			
	
	 conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);


		ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {
	           
	            while (rs.next())
	            {

 	total++;

	            } 
	            rs.close(); //
	        }
		return total;
	
}
//importa contactos 

public void importaContactos()
{
	ConexaoODBC conn=new ConexaoODBC();
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 

	Vector <Contacto> c=conn.contactos();
	
	if(c!=null)
	{
		for(int i=0; i<c.size();i++)
		{
			String cell=c.get(i).getTelefone().replace('\'', ' ');
			 try {
				st.executeUpdate(" UPDATE patient set cellphone =  \'"+cell+"\' WHERE patientid= \'"+c.get(i).getNid()+"\' AND cellphone IS NULL");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 	 
		}
		
	}
	
}

//importa enderecos

public void importaEnderecos()
{
	ConexaoODBC conn=new ConexaoODBC();
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 

	Vector <Endereco> c=conn.enderecos();
	
	if(c!=null)
	{
		for(int i=0; i<c.size();i++)
		{
			 try {
				 String a = c.get(i).getCelula().replace('\'', ' ');//.replaceAll("[\"\']",""); 
					 String b = c.get(i).getAvenida().replace('\'', ' ');//.replaceAll("[\"\']","");
				st.executeUpdate(" UPDATE patient set address1 =  \'"+a+"\' ,"
						+ " address2 =\'"+b+"\' WHERE patientid= \'"+c.get(i).getNid()+"\'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 	 
		}
		
	}
	
}
//importa confidentes

public void importaConfidentes()
{
	ConexaoODBC conn=new ConexaoODBC();
	try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 

	Vector <Confidente> c=conn.confidentes();
	
	if(c!=null)
	{
		for(int i=0; i<c.size();i++)
		{
			 try {
				 String a = c.get(i).getNome().replace('\'', ' ');//.replaceAll("[\"\']",""); 
					 String b = c.get(i).getTelefone().replace('\'', ' ');//.replaceAll("[\"\']","");
				st.executeUpdate(" UPDATE patient set nextofkinname =  \'"+a+"\' ,"
						+ " nextofkinphone =\'"+b+"\' WHERE patientid= \'"+c.get(i).getNid()+"\'");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 	 
		}
		
	}
	
}



public void dataProximoLevantamentoFormat() {

	        String query=" update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Oct\' from 4 for 6) WHERE dateexpectedstring like \'%Out%\';  "
	        		+ "update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'May\' from 4 for 6) WHERE dateexpectedstring like \'%Mai%\'; "
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Feb\' from 4 for 6) WHERE dateexpectedstring like \'%Fev%\'; "        		 
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Feb\' from 4 for 6) WHERE dateexpectedstring like \'%Fev%\';  " 
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Apr\' from 4 for 6) WHERE dateexpectedstring like \'%Abr%\';  " 
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Sep\' from 4 for 6) WHERE dateexpectedstring like \'%Set%\';  " 
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Aug\' from 4 for 6) WHERE dateexpectedstring like \'%Ago%\';  " 
	        		+ " update packagedruginfotmp  SET dateexpectedstring =  overlay(dateexpectedstring placing \'Dec\' from 4 for 6) WHERE dateexpectedstring like \'%Dez%\';  " ;


	    	ConexaoODBC conn=new ConexaoODBC();
	    	try {
	    		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	    	} catch (ClassNotFoundException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	} catch (SQLException e) {
	    		// TODO Auto-generated catch block
	    		e.printStackTrace();
	    	}
	    	 

	     
	    			 try {
	    				 st.executeUpdate(query);
	    			} catch (SQLException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    				 	 
	    		
	    		
	    	
	
	
}






public boolean jaTemFilaInicio(String nid)

{
boolean jatemFilaInicio=false;

	String query= " SELECT DISTINCT dispensas_e_prescricoes.nid  "
			+ " FROM  "
			+ "   " 
+" (SELECT "
			+ "	 dispensa_packege.nid "

+ " FROM "
+ " (  "
+ "   SELECT  "

 	 + " 	prescription.id, "
 + " package.packageid ,prescription.reasonforupdate as tipotarv "

	+ " 	 FROM  "
		 + " "
 + " 	 prescription,  "
 	+ " 	 	package "

 + " 	 WHERE   "

 + " 	 prescription.id = package.prescription  "
 			+ " "
 + " 	 AND  "
 + " 	 prescription.ppe=\'F\' "
  
+ " 	 AND   "

 + " 	 prescription.reasonforupdate IN ('Inicia')"
 + "  )as prescription_package,  "

 	+ "  (  "
 	+ " 			 SELECT  "
 				+ " "

 	 + " packagedruginfotmp.patientid as nid,  "
 	+ " packagedruginfotmp.packageid,"
 	 + " packagedruginfotmp.dispensedate as datalevantamento "
 	  
 	 + " FROM "
 	 	+ "  package, packagedruginfotmp  "

  	+ "  WHERE  "

		+ "  package.packageid=packagedruginfotmp.packageid  "
		+ " ) as dispensa_packege,"
     + "  ( "
    		  + "  select packagedruginfotmp.patientid,  "
 	 
      + "  max(packagedruginfotmp.dispensedate) as lastdispense "
 
	 
 	 + "  FROM "
 	+ "  package, packagedruginfotmp " 

 	+ "  WHERE  "

  	+ "  package.packageid=packagedruginfotmp.packageid " 

  
				 + " group by packagedruginfotmp.patientid "

  + "  ) as ultimadatahora  "

      + " WHERE  "
 	+ " dispensa_packege.packageid=prescription_package.packageid  "
 	 
 			+ "  and "
 	+ "  dispensa_packege.datalevantamento=ultimadatahora.lastdispense "
   + "   ) as dispensas_e_prescricoes ,"
    
   + "      patient "
        
       + "  where "
   
   + " dispensas_e_prescricoes.nid=patient.patientid AND dispensas_e_prescricoes.nid=\'"+nid+"\'";
	
	
	
	
	
	
	 try {
		conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 

 
			 try {
				 ResultSet rs=st.executeQuery(query);
					
				 
				 
				 
				 while (rs.next()){
						
					
						if(rs.getString("nid").equals(nid)) 
							{
							jatemFilaInicio=true;
							
							break;
							
							}
						
						System.out.println("/*/*//*///*/*//*/*/    "+rs.getString("nid"));
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	return jatemFilaInicio;
	
}


/**
 * Total de pacientes novos que iniciam dispensa trimestral
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesNovosDispensaTrimestral(String startDate, String endDate) throws ClassNotFoundException, SQLException{

      String query=" select sum(dispensatrimestral) "
              + "from prescription where reasonforupdate="+ "\'"+"Inicia"+"\'"+" "
              + "and "
              	+ "  date::timestamp::date >=  "
			+ "\'"+startDate+"\'"
			+ "AND date::timestamp::date <= "
			+ " \'"+endDate+"\'";
     
	int total=0;
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
        ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {      
                   rs.next();
	           total=rs.getInt("sum");
 	           rs.close(); //
	        }
    
	return total;
        
}


/**
 * Total de pacientes Manter  em dispensa trimestral
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesManterDispensaTrimestral(String startDate, String endDate) throws ClassNotFoundException, SQLException{

      String query=" select sum(dispensatrimestral) "
              + "from prescription where reasonforupdate="+ "\'"+"Manter"+"\'"+" "
              + "and "
              	+ "  date::timestamp::date >=  "
			+ "\'"+startDate+"\'"
			+ "AND date::timestamp::date <= "
			+ " \'"+endDate+"\'";
     
	int total=0;
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
        ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {          
	           rs.next();
	           total=rs.getInt("sum");
 	           rs.close(); //
	        }
    
	return total;
        
}



/**
 * Total de pacientes Alteracao  em dispensa trimestral
 * @param startDate
 * @param endDate
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
public int totalPacientesAlteracaoDispensaTrimestral(String startDate, String endDate) throws ClassNotFoundException, SQLException{

      String query=" select sum(dispensatrimestral) "
              + "from prescription where reasonforupdate="+ "\'"+"Reiniciar"+"\'"+" "
              + "and "
              	+ "  date::timestamp::date >=  "
			+ "\'"+startDate+"\'"
			+ "AND date::timestamp::date <= "
			+ " \'"+endDate+"\'";
     
	int total=0;
	conecta(iDartProperties.hibernateUsername, iDartProperties.hibernatePassword);
        ResultSet rs=st.executeQuery(query);
		if (rs != null)
	        {          
	           rs.next();
	           total=rs.getInt("sum");
 	           rs.close(); //
	        }
    
	return total;
        
}

}

