import java.util.*;
import java.awt.event.ActionListener;
import javax.swing.*;


public class FinestraAvvio extends JFrame{

	//attributi componenti della finestra
	private JFrame finestra;

	private JLabel titolo = new JLabel("Sistema bike sharing");

	private JButton registrazione = new JButton("Registrazione");

	private JButton accessoRastrelliera = new JButton("Accesso rastrelliera");

	private JButton restituzione = new JButton("Restituzion");
	
	//Costruttore
	public FinestraAvvio() {
		JPanel pannello = new JPanel();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(600,200);
		//aggiungo gli attributi sulla finestra
		pannello.add(titolo);
		pannello.add(registrazione);
		pannello.add(accessoRastrelliera);
		pannello.add(restituzione);
		this.add(pannello);
	}
	public void ascoltoRegistrazione(ActionListener AscoltaBottoneRegistrazione){
		registrazione.addActionListener(AscoltaBottoneRegistrazione);
	}
	public void ascoltoRastrelliera(ActionListener AscoltaBottoneRastrelliera){
		accessoRastrelliera.addActionListener(AscoltaBottoneRastrelliera);
	}
	public void ascoltoRestituzione(ActionListener AscoltaBottoneRestituzione){
		restituzione.addActionListener(AscoltaBottoneRestituzione);
	}

}