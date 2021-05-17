import java.util.*;
import java.text.DateFormat;

/**
 * Classe che rappresenta la carta di credito in possesso da un utente.
 * Utilizzata come unico metodo di pagamento per il prelievo delle bici nel sistema.
 */
public class CartaDiCredito {

	private int numeroCarta;

	private Date scadenza;
	
	private double residuo;

	//costruttore
	public CartaDiCredito(int numeroCarta, Date scadenza) {
		this.numeroCarta = numeroCarta;
		this.scadenza = scadenza;
		this.residuo = residuo;
	}

	/**
	 * @return numeroCarta
	 */
	public int getNumero() {
		return this.numeroCarta;
	}

	/**
	 * @return scadenza
	 */
	public Date getScadenza() {
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
	public void setNumero(int numeroCarta) {
		this.numeroCarta = numeroCarta;
	}

	/**
	 * @param scadenza
	 */
	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}
	
	/**
	 * @param residuo
	 */
	public void setResiduo(double residuo){
		this.residuo = residuo
	}

}