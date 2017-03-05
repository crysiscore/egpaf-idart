package org.celllife.idart.database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.celllife.idart.commonobjects.Confidente;
import org.celllife.idart.commonobjects.Contacto;
import org.celllife.idart.commonobjects.Endereco;
import org.celllife.idart.gui.sync.dispense.SyncLinha;
import org.celllife.idart.gui.sync.patients.SyncLinhaPatients;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
 
 
/**
 * Classe respons�vel por gerir conex�es com a base de dados do MS-Access
 * @author EdiasJambaia
 *
 */
public class ConexaoODBC {
	
    private static final String username = "Admin";
    private static final String password = "";
    private static final String DSN = "ACCESSJAVA"; // nome da fonte de dados
    private static final String driver = "sun.jdbc.odbc.JdbcOdbcDriver"; // driver usado
    private static Connection conn = null;
	private static Logger log = Logger.getLogger(ConexaoODBC.class);        
    /**
     *  retorna uma conex�o com a base de dados Access.
     *  
     */
    public static Connection getConnection() throws Exception {
    	DOMConfigurator.configure("log4j.xml");
        if(conn == null) {
  
            log.info("A Estabelecer a conexao com MS ACESS.....");
            String url = "jdbc:odbc:" + DSN;
            Class.forName(driver);
            try
            {
            conn = DriverManager.getConnection(url, username, password);
 
            log.info("A conexao com MS ACESS estabelecida com sucesso");
            
            }catch(Exception e)
            {
            	
            	log.error("N�o foi possivel conectar a base de dados MS ACESS.....");
            }
        }
        return conn;
    }
     
    /**
     * Fecha a conex�o com o Banco de dados access
     * Chamar esse m�todo ao sair da aplica��o
     */
     public static void close() {
        if(conn != null) {
            try {
            	System.out.println("++++++ A Fechar a conexao com MS ACESS.....");
                conn.close();
                System.out.println("++++++ Conexao com MS ACESS fechada com sucesso.....");
            } catch (SQLException ex) {
            	System.out.println("++++++ Problemas ao fechar a Conexao com MS ACESS.....");
                ex.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }
     


    
    
    // Insere uma dispensa em t_tarv MS ACCESS
   
    public static void insereT_tarv(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int semanas, 
    		String tipotarv, String dataproxima, int idade, String linha) throws Exception
    {
    	
    	
    	String newTipotarv=tipotarv;
    	
    	
   	 //verifica se o paciente ja tem fila inicio tarv
		  if(newTipotarv.trim().equals("Inicia"))
			{
			  System.out.println("A verificar se o paciente ja iniciou tarv no ms access = "+newTipotarv);
				 
						 
						 if(jaTemFilaInicio(nid))
						 {
							 newTipotarv="Manter" ;
							 System.out.println("O paciente ja iniciou tarv e o reason for update ficou  = "+newTipotarv);
								 
						 }
							
		       }
				  
				  else  System.out.println("REasonforupdade e diferente de inicio= "+newTipotarv);
				
    	
    	
    	
    	
    	
    	
    	int d=0;
    	if(semanas==1) d=7;
    	if(semanas==2) d=15;
    	if(semanas==4) d=30;
    	if(semanas==8) d=60;
    	if(semanas==12) d=90;
    	if(semanas==16) d=120;
    	if(semanas==20) d=150;
    	if(semanas==24) d=180;
    	
    	
    	try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1: //Se tiver apenas 1 medicamento na prescricao
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "idade,"
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+d+",\'"+newTipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: //Se tiver apenas 2 medicamento na prescricao
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+d+",\'"+newTipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3: //Se tiver apenas 3 medicamento na prescricao
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+d+",\'"+newTipotarv+"\',\'"+dataproxima+"\',"+qtdComp+","+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+d+",\'"+newTipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                      System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            
          //Actualizar t_paciente: emTArv , DataInicio e regime
            
            System.out.println("Inicia "+newTipotarv);
            if(newTipotarv.equals("Inicia"))
            {
            	updateTPacienteInicio(nid, datatarv, regime,linha);
            }
            
            //Actualizar t_paciente: emTArv , DataInicio , regime e transfOutraUs
            if(newTipotarv.equals("Transfer de"))
            {
            	updateTPacienteTransferidoDe(nid, datatarv, regime,linha);
            }
          
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
 
     
	 log.error("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
	 log.error(e.toString());
 

	/*MessageBox conexaoACCESS = new MessageBox(new Shell(), SWT.ICON_ERROR
			| SWT.OK);
	conexaoACCESS.setText("Conex�o com Base de Dados SESP");
	conexaoACCESS
	.setMessage("O iDART n�o est� a se conectar com o SESP.\n Por favor verifique se os cabos da rede est�o ligados no seu \ncomputador ou se o computador com SESP est� ligado!\n Saia do iDART e verifique estes apectos depois volte a entrar,\n se o problema persistir, todos registos de FILAS e registo dos novos pacientes n�o ser�o actualizados no SESP!  \n N�o foi poss�vel actualizada a �ltima opera��o no SESP!");
	conexaoACCESS.open();
	e.printStackTrace();*/
 
}
    	
    	
    }

	//insere t_tarv com motivo de mudanca de ARV�S
	public static void insereT_tarvMotivo(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int dias, 
    		String tipotarv, String dataproxima, int idade, String codmudanca, String linha) throws Exception
    {
    	try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1: //para 1 medicamento
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "codmudanca, "
            	   	   	+ "idade, "
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: //para 2 medicamentos
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  log.info("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3://para 3 medicamento
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\'))");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   //para 3 medicamento
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "codmudanca, "
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+codmudanca+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\'))");
                     log.info("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            	
          
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
	log.error("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
	 log.error(e.toString());
   e.printStackTrace();
   

/*	MessageBox conexaoACCESS = new MessageBox(new Shell(), SWT.ICON_ERROR
			| SWT.OK);
	conexaoACCESS.setText("Conex�o com Base de Dados SESP");
	conexaoACCESS
	.setMessage("O iDART n�o est� a se conectar com o SESP.\n Por favor verifique se os cabos da rede est�o ligados no seu \ncomputador ou se o computador com SESP est� ligado!\n Saia do iDART e verifique estes apectos depois volte a entrar,\n se o problema persistir, todos registos de FILAS e registo dos novos pacientes n�o ser�o actualizados no SESP!  \n N�o foi poss�vel actualizada a �ltima opera��o no SESP!");
	conexaoACCESS.open();
	e.printStackTrace();*/
 
}
    	
    	
    }

	//insere ttarv transferido de 
	public void insereT_tarvTransferidoDE(Vector medicamentos, String nid, String datatarv, int qtdComp,  String regime, int dias, 
    		String tipotarv, String dataproxima, int idade, String dataoutroservico, String linha) {

		try {
            // inicilizando a conex�o
            Connection conn = ConexaoODBC.getConnection();
 
            Statement st = conn.createStatement();
            System.out.println("++++++ A Preparar a Insercao de dados na tabela T_TARV do MS ACESS.....");
            //insere dados  
            
            switch(medicamentos.size())
            {
            case 1:
            	   st.executeUpdate(""
            	    	+ "insert into t_tarv"
            	     	+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
            	   	    + "QtdComp,"
                  		+ "dataoutroservico, "
            	   	   	+ "idade,"
                  		+ "compromido, "
                  		+ "linhaTerapeutica"
            	   		+ ") "
            	      + "values ("            	   
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 2: 
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataoutroservico,"
                  		+ "QtdComp,"
                  		+ "codmudanca, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  
            case 3:
            	  st.executeUpdate(""
                  		+ "insert into t_tarv"
                  		+ "("
                  		+ "nid,"
                  		+ "datatarv,"
                  		+ "codregime,"
                  		+ "dias,"
                  		+ "tipotarv,"
                  		+ "dataproxima,"
                  		+ "QtdComp,"
                  		+ "dataoutroservico, "
                  		+ "idade,"
                  		+ "compromido,"
                  		+ "compromido1,"
                  		+ "compromido2, "
                  		+ "linhaTerapeutica"
                  		+ ") "
                + "values ("
                + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                  System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                  
                  break;
                  default:   
                	  st.executeUpdate(""
                    		+ "insert into t_tarv"
                      		+ "("
                      		+ "nid,"
                      		+ "datatarv,"
                      		+ "codregime,"
                      		+ "dias,"
                      		+ "tipotarv,"
                      		+ "dataproxima,"
                      		+ "QtdComp,"
                      		+ "dataoutroservico, "
                      		+ "idade,"
                      		+ "compromido,"
                      		+ "compromido1,"
                      		+ "compromido2, "
                  		+ "linhaTerapeutica"
                      		+ ") "
                    + "values ("
                    + "\'"+nid+"\',\'"+datatarv+"\',\'"+regime+"\',"+dias+",\'"+tipotarv+"\',\'"+dataproxima+"\',"+qtdComp+",\'"+dataoutroservico+"\',"+idade+",\'"+(String)medicamentos.get(0)+"\',\'"+(String)medicamentos.get(1)+"\',\'"+(String)medicamentos.get(2)+"\',\'"+linha+"\')");
                      System.out.println("++++++ Os Dado inseridos com sucesso na tabela T_TARV.....");
                      
                      break;
                  
            }
            	
            updateTPacienteTransferidoDe(nid, datatarv, regime,linha);
            // fechando a conex�o
            ConexaoODBC.close();
        }


catch (Exception e)

{
	 System.out.println("++++++ Houve erro na Insercao de dados na tabela T_TARV do MS ACESS.....");
	 log.error(e.toString());
   e.printStackTrace();

/*	MessageBox conexaoACCESS = new MessageBox(new Shell(), SWT.ICON_ERROR
			| SWT.OK);
	conexaoACCESS.setText("Conex�o com Base de Dados SESP");
	conexaoACCESS
	.setMessage("O iDART n�o est� a se conectar com o SESP.\n Por favor verifique se os cabos da rede est�o ligados no seu \ncomputador ou se o computador com SESP est� ligado!\n Saia do iDART e verifique estes apectos depois volte a entrar,\n se o problema persistir, todos registos de FILAS e registo dos novos pacientes n�o ser�o actualizados no SESP!  \n N�o foi poss�vel actualizada a �ltima opera��o no SESP!");
	conexaoACCESS.open();
	e.printStackTrace();*/
 
}
	}


	
	public static void selectAcess( String nid){
		

		
		String sqlQueryPaciente = "SELECT " 
	            + "sexo, " 
	            + "datanasc, " 
	            + "nome, " 
	            + "apelido, " 
	            + "avenida "
	        
	 + "FROM  t_paciente where nid=\'"+nid+"\'";
	    
	   

		
		ResultSet rs = null;
		try {
			Connection conn = ConexaoODBC.getConnection();
			 
		    Statement st=conn.createStatement();
			rs = st.executeQuery(sqlQueryPaciente);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (rs != null)
	    {
	       
	        try {
				while (rs.next())
				{
	  
	System.out.println("Nome: "+ rs.getString("nome")+ " Sexo: "+rs.getString("sexo"));
	      
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 log.error(e.toString());
			} // � necess�rio fechar o resultado ao terminar
	    }
	}
	
public static void main(String [] ars)


{
	
	
	System.out.println("hdd "+codigoUS());
	
	selectAcess( "11050801/14/1062");
	 
	//inserePaciente("77454545455898500000","Nome O", "Apelido 0", new Date(), new Date(), 'M', new Date());

}

public int pacientesActivosEmTarv(){
	
	int pacientes=0;
	
	String query="  SELECT distinct t_tarv.nid FROM t_tarv, t_paciente "
			+ " WHERE t_tarv.nid=t_paciente.nid AND t_paciente.codestado IS NULL AND DATEDIFF(\'d\', t_tarv.dataproxima, #"+ new SimpleDateFormat("yyyy-MM-dd").format(new Date()) +"#) <=60  ";

	
	
	ResultSet rs = null;
	try {
		 conn = ConexaoODBC.getConnection();
		 
	    Statement st=conn.createStatement();
		rs = st.executeQuery(query);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (rs != null)
    {
       
        try {
			while (rs.next())
			{
  System.out.println(rs.getString("nid"));
pacientes+=1;
      
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // � necess�rio fechar o resultado ao terminar
    }
	
	
	return pacientes;
	
	
}

//consulta se existe um dado nid
public boolean existNid( String nid) {
	
boolean existe=false;
int i=0;
	
	String sqlQueryPaciente = "SELECT t_paciente.nid " 
          
 + " FROM  t_paciente where nid=\'"+nid+"\'"
 		+ "    UNION "
 		+ " "
 		+ " SELECT t_tarv.nid " 
        
       + " FROM  t_tarv where nid=\'"+nid+"\'"; 
    
   

	
	ResultSet rs = null;
	try {
		conn = ConexaoODBC.getConnection();
		 
	    Statement st=conn.createStatement();
		rs = st.executeQuery(sqlQueryPaciente);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if (rs!=null)
	{
		
	
		try {
			while(rs.next()){
				
				i++;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();	 log.error(e.toString());
		}
		
		
	}
		if(i>0) existe=true;

return existe;
}

public void inserePaciente(String nid, String nome, String apelido, Date dataabertura, Date datanasc, char sexo, Date datainiciotarv, String telefone)

{
	
	System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(dataabertura));
	boolean sucesso=false;





 try {
	 
	 if(telefone.trim().length()>6) insereCelular(nid, telefone.trim());
	 // inicilizando a conex�o
     conn = ConexaoODBC.getConnection();

     Statement st = conn.createStatement();
     
log.info(" A Preparar a Insercao de dados na tabela T_PACIENTE do MS ACESS.....");
	
	st.executeUpdate("INSERT INTO t_paciente "
			+ " (nid,hdd,nome,apelido, sexo,dataabertura,datainiciotarv,datanasc) "
			+ " VALUES "
			+ " (\'"
			+nid
			+ "\',\'"
			+ codigoUS()
			+ "\',\'"
			+ nome
			+ "\',\'"
			+ apelido
			+"\',\'"
			+ sexo
			+ "\',\'"
			+ new SimpleDateFormat("yyyy-MM-dd").format(dataabertura)
			+ "\',\'"
			+ new SimpleDateFormat("yyyy-MM-dd").format(datainiciotarv)
			+ "\',\'"
			+ new SimpleDateFormat("yyyy-MM-dd").format(datanasc)
			+ "\')");
st.executeQuery("INSERT INTO t_paciente "
		+ " (nid,hdd,nome,apelido, sexo,dataabertura,datainiciotarv,datanasc) "
		+ " VALUES "
		+ " (\'"
		+nid
		+ "\',\'"
		+ codigoUS()
		+ "\',\'"
		+ nome
		+ "\',\'"
		+ apelido
		+"\',\'"
		+ sexo
		+ "\',\'"
		+ new SimpleDateFormat("yyyy-MM-dd").format(dataabertura)
		+ "\',\'"
		+ new SimpleDateFormat("yyyy-MM-dd").format(datainiciotarv)
		+ "\',\'"
		+ new SimpleDateFormat("yyyy-MM-dd").format(datanasc)
		+ "\')");



} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	 log.error(e.toString());
	/*MessageBox conexaoACCESS = new MessageBox(new Shell(), SWT.ICON_ERROR
			| SWT.OK);
	conexaoACCESS.setText("Conex�o com Base de Dados SESP");
	conexaoACCESS
	.setMessage("O iDART n�o est� a se conectar com o SESP.\n Por favor verifique se os cabos da rede est�o ligados no seu \ncomputador ou se o computador com SESP est� ligado!\n Saia do iDART e verifique estes apectos depois volte a entrar,\n se o problema persistir, todos registos de FILAS e registo dos novos pacientes n�o ser�o actualizados no SESP!  \n N�o foi poss�vel actualizada a �ltima opera��o no SESP!");
	conexaoACCESS.open();
	e.printStackTrace();*/
}
 



}
//Devolve o codig

private static  String codigoUS()

{
	String codigoUS="";
	ResultSet rs=null;
	
	String q=" SELECT hdd from t_paciente";


	
	try {
		conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		rs=st.executeQuery(q);


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	if(rs!=null)
	{
		try {
			while(rs.next())
			{
				codigoUS=rs.getString("hdd");
			
			
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return codigoUS;


}

public ResultSet result_for_sync_dispense(){
	
	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		rs=st.executeQuery(" SELECT"
				+ " NID,"
				+ " MAX(datatarv) as ultimo_lev"
				+ " FROM"
				+ " t_tarv"
				+ "  GROUP BY nid"
				+ "");


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rs;
}

//insere levantamentos - Syncronizacao
 @SuppressWarnings("null")
public void syncdata_dispense(SyncLinha syncLinha) {
	// TODO Auto-generated method stub
	
	 System.out.println(" ******************************************************************************************");


	 try {
		 // inicilizando a conex�o
	     conn = ConexaoODBC.getConnection();

	     Statement st = conn.createStatement();
	     
	     if(!pesquisaRegime(syncLinha.getRegime()))
	     {
	    	 st.executeUpdate(" "
	    	 		+ "INSERT INTO "
	    	 		+ " t_regimeterapeutico "
	    	 		+ "("
	    	 		+ "codregime, "
	    	 		+ "idregime, "
	    	 		+ "linha, "
	    	 		+ "activo "
	    	 		+ ") "
	    	 		+ " VALUES "
	    	 		+ " ("
	    	 		+ "\'"+syncLinha.getRegime()+"\',"
	    	 				+ "\'"+syncLinha.getRegime()+"\',"
	    	 				+ "\'"+syncLinha.getLinha().charAt(0)+""+syncLinha.getLinha().charAt(1)+"\',true)");
	     }
	     
		 
	     if(existNid( syncLinha.getNid()))
	     {
	      String query=
		
				"INSERT INTO t_tarv ("
				+ "nid, "
				+ "datatarv, "
				+ "codregime, "
				+ "tipotarv,"
				+ " dataproxima, "
				+ "linhaTerapeutica"
				+ ") "
				+ "VALUES "
				+ "( "
				+ "\'"+ syncLinha.getNid()+ "\',"
						+ "\'"+syncLinha.getUltimo_lev() +"\',"
						+ "\'"+ syncLinha.getRegime()+"\',"
						+ "\'"+ syncLinha.getTipo_tarv()+"\',"
								+ " \'"+syncLinha.getDataproxima()+"\',"
						+ "\'"+syncLinha.getLinha() +"\'"
						 
						+ ")"
						 ;
	      st.executeUpdate(query);
	      System.out.println(query);
		
		//....
		
		
		System.out.println("nid "+syncLinha.getNid()+ "\n datatarv "+syncLinha.getUltimo_lev()+"\n dataproxima "+syncLinha.getDataproxima()+"\n \n \n");
		 
		//....
		if(syncLinha.getTipo_tarv().equals("Inicia"))
			updateTPacienteInicio(syncLinha.getNid(), syncLinha.getUltimo_lev(), syncLinha.getRegime(), syncLinha.getLinha());
			
		if(syncLinha.getTipo_tarv().equals("Transfer de"))
 
		updateTPacienteTransferidoDe(syncLinha.getNid(), syncLinha.getUltimo_lev(),syncLinha.getRegime(), syncLinha.getLinha());

	     } else System.out.println("   nid nao encontrado ------------------------------------------------------");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
	
}

//pesquisa regime
private boolean pesquisaRegime(String regime){
	boolean existe=false;
	
	int i=0;
	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		rs=st.executeQuery(" SELECT"
				+ "  * FROM t_regimeterapeutico"
				+ " WHERE codregime=\'"+regime
				+ "\'"
				+ "");


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		while (rs.next()) i++;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(i>0) existe=true;
	return existe;
}

private static boolean pesquisaRegime2(String regime){
	boolean existe=false;
	
	int i=0;
	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		rs=st.executeQuery(" SELECT"
				+ "  * FROM t_regimeterapeutico"
				+ " WHERE codregime=\'"+regime
				+ "\'"
				+ "");


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		while (rs.next()) i++;
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	if(i>0) existe=true;
	return existe;
}

public ResultSet result_for_sync_patients() {


	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		rs=st.executeQuery(" SELECT"
				+ " nid, "
				+ " nome,"
				+ " apelido, "
				+ " dataabertura,"
				+ "sexo, datanasc "
				+ " "
				+ " FROM"
				+ " t_paciente "
			
				+ "");


	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return rs;
	
}
//Insere o nr de celulaar em t_adulto
private void insereCelular (String nid, String celular){
	
	 try {
		 // inicilizando a conex�o
	     conn = ConexaoODBC.getConnection();

	     Statement st = conn.createStatement();
	     
 
	    
	    	 st.executeUpdate(" "
	    	 		+ "INSERT INTO "
	    	 		+ " t_adulto (nid, telefone) VALUES   "
	    	 		+ "(\'"
	    	 		+ nid +"\', \'"
	    	 		+ celular +"\'"
	    	 		+ ") "
	    	 	 
	    	 		+ " ");
	     
		
	     
	     
 

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
}

 private static void updateTPacienteInicio(String nid, String datatarv, String codregime, String linha){
		
	 try {
		 // inicilizando a conex�o
	     conn = ConexaoODBC.getConnection();

	     Statement st = conn.createStatement();
	     
	     if(!pesquisaRegime2(codregime))
	    	     {
	    	    	 st.executeUpdate(" "
	    	    	 		+ "INSERT INTO "
	    	    	 		+ " t_regimeterapeutico "
	    	    	 		+ "("
	    	    	 		+ "codregime, "
	    	    	 		+ "idregime, "
	    	    	 		+ "linha, "
	    	    	 		+ "activo "
	    	    	 		+ ") "
	    	    	 		+ " VALUES "
	    	    	 		+ " ("
	    	    	 		+ "\'"+codregime+"\',"
	    	    	 				+ "\'"+codregime+"\',"
	    	    	 				+ "\'"+linha.charAt(0)+""+linha.charAt(1)+"\',true)");
	    	     }
	    
	    	 st.executeUpdate(" "
	    	 		+ "UPDATE  "
	    	 		+ "t_paciente  "
	    	 		+ "SET emtarv=true, "
	    	 		+ "datainiciotarv=\'"+datatarv+"\',  "
	    	 				+ "codregime=\'"+codregime+"\'  "
	    	 						+ " WHERE nid=\'"+nid+"\' AND emtarv=false AND datainiciotarv IS NULL");
	     
		
	     
	     
 

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
 }
 
 private static void updateTPacienteTransferidoDe(String nid, String datatarv, String codregime, String linha){
		
	 try {
		 // inicilizando a conex�o
	     conn = ConexaoODBC.getConnection();

	     Statement st = conn.createStatement();
	     
	     if(!pesquisaRegime2(codregime))
	     {
	    	 st.executeUpdate(" "
	    	 		+ "INSERT INTO "
	    	 		+ " t_regimeterapeutico "
	    	 		+ "("
	    	 		+ "codregime, "
	    	 		+ "idregime, "
	    	 		+ "linha, "
	    	 		+ "activo "
	    	 		+ ") "
	    	 		+ " VALUES "
	    	 		+ " ("
	    	 		+ "\'"+codregime+"\',"
	    	 				+ "\'"+codregime+"\',"
	    	 				+ "\'"+linha.charAt(0)+""+linha.charAt(1)+"\',true)");
	     }
	    
	    	 st.executeUpdate(" "
	    	 		+ "UPDATE "
	    	 		+ "t_paciente "
	    	 		+ "SET "
	    	 		+ "emtarv=true, "
	    	 		+ "datainiciotarv=\'"+datatarv+"\', "
	    	 		+"codregime=\'"+codregime+"\', "
	    	 				+ "transfOutraUs= 0  WHERE nid=\'"+nid+"\' AND emtarv=false AND datainiciotarv IS NULL");
	     
		
	     
	    	
 

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	
	}
 }
 
 //Faz update t_paciente onde tipo tarv � inicio ou transferido de e..
 public void correccaoT_tarv_e_T_paciente(){
	Vector <SyncInicio> t_tarv=new Vector<SyncInicio>();
	ResultSet rs=null;
	try {
		try {
			conn = ConexaoODBC.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Statement st = conn.createStatement();
		rs=st.executeQuery(" SELECT nid, datatarv, codregime, tipotarv, linhaterapeutica "
				+ "   FROM t_tarv "
				+ " WHERE tipotarv=\'Inicia\' OR tipotarv= \'Transfer de\'"
				+ "");




		while (rs.next()) 
		{
			SyncInicio si=new SyncInicio(rs.getString("nid"),rs.getString("datatarv"),rs.getString("codregime"),rs.getString("tipotarv"),rs.getString("linhaterapeutica"));
			t_tarv.add(si);
		 
		}
		
		for(int i=0;i<t_tarv.size(); i++)
		{
			  if(!pesquisaRegime(t_tarv.get(i).getCodregime()))
			     {
			    	 st.executeUpdate(" "
			    	 		+ "INSERT INTO "
			    	 		+ " t_regimeterapeutico "
			    	 		+ "("
			    	 		+ "codregime, "
			    	 		+ "idregime, "
			    	 		+ "linha, "
			    	 		+ "activo "
			    	 		+ ") "
			    	 		+ " VALUES "
			    	 		+ " ("
			    	 		+ "\'"+t_tarv.get(i).getCodregime()+"\',"
			    	 				+ "\'"+t_tarv.get(i).getCodregime()+"\',"
			    	 				+ "\'"+t_tarv.get(i).getLinhaterapeutica().charAt(0)+""+t_tarv.get(i).getLinhaterapeutica().charAt(1)+"\',true)");
			     }
			  
			  
			if(t_tarv.get(i).getTipotarv().equals("Inicia"))
	    	 {
				st.executeUpdate(" "
	    	 
	    	 		+ "UPDATE "
	    	 		+ "t_paciente "
	    	 		+ "SET "
	    	 		+ "emtarv=true, "
	    	 		+ "datainiciotarv=\'"+t_tarv.get(i).getDatatarv()+"\', "
	    	 		+"codregime=\'"+t_tarv.get(i).getCodregime()+"\'  "
	    	 				+ " WHERE nid=\'"+t_tarv.get(i).getNid()+"\' AND datainiciotarv IS NULL");
				
	    	 }
			
			if(t_tarv.get(i).getTipotarv().equals("Transfer de"))
	    	 {
				st.executeUpdate(" "
	    	 
	 		+ "UPDATE "
	 		+ "t_paciente "
	 		+ "SET "
	 		+ "emtarv=true, "
	 		+ "datainiciotarv=\'"+t_tarv.get(i).getDatatarv()+"\', "
	 		+"codregime=\'"+t_tarv.get(i).getCodregime()+"\', "
	 				+ " transfOutraUs= 0  WHERE nid=\'"+t_tarv.get(i).getNid()+"\' AND datainiciotarv IS NULL ");

	    	 		 
				
	    	 }
	    	 
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
 
 private class SyncInicio{
	 private String nid;
	 private String datatarv;
	 private String  codregime;
	 private String tipotarv;
	 private String linhaterapeutica;
	 
public SyncInicio(String nid, String datatarv, String codregime, String tipotarv, String linhaterapeutica) {
		super();
		this.nid = nid;
		this.datatarv = datatarv;
		this.codregime = codregime;
		this.tipotarv = tipotarv;
			this.linhaterapeutica=linhaterapeutica	;
	}
	public SyncInicio(  ) {
		super();
		 
	}
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getDatatarv() {
		return datatarv;
	}
	public void setDatatarv(String datatarv) {
		this.datatarv = datatarv;
	}
	public String getCodregime() {
		return codregime;
	}
	public void setCodregime(String codregime) {
		this.codregime = codregime;
	}
	public String getTipotarv() {
		return tipotarv;
	}
	
	public void setTipotarv(String tipotarv) {
		this.tipotarv = tipotarv;
	}
	public String getLinhaterapeutica() {
		return linhaterapeutica;
	}
	public void setLinhaterapeutica(String linhaterapeutica) {
		this.linhaterapeutica = linhaterapeutica;
	}
	 
	 
 }

public boolean jaDispensado(String patientId, String resultdatatarv) {
	boolean ja_dispensado=false;
	int linhas=0;

 
	ResultSet rs=null;
 System.out.println("++++++++"+resultdatatarv);
		
			try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery(" SELECT * "
						+ "   FROM t_tarv "
						+ " WHERE nid=\'"+patientId+"\' AND datatarv=#"+resultdatatarv+"#"
						+ "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 linhas++;
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(linhas!=0)
	ja_dispensado=true;
		return ja_dispensado;
}

//este metodo apaga todas dispensas cuja data da dispensa � maior ou igual a data da proxima consulta a partir de maio
public void clean_delete() {
	// TODO Auto-generated method stub
	  
	try {
		Connection conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		st.executeQuery("DELETE FROM t_tarv WHERE datatarv >= dataproxima and datatarv > #2015-04-30#");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
      
}

//Limpa as despensas repetidas a partir de Maio 2015
public void clean_delete_mais_de_uma_despensa() {
	// TODO Auto-generated method stub
	  
	try {
		Connection conn = ConexaoODBC.getConnection();
		Statement st = conn.createStatement();
		st.executeQuery("DELETE FROM T_TARV AS T1 WHERE Exists (SELECT * FROM t_tarv as t2 WHERE t1.nid=t2.nid and t1.datatarv=t2.datatarv and t1.idtarv<t2.idtarv AND t1.datatarv > #2015-04-30#)");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
}

//N� de Activos em TARV Linha J do resumo mensal da US
public int linhaJResumoMensal(String startDate, String endDate){
	
	
int total=0;
total=cumulativoTarv(startDate)+novosTarv(startDate,endDate) -(suspenderTarv(endDate)+abandonos(endDate)+morte(endDate)+transferidoPara(endDate));
return total;
	
	
	
}
//Cumulativo TARV
public int cumulativoTarv(String startDate){
	
	
	int total=0;
	
	ResultSet rs=null;
 	try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery("SELECT DISTINCT "
						+ ""
						+ " NID FROM T_PACIENTE WHERE "
						+ ""
						+ "DATATARV2 < #"
						+ startDate 
						+"# AND nlivrotarv IS NOT NULL");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 total++;
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	return total;
	
	
	
}
//Novas entradas TARV
public int novosTarv(String startDate, String endDate){
	
	
	int total=0;
	ResultSet rs=null;
 	try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery("SELECT DISTINCT NID "
						+ ""
						+ "FROM T_PACIENTE WHERE "
						+ " DATATARV2 BETWEEN #"
						+  startDate 
						+ "# and  #"
						+ endDate 
						+ "# AND "
						+ " nlivrotarv IS NOT NULL");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 total++;
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	return total;
	
	
	
}

//Cumulativo suspender tarv
public int suspenderTarv( String endDate){
	
	
	int total=0;
	
	ResultSet rs=null;
 	try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery(" SELECT "
						+ " DISTINCT "
						+ " NID "
						+ "FROM T_PACIENTE "
						+ "WHERE codestado=\'Suspender Tarv\' "
						+ "AND nlivrotarv IS NOT NULL AND datasaidatarv < #"+endDate+"#");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 total++;
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	return total;
	
	
	
}
//Cumulativo transferidos para
public int transferidoPara( String endDate){
	
	
	int total=0;
	
	ResultSet rs=null;
 	try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery(" SELECT "
						+ " DISTINCT "
						+ " NID "
						+ "FROM T_PACIENTE "
						+ "WHERE codestado=\'Transferido para\' "
						+ "AND nlivrotarv IS NOT NULL AND datasaidatarv < #"+endDate+"#");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 total++;
			 
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	return total;
	
	
	
}
//Cumulativo abandonos
public int abandonos( String endDate){
	
	
	int total=0;
	
	
	ResultSet rs=null;
	 	try {
					conn = ConexaoODBC.getConnection();
					Statement st;
					 
					st = conn.createStatement();
				 
			 
					rs=st.executeQuery(" SELECT "
							+ " DISTINCT "
							+ " NID "
							+ "FROM T_PACIENTE "
							+ "WHERE codestado=\'Abandono\' "
							+ "AND nlivrotarv IS NOT NULL AND datasaidatarv < #"+endDate+"#");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
				 



			try {
				if(rs!=null)
				while (rs.next()) 
				{
					 total++;
				 
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
	
	return total;
	
	
	
}


//Cumulativo obitos
public int morte(String endDate){
	
	
	int total=0;
	
	
	
	
	ResultSet rs=null;
	 	try {
					conn = ConexaoODBC.getConnection();
					Statement st;
					 
					st = conn.createStatement();
				 
			 
					rs=st.executeQuery(" SELECT "
							+ " DISTINCT "
							+ " NID "
							+ "FROM T_PACIENTE "
							+ "WHERE codestado=\'Morte\' "
							+ "AND nlivrotarv IS NOT NULL AND datasaidatarv < #"+endDate+"#");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
				 



			try {
				if(rs!=null)
				while (rs.next()) 
				{
					 total++;
				 
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
	return total;
	
	
	
}

//Contactos
public Vector<Contacto> contactos(){
	
	
 
	
	
	Vector<Contacto> v =new Vector<Contacto> ();
	
	ResultSet rs=null;
	 	try {
					conn = ConexaoODBC.getConnection();
					Statement st;
					 
					st = conn.createStatement();
				 
			 
					rs=st.executeQuery(" SELECT DISTINCT nid , telefone from t_adulto where telefone IS NOT NULL AND nid IS NOT NULL"
							);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 
				 



			try {
				if(rs!=null)
				while (rs.next())
				{ String nid=rs.getString("nid"); String cell=rs.getString("telefone");
					if(cell.length()>=9)
					{ Contacto c=new Contacto(nid,cell);
				 v.add(c);
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			
	return v;
	
	
	
}

//Enderecos
public Vector<Endereco> enderecos(){
	
	

	
	
	Vector<Endereco> e =new Vector<Endereco> ();
	
	ResultSet rs=null;
	 	try {
					conn = ConexaoODBC.getConnection();
					Statement st;
					 
					st = conn.createStatement();
				 
			 
					rs=st.executeQuery(" SELECT  nid , celula, avenida from t_paciente where nid IS NOT NULL and (avenida is not null or celula is not null)"
							);
				} catch (Exception eX) {
					// TODO Auto-generated catch block
					eX.printStackTrace();
				}
			 
				 



			try {
				
				while (rs.next())
					
				{
				String nid=rs.getString("nid"); String celula=rs.getString("celula");  String avenida=rs.getString("avenida");
				
				System.out.println(nid+" "+celula+" "+avenida);
					  Endereco c=new Endereco(nid,celula,avenida);
				 e.add(c);
					 
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} 
			
			
	return e;
	
	
	
}

//pacientes com consulta expirada
public String expirouConsulta(String nid)
{
	
String data="";
	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st;
		 if(conn!=null)
		 {	st = conn.createStatement();
	 
 
		rs=st.executeQuery("select nid, max(dataproximaconsulta) as latest from t_seguimento where  nid =\'"+nid+"\' and dataproximaconsulta < #"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"#and dataproximaconsulta is not null group by nid "
				);
	}
	} catch (Exception eX) {
		// TODO Auto-generated catch block
		eX.printStackTrace();
	}
 
	 



try {
	if(rs!=null)
	
	while (rs.next())
		
	{
	data=rs.getString("latest");
		 
	}
} catch (SQLException ex) {
	// TODO Auto-generated catch block
	ex.printStackTrace();
} 
	return data;
	
	
}

//data consulta 
public String dataConsulta(String nid)
{
	
String data="";
	ResultSet rs=null;
	try {
		conn = ConexaoODBC.getConnection();
		Statement st;
		 if(conn!=null)
		 {
			 
		 
		st = conn.createStatement();
	 

		rs=st.executeQuery("select nid, max(dataproximaconsulta) as latest from t_seguimento where  nid =\'"+nid+"\' and dataproximaconsulta is not null group by nid "
				);
	
		 }
		 
	} catch (Exception eX) {
		// TODO Auto-generated catch block
		eX.printStackTrace();
	}

	 



try {
	if(rs!=null)
	while (rs.next())
		
	{
	data=rs.getString("latest");
		 
	}
} catch (SQLException ex) {
	// TODO Auto-generated catch block
	ex.printStackTrace();
} 
	return data;
	
	
}
//confidentes
public Vector<Confidente> confidentes() {

	
	

	
	
	Vector<Confidente> e =new Vector<Confidente> ();
	
	ResultSet rs=null;
	 	try {
					conn = ConexaoODBC.getConnection();
					Statement st;
					 
					st = conn.createStatement();
				 
			 
					rs=st.executeQuery(" SELECT  nid , nome, apelido, telefone from t_contacto where nid IS NOT NULL"
							);
				} catch (Exception eX) {
					// TODO Auto-generated catch block
					eX.printStackTrace();
				}
			 
				 



			try {
				
				while (rs.next())
					
				{
				String nid=rs.getString("nid"); String nome=rs.getString("nome")+""+rs.getString("apelido");  String telefone=rs.getString("telefone");
				 
					  Confidente  c=new Confidente(nid,nome,telefone); 
					
				 e.add(c);
					 
				}
			} catch (SQLException ex) {
				// TODO Auto-generated catch block
				ex.printStackTrace();
			} 
			
			
	return e;

}



public static boolean jaTemFilaInicio(String patientId) {
	boolean ja_tem_fila_inicio=false;
	 
 
	ResultSet rs=null;
	
			try {
				conn = ConexaoODBC.getConnection();
				Statement st;
				 
				st = conn.createStatement();
			 
		 
				rs=st.executeQuery(" SELECT tipotarv "
						+ "   FROM t_tarv "
						+ " WHERE nid=\'"+patientId+"\' "
						+ "");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
			 



		try {
			if(rs!=null)
			while (rs.next()) 
			{
				 
				if(rs.getString("tipotarv").contains(""
						+ "nicia")) {
					ja_tem_fila_inicio=true; break;
				}
				System.out.println(">Tipo tarv a ser verificado no mmetodo jaTemFilaInicio()");
				 
				}
				
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return ja_tem_fila_inicio;
}
}