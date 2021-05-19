import java.util.*;

/**
 * Classe che rappresenta un oggetto di tipo abbonamento che può essere acquistato e in seguito posseduto da un utente.
 * I due tipi di abbonamento(occasionale e annuale) sono definiti in classi che estendono questa.
 */
public abstract class Abbonamento {

	private double prezzo;

	private String scadenza;

	//costruttore
	public Abbonamento(double prezzo, String scadenza) {
		this.prezzo = prezzo;
		this.scadenza = scadenza;
	}

	/**
	 * @return prezzo
	 */
	public double getPrezzo() {
		return this.prezzo;
	}

	/**
	 * @return scadenza
	 */
	public String getScadenza() {
		return this.scadenza;
	}

	/**
	 * @param prezzo 
	 */
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

	/**
	 * @param scadenza 
	 */
	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}
	/**
	* @param scadenza
	* @return Boolean
	*/
	public Boolean controlloValidita(String scadenza){
		String[] giornoMeseAnno = scadenza.split("/");
		GregorianCalendar controlloScadenza = new GregorianCalendar(Integer.parseInt(giornoMeseAnno[2]),Integer.parseInt(giornoMeseAnno[1])+1,Integer.parseInt(giornoMeseAnno[0]));
		GregorianCalendar oggi = new GregorianCalendar(GregorianCalendar.YEAR,GregorianCalendar.MONTH+1,GregorianCalendar.DAY_OF_MONTH);
		//controllo se la data odierna non è successiva alla data di scadenza dell'abbonamento.
		if(oggi.before(controlloScadenza)){
			return true;
		}
		else{
			return false;
		}
	}
	//implementato nelle due classi generalizzate
	public abstract void inizioValidita();
}