package edu.uclm.esi.iso2.banco20193capas.model;

public class Compra {

	private double importe;
	private int token;

	public Compra(final double importe, final int token) {
		this.importe = importe;
		this.token = token;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(final double importe) {
		this.importe = importe;
	}

	public int getToken() {
		return token;
	}

	public void setToken(final int token) {
		this.token = token;
	}

	
}
