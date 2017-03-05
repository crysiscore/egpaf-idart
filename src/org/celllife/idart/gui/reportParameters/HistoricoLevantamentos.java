/*
 * iDART: The Intelligent Dispensing of Antiretroviral Treatment
 * Copyright (C) 2006 Cell-Life
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License version
 * 2 for more details.
 *
 * You should have received a copy of the GNU General Public License version 2
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.celllife.idart.gui.reportParameters;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import model.manager.reports.HHistoricoLevantamentos;

import org.apache.log4j.Logger;
import org.celllife.idart.gui.platform.GenericReportGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.misc.iDARTUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.vafada.swtcalendar.SWTCalendar;
import org.vafada.swtcalendar.SWTCalendarEvent;
import org.vafada.swtcalendar.SWTCalendarListener;

/**
 */
public class HistoricoLevantamentos extends GenericReportGui {
	
	
	private Group grpDateRange;
	
	private Group grpTipoTarv;


	private SWTCalendar calendarStart;

	private SWTCalendar calendarEnd;







	private Button chkBtnInicio;

	private Button chkBtnManutencao;
	
	private Button chkBtnAlteraccao;


	/**
	 * Constructor
	 *
	 * @param parent
	 *            Shell
	 * @param activate
	 *            boolean
	 */
	public HistoricoLevantamentos(Shell parent, boolean activate) {
		super(parent, REPORTTYPE_MIA, activate);
	}

	/**
	 * This method initializes newMonthlyStockOverview
	 */
	@Override
	protected void createShell() {
		Rectangle bounds = new Rectangle(100, 50, 600, 510);
		buildShell(REPORT_LEVANTAMENTOS_ARV, bounds);
		// create the composites
		createMyGroups();
	}

	private void createMyGroups() {


		createGrpDateInfo();
	}

	/**
	 * This method initializes compHeader
	 *
	 */
	@Override
	protected void createCompHeader() {
		iDartImage icoImage = iDartImage.REPORT_STOCKCONTROLPERCLINIC;
		buildCompdHeader(REPORT_LEVANTAMENTOS_ARV, icoImage);
	}






	/**
	 * This method initializes grpDateInfo
	 *
	 */
	private void createGrpDateInfo() {

		
		
		createGrpDateRange();

	}

	/**
	 * This method initializes compButtons
	 *
	 */
	@Override
	protected void createCompButtons() {
	}

	@SuppressWarnings("unused")
	@Override
	protected void cmdViewReportWidgetSelected() {

		if (iDARTUtil.before(calendarEnd.getCalendar().getTime(), calendarStart.getCalendar().getTime())){
			showMessage(MessageDialog.ERROR, "End date before start date",
					"You have selected an end date that is before the start date.\nPlease select an end date after the start date.");
			return;
		}
		
		if(chkBtnInicio.getSelection()==false && chkBtnManutencao.getSelection()==false && chkBtnAlteraccao.getSelection()==false)
		{
			
			showMessage(MessageDialog.ERROR, "Seleccionar Tipo Tarv",
					"Seleccione pelo menos um tipo TARV.");
			return;
			
		}

		else {
			try {
				
			
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
				 


				
				
				
				Date theStartDate = calendarStart.getCalendar().getTime(); 
			
				Date theEndDate=  calendarEnd.getCalendar().getTime(); 
				
			
				
				

				
				//theStartDate = sdf.parse(strTheDate);
				
				 
				
			

	
				
				HHistoricoLevantamentos report = new HHistoricoLevantamentos(
						getShell(), theStartDate, theEndDate,chkBtnInicio.getSelection(),chkBtnManutencao.getSelection(),chkBtnAlteraccao.getSelection());
				viewReport(report);
			} catch (Exception e) {
				getLog()
				.error(
						"Exception while running Historico levantamento report",
						e);
			}
		}

	}

	/**
	 * This method is called when the user presses "Close" button
	 *
	 */
	@Override
	protected void cmdCloseWidgetSelected() {
		cmdCloseSelected();
	}

	/**
	 * Method getMonthName.
	 *
	 * @param intMonth
	 *            int
	 * @return String
	 */



	@Override
	protected void setLogger() {
		setLog(Logger.getLogger(this.getClass()));
	}

	
	private void createGrpDateRange() {
		
		//Group tipo tarv
		grpTipoTarv = new Group(getShell(), SWT.NONE);
		grpTipoTarv.setText("Tipo Tarv:");
		grpTipoTarv.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpTipoTarv.setBounds(new Rectangle(55, 90, 520, 50));
		grpTipoTarv.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		//chk button Inicio
		chkBtnInicio= new Button(grpTipoTarv, SWT.CHECK);
		chkBtnInicio.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		chkBtnInicio.setBounds(new Rectangle(50, 20, 100, 20));
		chkBtnInicio.setText("Início");
		chkBtnInicio.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		chkBtnInicio.setSelection(false);
		
		//chk button  Manter
		chkBtnManutencao= new Button(grpTipoTarv, SWT.CHECK);
		chkBtnManutencao.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		chkBtnManutencao.setBounds(new Rectangle(350, 20, 100, 20));
		chkBtnManutencao.setText("Manutenção");
		chkBtnManutencao.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		chkBtnManutencao.setSelection(false);
		
		
		
	
		
		//chk button Alterar
		chkBtnAlteraccao= new Button(grpTipoTarv, SWT.CHECK);
		chkBtnAlteraccao.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		chkBtnAlteraccao.setBounds(new Rectangle(200, 20, 100, 20));
		chkBtnAlteraccao.setText("Alteração");
		chkBtnAlteraccao.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		chkBtnAlteraccao.setSelection(false);
		
		
		
		grpDateRange = new Group(getShell(), SWT.NONE);
		grpDateRange.setText("Período:");
		grpDateRange.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpDateRange.setBounds(new Rectangle(55, 160, 520, 201));
		grpDateRange.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		Label lblStartDate = new Label(grpDateRange, SWT.CENTER | SWT.BORDER);
		lblStartDate.setBounds(new org.eclipse.swt.graphics.Rectangle(40, 30,
				180, 20));
		lblStartDate.setText("Data Início:");
		lblStartDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		Label lblEndDate = new Label(grpDateRange, SWT.CENTER | SWT.BORDER);
		lblEndDate.setBounds(new org.eclipse.swt.graphics.Rectangle(300, 30,
				180, 20));
		lblEndDate.setText("Data Fim:");
		lblEndDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		calendarStart = new SWTCalendar(grpDateRange);
		calendarStart.setBounds(20, 55, 220, 140);

		calendarEnd = new SWTCalendar(grpDateRange);
		calendarEnd.setBounds(280, 55, 220, 140);
		calendarEnd.addSWTCalendarListener(new SWTCalendarListener() {
			@Override
			public void dateChanged(SWTCalendarEvent calendarEvent) {
				Date date = calendarEvent.getCalendar().getTime();
				
				
			}
		});
	}
	
	/**
	 * Method getCalendarEnd.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendarEnd() {
		return calendarEnd.getCalendar();
	}
	
	/**
	 * Method setEndDate.
	 * 
	 * @param date
	 *            Date
	 */
	public void setEndtDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarEnd.setCalendar(calendar);
	}
	
	/**
	 * Method addEndDateChangedListener.
	 * 
	 * @param listener
	 *            SWTCalendarListener
	 */
	public void addEndDateChangedListener(SWTCalendarListener listener) {

		calendarEnd.addSWTCalendarListener(listener);
	}
	
	/**
	 * Method getCalendarStart.
	 * 
	 * @return Calendar
	 */
	public Calendar getCalendarStart() {
		return calendarStart.getCalendar();
	}
	
	/**
	 * Method setStartDate.
	 * 
	 * @param date
	 *            Date
	 */
	public void setStartDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendarStart.setCalendar(calendar);
	}
	
	/**
	 * Method addStartDateChangedListener.
	 * 
	 * @param listener
	 *            SWTCalendarListener
	 */
	public void addStartDateChangedListener(SWTCalendarListener listener) {

		calendarStart.addSWTCalendarListener(listener);
	}
}
