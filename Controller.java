
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * 
 */
public class Controller {

	//View 1
	private FinestraAvvio avvio;

	//View 2
	private FinestraRegistrazione registrazione;

	//View 3
	private FinestraRestituzione restituzione;

	//View 4
	private FinestraAccessoRastrelliera rastrelliera;

	//Model
	private ControlloAccessoSblocco accesso;
	
	public Controller(FinestraAvvio avvio, FinestraRegistrazione registrazione, FinestraAccessoRastrelliera rastrelliera, FinestraRestituzione restituzione, ControlloAccessoSblocco accesso){
		this.avvio = avvio;
		this.accesso = accesso;
		this.registrazione = registrazione;
		this.avvio.ascoltoRegistrazione(new RegistrazioneAscolto());
		this.registrazione.ascoltoInviaDati(new confermaDatiAscolto());
		this.accesso = accesso;
	}
	
	public class RegistrazioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			registrazione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Registrazione cliccata");
		}
	}
	
	public class confermaDatiAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			String nome = registrazione.getTxtNome();
			String cognome = registrazione.getTxtCognome();
			String password = registrazione.getTxtPassword();
			String carta = registrazione.getTxtCarta();
			JOptionPane.showMessageDialog(null,"Conferma dati registrazione cliccato\n"+"nome inserito: " + nome +"\ncognome inserito: " + cognome+"\nnumero carta inserita: " + carta+"\npassword inserita: " + password);
		}
	}
	







}