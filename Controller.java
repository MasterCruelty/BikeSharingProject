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
		this.restituzione = restituzione;
		this.avvio.ascoltoRegistrazione(new RegistrazioneAscolto());
		this.avvio.ascoltoRastrelliera(new RastrellieraAscolto());
		this.avvio.ascoltoRestituzione(new RestituzioneAscolto());
		this.registrazione.ascoltoInviaDati(new ConfermaDatiAscolto());
		this.rastrelliera.ascoltoAccesso(new ConfermaAccessoAscolto());
		this.prelievobici.ascoltoNormale(new NormaleAscolto());
		this.prelievobici.ascoltoElettrica(new ElettricaAscolto());
		this.prelievobici.ascoltoSeggiolino(new SeggiolinoAscolto());
		this.restituzione.ascoltoConfermaRestituzione(new ConfermaRestituzioneAscolto());
		this.accesso = accesso;
	}
	
	public class RegistrazioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			registrazione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Inserire i dati richiesti per effettuare la registrazione.");
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
				if(accesso.getUtente().getAbbonamento() instanceof Annuale){
					accesso.getUtente().getAbbonamento().inizioValidita();
					String scadenza = accesso.getUtente().getAbbonamento().getScadenza();
					dao.updateScadenzaAbbonamento(codice_utente,scadenza);
					JOptionPane.showMessageDialog(null, "Abbonamento attivato!\nIl tuo abbonamento risulta valido fino a " + scadenza);
				}
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
			avvio.setVisible(false);
			JOptionPane.showMessageDialog(null, "Inserire le vostre credenziali per effettuare l'accesso.");
		}
	}
	
	public class ConfermaAccessoAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int codice = Integer.parseInt(rastrelliera.getTxtCodice());
			String password = rastrelliera.getTxtPassword();
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			//istanzio la classe dao per l'imminente select e ottenere i dati dell'utente dal codice utente inserito.
			UtenteDao dao = new UtenteDao();
			Utente utente = null;
			Utente staff = null;
			try{
				//due select differenti e due oggetti differenti per staff e utente 
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
					//setto l'utente come staff dai dati ottenuti tramite la select dal db
					//controllo se la password inserita per lo staff matcha con la password salvata sul database per il codice utente inserito.
					accesso.setUtente(staff.getNome(),staff.getCognome(),staff.getPassword(),staff.getStatus(),staff.getStaff(),null,null);
					if(accesso.getUtente().getPassword().equals(password)){
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
				//setto l'utente tramite i dati ottenuti dalla select dal db
				//controllo se la password inserita matcha con la password salvata sul database per il codice utente inserito.
				accesso.setUtente(utente.getNome(),utente.getCognome(),utente.getPassword(),utente.getStatus(),utente.getStaff(),utente.getAbbonamento(),utente.getCarta());
				if(accesso.getUtente().getPassword().equals(password)){
					RastrellieraDao rastredao = new RastrellieraDao();
					Rastrelliera rastre = null;
					try{
						rastre = rastredao.selectRastrelliera(numero_rastrelliera);
					}
					catch(SQLException e){
						e.printStackTrace();
					}
					if(rastre == null){
						JOptionPane.showMessageDialog(null,"Numero rastrelliera non esistente.");
						return;
					}
					//Se si tratta di un abbonamento occasionale, se la scadenza non esiste e quindi è il primo accesso, inizializzo la scadenza ora al primo accesso.
					if((accesso.getUtente().getAbbonamento() instanceof Occasionale) && accesso.getUtente().getAbbonamento().getScadenza().equals("")){
						accesso.getUtente().getAbbonamento().inizioValidita();
						String scadenza = accesso.getUtente().getAbbonamento().getScadenza();
						try{
							dao.updateScadenzaAbbonamento(codice,scadenza);
						}
						catch(SQLException e){
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null, "Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() +
															"Abbonamento attivato!\nIl tuo abbonamento risulta valido fino a " + scadenza);
					}
					//controllo se l'abbonamento risulta ancora valido confrontando la scadenza riportata con la data di oggi.
					if(accesso.getUtente().getAbbonamento().controlloValidita(accesso.getUtente().getAbbonamento().getScadenza())){
						JOptionPane.showMessageDialog(null, "Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() +
															"\nResiduo su carta: " + accesso.getUtente().getCarta().getResiduo() + 
															"\nAbbonamento valido.\nAccesso eseguito.");
					}
					else{
						JOptionPane.showMessageDialog(null,"Abbonamento scaduto. Non e' possibile accedere al servizio.");
						return;
					}
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
			//istanzio la classe dao per ottenere successivamente i dati della rastrelliera alla quale si vuole accedere.
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				//controllo se l'utente non stia già noleggiando una bicicletta.
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
			boolean check = false;
			String orarioprelievo = "";
			Bicicletta[] bicicletta = accesso.getRastrelliera().getBiciclette();
			for(int i = 0; i < bicicletta.length; i++){
				if(bicicletta[i].getTariffa() == 0.5 && bicicletta[i].getOrarioPrelievo().equals("")){
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
					dao.updateRastrelliera(tipologia,codice_utente,numero_rastrelliera,orarioprelievo,false);
					JOptionPane.showMessageDialog(null, "Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() +
													    "\nOperazione di noleggio riuscita!\nHai noleggiato una bicicletta normale.\nMorsa sbloccata!");
					rastrelliera.setTxtCodice("");
					rastrelliera.setTxtRastrelliera("");
					rastrelliera.setTxtPassword("");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Non ci sono biciclette normali disponibili su questa rastrelliera.");
				prelievobici.setVisible(false);
				avvio.setVisible(true);
			}
		}
	}
	
	public class ElettricaAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
			boolean check = false;
			String orarioprelievo = "";
			Bicicletta[] bicicletta = accesso.getRastrelliera().getBiciclette();
			for(int i = 0; i < bicicletta.length; i++){
				if(bicicletta[i].getTariffa() == 1.5 && bicicletta[i].getOrarioPrelievo().equals("")){
					GregorianCalendar orario_attuale = new GregorianCalendar();
					int ora_attuale = orario_attuale.get(GregorianCalendar.HOUR_OF_DAY);
					int minuto_attuale = orario_attuale.get(GregorianCalendar.MINUTE);
					orarioprelievo = (String.valueOf(ora_attuale) + ":" + String.valueOf(minuto_attuale));
					bicicletta[i].setOrarioPrelievo(orarioprelievo);
					check = true;
					break;
				}
			}
			String tipologia = "elettrica";
			if(check == true){
				try{
					dao.updateRastrelliera(tipologia,codice_utente,numero_rastrelliera,orarioprelievo,false);
					JOptionPane.showMessageDialog(null, "Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() +
													    "\nOperazione di noleggio riuscita!\nHai noleggiato una bicicletta elettrica.\nMorsa sbloccata!");
					rastrelliera.setTxtCodice("");
					rastrelliera.setTxtRastrelliera("");
					rastrelliera.setTxtPassword("");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Non ci sono biciclette normali disponibili su questa rastrelliera.");
				prelievobici.setVisible(false);
				avvio.setVisible(true);
			}
		}
	}
	
	public class SeggiolinoAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
			accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
			boolean check = false;
			String orarioprelievo = "";
			Bicicletta[] bicicletta = accesso.getRastrelliera().getBiciclette();
			for(int i = 0; i < bicicletta.length; i++){
				if(bicicletta[i].getTariffa() == 1.5 && bicicletta[i].getOrarioPrelievo().equals("") && (bicicletta[i] instanceof Elettrica)){
					if(((Elettrica)bicicletta[i]).getSeggiolino() == true){
						GregorianCalendar orario_attuale = new GregorianCalendar();
						int ora_attuale = orario_attuale.get(GregorianCalendar.HOUR_OF_DAY);
						int minuto_attuale = orario_attuale.get(GregorianCalendar.MINUTE);
						orarioprelievo = (String.valueOf(ora_attuale) + ":" + String.valueOf(minuto_attuale));
						bicicletta[i].setOrarioPrelievo(orarioprelievo);
						check = true;
						break;
					}
				}
			}
			String tipologia = "elettrica";
			if(check == true){
				try{
					dao.updateRastrelliera(tipologia,codice_utente,numero_rastrelliera,orarioprelievo,true);
					JOptionPane.showMessageDialog(null,"Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() +
													   "\nOperazione di noleggio riuscita!\nHai noleggiato una bicicletta elettrica con seggiolino.\nMorsa sbloccata!");
					rastrelliera.setTxtCodice("");
					rastrelliera.setTxtRastrelliera("");
					rastrelliera.setTxtPassword("");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
				}
				catch(SQLException e){
					e.printStackTrace();
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "Non ci sono biciclette normali disponibili su questa rastrelliera.");
				prelievobici.setVisible(false);
				avvio.setVisible(true);
			}
		}
	}
	
	public class RestituzioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			restituzione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Inserire i dati richiesti per effettuare la restituzione.");
		}
	}
	
	public class ConfermaRestituzioneAscolto implements ActionListener {
		public void actionPerformed(ActionEvent arg0){
			//prendo i dati inseriti nelle caselle di testo.
			int numero_rastrelliera = Integer.parseInt(restituzione.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(restituzione.getTxtCodice());
			RastrellieraDao dao = new RastrellieraDao();
			UtenteDao userdao = new UtenteDao();
			Rastrelliera rastre = null;
			Utente utente = null;
			Bicicletta bici = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				utente = userdao.selectUtente(codice_utente);
				accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
				accesso.setUtente(utente.getNome(),utente.getCognome(),utente.getPassword(),false,false,utente.getAbbonamento(),utente.getCarta());
				//controllo se effettivamente l'utente è in possesso di una bicicletta da restituire.
				if(dao.controlloNoleggio(codice_utente)){
					//controllo se la rastrelliera selezionata ha almeno 1 posto disponibile per restituire la bicicletta.
					if(accesso.getRastrelliera().getNumeroPosti() > 0){
						bici = dao.selectBicicletta(codice_utente);
						//calcolo la tariffa relativa all'utilizzo della bicicletta.
						double importo = accesso.calcolaTariffa(bici);
						accesso.getUtente().paga(importo);
						//aggiorno sul database il residuo della carta dell'utente e aggiorno la rastrelliera e i posti disponibili.
						userdao.updateResiduo(codice_utente,accesso.getUtente().getCarta().getResiduo());
						dao.restituzioneBicicletta(codice_utente,numero_rastrelliera);
						JOptionPane.showMessageDialog(null,"Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() + 
														   "\nRestituzione confermata! importo totale: " + importo + "\nResiduo: " + accesso.getUtente().getCarta().getResiduo() +
														   "\nSe l'importo supera i 150 euro significa che avete dovuto pagare una multa per eccesso di noleggio.");
						restituzione.setVisible(false);
						restituzione.setTxtCodice("");
						restituzione.setTxtRastrelliera("");
					}
					else{
						JOptionPane.showMessageDialog(null,"Tutti i posti sono occupati in questa rastrelliera, provarne un'altra.");
						return;
					}
				}
				else{
					JOptionPane.showMessageDialog(null,"Non hai noleggiato nessuna bicicletta, restituzione impossibile.");
					return;
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
}