import java.util.*;

/**
 * classe che rappresenta un oggetto di tipo rastrelliera. 
 * Ogni rastrelliera ha un certo numero di posti e diversi tipi di morsa in base alle biciclette posteggiate.
 */
public class Rastrelliera {

	private int numeroPosti;

	private int numeroRastrelliera;

	private Morsa[] morse;
	
	private Bicicletta[] biciclette;
	
	//costruttore
	public Rastrelliera(int numeroPosti, int numeroRastrelliera, Morsa[] morse,Bicicletta[] biciclette) {
		this.numeroPosti = numeroPosti;
		this.numeroRastrelliera = numeroRastrelliera;
		this.morse = morse;
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
	
	/**
	* @return morse
	*/
	public Morsa[] getMorse(){
		return this.morse;
	}
	
	/**
	* @param morse
	*/
	public void setMorse(Morsa[] morse){
		this.morse = morse;
	}
	
	public void rimuoviBicicletta(Rastrelliera rastrelliera, Bicicletta bici){
		Bicicletta[] bicicletteRastrelliera = rastrelliera.getBiciclette();
		for(int i = 0;i < bicicletteRastrelliera.length;i++){
			if(bicicletteRastrelliera[i].getOrarioPrelievo().equals(bici.getOrarioPrelievo())){
				bicicletteRastrelliera[i] = null;
				rastrelliera.setBiciclette(bicicletteRastrelliera);
			}
		}	
	}
}