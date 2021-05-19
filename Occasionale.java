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
			GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
			super.setScadenza(String.valueOf(oggi.get(GregorianCalendar.DAY_OF_MONTH+7)) + "/" + String.valueOf(oggi.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.YEAR+1)));
		}
		else{
			GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
			super.setScadenza(String.valueOf(oggi.get(GregorianCalendar.DAY_OF_MONTH+1)) + "/" + String.valueOf(oggi.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.YEAR)));
		}
	}
}