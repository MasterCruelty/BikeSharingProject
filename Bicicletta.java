
import java.util.*;

/**
 * 
 */
public abstract class Bicicletta {

	/**
	 * Default constructor
	 */
	public Bicicletta() {
	}

	/**
	 * 
	 */
	private static double tariffa;

	/**
	 * 
	 */
	private Time orarioPrelievo;





	/**
	 * @return
	 */
	public abstract double getTariffa();

	/**
	 * @return
	 */
	public Time getOrarioPrelievo() {
		// TODO implement here
		return null;
	}

	/**
	 * @param orario 
	 * @return
	 */
	public void setOrarioPrelievo(Time orario) {
		// TODO implement here
		return null;
	}

	/**
	 * @param tariffa 
	 * @return
	 */
	public abstract void setTariffa(double tariffa);

}