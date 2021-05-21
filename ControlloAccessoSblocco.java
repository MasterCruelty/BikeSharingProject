import java.util.*;
import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 * classe che rappresenta il punto di collegamenteo tra le classi Utente, Bicicletta e Rastrelliera.
 * Qui sono presenti le interazioni tra queste classi, usate come attributi della classe ControlloAccessoSblocco.
 */
public class ControlloAccessoSblocco {

	private Utente utente;

	private Bicicletta bici;

	private Rastrelliera rastrelliera;

	//costruttore
	public ControlloAccessoSblocco(Utente utente, Bicicletta bici, Rastrelliera rastrelliera) {
		this.utente = utente;
		this.bici = bici;
		this.rastrelliera = rastrelliera;
	}

	/**
	 * @param bicicletta 
	 * @return double
	 */
	public double calcolaTariffa(Bicicletta bicicletta) throws ParseException{
		GregorianCalendar orario_attuale = new GregorianCalendar();
		String orario_prelievo = bicicletta.getOrarioPrelievo();
		//orario_prelievo = orario_prelievo.split(":");
		
		int ora_attuale = orario_attuale.get(GregorianCalendar.HOUR_OF_DAY);
		int minuto_attuale = orario_attuale.get(GregorianCalendar.MINUTE);
		//ora_prelievo = Integer.parseInt(orario_prelievo[0]);
		//minuto_prelievo = Integer.parseInt(orario_prelievo[1]);
		
		SimpleDateFormat formato_ora = new SimpleDateFormat("HH:mm");
		Date attuale = formato_ora.parse(String.valueOf(ora_attuale) + String.valueOf(minuto_attuale));
		Date prelievo = formato_ora.parse(orario_prelievo);
		long differenza = attuale.getTime() - prelievo.getTime();
		long differenza_ore = differenza / (60 * 60 * 1000);
		long differenza_minuti = differenza / (60 * 1000);
		double tariffa = bicicletta.getTariffa();
		double importo = 0.0;
		if(differenza_minuti > 30){
			importo += tariffa;
		}
		if(differenza_ore > 0){
			importo+= tariffa * (differenza_ore * 2);
		}
		return importo;
	}
	/**
	 * @return utente
	 */
	public Utente getUtente() {
		return this.utente;
	}

	/**
	 * @return bici
	 */
	public Bicicletta getBicicletta() {
		return this.bici;
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
	 * @param statusStudente 
	 * @param staff
	 */
	public void setUtente(String nome, String cognome, boolean statusStudente, boolean staff) {
		this.utente.setNome(nome);
		this.utente.setCognome(cognome);
		this.utente.setStatus(statusStudente);
		this.utente.setStaff(staff);
	}

	/**
	 * @param tariffa 
	 * @param orarioPrelievo 
	 */
	public void setBicicletta(double tariffa, String orarioPrelievo) {
		this.bici.setTariffa(tariffa);
		this.bici.setOrarioPrelievo(orarioPrelievo);
	}

	/**
	 * @param posti 
	 * @param numeroRastrelliera 
	 */
	public void setRastrelliera(int posti, int numeroRastrelliera) {
		this.rastrelliera.setNumeroPosti(posti);
		this.rastrelliera.setNumeroRastrelliera(numeroRastrelliera);
	}

}