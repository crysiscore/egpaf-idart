package model.manager.reports;

import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import model.manager.excel.conversion.exceptions.ReportException;
import org.celllife.idart.commonobjects.LocalObjects;
import org.celllife.idart.database.dao.ConexaoJDBC;
import org.eclipse.swt.widgets.Shell;


public class HHistoricoLevantamentos extends AbstractJasperReport {
	
	
	private final Date theEndDate;
	private Date theStartDate;
	private boolean inicio;
	private boolean manutencao;
	private boolean alteraccao;
	


	public HHistoricoLevantamentos(Shell parent, Date theStartDate,
			Date theEndDate, boolean inicio,boolean manutencao,boolean alteraccao) {
		super(parent);
		
		this.theStartDate=theStartDate;
		this.theEndDate = theEndDate;
		this.alteraccao=alteraccao;
		this.inicio=inicio;
		this.manutencao=manutencao;
	}

	@Override
	protected void generateData() throws ReportException {
	}

	@Override
	protected Map<String, Object> getParameterMap() throws ReportException {




		
		// Set the parameters for the report
				Map<String, Object> map = new HashMap<String, Object>();
				

			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");

		map.put("date", theStartDate);
		map.put("dateFormat", dateFormat.format(theStartDate));
		map.put("monthStart", dateFormat.format(theStartDate));
		//calStart.add(Calendar.MONTH, 1);


		map.put("monthEnd", dateFormat.format(theEndDate));
		map.put("dateEnd", theEndDate);
		
		map.put("mes", mesPortugues(theStartDate));
		map.put("mes2",mesPortugues(theEndDate));

ConexaoJDBC con=new ConexaoJDBC();
		String query= con.getQueryHistoricoLevantamentos(this.inicio, this.manutencao, this.alteraccao,dateFormat.format(theStartDate),dateFormat.format(theEndDate));
		


		
		
		map.put("query",query);
	
		map.put("path", getReportPath());
		map.put("provincia","Zambézia");
		map.put("distrito","Nicoadala");
		

		map.put("facilityName", LocalObjects.currentClinic.getClinicName());

		return map;
	}


	@Override
	protected String getReportFileName() {
		return "HistoricoLevantamentos";
	}
	


private String mesPortugues(Date data )
{
	
	
	String mes="";
	
	 GregorianCalendar calendar = new GregorianCalendar();
	 
	 calendar.setTime(data);
	 
	@SuppressWarnings("static-access")
	int mesint =calendar.MONTH;
	
	switch(mesint)
	{
	case 1: mes="Janeiro";break;
	case 2: mes="Fevereiro";break;
	case 3: mes="Março";break;
	case 4: mes="Abril";break;
	case 5: mes="Maio";break;
	case 6: mes="Junho";break;
	case 7: mes="Julho";break;
	case 8: mes="Agosto";break;
	case 9: mes="Setembro";break;
	case 10: mes="Outubro";break;
	case 11: mes="Novembro";break;
	case 12: mes="Dezembro";break;
	default:mes="";break;


	
	}
	
	return mes;
}
}
