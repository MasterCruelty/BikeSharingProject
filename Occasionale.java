import java.util.*;

/**
 * sottoclasse che estende la classe abbonamento e rappresenta in particolare l'abbonamento di tipo Occasionale.
 * In base all'attributo settimanale la classe rappresenta un abbonamento settimanale oppure uno giornaliero.
 */
public class Occasionale extends Abbonamento {

	private int prezzo;
	
	private String scadenza;
	
	Boolean settimanale;
	
	//costruttore
	public Occasionale(int prezzo, String scadenza,Boolean settimanale) {
		super(prezzo,scadenza);
		this.settimanale = settimanale;
	}

	public void inizioValidita() {
		if(settimanale){
			GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
			this.scadenza = String.valueOf(oggi.get(GregorianCalendar.DAY_OF_MONTH+7)) + "/" + String.valueOf(oggi.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.YEAR+1));
		}
		else{
			GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
		this.scadenza = String.valueOf(oggi.get(GregorianCalendar.DAY_OF_MONTH+1)) + "/" + String.valueOf(oggi.get(GregorianCalendar.MONTH)) + "/" + String.valueOf(oggi.get(GregorianCalendar.YEAR));
		}
	}
}