package edu.uclm.esi.iso2.banco20193capas.exceptions;

public class ClienteNoAutorizadoException extends Exception {
	public ClienteNoAutorizadoException(final String nif, final Long identificador) {
		super("El cliente con NIF " + nif + " no está autorizado para operar en la cuenta " + identificador);
	}
}
