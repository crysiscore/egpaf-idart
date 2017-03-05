package org.celllife.idart.commonobjects;

public class Endereco {

	private String nid;
	private String celula;
	private String avenida;
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}




	public String getCelula() {
		return celula;
	}
	public void setCelula(String celula) {
		this.celula = celula;
	}
	public String getAvenida() {
		return avenida;
	}
	public void setAvenida(String avenida) {
		this.avenida = avenida;
	}
	public Endereco(String nid, String celula, String avenida) {
		super();
		this.nid = nid;
		this.celula = celula;
		this.avenida = avenida;
	}
	public Endereco() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
