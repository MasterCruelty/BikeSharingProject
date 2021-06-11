import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.SQLException;

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
			int numero_carta = Integer.parseInt(registrazione.getTxtCarta());
			String scadenza_carta = registrazione.getTxtScadenza();
			String tipo_abbonamento = registrazione.getTxtAbbonamento();
			CartaDiCredito carta = new CartaDiCredito(numero_carta,scadenza_carta,1000);
			Abbonamento abbonamento;
			if(tipo_abbonamento.equals("Settimanale")){
				abbonamento = new Occasionale(0,"",true);
			}
			else if(tipo_abbonamento.equals("Giornaliero")){
				abbonamento = new Occasionale(0,"",false);
			}
			else{
				abbonamento = new Annuale(0,"");
			}
			//creo un oggetto di tipo utente da dare come argomento alla classe DAO per l'inserimento dei dati su database. 
			Utente utente = new Utente(nome, cognome, false,false, null, carta);
			utente.acquista(abbonamento);
			//ri-setto vuote le caselle di testo della interfaccia grafica.
			registrazione.setTxtNome("");
			registrazione.setTxtCognome("");
			registrazione.setTxtPassword("");
			registrazione.setTxtCarta("");
			registrazione.setTxtScadenza("");
			registrazione.setTxtAbbonamento("");
			JOptionPane.showMessageDialog(null,"Conferma dati registrazione cliccato\n"+"nome inserito: " + nome +
												"\ncognome inserito: " + cognome+"\nnumero carta inserita: " + numero_carta+
												"\npassword inserita: " + password + "\nscadenza carta inserita: " + scadenza_carta +
												"\nabbonamento scelto: " + tipo_abbonamento);
			//creo un'istanza della classe DAO per la registrazione dell'utente su database.
			UtenteDao dao = new UtenteDao();
			//genero il codice utente univoco per l'utente
			int minimo = 10000;
			int massimo = 99999;
			int codice_utente = (int)Math.floor(Math.random()*(massimo-minimo+1));
			try{
				dao.registra(utente,codice_utente,password,abbonamento,carta);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			//restituisco la conferma della registrazione e visualizzo il codice utente generato e il pagamento effettuato.
			JOptionPane.showMessageDialog(null,"Registrazione confermata!\n Ecco il tuo codice utente personale: " + codice_utente + 
											   "\nHai pagato: " + utente.getAbbonamento().getPrezzo() + 
											   "\nResiduo su carta: " + utente.getCarta().getResiduo());
			registrazione.setVisible(false);
			avvio.setVisible(true);
		}
	}
}