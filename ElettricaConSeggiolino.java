
import java.util.*;

/**
 * 
 */
public class ElettricaConSeggiolino extends Bicicletta {

	/**
	 * Default constructor
	 */
	public ElettricaConSeggiolino() {
	}


	/**
	 * @return
	 */
	public abstract double getTariffa();

	/**
	 * @param tariffa 
	 * @return
	 */
	public abstract void setTariffa(double tariffa);

}