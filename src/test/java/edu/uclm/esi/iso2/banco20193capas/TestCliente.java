package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoCuenta;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoTarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Compra;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCliente extends TestCase{
	@Test
	public void testSetId() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.setId((long)12);
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testSetNif() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.setNif("2345678Y");
		}catch(Exception e) {
			fail("No se esperaba excepción");
		}
	}
	
	@Test
	public void testSetNombre() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.setNombre("Jesús");
		}catch(Exception e) {
			fail("No se esperaba excepción");
		}
	}
	
	@Test
	public void testGetNombre() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.getNombre();
		}catch(Exception e) {
			fail("No se esperaba excepción");
		}
	}
	
	@Test
	public void testSetApellidos() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.setApellidos("Lozano");
		}catch(Exception e) {
			fail("No se esperaba excepción");
		}
	}
	
	@Test
	public void testGetApellidos() {
		Cliente pepe = new Cliente("1234567X","Pepe","Otero");
		
		try {
			pepe.getApellidos();
		}catch(Exception e) {
			fail("No se esperaba excepción");
		}
	}
}
