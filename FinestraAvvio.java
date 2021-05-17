import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;


public class FinestraAvvio extends JFrame{

	//attributi componenti della finestra
	private JFrame finestra;

	private JLabel titolo = new JLabel("Sistema bike sharing");

	private JButton registrazione = new JButton("Registrazione");

	private JButton accessoRastrelliera = new JButton("Accesso rastrelliera");

	private JButton restituzione = new JButton("Restituzione");
	
	//Costruttore
	public FinestraAvvio() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		//aggiungo gli attributi componenti sul pannello
		pannello.add(titolo);
		pannello.add(registrazione);
		pannello.add(accessoRastrelliera);
		pannello.add(restituzione);
		//aggiungo il pannello con i suoi componenti alla finestra
		this.add(pannello);
	}
	//metodo usato dal controller per intercettare il click sul bottone registrazione
	public void ascoltoRegistrazione(ActionListener bottoneRegistrazione){
		registrazione.addActionListener(bottoneRegistrazione);
	}
	//metodo usato dal controller per intercettare il click sul bottone accesso rastrelliera.
	public void ascoltoRastrelliera(ActionListener bottoneRastrelliera){
		accessoRastrelliera.addActionListener(bottoneRastrelliera);
	}
	//metodo usato dal controller per intercettare il click sul bottone restituzione.
	public void ascoltoRestituzione(ActionListener bottoneRestituzione){
		restituzione.addActionListener(bottoneRestituzione);
	}

}