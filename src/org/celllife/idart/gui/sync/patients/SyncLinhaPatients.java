package org.celllife.idart.gui.sync.patients;

public class SyncLinhaPatients {
	private String nid;
private String datanasc;
private String pnomes;
private String unomes;
private String sexo;
private String dataabertura;
public String getNid() {
	return nid;
}
public void setNid(String nid) {
	this.nid = nid;
}
public String getDatanasc() {
	return datanasc;
}
public void setDatanasc(String datanasc) {
	this.datanasc = datanasc;
}
public String getPnomes() {
	return pnomes;
}
public void setPnomes(String pnomes) {
	this.pnomes = pnomes;
}
public String getUnomes() {
	return unomes;
}
public void setUnomes(String unomes) {
	this.unomes = unomes;
}
public String getSexo() {
	return sexo;
}
public void setSexo(String sexo) {
	this.sexo = sexo;
}
public String getDataabertura() {
	return dataabertura;
}
public void setDataabertura(String dataabertura) {
	this.dataabertura = dataabertura;
}
public SyncLinhaPatients(String nid, String datanasc, String pnomes, String unomes, String sexo, String dataabertura) {
	super();
	this.nid = nid;
	this.datanasc = datanasc;
	this.pnomes = pnomes;
	this.unomes = unomes;
	this.sexo = sexo;
	this.dataabertura = dataabertura;
}
public SyncLinhaPatients() {
	super();
	// TODO Auto-generated constructor stub
} 
 



}
