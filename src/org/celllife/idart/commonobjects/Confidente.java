package org.celllife.idart.commonobjects;

public class Confidente {

	private String nid;
	private String nome;
 
	private String telefone;
	
	public String getNid() {
		return nid;
	}
	public void setNid(String nid) {
		this.nid = nid;
	}




	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	 
	 
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Confidente(String nid, String nome,   String telefone) {
		super();
		this.nid = nid;
		this.nome = nome;
		 
		this.telefone = telefone;
	}
	public Confidente() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
