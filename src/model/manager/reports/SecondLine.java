/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.manager.reports;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.manager.excel.conversion.exceptions.ReportException;
import org.celllife.idart.commonobjects.LocalObjects;
import org.celllife.idart.database.dao.ConexaoJDBC;
import org.celllife.idart.database.dao.ConexaoODBC;
import org.celllife.idart.database.hibernate.StockCenter;
import org.celllife.idart.database.hibernate.User;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * @author agnaldo
 */
public class SecondLine extends AbstractJasperReport {

    private final  Date theEndDate;
    private final Date theStartDate;


    public SecondLine(Shell parent ,Date theStartDate,
            Date theEndDate) {
        super(parent);
        this.theStartDate = theStartDate;
        this.theEndDate = theEndDate;

    }

    @Override
    protected void generateData() throws ReportException {
      
    }

    @Override
    protected String getReportFileName() {
        return "pacientesemsegundalinha";
    }

    @Override
    protected Map<String, Object> getParameterMap() throws ReportException {
        // Set the parameters for the report
        Map<String, Object> map = new HashMap<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd"); // TODO Auto-generated catch block
        map.put("path", getReportPath());
        map.put("facilityName", LocalObjects.currentClinic.getClinicName());
        map.put("date",  theStartDate);
        map.put("dataFinal", theEndDate);
        
        // map.put("dataelaboracao", new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

        return map;
    }

}
