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
public class LinhaTerapeutica extends AbstractJasperReport {

   


    public LinhaTerapeutica(Shell parent) {
        super(parent);
    

    }

    @Override
    protected void generateData() throws ReportException {
      
    }

    @Override
    protected String getReportFileName() {
        return "NovasLinhasTerapeuticas";
    }

    @Override
    protected Map<String, Object> getParameterMap() throws ReportException {
        ConexaoJDBC conn = new ConexaoJDBC();

        // Set the parameters for the report
        Map<String, Object> map = new HashMap<>();

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd");
            map.put("facilityName", LocalObjects.currentClinic.getClinicName());

        return map;
    }

}
