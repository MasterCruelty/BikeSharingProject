import static org.junit.Assert.*;

import org.junit.Test;

public class TestUtente {

	@Test
	public void testAcquistoAbbonamento() {
		Abbonamento abbonamento = new Annuale(36,"");
		CartaDiCredito carta = new CartaDiCredito(1234,"04/22",1000);
		Utente utente = new Utente("nome", "cognome", "password",false,false,null,carta);
		utente.acquista(abbonamento);
		assertEquals(utente.getCarta().getResiduo(),1000-36,0.0002);
	}
	@Test
	public void testPagamento(){
		CartaDiCredito carta = new CartaDiCredito(1234,"04/22",1000);
		Utente utente = new Utente("nome", "cognome", "password",false,false,null,carta);
		utente.paga(950.50);
		assertEquals(utente.getCarta().getResiduo(),1000-950.50,0.0002);
	}
}
