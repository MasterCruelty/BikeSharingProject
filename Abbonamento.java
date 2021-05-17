
import java.util.*;

/**
 * 
 */
public abstract class Abbonamento {

	/**
	 * Default constructor
	 */
	public Abbonamento() {
	}

	/**
	 * 
	 */
	private void prezzo;

	/**
	 * 
	 */
	private void scadenza;


	/**
	 * @return
	 */
	public int getPrezzo() {
		// TODO implement here
		return 0;
	}

	/**
	 * @return
	 */
	public Date getScadenza() {
		// TODO implement here
		return null;
	}

	/**
	 * 
	 */
	public void inizioValidità() {
		// TODO implement here
	}

	/**
	 * @param prezzo 
	 * @return
	 */
	public void setPrezzo(int prezzo) {
		// TODO implement here
		return null;
	}

	/**
	 * @param scadenza 
	 * @return
	 */
	public void setScadenza(Date scadenza) {
		// TODO implement here
		return null;
	}

	/**
	 * 
	 */
	public abstract void inizioValidità();

}