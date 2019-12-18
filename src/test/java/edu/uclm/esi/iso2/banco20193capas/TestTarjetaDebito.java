package edu.uclm.esi.iso2.banco20193capas;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaSinTitularesException;
import edu.uclm.esi.iso2.banco20193capas.exceptions.CuentaYaCreadaException;
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
        this.pepe = new Cliente("12345X", "Pepe", "Pérez"); this.pepe.insert();
        this.ana = new Cliente("98765F", "Ana", "López"); this.ana.insert();
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
        }
        	catch (Exception e) {
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
			this.tdPepe.comprar(1234,100);        
			this.tdPepe.comprar(1234,100);
			this.tdPepe.comprar(1234,100);
			assertFalse(this.tdPepe.isActiva());
		} catch (ImporteInvalidoException | SaldoInsuficienteException | TarjetaBloqueadaException
				| PinInvalidoException e) {
		}

    }
    
}