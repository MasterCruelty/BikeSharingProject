import java.util.*;

/**
 * classe che rappresenta le due tipologie di bicicletta elettrica.
 * Il parametro seggiolino definisce se è presente o meno sulla bicicletta.
 */
public class Elettrica extends Bicicletta {

	private boolean seggiolino;
	//costruttore
	public Elettrica(double tariffa,String orario, boolean seggiolino) {
		super(tariffa,orario);
		super.setTariffa(1.50);
		this.seggiolino = seggiolino;
	}
	/**
	* @return seggiolino
	*/
	public boolean getSeggiolino(){
		return this.seggiolino;
	}
	
	/**
	* @param seggiolino
	*/
	public void setSeggiolino(boolean seggiolino){
		this.seggiolino = seggiolino;
	}
}