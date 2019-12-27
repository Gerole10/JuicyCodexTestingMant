package edu.uclm.esi.iso2.banco20193capas.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Representa un movimiento asociado a una tarjeta de crédito
 *
 */
@Entity
public class MovimientoTarjetaCredito {
	@Id @GeneratedValue(strategy = GenerationType.AUTO) 
	private Long identificacion;
	@ManyToOne
	private TarjetaCredito tarjeta;
	
	private double importe;
	private String concepto;
	private boolean liquidado;
	
	public MovimientoTarjetaCredito() {
		//Constructor por defecto
	}

	public MovimientoTarjetaCredito(final TarjetaCredito tarjeta, final double importe, final String concepto) {
		this.importe = importe;
		this.concepto = concepto;
		this.tarjeta = tarjeta;
	}

	public Long getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(final Long identificacion) {
		this.identificacion = identificacion;
	}

	public TarjetaCredito getTarjeta() {
		return tarjeta;
	}

	public void setTarjeta(final TarjetaCredito tarjeta) {
		this.tarjeta = tarjeta;
	}

	public double getImporte() {
		return importe;
	}

	public void setImporte(final double importe) {
		this.importe = importe;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(final String concepto) {
		this.concepto = concepto;
	}

	public boolean isLiquidado() {
		return liquidado;
	}

	public void setLiquidado(final boolean liquidado) {
		this.liquidado = liquidado;
	}
	
	
}
