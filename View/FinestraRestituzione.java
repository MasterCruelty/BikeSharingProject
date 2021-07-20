package View;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * classe view che rappresenta l'interfaccia grafica relativa alla fase di restituzione di una bici.
 */
public class FinestraRestituzione extends JFrame{
	
	//attributi componenti della finestra 
	private JLabel lbrastrelliera = new JLabel("numero rastrelliera");

	private JLabel lbcodice = new JLabel("codice utente");

	private JTextField txtrastrelliera = new JTextField(1);

	private JTextField txtcodice = new JTextField(6);

	private JButton btconferma = new JButton("Conferma restituzione");

	//costruttore
	public FinestraRestituzione() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		this.setLocationRelativeTo(null);
		//aggiungo gli attributi al JPanel
		pannello.add(lbrastrelliera);
		pannello.add(txtrastrelliera);
		pannello.add(lbcodice);
		pannello.add(txtcodice);
		pannello.add(btconferma);
		//aggiungo il pannello alla finestra
		this.add(pannello);
	}
	
	/**
	* @return txtcodice
	*/
	public String getTxtCodice(){
		return this.txtcodice.getText();
	}
	/**
	* @param String codice
	*/
	public void setTxtCodice(String codice){
		this.txtcodice.setText(codice);
	}
	
	/**
	* @return txtrastrelliera
	*/
	public String getTxtRastrelliera(){
		return this.txtrastrelliera.getText();
	}
	/**
	* @param String rastrelliera
	*/
	public void setTxtRastrelliera(String rastrelliera){
		this.txtrastrelliera.setText(rastrelliera);
	}
	//metodo usato dal controller per intercettare il click sul bottone conferma.
	public void ascoltoConfermaRestituzione(ActionListener bottoneConferma){
		btconferma.addActionListener(bottoneConferma);
	}

}