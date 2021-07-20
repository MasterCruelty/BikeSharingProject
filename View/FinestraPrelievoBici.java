package View;
import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * classe che rappresenta l'interfaccia grafica relativa al noleggio di una bicicletta da parte di un utente.
 */
public class FinestraPrelievoBici extends JFrame {

	private JLabel lbnormale = new JLabel("Normale");

	private JLabel lbelettrica = new JLabel("Elettrica");

	private JLabel lbseggiolino = new JLabel("Elettrica con Seggiolino");

	private JButton btnormale = new JButton("Noleggia");

	private JButton btelettrica = new JButton("Noleggia");

	private JButton btseggiolino = new JButton("Noleggia");
	
	//costruttore
	public FinestraPrelievoBici() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		this.setLocationRelativeTo(null);
		//aggiungo gli attributi componenti sul pannello
		pannello.add(lbnormale);
		pannello.add(btnormale);
		pannello.add(lbelettrica);
		pannello.add(btelettrica);
		pannello.add(lbseggiolino);
		pannello.add(btseggiolino);
		//aggiungo il pannello con i suoi componenti alla finestra
		this.add(pannello);
	}
	
	public void setTxtButtons(){
		this.btnormale.setText("Sposta");
		this.btelettrica.setText("Sposta");
		this.btseggiolino.setText("Sposta");
	}
	//metodo usato dal controller per intercettare il click sul bottone btnormale.
	public void ascoltoNormale(ActionListener bottoneNormale){
		btnormale.addActionListener(bottoneNormale);
	}
	//metodo usato dal controller per intercettare il click sul bottone btelettrica.
	public void ascoltoElettrica(ActionListener bottoneElettrica){
		btelettrica.addActionListener(bottoneElettrica);
	}
	//metodo usato dal controller per intercettare il click sul bottone btseggiolino.
	public void ascoltoSeggiolino(ActionListener bottoneSeggiolino){
		btseggiolino.addActionListener(bottoneSeggiolino);
	}
}