/**
 * @author EdiasJambaia
 * @since Setembro 2014

 */

package org.celllife.idart.gui.sync.dispense;
 
import java.util.Vector;

import org.celllife.idart.database.dao.ConexaoJDBC;
import org.celllife.idart.database.dao.ConexaoODBC;
import org.celllife.idart.database.hibernate.util.HibernateUtil;
import org.celllife.idart.gui.platform.GenericFormGui;
import org.celllife.idart.gui.utils.ResourceUtils;
import org.celllife.idart.gui.utils.iDartFont;
import org.celllife.idart.gui.utils.iDartImage;
import org.celllife.idart.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
 
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.hibernate.Session;

public class Sync extends GenericFormGui  {

	public static Shell shell;
	
	public Sync(Shell parent, Session hSession) {
		super(parent, hSession);
		// TODO Auto-generated constructor stub
	}

	private TableColumn clmLevIdart;

	private TableColumn clmNID;

	private TableColumn clmSpace;

	private TableColumn clmLevSesp;
	
	

	private Group grpSync;
	
	private Table tblSync;

	private TableEditor editor;

	private Button 	btnSync;



	public Sync(Shell theParent,
			boolean fromShortcut) {
	
		super(theParent, HibernateUtil.getNewSession());
	}

	/**
	 * This method initializes getMyShell()
	 */
	@Override
	protected void createShell() {
		String shellTxt = Messages.getString("sync.title"); //$NON-NLS-1$
		Rectangle bounds = new Rectangle(30, 50, 900, 600);
		
	
			
		// Parent Generic Methods ------
		buildShell(shellTxt, bounds); // generic shell build
	}

	@Override
	protected void createContents() {
	

		createGrpDrugs();
		
		// btnCancel
		btnCancel = new Button(getShell(), SWT.NONE);
		btnCancel.setText(Messages.getString("alerta.button.fechar.text")); //$NON-NLS-1$
		btnCancel.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnCancel.setToolTipText(Messages
				.getString("alerta.button.fechar.tooltip")); //$NON-NLS-1$
		
		btnCancel.setBounds(350,500, 100, 40);
		
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdCancelWidgetSelected();
			}
		});
		
		// btnSync
		btnSync = new Button(getShell(), SWT.NONE);
		btnSync.setText("Sincronizar"); //$NON-NLS-1$
		btnSync.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		btnSync.setToolTipText("Clique para sincronizar dados"); //$NON-NLS-1$
		
		btnSync.setBounds(200,500, 100, 40);
		
		btnSync.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(org.eclipse.swt.events.SelectionEvent e) {
				cmdSyncWidgetSelected();
			}
		});

	}

	/**
	 * Method addDisposeListener.
	 * 
	 * @param dl
	 *            DisposeListener
	 */
	public void addDisposeListener(DisposeListener dl) {
		getShell().addDisposeListener(dl);
	}

	/**
	 * This method initializes compHeader
	 * 
	 */
	@Override
	protected void createCompHeader() {
		String headerTxt = "Sincronização das bases de dados iDART e SESP (Levantamentos de Medicamentos)";
		iDartImage icoImage = iDartImage.HOURGLASS;
		buildCompHeaderSync(headerTxt, icoImage);
	}
	
	/**
	 * This method initializes grpDrugs
	 * 
	 */
	private void createGrpDrugs() {

		grpSync = new Group(getShell(), SWT.NONE);

		grpSync.setText("Pacientes a Sincronizar os levantamentos de Medicamentos");
		
		grpSync.setBounds(new Rectangle(50, 80, 800, 400));
		
		grpSync.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		
		createDrugsTable();
	}

	/**
	 * This method initializes tblDrugs
	 * 
	 * 
	 */
	private void createDrugsTable() {

		tblSync = new Table(grpSync, SWT.FULL_SELECTION);
		tblSync.setLinesVisible(true);
		tblSync.setBounds(new Rectangle(50, 25, 700, 350));
		tblSync.setFont(ResourceUtils.getFont(iDartFont.VERASANS_8));
		tblSync.setHeaderVisible(true);

		// 0 - clmSpace
		clmSpace = new TableColumn(tblSync, SWT.CENTER);
		clmSpace.setWidth(28);
		clmSpace.setText("No");

	

		// 1 - clmnid
		clmNID = new TableColumn(tblSync, SWT.NONE);
		clmNID.setText("NID");
		clmNID.setWidth(235);
		clmNID.setResizable(false);
		
		// 2 - clmlevIdart
		clmLevIdart = new TableColumn(tblSync, SWT.CENTER);
		clmLevIdart.setText("ÚLTIMO LEVANTAMENTO - IDART");
		clmLevIdart.setWidth(50);
		clmLevIdart.setResizable(false);

		// 3 - clmlevSesp
		clmLevSesp = new TableColumn(tblSync, SWT.CENTER);
		clmLevSesp.setText("ÚLTIMO LEVANTAMENTO - SESP");
		clmLevSesp.setWidth(50);
		clmLevSesp.setResizable(false);
		
		editor = new TableEditor(tblSync);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;

		ConexaoJDBC c=new ConexaoJDBC();
int i=0;
Vector<SyncLinha> synclinha=c.sync_table_dispense();

for(int j=0;j<synclinha.size();j++)
{
				final TableItem item = new TableItem(tblSync, SWT.NONE);
				    item.setText(new String[] {(++i)+"", synclinha.get(j).getNid(), synclinha.get(j).getUltimo_lev(), synclinha.get(j).getUltimo_sesp() });
}
				 
		   clmSpace.pack();
			clmNID.pack();
			clmLevIdart.pack();
			clmLevSesp.pack();
	}



	@Override
	protected void cmdCancelWidgetSelected() {
		// TODO Auto-generated method stub
		closeShell(false);
	}


	@Override
	protected void setLogger() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createCompButtons() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void clearForm() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean submitForm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean fieldsOk() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void cmdSaveWidgetSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void cmdClearWidgetSelected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void enableFields(boolean enable) {
		// TODO Auto-generated method stub
		
	}
	

	protected void cmdSyncWidgetSelected() {
		// TODO Auto-generated method stub
 
	 
//ConexaoJDBC c=new ConexaoJDBC();
//ConexaoODBC c2=new ConexaoODBC();	
//  Vector<SyncLinha> synclinha=c.sync_table();
// 
// for(int i=0; i<synclinha.size() ; i++)
// {
//	 c2.syncdata(synclinha.get(i));
//	 
//	 
// }

	SyncProgressBar dpb = new SyncProgressBar(getShell());
    dpb.initGuage();
      dpb.open();
        
	}

}

class SyncProgressBar extends ProgressBarDialog {
	private static ConexaoJDBC jdbc=new ConexaoJDBC();
	  private String[] info = null;
	  
	  public SyncProgressBar(Shell parent) {
	       super(parent);
	   }
	  

	  @Override
	  public void initGuage() {
		  
		  
	 
		  ConexaoODBC c2=new ConexaoODBC();	
		    Vector<SyncLinha> synclinha=jdbc.sync_table_dispense();
		 
		    info = new String[synclinha.size()];
		    
		   for(int i=0; i<synclinha.size() ; i++)
		   {
		  	 c2.syncdata_dispense(synclinha.get(i));
		  	 
		  	info[i] = "A processar  nid  " + synclinha.get(i).getNid();
		   }
		 
//		   MessageBox conexaoACCESS = new MessageBox(new Shell(), SWT.ICON_INFORMATION
//					| SWT.OK);
//       	conexaoACCESS.setText("Dados Sincronizados");
//       	conexaoACCESS
//			.setMessage("A SINCRONIZAÇÃO TERMINADA COM SUCESSO!");
//       	conexaoACCESS.open();
//       	
	    this.setExecuteTime(info.length);
    this.setMayCancel(true);
	    this.setProcessMessage("Por favor aguarde....");
	    this.setShellTitle("Sincronização de Dados");
	    jdbc.delete_sync_temp_dispense();
	  }

	  @Override
	  protected String process(int arg0) {
	    try{
	      Thread.sleep((long)(Math.random() * 300));
	    }catch(Exception e){e.printStackTrace();}
	    
	    return info[arg0 - 1];
	  }

	}

