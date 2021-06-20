import java.util.*;

/**
 * Classe che rappresenta un oggetto di tipo bicicletta.
 * I diversi tipi di bicicletta ereditano questa classe astratta.
 */
public abstract class Bicicletta {

	private double tariffa;

	private String orarioPrelievo;
	
	//costruttore
	public Bicicletta(double tariffa, String orarioPrelievo) {
		this.tariffa = tariffa;
		this.orarioPrelievo = orarioPrelievo;
	}

	/**
	* @return tariffa
	*/
	public double getTariffa(){
		return this.tariffa;
	}
	
	/**
	 * @param tariffa 
	 */
	public void setTariffa(double tariffa){
		this.tariffa = tariffa;
	}
	/**
	 * @return orarioPrelievo
	 */
	public String getOrarioPrelievo() {
		return this.orarioPrelievo;
	}

	/**
	 * @param orario 
	 * @return
	 */
	public void setOrarioPrelievo(String orario) {
		this.orarioPrelievo = orario;
	}
}