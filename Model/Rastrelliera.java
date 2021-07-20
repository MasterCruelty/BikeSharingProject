package Model;
import java.util.*;

/**
 * classe che rappresenta un oggetto di tipo rastrelliera. 
 * Ogni rastrelliera ha un certo numero di posti e diversi tipi di morsa in base alle biciclette posteggiate.
 */
public class Rastrelliera {

	private int numeroPosti;

	private int numeroRastrelliera;
	
	private Bicicletta[] biciclette;
	
	//costruttore
	public Rastrelliera(int numeroPosti, int numeroRastrelliera, Bicicletta[] biciclette) {
		this.numeroPosti = numeroPosti;
		this.numeroRastrelliera = numeroRastrelliera;
		this.biciclette = biciclette;
	}

	/**
	 * @return numeroPosti
	 */
	public int getNumeroPosti() {
		return this.numeroPosti;
	}

	/**
	 * @param posti 
	 */
	public void setNumeroPosti(int posti) {
		this.numeroPosti = posti;
	}

	/**
	 * @return numeroRastrelliera
	 */
	public int getNumeroRastrelliera() {
		return this.numeroRastrelliera;
	}

	/**
	 * @param numero 
	 */
	public void setNumeroRastrelliera(int numero) {
		this.numeroRastrelliera = numero;
	}
	/**
	* @return biciclette
	*/
	public Bicicletta[] getBiciclette(){
		return this.biciclette;
	}
	
	/**
	* @param biciclette
	*/
	public void setBiciclette(Bicicletta[] biciclette){
		this.biciclette = biciclette;
	}
}