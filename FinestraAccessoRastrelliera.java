import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *  classe che rappresenta l'interfaccia grafica relativa all'accesso di un utente a una rastrelliera.
 */
public class FinestraAccessoRastrelliera extends JFrame{

	private JLabel lbcodice = new JLabel("Codice utente");

	private JLabel lbpassword = new JLabel("Password");

	private JLabel lbrastrelliera = new JLabel("Numero rastrelliera");

	private JTextField txtcodice = new JTextField(5);

	private JTextField txtpassword = new JTextField(10);

	private JTextField txtrastrelliera = new JTextField(1);

	private JButton btnaccesso = new JButton("Conferma accesso");

	//costruttore
	public FinestraAccessoRastrelliera() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		this.setLocationRelativeTo(null);
		//aggiungo gli attributi componenti sul pannello
		pannello.add(lbcodice);
		pannello.add(txtcodice);
		pannello.add(lbpassword);
		pannello.add(txtpassword);
		pannello.add(lbrastrelliera);
		pannello.add(txtrastrelliera);
		pannello.add(btnaccesso);
		//aggiungo il pannello con i suoi componenti alla finestra
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
	* @return txtpassword
	*/
	public String getTxtPassword(){
		return this.txtpassword.getText();
	}
	/**
	* @param String password
	*/
	public void setTxtPassword(String password){
		this.txtpassword.setText(password);
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
	//metodo usato dal controller per intercettare il click sul bottone per confermare i dati inseriti.
	public void ascoltoAccesso(ActionListener bottoneAccesso){
		btnaccesso.addActionListener(bottoneAccesso);
	}
}