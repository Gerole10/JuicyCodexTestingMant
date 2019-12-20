package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.Tarjeta;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoAutorizadoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ClienteNoEncontradoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaInvalidaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarjetaCredito extends TestCase{
	
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
	public void testSacarDinero() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(pepe.getNif(), 5000);
			tarjeta.setPin(1234);
			tarjeta.sacarDinero(1234,1);
		}catch(PinInvalidoException e) {
			fail("Es por el pin");
		}catch(CuentaYaCreadaException c) {
			fail("Cuenta ya creada");
		}catch(ClienteNoEncontradoException c) {
			fail("Cliente no encontrado");
		}catch(TarjetaBloqueadaException c) {
			fail("Tarjeta bloqueada");
		}catch(ClienteNoAutorizadoException c) {
			fail("Cliente no autorizado");
		}catch(SaldoInsuficienteException c) {
			fail("Saldo insuficiente");
		}catch(ImporteInvalidoException c) {
			fail("Importe invalido");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
		
	}
	
	@Test
	public void testGetCredito() {
		
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = cuenta.emitirTarjetaCredito("123456g", 5000);
			tarjeta.getCredito();
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
	public void testComprar() {
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = cuenta.emitirTarjetaCredito("123456g", 5000);
			tarjeta.setPin(1223);
			tarjeta.comprar(1223, 200.0);
		}catch(Exception e) {
			fail("No se esperaba ninguna excepción");
		}
	}
	@Test
	public void testComprarSaldoInsuficiente() {
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = cuenta.emitirTarjetaCredito("123456g", 100);
			tarjeta.setPin(1223);
			tarjeta.comprar(1223, 200.0);
		}catch(SaldoInsuficienteException e) {
			
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testComprarImporteInvalido() {
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = cuenta.emitirTarjetaCredito("123456g", 100);
			tarjeta.setPin(1223);
			tarjeta.comprar(1223, -0.2);
		}catch(ImporteInvalidoException e) {
			
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
}
