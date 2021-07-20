package View;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;


public class FinestraRegistrazione extends JFrame{

	//attributi componenti della finestra
	private JLabel lbnome = new JLabel("nome");

	private JLabel lbcognome = new JLabel("cognome");

	private JLabel lbpassword = new JLabel("password");

	private JLabel lbcarta = new JLabel("carta di credito");
	
	private JLabel lbscadenza = new JLabel("scadenza carta");

	private JLabel lbabbonamento = new JLabel("tipologia abbonamento");

	private JTextField txtnome = new JTextField(10);

	private JTextField txtcognome = new JTextField(10);

	private JTextField txtpassword = new JTextField(10);

	private JTextField txtcarta = new JTextField(16);

	private JTextField txtscadenza = new JTextField(4);

	private JTextField txtabbonamento = new JTextField(10);
	
	private JCheckBox checkstudente = new JCheckBox("Studente?");

	private JButton btn_invia = new JButton("conferma");
	
	//costruttore
	public FinestraRegistrazione() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(650,200);
		this.setLocationRelativeTo(null);
		//aggiungo gli attributi componenti sul pannello
		pannello.add(lbnome);
		pannello.add(txtnome);
		pannello.add(lbcognome);
		pannello.add(txtcognome);
		pannello.add(lbpassword);
		pannello.add(txtpassword);
		pannello.add(lbcarta);
		pannello.add(txtcarta);
		pannello.add(lbscadenza);
		pannello.add(txtscadenza);
		pannello.add(lbabbonamento);
		pannello.add(txtabbonamento);
		pannello.add(checkstudente);
		pannello.add(btn_invia);
		//aggiungo il pannello con i suoi componenti alla finestra
		this.add(pannello);
	}
	/**
	* @return txtnome
	*/
	public String getTxtNome(){
		return this.txtnome.getText();
	}
	/**
	* @param nome
	*/
	public void setTxtNome(String nome){
		this.txtnome.setText(nome);
	}
	/**
	* @return txtcognome
	*/
	public String getTxtCognome(){
		return this.txtcognome.getText();
	}
	/**
	* @param cognome
	*/
	public void setTxtCognome(String cognome){
		this.txtcognome.setText(cognome);
	}
	/**
	* @return txtpassword
	*/
	public String getTxtPassword(){
		return this.txtpassword.getText();
	}
	/**
	* @param password
	*/
	public void setTxtPassword(String password){
		this.txtpassword.setText(password);
	}
	/**
	* @return txtcarta
	*/
	public String getTxtCarta(){
		return this.txtcarta.getText();
	}
	/**
	* @param carta
	*/
	public void setTxtCarta(String carta){
		this.txtcarta.setText(carta);
	}
	/**
	* @return txtscadenza
	*/
	public String getTxtScadenza(){
		return this.txtscadenza.getText();
	}
	/**
	* @param scadenza
	*/
	public void setTxtScadenza(String scadenza){
		this.txtscadenza.setText(scadenza);
	}
	/**
	* @return txtabbonamento
	*/
	public String getTxtAbbonamento(){
		return this.txtabbonamento.getText();
	}
	/**
	* @param abbonamento
	*/
	public void setTxtAbbonamento(String abbonamento){
		this.txtabbonamento.setText(abbonamento);
	}
	
	/**
	* @return checkstudente
	*/
	public boolean getCheckStudente(){
		return this.checkstudente.isSelected();
	}
	/**
	* @param selezione
	*/
	public void setCheckStudente(boolean selezione){
		this.checkstudente.setSelected(selezione);
	}
	//metodo usato dal controller per intercettare il click sul bottone per confermare i dati inseriti.
	public void ascoltoInviaDati(ActionListener bottoneInvia){
		btn_invia.addActionListener(bottoneInvia);
	}
}