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
	public boolean controlloValidita(String scadenza){
		String[] giornoMeseAnno = scadenza.split("/");
		int anno = Integer.parseInt(giornoMeseAnno[2]);
		int mese = Integer.parseInt(giornoMeseAnno[1]);
		int giorno = Integer.parseInt(giornoMeseAnno[0]);
		//istanzio la classe calendar
		Calendar controlloScadenza = Calendar.getInstance();
		//setto anno, mese e giorno con la data di scadenza passata come parametro.
		controlloScadenza.set(anno,mese,giorno);
		Calendar oggi = Calendar.getInstance();
		//aggiusto il mese perchè partono da 0 in Calendar
		int oggimese = oggi.get(Calendar.MONTH) + 1;
		oggi.set(oggi.get(Calendar.YEAR),oggimese,oggi.get(Calendar.DAY_OF_MONTH));
		//controllo se la data odierna non è successiva alla data di scadenza dell'abbonamento.
		if(controlloScadenza.after(oggi)){
			return true;
		}
		else{
			return false;
		}
	}
	//implementato nelle due classi generalizzate
	public abstract void inizioValidita();
}