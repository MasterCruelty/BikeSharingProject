import java.util.*;

/**
 * classe che rappresenta una bicicletta di tipo tradizionale.
 * Oltre alle componenti ereditate dalla classe bicicletta, vi è un altro parametro per la prima mezz'ora gratuita.
 */
public class Normale extends Bicicletta {

	private boolean gratuita = false;
	
	//costruttore
	public Normale(double tariffa, String orario, boolean gratis) {
		super(tariffa,orario);
		this.setTariffa(0.50);
		this.gratuita = gratis;
	}

	/**
	 * @return mezzoraGratuita
	 */
	public Boolean getGratuita() {
		return this.gratuita;
	}

	/**
	 * @param gratis 
	 */
	public void setGratuita(Boolean gratis) {
		this.gratuita = gratis;
	}
}