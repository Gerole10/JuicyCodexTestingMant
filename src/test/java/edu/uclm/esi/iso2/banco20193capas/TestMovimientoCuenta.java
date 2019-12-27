package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.MovimientoCuenta;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMovimientoCuenta extends TestCase{
	
	private Cuenta cuenta;
	private Cliente cliente;
	
	@Before
	public void setUp() {
		this.cuenta = new Cuenta(12);
		this.cliente = new Cliente("12345X","Pepe","Pérez");
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
	}
	
	@Test
	public void testSetId() {
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,100.0,"Prueba");
			movi.setIdentificacion((long) 12);
		}catch(Exception e) {
			fail("No se esperaba que saltase ninguna excepción");
		}
	}
	
	@Test
	public void testSetCuenta() {
		Cuenta cuentanueva = new Cuenta(13);
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,200.0,"Prueba");
			movi.setCuenta(cuentanueva);
		}catch(Exception e) {
			fail("No se esperaba que saliese alguna excepción");
		}
	}
	
	@Test
	public void testSetConcepto() {
		Cuenta cuentanueva = new Cuenta(13);
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,200.0,"Prueba");
			movi.setConcepto("Concepto nuevo");
		}catch(Exception e) {
			fail("No se esperaba que saliese alguna excepción");
		}
	}

	@Test
	public void testSetImporte() {
		Cuenta cuentanueva = new Cuenta(13);
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,200.0,"Prueba");
			movi.setImporte(350.0);
		}catch(Exception e) {
			fail("No se esperaba que saliese alguna excepción");
		}
	}
	
	@Test
	public void testGetConcepto() {
		Cuenta cuentanueva = new Cuenta(13);
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,200.0,"Prueba");
			movi.getConcepto();
		}catch(Exception e) {
			fail("No se esperaba que saliese alguna excepción");
		}
	}
	
	@Test
	public void testGetCuenta() {
		Cuenta cuentanueva = new Cuenta(13);
		try {
			MovimientoCuenta movi = new MovimientoCuenta(cuenta,200.0,"Prueba");
			movi.getCuenta();
		}catch(Exception e) {
			fail("No se esperaba que saliese alguna excepción");
		}
	}
}
