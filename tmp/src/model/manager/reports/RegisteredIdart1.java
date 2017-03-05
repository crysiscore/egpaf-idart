package model.manager.reports;




import java.text.SimpleDateFormat;


import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import model.manager.excel.conversion.exceptions.ReportException;












import org.celllife.idart.commonobjects.LocalObjects;


import org.eclipse.swt.widgets.Shell;


public class RegisteredIdart1 extends AbstractJasperReport {
	
	
	private final Date theEndDate;
	private Date theStartDate;
	


	public RegisteredIdart1(Shell parent, Date theStartDate,
			Date theEndDate) {
		super(parent);
		
		this.theStartDate=theStartDate;
		this.theEndDate = theEndDate;
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

		System.out.print("mes 1"+mesPortugues(theStartDate));
		System.out.print("Mes 2"+mesPortugues(theEndDate));

		map.put("path", getReportPath());

		map.put("facilityName", LocalObjects.currentClinic.getClinicName());



 


		
		return map;
	}


	@Override
	protected String getReportFileName() {
		return "RegisteredIdart";
	}
	


private String mesPortugues(Date data )
{
	
	
	
	String mes="";
	
	Calendar calendar = new GregorianCalendar();
	 Date trialTime = data;
	 calendar.setTime(trialTime);
	 

	int mesint =calendar.get(Calendar.MONTH);
	System.out.println(mesint);
	switch(mesint)
	{
	case 0: mes="Janeiro";break;
	case 1: mes="Fevereiro";break;
	case 2: mes="Mar�o";break;
	case 3: mes="Abril";break;
	case 4: mes="Maio";break;
	case 5: mes="Junho";break;
	case 6: mes="Julho";break;
	case 7: mes="Agosto";break;
	case 8: mes="Setembro";break;
	case 9: mes="Outubro";break;
	case 10: mes="Novembro";break;
	case 11: mes="Dezembro";break;
	default:mes="";break;
	
	}
	
	return mes;
}
}
