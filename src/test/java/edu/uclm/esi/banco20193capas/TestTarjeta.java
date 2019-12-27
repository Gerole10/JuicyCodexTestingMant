package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarjeta extends TestCase{
	
	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
	}
	
	@Test
	public void testSetId() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			Tarjeta tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(pepe.getNif(), 5000);
			
			tarjeta.setId((long)12);
		}catch(CuentaYaCreadaException c) {
			fail("Cuenta ya creada");
		}catch(ClienteNoEncontradoException c) {
			fail("Cliente no encontrado");
		}catch(ClienteNoAutorizadoException c) {
			fail("Cliente no autorizado");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
		
	}
	
	@Test
	public void testSetActiva() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			Tarjeta tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(pepe.getNif(), 5000);
			
			tarjeta.setActiva(true);
		}catch(CuentaYaCreadaException c) {
			fail("Cuenta ya creada");
		}catch(ClienteNoEncontradoException c) {
			fail("Cliente no encontrado");
		}catch(ClienteNoAutorizadoException c) {
			fail("Cliente no autorizado");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
		
	}
	
	@Test
	public void testGetTitular() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			Tarjeta tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(pepe.getNif(), 5000);
			
			tarjeta.getTitular();
		}catch(CuentaYaCreadaException c) {
			fail("Cuenta ya creada");
		}catch(ClienteNoEncontradoException c) {
			fail("Cliente no encontrado");
		}catch(ClienteNoAutorizadoException c) {
			fail("Cliente no autorizado");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
		
	}
	@Test
	public void testGetCuenta() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			Tarjeta tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(pepe.getNif(), 5000);
			
			tarjeta.getCuenta();
		}catch(CuentaYaCreadaException c) {
			fail("Cuenta ya creada");
		}catch(ClienteNoEncontradoException c) {
			fail("Cliente no encontrado");
		}catch(ClienteNoAutorizadoException c) {
			fail("Cliente no autorizado");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
		
	}
}