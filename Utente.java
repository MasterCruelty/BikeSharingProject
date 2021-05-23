import java.util.*;

/**
 * classe che rappresenta un utente e le operazioni che può effettuare.
 * Tramite due attributi boolean viene specificato che l'utente è uno studente, un membro del personale o nessuno dei due.
 */
public class Utente {

	private String nome;

	private String cognome;

	private boolean statusStudente;

	private boolean staff;

	private Abbonamento abbonamento;

	private CartaDiCredito carta;
	
	//costruttore
	public Utente(String nome, String cognome, Boolean statusStudente, Boolean staff, Abbonamento abbonamento, CartaDiCredito carta) {
		this.nome = nome;
		this.cognome = cognome;
		this.statusStudente = statusStudente;
		this.staff = staff;
		this.abbonamento = abbonamento;
		this.carta = carta;
	}

	/**
	 * @return nome
	 */
	public String getNome() {
		return this.nome;
	}

	/**
	 * @return cognome
	 */
	public String getCognome() {
		// TODO implement here
		return this.cognome;
	}

	/**
	 * @return statusStudente
	 */
	public boolean getStatus() {
		// TODO implement here
		return this.statusStudente;
	}

	/**
	 * @return abbonamento
	 */
	public Abbonamento getAbbonamento() {
		// TODO implement here
		return this.abbonamento;
	}

	/**
	 * @return carta
	 */
	public CartaDiCredito getCarta() {
		// TODO implement here
		return this.carta;
	}

	/**
	 * @return staff
	 */
	public boolean getStaff() {
		// TODO implement here
		return this.staff;
	}

	/**
	 * @param nome 
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @param cognome 
	 */
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	/**
	 * @param status 
	 */
	public void setStatus(boolean status) {
		this.statusStudente = status;
	}

	/**
	 * @param carta 
	 */
	public void setCarta(CartaDiCredito carta) {
		this.carta = carta;
	}

	/**
	 * @param abbonamento 
	 */
	public void setAbbonamento(Abbonamento abbonamento) {
		this.abbonamento = abbonamento;
	}

	/**
	 * @param staff 
	 */
	public void setStaff(boolean staff) {
		this.staff = staff;
	}

	/**
	 * @param carta 
	 */
	public void paga(double importo) {
		this.carta.setResiduo(this.carta.getResiduo() - importo);
	}

	/**
	 * @param carta 
	 */
	public void acquista(Abbonamento abbonamento) {
		this.carta.setResiduo(this.carta.getResiduo() - abbonamento.getPrezzo());
		this.abbonamento = abbonamento;
	}

	/**
	 * @param bici 
	 * @param numero 
	 */
	public void ricolloca(Bicicletta bici, Rastrelliera numero) {
		Bicicletta[] bicicletteRastrelliera = numero.getBiciclette();
		if(bicicletteRastrelliera.length == numero.getNumeroPosti()){
			System.out.println("Rastrelliera " + numero.getNumeroRastrelliera() + " già completa");
		}
		else{
			for(int i = 0;i<bicicletteRastrelliera.length;i++){
				if(bicicletteRastrelliera[i] == null){
					bicicletteRastrelliera[i] = bici;
					break;
				}
			}
		}
	}
}