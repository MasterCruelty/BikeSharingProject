import java.util.*;

/**
 * sottoclasse che estende la classe abbonamento e rappresenta in particolare l'abbonamento di tipo annuale.
 * Eredita tutti i metodi della classe Abbonamento e implementa inizioValidita() impostando la scadenza a un anno solare dalla data odierna.
 */
public class Annuale extends Abbonamento {
	
	private int prezzo = 36;
	
	private String scadenza;
	
	//costruttore
	public Annuale(int prezzo, String scadenza) {
		super(prezzo, scadenza);
	}
	
	public void inizioValidita() {
		GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
		this.scadenza = String.valueOf(oggi.get(GregorianCalendar.DAY_OF_MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.YEAR+1));
	}
}