import java.util.*;

/**
 * sottoclasse che estende la classe abbonamento e rappresenta in particolare l'abbonamento di tipo annuale.
 * Eredita tutti i metodi della classe Abbonamento e implementa inizioValidita() impostando la scadenza a un anno solare dalla data odierna.
 */
public class Annuale extends Abbonamento {
	
	//costruttore
	public Annuale(double prezzo, String scadenza) {
		super(prezzo, scadenza);
		super.setPrezzo(36);
	}
	
	public void inizioValidita() {
		Calendar oggi = Calendar.getInstance();
		int giorno = oggi.get(Calendar.DAY_OF_MONTH);
		int mese = oggi.get(Calendar.MONTH) +1;
		int anno = oggi.get(Calendar.YEAR) + 1;
		this.setScadenza(String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
	}
}