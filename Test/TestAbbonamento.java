package Test;
import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class TestAbbonamento {

	@Test
	public void testValidita() {
		//test controllo validità abbonamento annuale.
		String scadenza = "01/01/2023";
		Abbonamento abbonamento = new Annuale(36,scadenza);
		boolean validita = abbonamento.controlloValidita(scadenza);
		assertTrue(validita);
	}
	@Test
	public void testInizioValidita(){
		//test inizio validità su abbonamento settimanale
		Abbonamento settimanale = new Occasionale(0,"",true);
		settimanale.inizioValidita();
		Calendar oggi = Calendar.getInstance();
		int giorno = oggi.get(Calendar.DAY_OF_MONTH) + 7;
		int mese = oggi.get(Calendar.MONTH) +1;
		int anno = oggi.get(Calendar.YEAR);
		assertEquals(settimanale.getScadenza(),String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
		//test inizio validità su abbonamento giornaliero
		Abbonamento giornaliero = new Occasionale(0,"",false);
		giornaliero.inizioValidita();
		giorno = oggi.get(Calendar.DAY_OF_MONTH) + 1;
		mese = oggi.get(Calendar.MONTH) +1;
		anno = oggi.get(Calendar.YEAR);
		assertEquals(giornaliero.getScadenza(),String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
		//test inizio validità su abbonamento annuale
		Abbonamento annuale = new Annuale(36,"");
		annuale.inizioValidita();
		giorno = oggi.get(Calendar.DAY_OF_MONTH);
		mese = oggi.get(Calendar.MONTH) +1;
		anno = oggi.get(Calendar.YEAR) + 1;
		assertEquals(annuale.getScadenza(),String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
	}
}
