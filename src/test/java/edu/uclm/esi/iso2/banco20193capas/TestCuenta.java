package edu.uclm.esi.iso2.banco20193capas;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.*;
import edu.uclm.esi.iso2.banco20193capas.model.*;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCuenta extends TestCase {

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
	public void testCuentaYaInsertada() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();
		Cuenta cuentaPepe = new Cuenta(1);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.addTitular(pepe);
			fail("Esperaba cuentaYaCreadaException");
		} catch (CuentaYaCreadaException e) {
		} catch (CuentaSinTitularesException e) {
			fail("Esperaba cuentaSinTitularesException");
		}
	}

	@Test
	public void testAddTitulares() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		Cliente ana = new Cliente("98765F", "Ana", "López");
		Cliente pablo = new Cliente("95472K", "Pablo", "Álvarez");
		Cliente diego = new Cliente("12345A", "Diego", "Martinez");
		Cliente yoan = new Cliente("23456N", "Yoan", "Carlos");
		pepe.insert();
		ana.insert();
		pablo.insert();
		diego.insert();
		yoan.insert();
		Cuenta cuenta = new Cuenta(1);
		assertTrue(cuenta.getTitulares().size() == 0);
		Cuenta cuentaConLista = new Cuenta();
		cuentaConLista.setId((long) 2);
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		listaClientes.add(yoan);
		listaClientes.add(pablo);
		try {
			cuenta.insert();
			fail("Esperaba cuentaSinTitularesException");
			cuenta.addTitular(pepe);
			assertTrue(cuenta.getTitulares().size() == 1);
			cuenta.addTitular(ana);
			cuenta.addTitular(pablo);
			assertTrue(cuenta.getTitulares().size() == 3);
			cuentaConLista.setTitulares(listaClientes);
			assertTrue(cuentaConLista.getTitulares().size() == 2);
		} catch (CuentaSinTitularesException e) {
		} catch (CuentaYaCreadaException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testIngresoNegativo() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();
		Cuenta cuenta = new Cuenta(1);
		try {
			cuenta.addTitular(pepe);
			cuenta.ingresar(-1);
		} catch (CuentaYaCreadaException e) {
		} catch (ImporteInvalidoException e) {
			fail("Se ha producido ImporteInvalidoException");
		} 
	}
	
	@Test
	public void testSetTitulares() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		Cliente juan = new Cliente("34567Y","Juan","Gómez");
		pepe.insert();
		juan.insert();
		List<Cliente> lista = new ArrayList<Cliente>();
		lista.add(pepe);
		lista.add(juan);
		Cuenta cuenta = new Cuenta(1);
		try {
			cuenta.setTitulares(lista);
		}catch(Exception e) {
			
		}
	}

	@Test
	public void testRetiradaNegativo() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();
		Cuenta cuenta = new Cuenta(1);
		try {
			cuenta.addTitular(pepe);
			cuenta.retirar(-1);
			fail("Se ha producido ImporteInvalidoException");
		} catch (CuentaYaCreadaException e) {
		} catch (ImporteInvalidoException e) {
		} catch (SaldoInsuficienteException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testYaCreada() {
		Cuenta cuenta = new Cuenta(1);
		assertFalse(cuenta.isCreada());
		cuenta.setCreada(true);
		assertTrue(cuenta.isCreada());
	}

	@Test
	public void testRetiradaSinSaldo() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();
		Cuenta cuentaPepe = new Cuenta(1);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e);
		}
		try {
			cuentaPepe.retirar(2000);
			fail("Esperaba SaldoInsuficienteException");
		} catch (ImporteInvalidoException e) {
			fail("Se ha producido ImporteInvalidoException");
		} catch (SaldoInsuficienteException e) {
		}
	}

	@Test
	public void testCreacionDeUnaCuenta() {
		try {
			Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
			pepe.insert();

			Cuenta cuentaPepe = new Cuenta(1);
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);
			assertTrue(cuentaPepe.getSaldo() == 1000);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testNoCreacionDeUnaCuenta() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);

		try {
			cuentaPepe.insert();
			fail("Esperaba CuentaSinTitularesException");
		} catch (CuentaSinTitularesException e) {
		}
	}

	@Test
	public void testTransferencia() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cliente ana = new Cliente("98765F", "Ana", "López");
		ana.insert();

		Cuenta cuentaPepe = new Cuenta(1);
		Cuenta cuentaAna = new Cuenta(2);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaAna.addTitular(ana);
			cuentaAna.insert();

			cuentaPepe.ingresar(1000);
			assertTrue(cuentaPepe.getSaldo() == 1000);

			cuentaPepe.transferir(cuentaAna.getId(), 500, "Alquiler");
			assertTrue(cuentaPepe.getSaldo() == 495);
			assertTrue(cuentaAna.getSaldo() == 500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}

	@Test
	public void testCompraConTC() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();

			cuentaPepe.ingresar(1000);
			cuentaPepe.retirar(200);
			;
			assertTrue(cuentaPepe.getSaldo() == 800);

			TarjetaCredito tc = cuentaPepe.emitirTarjetaCredito("12345X", 1000);
			tc.comprar(tc.getPin(), 300);
			assertTrue(tc.getCreditoDisponible() == 700);
			tc.liquidar();
			assertTrue(tc.getCreditoDisponible() == 1000);
			assertTrue(cuentaPepe.getSaldo() == 500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}

	@Test
	public void testTransferenciaOK() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();
		Cliente ana = new Cliente("98765K", "Ana", "López");
		ana.insert();

		Cuenta cuentaPepe = new Cuenta(1);
		Cuenta cuentaAna = new Cuenta(2);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);
			cuentaAna.addTitular(ana);
			cuentaAna.insert();
			cuentaPepe.transferir(2L, 500, "Alquiler");
			assertTrue(cuentaPepe.getSaldo() == 495);
			assertTrue(cuentaAna.getSaldo() == 500);
		} catch (Exception e) {
			fail("Excepción inesperada");
		}
	}

	@Test
	public void testTransferenciaALaMismaCuenta() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();
			cuentaPepe.ingresar(1000);
			cuentaPepe.transferir(1L, 100, "Alquiler");
			fail("Esperaba CuentaInvalidaException");
		} catch (CuentaInvalidaException e) {
		} catch (Exception e) {
			fail("Se ha lanzado una excepción inesperada: " + e);
		}
	}

	@Test
	public void testCompraPorInternetConTC() {
		Cliente pepe = new Cliente("12345X", "Pepe", "Pérez");
		pepe.insert();

		Cuenta cuentaPepe = new Cuenta(1);
		try {
			cuentaPepe.addTitular(pepe);
			cuentaPepe.insert();

			cuentaPepe.ingresar(1000);
			cuentaPepe.retirar(200);
			;
			assertTrue(cuentaPepe.getSaldo() == 800);

			TarjetaCredito tc = cuentaPepe.emitirTarjetaCredito("12345X", 1000);
			int token = tc.comprarPorInternet(tc.getPin(), 300);
			assertTrue(tc.getCreditoDisponible() == 1000);
			tc.confirmarCompraPorInternet(token);
			assertTrue(tc.getCreditoDisponible() == 700);
			tc.liquidar();
			assertTrue(tc.getCreditoDisponible() == 1000);
			assertTrue(cuentaPepe.getSaldo() == 500);
		} catch (Exception e) {
			fail("Excepción inesperada: " + e.getMessage());
		}
	}
	@Test
	public void testsetID() {
		try {
			Cuenta cuenta = new Cuenta(1);
			cuenta.setId((long)1);
		} catch(Exception e) {
			fail("No se esperaba ninguna excepción");
		}
	}
	
	@Test
	public void testIsCreada() {
		Cuenta cuenta = new Cuenta(12);
		Cliente pepe = new Cliente("12345fg","Pepe","Pérez");
		try {
			pepe.insert();
			cuenta.addTitular(pepe);
			cuenta.isCreada();
		}catch(Exception e) {
			
		}
	}
	
	@Test
	public void testClienteNoAutorizado() {
		Cuenta cuenta = new Cuenta(23);
		Cliente pepe = new Cliente("123456g","Pepe","Otero");
		Cliente jesus = new Cliente("98765t","Jesús","Cabañero");
		try {
			pepe.insert();
			jesus.insert();
			cuenta.addTitular(pepe);
			cuenta.insert();
			TarjetaCredito tarjeta = new TarjetaCredito();
			tarjeta = cuenta.emitirTarjetaCredito(jesus.getNif(), 5000);
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
			
		}catch(SaldoInsuficienteException c) {
			fail("Saldo insuficiente");
		}catch(ImporteInvalidoException c) {
			fail("Importe invalido");
		}catch(CuentaSinTitularesException e) {
			fail("Cuenta sin titulares");
		}
	}
	
}
