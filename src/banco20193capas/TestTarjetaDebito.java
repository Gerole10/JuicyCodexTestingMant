package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.ImporteInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.PinInvalidoException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.SaldoInsuficienteException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.TarjetaBloqueadaException;
import edu.uclm.esi.iso2.banco20193capas.model.Cliente;
import edu.uclm.esi.iso2.banco20193capas.model.Cuenta;
import edu.uclm.esi.iso2.banco20193capas.model.Manager;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaDebito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarjetaDebito extends TestCase {

	private Cuenta cuentaPepe, cuentaAna;
	private Cliente pepe, ana;
	private TarjetaDebito tdPepe, tdAna;

	@Before
	public void setUp() {
		Manager.getMovimientoDAO().deleteAll();
		Manager.getMovimientoTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaCreditoDAO().deleteAll();
		Manager.getTarjetaDebitoDAO().deleteAll();
		Manager.getCuentaDAO().deleteAll();
		Manager.getClienteDAO().deleteAll();
		this.pepe = new Cliente("12345X", "Pepe", "Pérez");
		this.pepe.insert();
		this.ana = new Cliente("98765F", "Ana", "López");
		this.ana.insert();
		this.cuentaPepe = new Cuenta(1);
		this.cuentaAna = new Cuenta(2);
		try {
			this.cuentaPepe.addTitular(pepe);
			this.cuentaPepe.insert();
			this.cuentaPepe.ingresar(1000);
			this.cuentaAna.addTitular(ana);
			this.cuentaAna.insert();
			this.cuentaAna.ingresar(1000);
			this.tdPepe = this.cuentaPepe.emitirTarjetaDebito(pepe.getNif());
			this.tdAna = this.cuentaAna.emitirTarjetaDebito(ana.getNif());
		} catch (Exception e) {
			fail("Excepción inesperada en setUp(): " + e);
		}

	}

	@Test
	public void testCambiarPin() {
		this.tdPepe.setPin(0000);
		try {
			this.tdPepe.cambiarPin(0000, 1234);
			this.tdPepe.cambiarPin(5678, 1234);

			fail("Se esperaba PinInvalidoException");
		} catch (PinInvalidoException e) {
		}
	}

	@Test
	public void testBloquearTarjetaDebito() {
		this.tdPepe.setPin(0000);
		try {
			this.tdPepe.comprar(1234, 100);
			this.tdPepe.comprar(1234, 100);
			this.tdPepe.comprar(1234, 100);
			assertFalse(this.tdPepe.isActiva());
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}

	}

	@Test
	public void testSacarDinero() {
		this.tdAna.setPin(0000);
		try {
			this.tdAna.comprar(0000, 500);
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testSacarDineroSinSaldo() {
		this.tdAna.setPin(0000);
		try {
			this.tdAna.sacarDinero(0000, 500);
			this.tdAna.sacarDinero(0000, 10000000);
			fail("Se espera SaldoInsuficienteException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {

		}
	}

	@Test
	public void testSacarDineroExceptionTarjetaBloqueada() {
		this.tdAna.setPin(0000);
		this.tdAna.setActiva(false);
		try {
			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.sacarDinero(0000, 100);
			this.tdAna.sacarDinero(0000, 100);
			this.tdAna.sacarDinero(0000, 100);
			this.tdAna.sacarDinero(0000, 100);
			fail("Se espera TarjetaBloqueadaException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testSacarDineroExceptionPinInvalido() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {
			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.sacarDinero(1234, 100);
			this.tdAna.sacarDinero(1234, 100);
			this.tdAna.sacarDinero(1234, 100);
			this.tdAna.sacarDinero(1234, 100);
			fail("Se espera PinInvalidoException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarExceptionSaldoInsuficiente() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprar(0000, 250);
			this.tdAna.comprar(0000, 350);
			this.tdAna.comprar(0000, 1250);
			fail("Se espera SaldoInsuficienteException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarExceptionTarjetaBloqueada() {
		this.tdAna.setPin(0000);
		this.tdAna.setActiva(false);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprar(0000, 100);
			this.tdAna.comprar(0000, 100);
			this.tdAna.comprar(0000, 250);
			fail("Se espera TarjetaBloqueadaException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarExceptionPinInvalido() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprar(1234, 100);
			this.tdAna.comprar(1234, 100);
			this.tdAna.comprar(1234, 250);
			fail("Se espera PinInvalidoException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarPorInternetExceptionSaldoInsuficiente() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {
			this.cuentaAna.retirar(this.cuentaAna.getSaldo() + 1);
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprarPorInternet(0000, 500);
			this.tdAna.comprarPorInternet(0000, 750);
			fail("Se espera SaldoInsuficienteException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarPorInternetExceptionPinInvalido() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprarPorInternet(1234, 500);
			this.tdAna.comprarPorInternet(1234, 100);
			this.tdAna.comprarPorInternet(1234, 100);
			fail("Se espera PinInvalidoException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarPorInternetExceptionTarjetaBloqueada() {
		this.tdAna.setPin(0000);
		this.tdAna.setActiva(false);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprarPorInternet(0000, 500);
			this.tdAna.comprarPorInternet(0000, 100);
			this.tdAna.comprarPorInternet(0000, 100);
			fail("Se espera TarjetaBloqueadaException");
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprar() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprar(0000, 500);
			this.tdAna.comprar(0000, 350);
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

	@Test
	public void testComprarPorInternet() {
		this.tdAna.setActiva(true);
		this.tdAna.setPin(0000);
		try {

			this.cuentaAna.retirar(this.cuentaAna.getSaldo());
			this.cuentaAna.ingresar(1000);
			this.tdAna.comprarPorInternet(0000, 500);
			this.tdAna.comprarPorInternet(0000, 350);
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}
	}

}