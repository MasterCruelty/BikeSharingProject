import java.util.*;

/**
 * classe che rappresenta una bicicletta di tipo tradizionale.
 * Oltre alle componenti ereditate dalla classe bicicletta, vi è un altro parametro per la prima mezz'ora gratuita.
 */
public class Normale extends Bicicletta {

	private boolean mezzoraGratuita = false;
	
	//costruttore
	public Normale(double tariffa = 0.50, String orario, boolean mezzoraGratuita) {
		super(tariffa,orario);
		this.mezzoraGratuita = mezzoraGratuita;
	}

	/**
	 * @return mezzoraGratuita
	 */
	public Boolean getMezzoraGratuita() {
		return this.mezzoraGratuita;
	}

	/**
	 * @param gratis 
	 */
	public void setMezzoraGratuita(Boolean gratis) {
		this.mezzoraGratuita = gratis;
	}
}