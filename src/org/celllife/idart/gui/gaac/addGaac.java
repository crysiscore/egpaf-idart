/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.celllife.idart.gui.gaac;


import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import model.manager.GaacManager;
import model.manager.PatientManager;
import org.apache.log4j.Logger;
import org.celllife.function.DateRuleFactory;
import org.celllife.idart.commonobjects.iDartProperties;
import org.celllife.idart.database.hibernate.Gaac;
import org.celllife.idart.database.hibernate.GaacMember;
import org.celllife.idart.database.hibernate.Patient;
import org.celllife.idart.database.hibernate.PatientIdentifier;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.misc.iDARTChangeListener;
import org.celllife.idart.gui.patient.TextAdapter;
import org.celllife.idart.gui.platform.GenericFormGui;
import static org.celllife.idart.gui.platform.GenericGuiInterface.EMPTY;
import org.celllife.idart.gui.search.PatientSearch;
import org.celllife.idart.gui.utils.ComboUtils;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartColor;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.gui.widget.DateButton;
import org.celllife.idart.gui.widget.DateInputValidator;
import org.celllife.idart.messages.Messages;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.hibernate.HibernateException;
import org.jdesktop.swingx.JXDatePicker;

/**
 *
 * @author agnaldo
 */
public class addGaac extends GenericFormGui implements iDARTChangeListener {

    private Gaac localGaac;
    private Patient localPatient;
    private GaacMember gaacMember;
   
     /* Are we adding a new patient of updating an existing patient?
     */
    private boolean isAddnotUpdate;

    /**
     * Creates new form AddGaac
     *
     * @param parent
     * @param add
     */
    public addGaac(Shell parent, boolean add) {
        super(parent, HibernateUtil.getNewSession());
        isAddnotUpdate = add;
        enable = true;
    }

    public addGaac(Shell parent) {
        super(parent, HibernateUtil.getNewSession());

    }

    /**
     * Constructor for AddPatient.
     *
     * @param parent Shell
     * @param g Gaac
     */
    public addGaac(Shell parent, Gaac g) {
        super(parent, HibernateUtil.getNewSession());
        localGaac = GaacManager.getGaac(getHSession(), g.getId());
        // updateGUIforNewLocalPatient();
    }

    private boolean FieldsOk() {

        boolean ok = true;
        Date today = Calendar.getInstance().getTime();
        try {
            if (jdate_gaac_startdate.getDate().after(today)) {
                JOptionPane.showMessageDialog(null, "Data Inicio nao pode ser no futuro", "Erro", ERROR_MESSAGE);
                ok = false;
            }
        } catch (java.lang.NullPointerException ex) {

            JOptionPane.showMessageDialog(null, "Data Inicio  vazia/errada ", "Erro", ERROR_MESSAGE);
            ok = false;
        }

        if (txt_gaac_name.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nome do Gaac nao pode ser vazio", "Erro", ERROR_MESSAGE);
            ok = false;
        } else if (txt_gaac_id.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "ID do Gaac nao pode ser vazio", "Erro", ERROR_MESSAGE);
            ok = false;
        } else if (txt_gaac_focal_point.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O Gaac tem de ter um ponto focal.", "Erro", ERROR_MESSAGE);
            ok = false;
        } else if (combo_gaac_afinidade.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O Gaac tem de ter um ponto focal.", "Erro", ERROR_MESSAGE);
            ok = false;
        }
        return ok;
    }

    private void cmdSearchWidgetSelected() {

        if (localPatient == null) {
            PatientSearch search = new PatientSearch(getShell(), getHSession());
            search.setShowInactive(true);
            PatientIdentifier identifier = search.search(txt_gaac_focal_point.getText());

            if (identifier != null) {
                localPatient = identifier.getPatient();
                updateGUIforNewLocalPatient();
            } // if we've returned from the search GUI with the user having
            // pressed "cancel", enable the search button
            else if (!btn_gaac_search_patient.isDisposed()) {
                btn_gaac_search_patient.setEnabled(true);

            }
        } else {
            //editPatientIdentifiers();
        }
    }

    private void updateGUIforNewLocalPatient() {
        loadPatientDetails();
        txt_gaac_focal_point.setEnabled(false);
        txt_gaac_name.setFocus();
        btn_gaac_search_patient.setText(Messages.getString("patient.button.editid")); //$NON-NLS-1$
        btn_gaac_search_patient.setToolTipText(Messages.getString("patient.button.editid.tooltip")); //$NON-NLS-1$
        //btnEkapaSearch.setEnabled(false);
        
        //enableFields(true);
    }

    public void loadPatientDetails() {

        try {
            // populate the GUI
            txt_gaac_focal_point.setText(localPatient.getPatientId());
            txt_gaac_fpont_name.setText(localPatient.getFirstNames() + " " + localPatient.getLastname());
        } catch (Exception e) {
            getLog().error(e);
        }
    }

    @Override
    protected void clearForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean submitForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected boolean fieldsOk() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void createCompHeader() {
        String headerTxt = isAddnotUpdate ? Messages
                .getString("Gaac.button.title.add") //$NON-NLS-1$
                : Messages.getString("Gaac.button.title.update"); //$NON-NLS-1$
        iDartImage icoImage = (isAddnotUpdate ? iDartImage.PATIENTNEW
                : iDartImage.PATIENTUPDATE);
        buildCompHeader(headerTxt, icoImage);
    }

    @Override
    protected void createCompButtons() {
        buildCompButtons();
    }

    @Override
    protected void cmdSaveWidgetSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void cmdClearWidgetSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void cmdCancelWidgetSelected() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void enableFields(boolean enable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void createContents() {
        createCompGaacInfo();
    }

    @Override
    protected void setLogger() {
        Logger log = Logger.getLogger(this.getClass());
        setLog(log);
    }

    @Override
    protected void createShell() {
        isAddnotUpdate = (Boolean) GenericFormGui
                .getInitialisationOption(GenericFormGui.OPTION_isAddNotUpdate);
        String shellTxt = isAddnotUpdate ? Messages.getString("Gaac.button.title.add") //$NON-NLS-1$
                : Messages.getString("Gaac.button.title.update"); //$NON-NLS-1$
        Rectangle bounds = new Rectangle(25, 0, 900, 700);
        buildShell(shellTxt, bounds); // generic shell build
    }

    @Override
    public void changed(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Variables declaration - do not modify                     
    private Button btn_gaac_cancel;
    private Button btn_gaac_remove_patient;
    private Button btn_gaac_save;
    private Button btn_gaac_search_patient;
    private Combo combo_gaac_afinidade;
    private Label lbl_gaac_id;
    private Label lblb_gaa_name;
    private Label lbl_gaac_desc;
    private Label lbl_gaac_focal_p;
    private Label lbl_gaac_startdate;
    private Label lbl_gaac_affinity;
    private Panel jPanel1;
    private Panel jPanel2;
    private Panel jPanel3;
    private ScrollPane jScrollPane1;
    private ScrollPane jScrollPane2;
    private JXDatePicker jdate_gaac_startdate;
    private DateButton btnGaacStartDate;
    private DateButton btnGaacEndDate;
    private Label lbl_gaac_add_member;
    private Panel pnl_gaac_remove_patient;
    private Panel pnl_name;
    private Panel pnl_name1;
    private Panel pnl_name2;
    private Panel pnl_name3;
    private Panel pnl_name4;
    private Panel pnl_name7;
    private Panel pnl_name8;
    private Table table_gaac_members;
    private TextArea txt_gaac_descpt;
    private TextAdapter txt_gaac_focal_point;
    private Text txt_gaac_id;
    private Text txt_gaac_name;
    private Text txt_gaac_fpont_name;
    private Text txt_gaac_startdate;
    private Text txt_gaac_crumbled_reason;
    private Text txt_gaac_voided_reason;
    private Text txtAreaGaacNotes;
       // Prescription Notes
    private  Label lblGaacNotes;
    boolean enable ;
    private Label  lbl_crumbled;
    private CCombo cmbGaacCrumbled;
    private CCombo cmbDesintegrao;
    private CCombo cmbAffinity;
    
    private Label lbl_gaac_fpont_name;
        private Composite compGaacInfo;
    // End of variables declaration                   
    private Group grpParticulars;

    protected void buildCompButtons() {

        Composite myCmp = new Composite(getShell(), SWT.NONE);

        RowLayout rowlyt = new RowLayout();
        rowlyt.justify = true;
        rowlyt.pack = false;
        rowlyt.spacing = 10;
        myCmp.setLayout(rowlyt);

        RowData rowD = new RowData(170, 30);

        setCompButtons(new Composite(myCmp, SWT.NONE));
        getCompButtons().setLayout(rowlyt);

        // btnSave
        /*btn_gaac_save = new Button(getCompButtons(), SWT.NONE);
		btn_gaac_save.setText(btnSaveText); //$NON-NLS-1$
		btn_gaac_save.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btn_gaac_save
		.setToolTipText(Messages.getString("genericformgui.button.save.tooltip")); //$NON-NLS-1$


		// btnClear
		btn_gaac_cancel = new Button(getCompButtons(), SWT.NONE);
		btn_gaac_cancel.setText(Messages.getString("genericformgui.button.clear.text")); //$NON-NLS-1$
		btn_gaac_cancel.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btn_gaac_cancel.setToolTipText(Messages
				.getString("genericformgui.button.clear.tooltip")); //$NON-NLS-1$


				btnCancel = new Button(getCompButtons(), SWT.NONE);
				btnCancel.setText( "Fechar"); //$NON-NLS-1$
				btnCancel.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
				btnCancel.setToolTipText(Messages
						.getString("Clique para fechar a tela")); //$NON-NLS-1$
				
				
		// Adding button listener
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdSaveWidgetSelected();
			}
		});

		btnClear.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdClearWidgetSelected();
			}
		});

		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdCancelWidgetSelected();
			}
		});   */
        Control[] buttons = getCompButtons().getChildren();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setLayoutData(rowD);
        }

        getCompButtons().pack();
        Rectangle b = getShell().getBounds();
        myCmp.setBounds(0, b.height - 79, b.width, 40);
    }

    /**
     * This method initializes createCompGaacInfo
     *
     */
    private void createCompGaacInfo() {
        compGaacInfo = new Composite(getShell(), SWT.NONE);
        compGaacInfo.setBounds(new Rectangle(16, 55, 854, 505));

        // create the composites
        createGrpParticulars();
        //createGrpEpisodes();
        //createCmpTabbedInfo();
        Label lblInstructions = new Label(compGaacInfo, SWT.CENTER);
        lblInstructions.setBounds(new Rectangle(100, 15, 350, 20));
        lblInstructions.setText(Messages.getString("common.label.compulsory")); //$NON-NLS-1$
        lblInstructions.setFont(ResourceUtils
                .getFont(iDartFont.VERASANS_10_ITALIC));
    }

    private void createGrpParticulars() {

        int col2x = 115;

        // grpParticulars
        grpParticulars = new Group(compGaacInfo, SWT.NONE);
        grpParticulars.setBounds(new Rectangle(30, 40, 800, 255));
        grpParticulars.setText(Messages.getString("gaac.group.particulars")); //$NON-NLS-1$
        grpParticulars.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

        // Nome
        lblb_gaa_name = new Label(grpParticulars, SWT.NONE);
        lblb_gaa_name.setBounds(new Rectangle(7, 55, 84, 20));
        lblb_gaa_name.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        lblb_gaa_name
                .setText(Messages.getString("common.compulsory.marker") + Messages.getString("gaac.group.name")); //$NON-NLS-1$ //$NON-NLS-2$
        txt_gaac_name = new Text(grpParticulars, SWT.BORDER);
        txt_gaac_name.setBounds(new Rectangle(col2x, 55, 150, 20));
        txt_gaac_name.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

        // Identificador
        lbl_gaac_id = new Label(grpParticulars, SWT.NONE);
        lbl_gaac_id.setBounds(new Rectangle(7, 95, 84, 20));
        lbl_gaac_id.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        lbl_gaac_id
                .setText(Messages.getString("common.compulsory.marker") + Messages.getString("gaac.group.identifier"));
        txt_gaac_id = new Text(grpParticulars, SWT.BORDER);
        txt_gaac_id.setBounds(new Rectangle(col2x, 95, 150, 20));
        txt_gaac_id.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

        //Ponto focal
        lbl_gaac_focal_p = new Label(grpParticulars, SWT.NONE);
        lbl_gaac_focal_p.setBounds(new Rectangle(7, 135, 90, 20));
        lbl_gaac_focal_p
                .setText(Messages.getString("common.compulsory.marker") + Messages.getString("gaac.group.focal.point")); //$NON-NLS-1$ //$NON-NLS-2$
        lbl_gaac_focal_p.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

        txt_gaac_focal_point = new TextAdapter(grpParticulars, SWT.BORDER);
        txt_gaac_focal_point.setBounds(new Rectangle(col2x, 135, 150, 20));
        txt_gaac_focal_point.setData(iDartProperties.SWTBOT_KEY, "txtPatientId"); //$NON-NLS-1$
        txt_gaac_focal_point.setFocus();
        txt_gaac_focal_point.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        txt_gaac_focal_point.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if ((btn_gaac_search_patient != null) && (btn_gaac_search_patient.getEnabled())) {
                    if ((e.character == SWT.CR)
                            || (e.character == (char) iDartProperties.intValueOfAlternativeBarcodeEndChar)) {
                        cmdSearchWidgetSelected();
                    }
                }
            }
        });

        if (isAddnotUpdate) {
            txt_gaac_focal_point.setEnabled(true);
        }

        btn_gaac_search_patient = new Button(grpParticulars, SWT.NONE);
        btn_gaac_search_patient.setBounds(new Rectangle(270, 135, 120, 28));

        if (isAddnotUpdate) {
            btn_gaac_search_patient.setText(Messages.getString("patient.button.editid")); //$NON-NLS-1$
            btn_gaac_search_patient
                    .setToolTipText(Messages.getString("patient.button.editid.tooltip")); //$NON-NLS-1$
        } else {
            btn_gaac_search_patient.setText(Messages.getString("patient.button.search")); //$NON-NLS-1$
            btn_gaac_search_patient
                    .setToolTipText(Messages.getString("patient.button.search.tooltip")); //$NON-NLS-1$
        }
        btn_gaac_search_patient.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        btn_gaac_search_patient.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                cmdSearchWidgetSelected();
            }
        });
        
         // FirstNames
         lbl_gaac_fpont_name = new Label(grpParticulars, SWT.NONE);
        lbl_gaac_fpont_name.setBounds(new Rectangle(7, 175, 95, 20));
        lbl_gaac_fpont_name.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        lbl_gaac_fpont_name
                .setText(Messages.getString("common.compulsory.marker") + Messages.getString("gaac.group.focal.point.name")); //$NON-NLS-1$ //$NON-NLS-2$
        txt_gaac_fpont_name = new Text(grpParticulars, SWT.BORDER);
        txt_gaac_fpont_name.setBounds(new Rectangle(col2x, 175, 150, 20));
        txt_gaac_fpont_name.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        txt_gaac_fpont_name.setEditable(false);

        // Start Date
         lbl_gaac_startdate = new Label(grpParticulars, SWT.NONE);
        lbl_gaac_startdate.setBounds(new Rectangle(7, 215, 84, 20));
        lbl_gaac_startdate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        lbl_gaac_startdate
                .setText(Messages.getString("common.compulsory.marker") + Messages.getString("gaac.group.startdate"));
     
                    
                     btnGaacStartDate = new DateButton(grpParticulars,
                DateButton.ZERO_TIMESTAMP, new DateInputValidator(
                        DateRuleFactory.beforeNowInclusive(true)));
        btnGaacStartDate.setBounds(col2x,210, 100, 25);
        btnGaacStartDate.setText(Messages.getString("gaac.label.startdate")); //$NON-NLS-1$
        btnGaacStartDate.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        btnGaacStartDate.setToolTipText(Messages.getString("patient.button.startdate.tooltip")); //$NON-NLS-1$
        
           // Prescription Notes
         lblGaacNotes = new Label(grpParticulars, SWT.CENTER | SWT.BORDER);
        lblGaacNotes.setBounds(new Rectangle(500, 135, 230, 20));
        lblGaacNotes.setText("Notas do Gaac:");
        lblGaacNotes.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));

        txtAreaGaacNotes = new Text(grpParticulars, SWT.MULTI | SWT.WRAP
                | SWT.V_SCROLL | SWT.BORDER);
        txtAreaGaacNotes.setBounds(new Rectangle(500, 135, 230, 100));
        txtAreaGaacNotes.setText("");
        txtAreaGaacNotes.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        txtAreaGaacNotes.setEnabled(true);
        txtAreaGaacNotes.setEditable(true);
        

//$NON-NLS-1$ //$NON-NLS-2$
        //combo_gaac_afinidade= new Combo(grpParticulars, SWT.BORDER);
        //combo_gaac_afinidade.setBounds(new Rectangle(col2x, 112, 50, 18));
        //combo_gaac_afinidade.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
        //combo_gaac_afinidade.setBackground(ResourceUtils.getColor(iDartColor.WHITE));
        //combo_gaac_afinidade.addKeyListener(new KeyAdapter() {
        //    @Override
        //   public void keyReleased(KeyEvent e) {
        //        String theText = combo_gaac_afinidade.getText();
        //        if (combo_gaac_afinidade.indexOf(theText) == -1) {
        //            combo_gaac_afinidade.setText(combo_gaac_afinidade.getItem(0));
        //        }
        //    }
        //});
        // populate the comboboxes
        // ComboUtils.populateDateCombos(cmbDOBDay, cmbDOBMonth, cmbDOBYear,       false, false);
    }
}
