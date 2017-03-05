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

package org.celllife.idart.gui.drug;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import model.manager.AdministrationManager;
import model.manager.DrugManager;

import org.apache.log4j.Logger;
import org.celllife.idart.commonobjects.CommonObjects;
import org.celllife.idart.database.hibernate.AtcCode;
import org.celllife.idart.database.hibernate.ChemicalCompound;
import org.celllife.idart.database.hibernate.ChemicalDrugStrength;
import org.celllife.idart.database.hibernate.Drug;
import org.celllife.idart.database.hibernate.Form;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.search.Search;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartColor;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.misc.iDARTUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.jfree.util.Log;

/**
 */
public class AddDrug extends GenericFormGui {

	private static final String ID = "id";

	private Button btnSearch;

	private Button rdBtnSideTreatment;

	private Button rdBtnARV;
	
	//Se o ARV eh ou nao pediatrico
	private Button chkBtnPediatric;

	private Button chkBtnAdult;
	
	private Text txtName;

	private Text txtPacksize;

	private Text txtDispensingInstructions1;

	private Text txtDispensingInstructions2;

	boolean isAddnotUpdate;

	private Drug localDrug;

	private Text txtAmountPerTime;

	private Text txtTimesPerDay;

	private Text txtAtc;

	private Text txtMims;

	private Combo cmbForm;

	private Composite compInstructions;

	private Table tblChemicalCompounds;

	private Button btnAddChemical;

	private TableEditor editor;

	private Label lblTablets;

	private Label lblTake;

	private Label lblPackDescription;

	private Group grpChemicalCompounds;

	private Button btnAtcSearch;

	private Group grpDrugInfo;

	private Button btnEditChemical;

	/**
	 * Use true if you want to add a new drug, use false if you are updating an
	 * existing drug
	 * 
	 * @param parent
	 *            Shell
	 */
	public AddDrug(Shell parent) {
		super(parent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes newDrug
	 */
	@Override
	protected void createShell() {
		isAddnotUpdate = (Boolean) getInitialisationOption(OPTION_isAddNotUpdate);
		// The GenericFormsGui class needs
		// Header text, icon URL, shell bounds
		String shellTxt = isAddnotUpdate ? "Registar Novo Medicamento"
				: "Actualizar um Medicamento";
		Rectangle bounds = new Rectangle(25, 0, 800, 600);
		// Parent Generic Methods ------
		buildShell(shellTxt, bounds); // generic shell build
	}

	@Override
	protected void createContents() {
		createCompDrugInfo();
		createGrpStandardDosages();
		createCompInstructions();
		createGrpChemicalCompounds();

		if (isAddnotUpdate) {
			enableFields(true);
			txtName.setFocus();
		} else {
			enableFields(false);
			btnSearch.setFocus();
		}
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = (isAddnotUpdate ? "Registar novo Medicamento"
				: "Actualizar ARV existente");
		iDartImage icoImage = iDartImage.PRESCRIPTIONADDDRUG;
		buildCompHeader(headerTxt, icoImage);
	}

	@Override
	protected void createCompButtons() {
		// Parent Class generic call
		buildCompButtons();
	}

	/**
	 * This method initializes compDrugInfo
	 * 
	 */
	private void createCompDrugInfo() {

		// compDrugInfo
		grpDrugInfo = new Group(getShell(), SWT.NONE);
		grpDrugInfo.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpDrugInfo.setText("Detalhes do Medicamento");
		grpDrugInfo.setBounds(new Rectangle(16, 115, 485, 330));
		//16, 423, 485, 61));
		GridLayout layout = new GridLayout(3, false);
		layout.verticalSpacing = 10;
		grpDrugInfo.setLayout(layout);

		Label lblDrugSearch = new Label(grpDrugInfo, SWT.NONE);
		lblDrugSearch.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));

		if (isAddnotUpdate) {
			lblDrugSearch.setText("");
		} else {
			lblDrugSearch.setText("*Procurar ARV para Actualizar:");
		}
		lblDrugSearch.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// btnSearch
		btnSearch = new Button(grpDrugInfo, SWT.NONE);
		btnSearch.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 2,1));
		btnSearch.setText("Procurar Medicamento");
		btnSearch.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnSearch.setVisible(!isAddnotUpdate);
		btnSearch
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdSearchWidgetSelected();
			}
		});
		btnSearch
		.setToolTipText("Pressione este botão para procurar um ARV existente.");

		// lblName & txtName
		Label lblName = new Label(grpDrugInfo, SWT.NONE);
		lblName.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblName.setText("* Nome:");
		lblName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		txtName = new Text(grpDrugInfo, SWT.BORDER);
		txtName.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 2,1));
		txtName.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		// lblNSN & txtNSN
		Label lblatc = new Label(grpDrugInfo, SWT.NONE);
		lblatc.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblatc.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblatc.setText(" Código FNM:");
	

		txtAtc = new Text(grpDrugInfo, SWT.BORDER);
		txtAtc.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 1,1));
		txtAtc.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		txtAtc.setEditable(false);
		
		btnAtcSearch = new Button(grpDrugInfo, SWT.NONE);
		btnAtcSearch.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		btnAtcSearch.setText("Procurar");
		btnAtcSearch.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnAtcSearch.setEnabled(false);
		btnAtcSearch.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdAtcSearchWidgetSelected();
			}
		});
		btnAtcSearch.setToolTipText("Pressione este botão para pesquisar por código FNM.");

//		// lblStockCode & txtStockCode
//		Label lblMims = new Label(grpDrugInfo, SWT.NONE);
//		lblMims.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
//		lblMims.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
//		lblMims.setText("  MIMS Reference:");
//		lblMims
//		.setToolTipText("This is recorded on the report Receipts and Issues: ARV Drugs");
//
//		txtMims = new Text(grpDrugInfo, SWT.BORDER);
//		txtMims.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 2,1));
//		txtMims.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// lblFormLanguage1 & txtFormLanguage1
		Label lblFormLanguage1 = new Label(grpDrugInfo, SWT.NONE);
		lblFormLanguage1.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblFormLanguage1.setText("* Forma Farmacéutica:");
		lblFormLanguage1.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		cmbForm = new Combo(grpDrugInfo, SWT.BORDER);
		cmbForm.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 2,1));
		CommonObjects.populateForms(getHSession(), cmbForm);
		cmbForm.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		cmbForm.setText("");
		cmbForm.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
		cmbForm.setVisibleItemCount(cmbForm.getItemCount());

		cmbForm.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent evt) {
				String theFormStr = cmbForm.getText();
				if (!"".equalsIgnoreCase(theFormStr)) {
					populateGrpStandardDosages(theFormStr);
					Form theForm = AdministrationManager.getForm(getHSession(),
							theFormStr);
					lblPackDescription.setText(theForm.getFormLanguage1()
							.equals("drops") ? "ml" : theForm
									.getFormLanguage1());

					txtDispensingInstructions1.setText("");
					txtDispensingInstructions2.setText("");
				}

			}
		});
		cmbForm.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String theText = cmbForm.getText();
				if (theText.length() > 2) {
					String s = theText.substring(0, 1);
					String t = theText.substring(1, theText.length());
					theText = s.toUpperCase() + t;
					String[] items = cmbForm.getItems();
					for (int i = 0; i < items.length; i++) {
						if (items[i].length() > 2
								&& items[i].substring(0, 3).equalsIgnoreCase(
										theText)) {
							cmbForm.setText(items[i]);
							cmbForm.setFocus();
						}
					}
				}
			}
		});

		// lblPacksize & txtPacksize
		Label lblPacksize = new Label(grpDrugInfo, SWT.NONE);
		lblPacksize.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblPacksize.setText("* Quantidade no Frasco:");
		lblPacksize.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		txtPacksize = new Text(grpDrugInfo, SWT.BORDER);
		txtPacksize.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1,1));
		txtPacksize.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		lblPackDescription = new Label(grpDrugInfo, SWT.NONE);
		lblPackDescription.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblPackDescription.setText("");
		lblPackDescription.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// lblDispensingInstructions1 & txtDispenseInstr
		Label lblDispensingInstructions1 = new Label(grpDrugInfo, SWT.NONE);
		lblDispensingInstructions1.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblDispensingInstructions1
		.setText("  Instrução de toma(linha 1):");
		lblDispensingInstructions1.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		lblDispensingInstructions1
		.setToolTipText("Isto aparece na etiqueta do medicamento");

		txtDispensingInstructions1 = new Text(grpDrugInfo, SWT.BORDER);
		txtDispensingInstructions1.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 2,1));
		txtDispensingInstructions1.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));

		// lblDispensingInstructions2 & txtDispensingInstructions2
		Label lblDispensingInstructions2 = new Label(grpDrugInfo, SWT.NONE);
		lblDispensingInstructions2.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblDispensingInstructions2
		.setText("  Instrução de toma(linha 2):");
		lblDispensingInstructions2.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		lblDispensingInstructions2
		.setToolTipText("Isto aparece na etiqueta do medicamento");

		txtDispensingInstructions2 = new Text(grpDrugInfo, SWT.BORDER);
		txtDispensingInstructions2.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 2,1));
		txtDispensingInstructions2.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		
		// Account Status
				Label lblSideTreatment = new Label(grpDrugInfo, SWT.NONE);
				lblSideTreatment.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
				lblSideTreatment.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
				lblSideTreatment.setText("* O medicamento é ARV?:");

				rdBtnSideTreatment = new Button(grpDrugInfo, SWT.RADIO);
				rdBtnSideTreatment.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1,1));
				rdBtnSideTreatment.setLayoutData(new GridData(110, 20));
				rdBtnSideTreatment.setText("Não");
				rdBtnSideTreatment.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
				rdBtnSideTreatment.setSelection(false);

				rdBtnARV = new Button(grpDrugInfo, SWT.RADIO);
				rdBtnARV.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
				rdBtnARV.setText("Sim");
				rdBtnARV.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
				rdBtnARV.setSelection(true);

		// Pediatric and adult
		Label lblPediatrc = new Label(grpDrugInfo, SWT.NONE);
		lblPediatrc.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		lblPediatrc.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		lblPediatrc.setText("* O medicamento é:");

		chkBtnPediatric = new Button(grpDrugInfo, SWT.CHECK);
		chkBtnPediatric.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, false, false, 1,1));
		chkBtnPediatric.setLayoutData(new GridData(130, 20));
		chkBtnPediatric.setText("Pediátrico");
		chkBtnPediatric.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		chkBtnPediatric.setSelection(false);

		chkBtnAdult = new Button(grpDrugInfo, SWT.CHECK);
		chkBtnAdult.setLayoutData(new GridData(GridData.BEGINNING, GridData.BEGINNING, false, false, 1,1));
		chkBtnAdult.setText("Adulto");
		chkBtnAdult.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		chkBtnAdult.setSelection(false);
		
		grpDrugInfo.layout();
	}

	protected void cmdAtcSearchWidgetSelected() {
		Search atcSearch = new Search(getHSession(), getShell(),
				CommonObjects.ATC) {};

		if (atcSearch.getValueSelected() != null) {

			AtcCode atc = AdministrationManager.getAtccodeFromCode(getHSession(), atcSearch
					.getValueSelected()[1]);
			
			if (atc == null){
				return;
			}
			
			Set<ChemicalCompound> ccs = atc.getChemicalCompounds();
			String name = atc.getName();
			if (ccs != null && ccs.size() == 1){
				String acronym = ccs.iterator().next().getAcronym();
				if (acronym != null && !acronym.isEmpty()){
					name = "[" + acronym + "] " + name;
				}
			}
			txtName.setText(name);
			txtAtc.setText(atc.getCode());
			String mims = atc.getMims();
			if (mims != null)
				//txtMims.setText(mims);
			
			if (ccs == null || ccs.isEmpty()){
				return;
			}
			
			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				boolean foundMatch = false;
				for (ChemicalCompound cc : ccs) {
					if (((ChemicalCompound) tblChemicalCompounds.getItem(i)
							.getData()).getId() == cc.getId()) {
						tblChemicalCompounds.getItem(i).setChecked(true);
						foundMatch = true;
						break;
					}
				}

				if (!foundMatch){
					tblChemicalCompounds.getItem(i).setChecked(false);
				}
			}
		}
		
	}

	/**
	 * This method initializes grpStandadDosages
	 * 
	 */
	private void createGrpStandardDosages() {

		// grpStandadDosages
		Group grpStandadDosages = new Group(getShell(), SWT.NONE);
		grpStandadDosages.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		grpStandadDosages.setText("Posologia ");
		grpStandadDosages.setLayout(null);
		grpStandadDosages.setBounds(new Rectangle(16, 450, 485, 61));

		// lblTake
		lblTake = new Label(grpStandadDosages, SWT.CENTER);
		lblTake.setBounds(new Rectangle(27, 28, 107, 22));
		lblTake.setText("Tomar");
		lblTake.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// txtAmountPerTime
		txtAmountPerTime = new Text(grpStandadDosages, SWT.BORDER);
		txtAmountPerTime.setBounds(new Rectangle(137, 26, 40, 22));
		txtAmountPerTime.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// lblTablets
		lblTablets = new Label(grpStandadDosages, SWT.CENTER);
		lblTablets.setBounds(new Rectangle(180, 28, 76, 22));
		lblTablets.setText("Comprimido(s)");
		lblTablets.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// txtTimesPerDay
		txtTimesPerDay = new Text(grpStandadDosages, SWT.BORDER);
		txtTimesPerDay.setBounds(new Rectangle(260, 26, 40, 22));
		txtTimesPerDay.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

		// lblTimesPerDay
		Label lblTimesPerDay = new Label(grpStandadDosages, SWT.CENTER);
		lblTimesPerDay.setBounds(new Rectangle(298, 28, 126, 22));
		lblTimesPerDay.setText("vezes por dia");
		lblTimesPerDay.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
	}

	/**
	 * This method initializes compButtons
	 * 
	 */

	@Override
	protected void cmdSaveWidgetSelected() {

		if (fieldsOk()) {
			MessageBox mSave = new MessageBox(getShell(), SWT.ICON_QUESTION
					| SWT.YES | SWT.NO);
			mSave.setText(isAddnotUpdate ? "Registar novo medicamento" : "Actualizar Detalhes");
			mSave
			.setMessage(isAddnotUpdate ? "Quer mesmo registar estem medicamento na base de dados?"
					: "Quer salvar as mudanças efectuadas sobres este medicamento?");

			switch (mSave.open()) {

			case SWT.YES:

				Transaction tx = null;
				String action = "";
				try {
					tx = getHSession().beginTransaction();
					if (isAddnotUpdate) {
						localDrug = new Drug();
						setLocalDrug();
						DrugManager.saveDrug(getHSession(), localDrug);
						action = "added";
					} else {
						setLocalDrug();
						action = "updated";
					}

					tx.commit();
					getHSession().flush();

					// Updating the drug list after being saved.
					MessageBox m = new MessageBox(getShell(),
							SWT.ICON_INFORMATION | SWT.OK);
					m.setMessage("Medicamento '".concat(localDrug.getName()).concat(
							"' has been " + action + "."));
					m.setText("Base de Dados actualizado");
					m.open();

				} catch (HibernateException he) {
					MessageBox m = new MessageBox(getShell(), SWT.OK
							| SWT.ICON_INFORMATION);
					m.setText("Problemas ao salvar na Base de Dados");
					m
					.setMessage("Houve problemas ao salvar informação do medicamento na base de dados. Tente de novo.");
					m.open();
					if (tx != null) {
						tx.rollback();
					}
					getLog().error(he);
				}
				cmdCancelWidgetSelected(); // go back to previous screen
				break;
			case SWT.NO:
				// do nothing
			}

		}
	}

	/**
	 * Clears the form
	 */
	@Override
	public void clearForm() {

		try {

			txtName.setText("");
			cmbForm.setText("");
			txtPacksize.setText("");
			lblPackDescription.setText("");
			txtDispensingInstructions1.setText("");
			txtDispensingInstructions2.setText("");
			btnSearch.setEnabled(true);
			txtAtc.setText("");
			chkBtnPediatric.setSelection(false);
			chkBtnAdult.setSelection(false);
			//txtMims.setText("");
			txtTimesPerDay.setText("");
			txtAmountPerTime.setText("");

			localDrug = null;
			enableFields(isAddnotUpdate);

			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				tblChemicalCompounds.getItem(i).setText(1, "");
				tblChemicalCompounds.getItem(i).setChecked(false);
			}

			Control old = editor.getEditor();
			if (old != null) {
				old.dispose();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void cmdCancelWidgetSelected() {
		cmdCloseSelected();
	}

	private void cmdAddChemicalWidgetSelected(final ChemicalCompound cc) {

		final AddChemicalCompound ac = new AddChemicalCompound(getShell(), getHSession(), cc);
		ac.getShell().addDisposeListener(new DisposeListener() {
			@Override
			public void widgetDisposed(DisposeEvent e) {

				if (AddChemicalCompound.compoundAdded != null) {
					ChemicalCompound ncc = DrugManager
					.getChemicalCompoundByName(getHSession(),
							AddChemicalCompound.compoundAdded.getName());

					if (ncc != null) {
						if (cc == null){
							// new cc
							TableItem ti = new TableItem(tblChemicalCompounds,
									SWT.NONE);
		
							populateTableItem(ncc, ti);
						} else {
							// edit cc
							TableItem[] items = tblChemicalCompounds.getItems();
							for (TableItem ti : items) {
								if (ti.getData(ID).equals(ncc.getId())){
									// populate table
									populateTableItem(ncc, ti);
								}
							}
						}
					}
				}
			}
		});
	}

	private void cmdSearchWidgetSelected() {

		Search drugSearch = new Search(getHSession(), getShell(),
				CommonObjects.DRUG) {};

		if (drugSearch.getValueSelected() != null) {

			localDrug = DrugManager.getDrug(getHSession(), drugSearch
					.getValueSelected()[0]);
			loadDrugDetails();
			btnSearch.setEnabled(false);
			// txtBarcode.setEditable(false);
			enableFields(true);
			txtName.setFocus();

		} else {
			btnSearch.setEnabled(true);
		}

	}

	@Override
	protected void cmdClearWidgetSelected() {
		clearForm();
	}

	private void loadDrugDetails() {

		txtName.setText(localDrug.getName());
		cmbForm.setText(localDrug.getForm().getForm());
		txtPacksize.setText(String.valueOf(localDrug.getPackSize()));

		if(localDrug.getPediatric()=='T') chkBtnPediatric.setSelection(true);
		if(localDrug.getPediatric()=='F') chkBtnAdult.setSelection(true);
		Form theForm = localDrug.getForm();
		lblPackDescription
		.setText(theForm.getFormLanguage1().equals("drops") ? "ml"
				: theForm.getFormLanguage1());
		txtDispensingInstructions1.setText(localDrug
				.getDispensingInstructions1());
		txtDispensingInstructions2.setText(localDrug
				.getDispensingInstructions2());

		populateGrpStandardDosages(cmbForm.getText());

		Object amntpertime = iDARTUtil.isInteger(""
				+ localDrug.getDefaultAmnt());
		String tmp = (amntpertime == null) ? "" + localDrug.getDefaultAmnt()
				: "" + amntpertime.toString();
		txtAmountPerTime.setText(tmp);


		txtTimesPerDay.setText(String.valueOf(localDrug.getDefaultTimes()));

		btnSearch.setEnabled(false);

		if (localDrug.getSideTreatment() == 'T') {
			rdBtnSideTreatment.setSelection(true);
			rdBtnARV.setSelection(false);
		}

		else {
			rdBtnARV.setSelection(true);
			rdBtnSideTreatment.setSelection(false);
		}

		if (localDrug.getStockCode() != null) {
			//txtMims.setText(localDrug.getStockCode());
		}

		if (localDrug.getAtccode() != null) {
			txtAtc.setText(localDrug.getAtccode().getCode());
		}

		Iterator<ChemicalDrugStrength> chemicalDrugStrengthIt = localDrug
		.getChemicalDrugStrengths().iterator();

		while (chemicalDrugStrengthIt.hasNext()) {
			ChemicalDrugStrength cds = chemicalDrugStrengthIt.next();
			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				if (((ChemicalCompound) tblChemicalCompounds.getItem(i)
						.getData()).getId() == cds.getChemicalCompound()
						.getId()) {
					tblChemicalCompounds.getItem(i).setChecked(true);

					double d = cds.getStrength();
					String strength = "";

					Object o = iDARTUtil.isInteger("" + d);
					if (o == null) {
						strength += d;
					} else {
						strength += Integer.parseInt(o.toString());
					}


					tblChemicalCompounds.getItem(i).setText(1,
							strength.toString());
				}

			}

		}
		
		grpDrugInfo.layout();

	}

	/**
	 * Check if the necessary field names are filled in. Returns true if there
	 * are fields missing
	 * 
	 * @return boolean
	 */
	@Override
	protected boolean fieldsOk() {

		// checking all simple fields

		if (txtName.getText().equals("")) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage("Nome do medicamento não pode ser vazio");
			b.setText("Informação em Falta");
			b.open();
			txtName.setFocus();
			return false;
		}
		
		
		
		if (chkBtnAdult.getSelection() && chkBtnPediatric.getSelection()) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage("O ARV deve ser Pediatrico ou de Adulto, Não ambos ");
			b.setText("Selecçao Errada");
			b.open();
			
			return false;
		}
		
		
		if (!chkBtnAdult.getSelection() && !chkBtnPediatric.getSelection()) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage(" Seleccione se o ARV é pediátrico ou de Adulto ");
			b.setText("Selecçao Errada");
			b.open();
			
			return false;
		}
		

		if (DrugManager.drugNameExists(getHSession(), txtName.getText())
				&& isAddnotUpdate) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b
			.setMessage("Nome do Medicameento já existe. Inserir um nome diferente");
			b.setText("Nome do medicamento suplicado");
			b.open();
			txtName.setFocus();
			return false;
		}

		if (cmbForm.indexOf(cmbForm.getText()) == -1) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b
			.setMessage("A forma do medicamento deve ser da lista.");
			b.setText("Informação incorrecta");
			b.open();
			cmbForm.setFocus();
			return false;
		}

		if (txtPacksize.getText().equals("")) {
			MessageBox b = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
			b.setMessage("Inserir quantidade no frasco");
			b.setText("Informação em Falta");
			b.open();

			return false;
		}

		try {
			Integer.parseInt(txtPacksize.getText());
		} catch (NumberFormatException nfe) {
			MessageBox incorrectData = new MessageBox(getShell(),
					SWT.ICON_ERROR | SWT.OK);
			incorrectData.setText("Quantidade no Frasco incorrecto");
			incorrectData
			.setMessage("A Quantidade no Frasco que inseriu é inválido. Inserir Número.");
			incorrectData.open();
			txtPacksize.setFocus();
			return false;
		}

		if (txtAmountPerTime.isVisible()
				& (!txtAmountPerTime.getText().trim().equals(""))) {

			try {
				Double.parseDouble(txtAmountPerTime.getText().trim());
			} catch (NumberFormatException nfe) {
				MessageBox incorrectData = new MessageBox(getShell(),
						SWT.ICON_ERROR | SWT.OK);
				incorrectData.setText("Valor do padrão da posologia incorrecto");
				incorrectData
				.setMessage("A posologia padrão que inseriu é incorrecto. Inserir número.");
				incorrectData.open();
				txtAmountPerTime.setFocus();
				return false;
			}

		}
		
		if (!txtAtc.getText().trim().isEmpty()){
			AtcCode atccode = AdministrationManager.getAtccodeFromCode(getHSession(),
					txtAtc.getText().trim());
			if (atccode == null){
				showMessage(MessageDialog.ERROR, "Código FNM Disconhecido", "O código FNM que seleccionou não está na base de dados.");
				return false;
			}
		}

		if (!txtTimesPerDay.getText().trim().equals("")) {
			try {
				Integer.parseInt(txtTimesPerDay.getText());
			} catch (NumberFormatException nfe) {
				MessageBox incorrectData = new MessageBox(getShell(),
						SWT.ICON_ERROR | SWT.OK);
				incorrectData.setText("o valor do padrão da posologia incorrecto");
				incorrectData
				.setMessage("A posologia padrão que inseriu é incorrecto. Inserir número.");
				incorrectData.open();
				txtTimesPerDay.setFocus();
				return false;
			}
		}

		// end of checking all simple fields

		Set<ChemicalDrugStrength> chemicalDrugStrengthList = new HashSet<ChemicalDrugStrength>();

		// go through and check the chemical compounds table
		for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
			TableItem ti = tblChemicalCompounds.getItem(i);

			// there should be a strength for each checked chemical compound
			if (ti.getChecked()) {
				// create a ChemicalCompound object from the TableItem
				// information
				String currentChemComString = ti.getText(0);

				// get the acronym and name
				int endOfAcronym = currentChemComString.indexOf("]") + 1;
				String acronym = currentChemComString
				.substring(0, endOfAcronym).trim();
				String name = currentChemComString.substring(endOfAcronym + 1,
						currentChemComString.length()).trim();

				// create the chemicalCompound using name and acronym
				ChemicalCompound currentChemicalCompound = new ChemicalCompound(
						name, acronym);

				int strength = 0;
				try {
					strength = Integer.parseInt(ti.getText(1));

					if (strength <= 0) {
						MessageBox incorrectData = new MessageBox(getShell(),
								SWT.ICON_ERROR | SWT.OK);
						incorrectData.setText("O valor da posologia não é Válido");
						incorrectData.setMessage("A posologia inserida para "
								+ ti.getText(0)
								+ " é inválido. Inserir valor positivo");
						incorrectData.open();
						txtPacksize.setFocus();
						return false;
					}
				} catch (NumberFormatException nfe) {

					if (ti.getText(1).trim().equals("")) {
						MessageBox incorrectData = new MessageBox(getShell(),
								SWT.ICON_ERROR | SWT.OK);
						incorrectData.setText("Posologia do componente não foi inserido");
						incorrectData
						.setMessage("Você indicou que este medicamento contém o componente "
								+ ti.getText(0)
								+ ", mas não entraram a posologia do componente.");
						incorrectData.open();
						txtPacksize.setFocus();
						return false;
					} else {
						MessageBox incorrectData = new MessageBox(getShell(),
								SWT.ICON_ERROR | SWT.OK);
						incorrectData.setText("posologia do componente não insserido");
						incorrectData
						.setMessage("O posologia do componente inserido para  "
								+ ti.getText(0)
								+ " não é número. \n\nInserir um número.");
						incorrectData.open();
						txtPacksize.setFocus();
						return false;
					}
				}

				// get the drug
				if (localDrug == null) {
					localDrug = new Drug();
				}

				// create ChemicalDrugStrength which combines
				// chemicalCompound
				// and strength information
				ChemicalDrugStrength currentChemicalStrength = new ChemicalDrugStrength(
						currentChemicalCompound, strength, localDrug);

				// add to the set of chemical compounds for this drug
				chemicalDrugStrengthList.add(currentChemicalStrength);

			}
		}// end of for loop

		if (localDrug != null) {
			setLocalDrug();
		}
		// all ARV drugs must have a chemical compound and must not have the
		// same chemical compounds and strengths as an existing drug
		if (rdBtnARV.getSelection()) {

			if (localDrug == null) {
				localDrug = new Drug();
			}

			setLocalDrug();

			if (chemicalDrugStrengthList.isEmpty()) {
				MessageBox m = new MessageBox(getShell(), SWT.ICON_INFORMATION
						| SWT.OK);
				m.setText("Sem componentes");
				m
				.setMessage("Todos medicamentos ARV tem uma composição química. \nAdicionar uma composição para"
						+ localDrug.getName());
				m.open();

				return false;
			} else {
				/*// check that there are no other drugs already with the same
				// chemical composition
				String chemicalDrugMatch = DrugManager
				.existsChemicalComposition(getHSession(),
						chemicalDrugStrengthList, localDrug.getName());
				
				boolean flag = DrugManager
				.formChemicalComposition(getHSession(),
						chemicalDrugStrengthList, localDrug.getName(), localDrug.getForm().getForm());
				
				if (chemicalDrugMatch != null) {
					if(flag){
						MessageBox m = new MessageBox(getShell(),
								SWT.ICON_INFORMATION | SWT.OK);
						m.setText("Duplicate ARV Drug");
						m
						.setMessage("The drug you are trying to add has the same chemical composition as drug '"
								+ chemicalDrugMatch +" Form ("+localDrug.getForm().getForm()+")"
								+ "' which is already in the database.\n\nIf this is the same drug, but a different manufacturer, you will capture this information when you receive the stock using 'Stock Arrives at the Pharmacy' screen.");
						m.open();
						return false;
					}
					
				}*/
			}
		}
		return true;
	}

	private void setLocalDrug() {

		try {

			localDrug.setName(txtName.getText());

			localDrug.setPackSize(Integer.parseInt(txtPacksize.getText()));
			localDrug.setDispensingInstructions1(txtDispensingInstructions1
					.getText());
			localDrug.setDispensingInstructions2(txtDispensingInstructions2
					.getText());
			localDrug.setModified('T');
			localDrug.setForm(AdministrationManager.getForm(getHSession(),
					cmbForm.getText()));

			double amnt = 0;
			int times = 0;

			if (!txtAmountPerTime.getText().equals("")) {
				amnt = Double.valueOf(txtAmountPerTime.getText());

			}

			if (!txtTimesPerDay.getText().equals("")) {
				times = Integer.parseInt(txtTimesPerDay.getText());
			}

			localDrug.setDefaultAmnt(amnt);
			localDrug.setDefaultTimes(times);

			if (rdBtnSideTreatment.getSelection()) {
				localDrug.setSideTreatment('T');
			}

			else {
				localDrug.setSideTreatment('F');
			}

			//ARV ADULT or Pedriatric
			if (chkBtnPediatric.getSelection()) {
				localDrug.setPediatric('T');
			}

			if (chkBtnAdult.getSelection()) {
				localDrug.setPediatric('T');
			}
			
			if (!txtAtc.getText().trim().isEmpty()){
				localDrug.setAtccode(AdministrationManager.getAtccodeFromCode(getHSession(),
					txtAtc.getText().trim()));
			}
			//localDrug.setStockCode(txtMims.getText());

			if (localDrug.getChemicalDrugStrengths() == null) {
				localDrug
				.setChemicalDrugStrengths(new TreeSet<ChemicalDrugStrength>());
			}
			localDrug.getChemicalDrugStrengths().clear();

			for (int i = 0; i < tblChemicalCompounds.getItemCount(); i++) {
				TableItem ti = tblChemicalCompounds.getItem(i);
				if (ti.getChecked()) {

					Log.debug("found checked chem: " + ti);
					localDrug.getChemicalDrugStrengths().add(
							new ChemicalDrugStrength((ChemicalCompound) ti
									.getData(), Integer.parseInt(ti.getText(1)),
									localDrug));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Method enableFields.
	 * 
	 * @param enable
	 *            boolean
	 */
	@Override
	protected void enableFields(boolean enable) {

		txtName.setEnabled(enable);
		cmbForm.setEnabled(enable);
		txtPacksize.setEnabled(enable);
		txtDispensingInstructions1.setEnabled(enable);
		txtDispensingInstructions2.setEnabled(enable);
		rdBtnSideTreatment.setEnabled(enable);
		chkBtnPediatric.setEnabled(enable);
		chkBtnAdult.setEnabled(enable);
		//chkBtnPediatric.setSelection(false);
		//chkBtnAdult.setSelection(false);
		rdBtnARV.setEnabled(enable);
		txtAmountPerTime.setEnabled(enable);
		txtTimesPerDay.setEnabled(enable);
		txtAtc.setEnabled(enable);
		//txtMims.setEnabled(enable);
		btnSave.setEnabled(enable);
		grpChemicalCompounds.setEnabled(enable);
		tblChemicalCompounds.setEnabled(enable);
		btnAddChemical.setEnabled(enable);
		btnEditChemical.setEnabled(enable);
		btnAtcSearch.setEnabled(enable);

		if (enable) {
			cmbForm.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
		} else {
			cmbForm.setBackground(ResourceUtils
					.getColor(iDartColor.WIDGET_BACKGROUND));
		}

	}

	/**
	 * Method populateGrpStandardDosages.
	 * 
	 * @param theFormString
	 *            String
	 */
	public void populateGrpStandardDosages(String theFormString) {
		Form form = AdministrationManager.getForm(getHSession(), theFormString);
		lblTake.setText(form.getActionLanguage1());
		lblTablets.setText(form.getFormLanguage1());

		if (lblTablets.getText().equals("")) {
			txtAmountPerTime.setVisible(false);
		} else {
			txtAmountPerTime.setVisible(true);
		}

		if ((form.getDispInstructions1() != null)
				&& (!form.getDispInstructions1().equals(""))) {
			txtDispensingInstructions1.setText(form.getDispInstructions1());
		}
		if ((form.getDispInstructions2() != null)
				&& (!form.getDispInstructions2().equals(""))) {
			txtDispensingInstructions2.setText(form.getDispInstructions2());
		}
	}

	/**
	 * This method initializes compInstructions
	 * 
	 */
	private void createCompInstructions() {
		compInstructions = new Composite(getShell(), SWT.NONE);
		compInstructions.setLayout(null);
		compInstructions.setBounds(new Rectangle(270, 79, 360, 25));

		Label lblInstructions = new Label(compInstructions, SWT.CENTER);
		lblInstructions.setBounds(new Rectangle(0, 0, 360, 25));
		lblInstructions.setText("Todos campos marcados com * são obrigatórios");
		lblInstructions.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_10_ITALIC));
	}

	/**
	 * This method initializes grpChemicalCcompounds
	 * 
	 */
	private void createGrpChemicalCompounds() {
		grpChemicalCompounds = new Group(getShell(), SWT.NONE);
		grpChemicalCompounds.setText("Composição química ");
		grpChemicalCompounds.setBounds(new Rectangle(524, 110, 235, 372));
		grpChemicalCompounds.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));
		
		tblChemicalCompounds = new Table(grpChemicalCompounds, SWT.CHECK
				| SWT.FULL_SELECTION | SWT.BORDER);
		tblChemicalCompounds.setHeaderVisible(true);
		tblChemicalCompounds.setLinesVisible(true);
		tblChemicalCompounds.setBounds(new Rectangle(12, 20, 213, 301));
		tblChemicalCompounds.setFont(ResourceUtils
				.getFont(iDartFont.VERASANS_8));

//		Label lblAddChemical = new Label(grpChemicalCompounds, SWT.NONE);
//		lblAddChemical.setBounds(new Rectangle(14, 334, 30, 26));
//		lblAddChemical.setText("");
//		lblAddChemical.setImage(ResourceUtils.getImage(iDartImage.DRUG_30X26));
		
		btnAddChemical = new Button(grpChemicalCompounds, SWT.NONE);
		btnAddChemical.setBounds(new Rectangle(14, 332, 100, 30));
		btnAddChemical.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnAddChemical.setText("Adicionar composto");
		btnAddChemical
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				cmdAddChemicalWidgetSelected(null);
			}
		});
		
		btnEditChemical = new Button(grpChemicalCompounds, SWT.NONE);
		btnEditChemical.setBounds(new Rectangle(120, 332, 100, 30));
		btnEditChemical.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnEditChemical.setText("Editar Composto");
		btnEditChemical.setEnabled(false);
		btnEditChemical
		.addSelectionListener(new org.eclipse.swt.events.SelectionAdapter() {
			@Override
			public void widgetSelected(
					org.eclipse.swt.events.SelectionEvent e) {
				TableItem[] selection = tblChemicalCompounds.getSelection();
				if (selection.length > 0){
					ChemicalCompound cc = (ChemicalCompound) selection[0].getData();
					cmdAddChemicalWidgetSelected(cc);
				}
			}
		});

		TableColumn tblColChemicalCompounds = new TableColumn(
				tblChemicalCompounds, SWT.NONE);
		tblColChemicalCompounds.setWidth(140);
		tblColChemicalCompounds.setText("Medicamento");

		TableColumn tblClmStrength = new TableColumn(tblChemicalCompounds,
				SWT.NONE);
		tblClmStrength.setWidth(72);
		tblClmStrength.setText("Posologia");
		populateTblChemicalCompounds();

		editor = new TableEditor(tblChemicalCompounds);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		tblChemicalCompounds.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent event) {
				// Dispose any existing editor
				Control old = editor.getEditor();
				if (old != null) {
					old.dispose();
				}

				// Determine where the mouse was clicked
				Point pt = new Point(event.x, event.y);

				// Determine which row was selected
				final TableItem item = tblChemicalCompounds.getItem(pt);
				if (item != null) {
					// Determine which column was selected
					int column = -1;
					for (int i = 0, n = tblChemicalCompounds.getColumnCount(); i < n; i++) {
						Rectangle rect = item.getBounds(i);
						if (rect.contains(pt)) {
							// This is the selected column
							column = i;
							break;
						}
					}

					if (column == 1) {
						// Create the Text object for our editor

						final Text text = new Text(tblChemicalCompounds,
								SWT.NONE);
						text.setForeground(item.getForeground());
						text.setBackground(ResourceUtils
								.getColor(iDartColor.GRAY));
						text.setFont(ResourceUtils
								.getFont(iDartFont.VERASANS_8));
						text.setText(item.getText(column));
						text.setForeground(item.getForeground());
						text.selectAll();
						text.setFocus();

						editor.minimumWidth = text.getBounds().width;

						// Set the control into the editor
						editor.setEditor(text, item, column);

						final int col = column;
						text.addModifyListener(new ModifyListener() {
							@Override
							public void modifyText(ModifyEvent event1) {

								item.setText(col, text.getText());

								// if you've set a strength, check the column
								if (!text.getText().trim().equals("")) {
									item.setChecked(true);
								}

								else if (item.getChecked()) {
									item.setChecked(false);
								}
							}
						});
					}
				}
			}
		});

		// if the user unchecks (or checks) a colum, clear the current contents
		// of the strength field
		tblChemicalCompounds.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {

				if (event.detail == SWT.CHECK) {
					TableItem ti = (TableItem) event.item;
					ti.setText(1, "");

				}
			}
		});

	}

	private void populateTblChemicalCompounds() {
		List<ChemicalCompound> chemicalCompoundList = new ArrayList<ChemicalCompound>();

		chemicalCompoundList = DrugManager
		.getAllChemicalCompounds(getHSession());

		Iterator<ChemicalCompound> chemicalCompoundIt = chemicalCompoundList
		.iterator();

		while (chemicalCompoundIt.hasNext()) {
			ChemicalCompound cc = chemicalCompoundIt.next();
			TableItem ti = new TableItem(tblChemicalCompounds, SWT.NONE);

			// populate table
			populateTableItem(cc, ti);
		}
	}

	/**
	 * @param cc
	 * @param ti
	 */
	private void populateTableItem(ChemicalCompound cc, TableItem ti) {
		ti.setText(0, "[" + cc.getAcronym() + "] " + cc.getName());
		ti.setData(cc);
		ti.setData(ID, cc.getId());
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
		Logger log = Logger.getLogger(this.getClass());
		setLog(log);
	}

}
