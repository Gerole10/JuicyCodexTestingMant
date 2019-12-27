package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.MovimientoTarjetaCredito;
import edu.uclm.esi.iso2.banco20193capas.model.TarjetaCredito;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMovimientoTarjetaCredito extends TestCase{
	
	@Test
	public void testSetTarjeta() {
		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.setTarjeta(tarjeta);
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testSetId() {
		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.setIdentificacion((long)13);
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testSetImporte() {
		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.setImporte(300.0);
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testSetConcepto() {
		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.setConcepto("Jesús");
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testGetConcepto() {
		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.getConcepto();
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	
	@Test
	public void testGetTarjeta() {

		try {
			TarjetaCredito tarjeta = new TarjetaCredito();
			
			MovimientoTarjetaCredito movi = new MovimientoTarjetaCredito(tarjeta,200.0,"Prueba");
			movi.getTarjeta();
		}catch(Exception e) {
			fail("No se esperaba esta excepción");
		}
	}
	

}
