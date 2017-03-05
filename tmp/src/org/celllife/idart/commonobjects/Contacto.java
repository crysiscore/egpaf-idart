package org.celllife.idart.commonobjects;

public class Contacto {

	private String nid;
	private String telefone;
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Contacto(String nid, String telefone) {
		super();
		this.nid = nid;
		this.telefone = telefone;
	}
	public Contacto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
