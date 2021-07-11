import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * classe che rappresenta il punto di collegamenteo tra le classi Utente, Bicicletta e Rastrelliera.
 * Qui sono presenti le interazioni tra queste classi, usate come attributi della classe ControlloAccessoSblocco.
 */
public class ControlloAccessoSblocco {

	private Utente utente = null;

	private Rastrelliera rastrelliera = null;

	//costruttore
	public ControlloAccessoSblocco(Utente utente, Rastrelliera rastrelliera) {
		this.utente = utente;
		this.rastrelliera = rastrelliera;
	}
	//2nd costruttore
	public ControlloAccessoSblocco(){
	}

	/**
	 * @param bicicletta 
	 * @return double
	 */
	public double calcolaTariffa(Bicicletta bicicletta){
		Calendar orario_attuale = Calendar.getInstance();
		String orario_prelievo = bicicletta.getOrarioPrelievo();
			
		int ora_attuale = orario_attuale.get(Calendar.HOUR_OF_DAY);
		int minuto_attuale = orario_attuale.get(Calendar.MINUTE);
		
		SimpleDateFormat formato_ora = new SimpleDateFormat("HH:mm");
		Date attuale = null;
		Date prelievo = null;
		try{
			attuale = formato_ora.parse(String.valueOf(ora_attuale) + ":" + String.valueOf(minuto_attuale) + ":00");
			prelievo = formato_ora.parse(orario_prelievo);
		}
		catch(ParseException e){
			e.printStackTrace();
		}
		long differenza = attuale.getTime() - prelievo.getTime();
		long differenza_ore = differenza / (60 * 60 * 1000);
		long differenza_minuti = differenza / (60 * 1000);
		double tariffa = bicicletta.getTariffa();
		double importo = 0.0;
		if(differenza_minuti > 30 && bicicletta instanceof Normale){
			importo += tariffa;
		}
		else if(differenza_minuti > 30){
			importo += tariffa * 2;
		}
		if(differenza_ore > 0){
			importo+= tariffa * (differenza_ore * 2);
		}
		return importo;
	}
	/**
	* @param tipo_abbonamento
	* @return boolean
	*/
	public boolean controlloDatiRegistrazione(String tipo_abbonamento){
		if(tipo_abbonamento.equals("Giornaliero") || tipo_abbonamento.equals("Settimanale") || tipo_abbonamento.equals("Annuale")){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * @return utente
	 */
	public Utente getUtente() {
		return this.utente;
	}
	/**
	 * @return rastrelliera
	 */
	public Rastrelliera getRastrelliera() {
		return this.rastrelliera;
	}

	/**
	 * @param nome 
	 * @param cognome 
	 * @param password
	 * @param statusStudente 
	 * @param staff
	 */
	public void setUtente(String nome, String cognome, String password, boolean statusStudente, boolean staff, Abbonamento abbonamento, CartaDiCredito carta) {
		this.utente = new Utente(nome,cognome,password, statusStudente,staff,abbonamento,carta);
	}
	/**
	 * @param posti 
	 * @param numeroRastrelliera 
	 */
	public void setRastrelliera(int posti, int numeroRastrelliera,Bicicletta[] biciclette) {
		this.rastrelliera = new Rastrelliera(posti,numeroRastrelliera,biciclette);
	}

}