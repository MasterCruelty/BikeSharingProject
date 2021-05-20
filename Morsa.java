import java.util.*;

/**
 * classe che simula la morsa e il suo blocco/sblocco all'interno di una rastrelliera.
 */
public class Morsa {

	private String tipoMorsa;

	public Morsa(String tipo) {
		this.tipoMorsa = tipo;
	}


	/**
	 * @return tipoMorsa
	 */
	public String getTipoMorsa() {
		return this.tipoMorsa;
	}

	/**
	 * @param tipoMorsa 
	 */
	public void setTipoMorsa(String tipoMorsa) {
		this.tipoMorsa = tipoMorsa;
	}

	public void sblocco() {
		System.out.println("Morsa " + this.getTipoMorsa() + " sbloccata.");
	}

	public void blocca() {
		System.out.println("Morsa " + this.getTipoMorsa() + " bloccata.");
	}

}