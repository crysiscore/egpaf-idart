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

package org.celllife.idart.gui.stockArrives;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.manager.StockManager;
import model.manager.reports.StockReceiptReport;

import org.apache.log4j.Logger;
import org.celllife.idart.database.hibernate.Stock;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.misc.iDARTChangeListener;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;

/**
 */
public class StockArrives extends GenericFormGui implements iDARTChangeListener{

	private Table tblDrugs;

	private Button btnAddStock;

	private Button btnRemoveRow;

	private Group grpStockTable;

	private boolean clearAfterSave = false;

	/**
	 * Constructor for StockArrives.
	 * 
	 * @param parent
	 *            Shell
	 */
	public StockArrives(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes newStockArrives
	 */
	@Override
	protected void createShell() {
		String shellTxt = "Entradas de Stock na Farm�cia";
		Rectangle bounds = new Rectangle(0, 100, 900, 700);
		buildShell(shellTxt, bounds);
	}

	/**
	 * This method initializes compHeader
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = "Entradas de Stock na Farm�cia";
		iDartImage icoImage = iDartImage.PACKAGESARRIVE;
		buildCompHeader(headerTxt, icoImage);
	}

	/**
	 * This method initializes composite
	 * 
	 */
	@Override
	protected void createContents() {

		grpStockTable = new Group(getShell(), SWT.BORDER);
		grpStockTable.setBounds(new Rectangle(5, 137, 882, 369));

		createTblDrugs();
		btnAddStock = new Button(grpStockTable, SWT.NONE);
		btnAddStock.setBounds(new org.eclipse.swt.graphics.Rectangle(245, 320,
				160, 30));
		btnAddStock.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnAddStock.setText("Adicionar Stock");
		btnAddStock
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdAddStockWidgetSelected();
			}
		});
		btnAddStock.setFocus();

		btnRemoveRow = new Button(grpStockTable, SWT.NONE);
		btnRemoveRow.setBounds(new org.eclipse.swt.graphics.Rectangle(465, 320,
				160, 30));
		btnRemoveRow.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnRemoveRow.setText("Remover Lote Selecciodo");
		btnRemoveRow
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdRemoveRowWidgetSelected();
			}
		});
	}

	/**
	 * This method initializes tblDrugs
	 * 
	 */
	private void createTblDrugs() {

		tblDrugs = new Table(grpStockTable, SWT.FULL_SELECTION);
		tblDrugs.setHeaderVisible(true);
		tblDrugs.setLinesVisible(true);
		tblDrugs.setBounds(new Rectangle(5, 10, 870, 300));
		tblDrugs.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		TableColumn tblClmNumber = new TableColumn(tblDrugs, SWT.NONE);
		tblClmNumber.setWidth(25);
		tblClmNumber.setText("No");

		TableColumn tblClmDrugName = new TableColumn(tblDrugs, SWT.NONE);
		tblClmDrugName.setWidth(194);
		tblClmDrugName.setText("Nome do Medicamento");

		TableColumn tblClmPackSize = new TableColumn(tblDrugs, SWT.NONE);
		tblClmPackSize.setWidth(56);
		tblClmPackSize.setText("Tamanho do Pacote");

		TableColumn tblClmDescription = new TableColumn(tblDrugs, SWT.NONE);
		tblClmDescription.setWidth(70);
		tblClmDescription.setText("Forma");

		TableColumn tblClmUnitsReceived = new TableColumn(tblDrugs, SWT.NONE);
		tblClmUnitsReceived.setWidth(60);
		tblClmUnitsReceived.setText("Unidades Recebidas");

		TableColumn tblClmPharmacy = new TableColumn(tblDrugs, SWT.NONE);
		tblClmPharmacy.setWidth(105);
		tblClmPharmacy.setText("Farm�cia");

		TableColumn tblClmManufacturer = new TableColumn(tblDrugs, SWT.NONE);
		tblClmManufacturer.setWidth(110);
		tblClmManufacturer.setText("Fabricante");

		TableColumn tblClmBatchNumber = new TableColumn(tblDrugs, SWT.NONE);
		tblClmBatchNumber.setWidth(60);
		tblClmBatchNumber.setText("No do Lote");

		TableColumn tblClmExpiryMonth = new TableColumn(tblDrugs, SWT.NONE);
		tblClmExpiryMonth.setWidth(66);
		tblClmExpiryMonth.setText("Expira��o");

		TableColumn tblClmShelfNo = new TableColumn(tblDrugs, SWT.NONE);
		tblClmShelfNo.setWidth(40);
		tblClmShelfNo.setText("Prateleira");

		TableColumn tblClmUnitPrice = new TableColumn(tblDrugs, SWT.NONE);
		tblClmUnitPrice.setWidth(60);
		tblClmUnitPrice.setText("Pre�o/Unidade");

		TableColumn tblClmCaptureDate = new TableColumn(tblDrugs, SWT.NONE);
		tblClmCaptureDate.setWidth(0);

	}

	/**
	 * This method initializes compButtons
	 * 
	 */
	@Override
	protected void createCompButtons() {
		buildCompButtons();
		btnSave.setEnabled(false);
	}

	private void cmdAddStockWidgetSelected() {
		try {
			// Add a new table item
			TableItem ti = new TableItem(tblDrugs, SWT.NONE);
			DeliveryDetails myDeliveryDetails = new DeliveryDetails(this, ti);
			myDeliveryDetails.addChangeListener(this);
			ti.setText(0, (Integer.toString(tblDrugs.getItemCount())));
			myDeliveryDetails.setTableItem(ti);

			btnSave.setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get the current unsaved stock batches on this screen. Used by delivery
	 * details screen to prepopulaet last unit price
	 * 
	 * @return
	 */
	public List<Stock> getStockBatches() {
		List<Stock> stockList = new ArrayList<Stock>();
		if (tblDrugs != null) {
			for (TableItem ti : tblDrugs.getItems()) {
				if (ti.getData() != null) {
					stockList.add((Stock) ti.getData());
				}
			}

		}

		return stockList;

	}

	private void cmdRemoveRowWidgetSelected() {
		try {
			if (tblDrugs.getSelection().length == 0) {
				MessageBox missing = new MessageBox(getShell(), SWT.ICON_ERROR
						| SWT.OK);
				missing.setText("Nenhum lote seleccionado");
				missing
				.setMessage(""
						+ ""
						+ "Por favor seleccione a linha na tabela que cont�m o lote que n�o quer salvar.");
				missing.open();
			} else {
				int index = tblDrugs.getSelectionIndex();
				tblDrugs.remove(index);
				for (int i = index; i < tblDrugs.getItemCount(); i++) {
					TableItem oti = tblDrugs.getItem(i);
					int number = Integer.parseInt(oti.getText(0));
					number--;
					oti.setText(0, "" + number);
				}

				if(tblDrugs.getItemCount() == 0) {
					btnSave.setEnabled(false);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void cmdSaveWidgetSelected() {
		Transaction tx = null;
		try {
			tx = getHSession().beginTransaction();
			for (TableItem ti : tblDrugs.getItems()) {
				if (ti.getData() != null) {
					Stock newStock = (Stock) ti.getData();
					getHSession().save(newStock);
					StockManager.updateStockLevel(getHSession(), newStock);
				}
			}

			getHSession().flush();
			tx.commit();
			MessageBox m = new MessageBox(getShell(), SWT.ICON_QUESTION
					| SWT.YES | SWT.NO);
			m
			.setMessage("O Stock foi actualizado. Gostaria de visualizar o relat�rio de esntradas de stock?");
			m.setText("Actualiza��o da base de dados com a entrada de stock");

			if (m.open() == SWT.YES) {
				Date today = new Date();
				// hack to allow the table to be cleared without asking the user
				clearAfterSave = true;
				StockReceiptReport report = new StockReceiptReport(getShell(),
						today, today);
				viewReport(report);
			}
			else {
				clearAfterSave = true;
				cmdClearWidgetSelected();
			}

			cmdCancelSelected();

		}

		catch (HibernateException he) {
			MessageBox m = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			m
			.setMessage("Houve algum problema ao salvar o stock na base de dados.");
			m.setText("Falha na actualiza��o da base de dados");
			if (tx != null) {
				tx.rollback();
			}
			getLog().error("Hibernate Exception Saving Stock", he);
		}
	}

	@Override
	protected void cmdCancelWidgetSelected() {
		if (tblDrugs.getItemCount() > 0) {
			MessageBox m = new MessageBox(getShell(), SWT.ICON_WARNING
					| SWT.YES | SWT.NO);
			m.setText("Cancelar sem gravar a informa��o na base de dados?");
			m
			.setMessage("O stock que registou n�o foi gravado na base de dados. \n\nTem a certeza que quer fechar esta tela sem gravar as esntradas de stock?");

			if (m.open() == SWT.YES) {
				cmdCloseSelected();
			}
		} else {
			cmdCloseSelected();
		}
	}

	@Override
	protected void clearForm() {
	}

	@Override
	protected void cmdClearWidgetSelected() {
		if (tblDrugs.getItemCount() > 0) {

			if(!clearAfterSave) {
				MessageBox m = new MessageBox(getShell(), SWT.ICON_WARNING
						| SWT.YES | SWT.NO);
				m.setText("Limpar a lista de entradas de stock?");
				m
				.setMessage("Tem certeza que quer limpar a lista de entradas de stock?");

				if (m.open() == SWT.YES) {
					tblDrugs.removeAll();
				}

			}
			else {
				tblDrugs.removeAll();
			}
			clearAfterSave = false;
		}
		btnSave.setEnabled(false);
	}

	/**
	 * Method enableFields.
	 * 
	 * @param enable
	 *            boolean
	 */
	@Override
	protected void enableFields(boolean enable) {
	}

	/**
	 * Method fieldsOk.
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean fieldsOk() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.celllife.idart.gui.misc.iDARTChangeListner#changed(java.lang.Object)
	 */
	@Override
	public void changed(Object o) {
		if (o != null && o instanceof String) {
			if(((String)o).equalsIgnoreCase("Cancel"))
				if(tblDrugs.getItemCount() == 0 ) {
					btnSave.setEnabled(false);
				}
		}
	}

	/**
	 * Method submitForm.
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean submitForm() {
		return false;
	}

	@Override
	protected void setLogger() {
		setLog(Logger.getLogger(this.getClass()));
	}

}
