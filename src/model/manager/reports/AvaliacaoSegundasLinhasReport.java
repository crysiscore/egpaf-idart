package model.manager.reports;

import java.util.HashMap;
import java.util.Map;
import model.manager.AdministrationManager;

import model.manager.excel.conversion.exceptions.ReportException;

import org.celllife.idart.commonobjects.LocalObjects;
import org.celllife.idart.database.hibernate.Clinic;
import org.celllife.idart.database.hibernate.StockCenter;
import org.eclipse.swt.widgets.Shell;

public class AvaliacaoSegundasLinhasReport extends AbstractJasperReport {

        private final String clinicName;
	private final String month;
	private final StockCenter stockCenter;
	private final String year;

	public AvaliacaoSegundasLinhasReport(Shell parent, String clinicName,
                        String Month, String Year,
			StockCenter stockCenter) {
		super(parent);
		month = Month;
		year = Year;
		this.stockCenter = stockCenter;
                this.clinicName=clinicName;
	}

	@Override
	protected void generateData() throws ReportException {
	}

	@Override
	protected Map<String, Object> getParameterMap() throws ReportException {
		int MonthInt = 0;
		if (month.equals("Janeiro")) {
			MonthInt = 1;
		} else if (month.equals("Fevereiro")) {
			MonthInt = 2;
		} else if (month.equals("Março")) {
			MonthInt = 3;
		} else if (month.equals("Abril")) {
			MonthInt = 4;
		} else if (month.equals("Maio")) {
			MonthInt = 5;
		} else if (month.equals("Junho")) {
			MonthInt = 6;
		} else if (month.equals("Julho")) {
			MonthInt = 7;
		} else if (month.equals("Agosto")) {
			MonthInt = 8;
		} else if (month.equals("Setembro")) {
			MonthInt = 9;
		} else if (month.equals("Outubro")) {
			MonthInt = 10;
		} else if (month.equals("Novembro")) {
			MonthInt = 11;
		} else if (month.equals("Dezembro")) {
			MonthInt = 12;
		}
		String startDayStr;
		if (MonthInt > 9) {
			startDayStr = year + "-" + MonthInt + "-20 00:00:00";
		} else {
			startDayStr = year + "-0" + MonthInt + "-20 00:00:00";
		}

		java.sql.Timestamp theDate = java.sql.Timestamp.valueOf(startDayStr);
                Clinic c = AdministrationManager.getClinic(hSession, clinicName);
		// Set the parameters for the report
		Map<String, Object> map = new HashMap<String, Object>();
                map.put("path", getReportPath());
		map.put("clinic", clinicName);
		map.put("clinicid", new Integer(c.getId()));
		map.put("stockCenterId", stockCenter.getId());
		map.put("stockCenterName", stockCenter.getStockCenterName());
		map.put("date", theDate);
		map.put("facilityName", LocalObjects.pharmacy.getPharmacyName());
		map.put("pharmacist1", LocalObjects.pharmacy.getPharmacist());
		map.put("pharmacist2", LocalObjects.pharmacy.getAssistantPharmacist());
		return map;
	}

	@Override
	protected String getReportFileName() {
		return "pacientesemsegundalinhaAvaliacao";
	}

}
