import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import java.sql.SQLException;

/**
 * Classe che rappresenta il controller nel pattern MVC. 
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
	
	//View 5
	private FinestraPrelievoBici prelievobici;

	//Model
	private ControlloAccessoSblocco accesso;
	
	//costruttore
	public Controller(FinestraAvvio avvio, FinestraRegistrazione registrazione, FinestraAccessoRastrelliera rastrelliera, FinestraPrelievoBici prelievobici, FinestraRestituzione restituzione, ControlloAccessoSblocco accesso){
		this.avvio = avvio;
		this.accesso = accesso;
		this.registrazione = registrazione;
		this.rastrelliera = rastrelliera;
		this.prelievobici = prelievobici;
		this.avvio.ascoltoRegistrazione(new RegistrazioneAscolto());
		this.avvio.ascoltoRastrelliera(new RastrellieraAscolto());
		this.registrazione.ascoltoInviaDati(new ConfermaDatiAscolto());
		this.rastrelliera.ascoltoAccesso(new ConfermaAccessoAscolto());
		this.prelievobici.ascoltoNormale(new NormaleAscolto());
		this.accesso = accesso;
	}
	
	public class RegistrazioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			registrazione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Inserire i dati richiesti per effettuare la registrazione");
		}
	}
	
	public class ConfermaDatiAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			String nome = registrazione.getTxtNome();
			String cognome = registrazione.getTxtCognome();
			String password = registrazione.getTxtPassword();
			long numero_carta = Long.parseLong(registrazione.getTxtCarta());
			String scadenza_carta = registrazione.getTxtScadenza();
			String tipo_abbonamento = "";
			while(true){
				tipo_abbonamento = registrazione.getTxtAbbonamento();
				if(!accesso.controlloDatiRegistrazione(tipo_abbonamento)){
					JOptionPane.showMessageDialog(null,"Formato errato!\nEsempi inserimento:\n"+
												       "scadenza_carta: mm/yyyy\nTipo Abbonamento: Giornaliero/Settimanale/Annuale.");
					return;
				}
				else{
					break;
				}
			}
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
			accesso.setUtente(nome,cognome, password, false,false,null,carta);
			accesso.getUtente().acquista(abbonamento);
			//ri-setto vuote le caselle di testo della interfaccia grafica.
			registrazione.setTxtNome("");
			registrazione.setTxtCognome("");
			registrazione.setTxtPassword("");
			registrazione.setTxtCarta("");
			registrazione.setTxtScadenza("");
			registrazione.setTxtAbbonamento("");
			JOptionPane.showMessageDialog(null,"Dati registrazione confermati:\n"+"nome inserito: " + nome +
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
				dao.registra(accesso.getUtente(),codice_utente,accesso.getUtente().getAbbonamento(),accesso.getUtente().getCarta());
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			//restituisco la conferma della registrazione e visualizzo il codice utente generato e il pagamento effettuato.
			JOptionPane.showMessageDialog(null,"Registrazione confermata!\n Ecco il tuo codice utente personale: " + codice_utente + 
											   "\nHai pagato: " + accesso.getUtente().getAbbonamento().getPrezzo() + 
											   "\nResiduo su carta: " + accesso.getUtente().getCarta().getResiduo());
			registrazione.setVisible(false);
			avvio.setVisible(true);
		}
	}
	
	public class RastrellieraAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			rastrelliera.setVisible(true);
			JOptionPane.showMessageDialog(null, "Inserire le vostre credenziali per effettuare l'accesso.");
		}
	}
	
	public class ConfermaAccessoAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int codice = Integer.parseInt(rastrelliera.getTxtCodice());
			String password = rastrelliera.getTxtPassword();
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			UtenteDao dao = new UtenteDao();
			Utente utente = null;
			Utente staff = null;
			try{
				utente = dao.selectUtente(codice);
				staff = dao.selectStaff(codice);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			if(utente == null){
				if(staff == null){
					JOptionPane.showMessageDialog(null, "Codice utente non esistente.");
				}
				else{
					if(staff.getPassword().equals(password)){
						JOptionPane.showMessageDialog(null, "Accesso staff eseguito.");
						rastrelliera.setVisible(false);
						prelievobici.setTxtButtons();
						prelievobici.setVisible(true);
					}
					else{
						JOptionPane.showMessageDialog(null, "Password errata.");
					}
				}
			}
			else{
				if(utente.getPassword().equals(password)){
					JOptionPane.showMessageDialog(null, "Accesso eseguito.");
					rastrelliera.setVisible(false);
					prelievobici.setVisible(true);
					
				}
				else{
					JOptionPane.showMessageDialog(null, "Password errata.");
				}
			}
		}
	}
	
	public class NormaleAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastrelliera = null;
			try{
				rastrelliera = dao.selectRastrelliera(numero_rastrelliera);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			if(rastrelliera == null){
				JOptionPane.showMessageDialog(null,"Numero rastrelliera non esistente.");
				return;
			}
			Bicicletta[] bicicletta = rastrelliera.getBiciclette();
			boolean check = false;
			String orarioprelievo = "";
			for(int i = 0; i <= bicicletta.length;i++){
				if(bicicletta[i].getTariffa() == 0.50 && bicicletta[i].getOrarioPrelievo().equals("")){
					GregorianCalendar orario_attuale = new GregorianCalendar();
					int ora_attuale = orario_attuale.get(GregorianCalendar.HOUR_OF_DAY);
					int minuto_attuale = orario_attuale.get(GregorianCalendar.MINUTE);
					orarioprelievo = (String.valueOf(ora_attuale) + ":" + String.valueOf(minuto_attuale));
					bicicletta[i].setOrarioPrelievo(orarioprelievo);
					check = true;
					break;
				}
			}
			String tipologia = "normale";
			if(check == true){
				try{
					dao.updateRastrelliera(tipologia,codice_utente,numero_rastrelliera,orarioprelievo);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			
		}
	}
}