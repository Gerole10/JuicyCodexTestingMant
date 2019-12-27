package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.model.Compra;
import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCompra extends TestCase{
	
	@Test
	public void testSetImporte() {
		try {
			Compra compra = new Compra(12.0,13);
			compra.setImporte(14.0);
		}catch(Exception e) {
			fail("No se esperaba ninguna excepción");
		}
	}
	
	@Test
	public void testSetToken() {
		try {
			Compra compra = new Compra(14.0,12);
			compra.setToken(15);
		}catch(Exception e) {
			fail("No se esperaba ninguna excepción");
		}
	}

}
