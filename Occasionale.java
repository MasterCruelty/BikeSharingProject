import java.util.*;

/**
 * sottoclasse che estende la classe abbonamento e rappresenta in particolare l'abbonamento di tipo Occasionale.
 * In base all'attributo settimanale la classe rappresenta un abbonamento settimanale oppure uno giornaliero.
 * Oltre a ciò implementa il metodo inizioValidita definito in modo astratto nella superclasse.
 */
public class Occasionale extends Abbonamento {

	boolean settimanale;
	
	//costruttore
	public Occasionale(double prezzo, String scadenza,boolean settimanale) {
		super(prezzo,scadenza);
		this.settimanale = settimanale;
		if(settimanale){
			super.setPrezzo(9);
		}
		else{
			super.setPrezzo(4.50);
		}
	}

	public void inizioValidita() {
		if(settimanale){
			Calendar oggi = Calendar.getInstance();
			int giorno = oggi.get(Calendar.DAY_OF_MONTH) + 7;
			int mese = oggi.get(Calendar.MONTH) +1;
			int anno = oggi.get(Calendar.YEAR);
			this.setScadenza(String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
		}
		else{
			Calendar oggi = Calendar.getInstance();
			int giorno = oggi.get(Calendar.DAY_OF_MONTH) + 1;
			int mese = oggi.get(Calendar.MONTH) +1;
			int anno = oggi.get(Calendar.YEAR);
			this.setScadenza(String.valueOf(giorno) + "/" + String.valueOf(mese) + "/" + String.valueOf(anno));
		}
	}
}