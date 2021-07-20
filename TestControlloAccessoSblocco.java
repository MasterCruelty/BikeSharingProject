import static org.junit.Assert.*;

import org.junit.Test;

public class TestControlloAccessoSblocco {

	@Test
	public void testMulta() {
		long ore = 2;
		long minuti = 1;
		ControlloAccessoSblocco accesso = new ControlloAccessoSblocco(null,null);
		assertTrue(accesso.controlloMulta(ore,minuti));
	}
	@Test
	public void testControlloDati(){
		ControlloAccessoSblocco accesso = new ControlloAccessoSblocco(null,null);
		String abbonamento = "Settimanale";
		assertTrue(accesso.controlloDatiRegistrazione(abbonamento));
		
	}

}
