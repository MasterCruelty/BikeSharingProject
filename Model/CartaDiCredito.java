package Model;
import java.util.*;
import java.text.DateFormat;

/**
 * Classe che rappresenta la carta di credito in possesso da un utente.
 * Utilizzata come unico metodo di pagamento per il prelievo delle bici nel sistema.
 */
public class CartaDiCredito {

	private long numeroCarta; //il numero della carta di credito.

	private String scadenza;   //la scadenza riportata sulla carta di credito.
	
	private double residuo;  //il denaro residuo prelevabile dalla carta di credito.

	//costruttore
	public CartaDiCredito(long numeroCarta, String scadenza, double residuo) {
		this.numeroCarta = numeroCarta;
		this.scadenza = scadenza;
		this.residuo = residuo;
	}

	/**
	 * @return numeroCarta
	 */
	public long getNumero() {
		return this.numeroCarta;
	}

	/**
	 * @return scadenza
	 */
	public String getScadenza() {
		return this.scadenza;
	}
	
	/**
	 * @return residuo
	 */
	public double getResiduo(){
		return this.residuo;
	}
	
	/**
	 * @param numeroCarta
	 */
	public void setNumero(long numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	/**
	 * @param scadenza
	 */
	public void setScadenza(String scadenza) {
		this.scadenza = scadenza;
	}
	
	/**
	 * @param residuo
	 */
	public void setResiduo(double residuo){
		this.residuo = residuo;
	}

}