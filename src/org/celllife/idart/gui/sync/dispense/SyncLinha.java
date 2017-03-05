package org.celllife.idart.gui.sync.dispense;

public class SyncLinha {
	private String nid;
private String ultimo_lev;
private String dataproxima;
private String tipo_tarv;
private String regime;
private String linha;
private String ultimo_sesp;
public String getNid() {
	return nid;
}
public void setNid(String nid) {
	this.nid = nid;
}
public String getUltimo_lev() {
	return ultimo_lev;
}
public void setUltimo_lev(String ultimo_lev) {
	this.ultimo_lev = ultimo_lev;
}
public String getTipo_tarv() {
	return tipo_tarv;
}
public void setTipo_tarv(String tipo_tarv) {
	this.tipo_tarv = tipo_tarv;
}
public String getRegime() {
	return regime;
}
public void setRegime(String regime) {
	this.regime = regime;
}
public String getLinha() {
	return linha;
}
public void setLinha(String linha) {
	this.linha = linha;
}
public String getUltimo_sesp() {
	return ultimo_sesp;
}
public void setUltimo_sesp(String ultimo_sesp) {
	this.ultimo_sesp = ultimo_sesp;
}


public String getDataproxima() {
	return dataproxima;
}
public void setDataproxima(String dataproxima) {
	this.dataproxima = dataproxima;
}
public SyncLinha() {
	super();
	// TODO Auto-generated constructor stub
}
public SyncLinha(String nid, String ultimo_lev, String tipo_tarv, String regime, String linha, String ultimo_sesp, String dataproxima) {
	super();
	this.nid = nid;
	this.ultimo_lev = ultimo_lev;
	this.tipo_tarv = tipo_tarv;
	this.regime = regime;
	this.linha = linha;
	this.ultimo_sesp = ultimo_sesp;
	this.dataproxima=dataproxima;
}



}
