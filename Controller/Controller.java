package Controller;
import View.*;
import Model.*;
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
		this.avvio.ascoltoStatistiche(new StatisticheAscolto());
		this.registrazione.ascoltoInviaDati(new ConfermaDatiAscolto());
		this.rastrelliera.ascoltoAccesso(new ConfermaAccessoAscolto());
		this.prelievobici.ascoltoNormale(new NormaleAscolto());
		this.prelievobici.ascoltoElettrica(new ElettricaAscolto());
		this.prelievobici.ascoltoSeggiolino(new SeggiolinoAscolto());
		this.restituzione.ascoltoConfermaRestituzione(new ConfermaRestituzioneAscolto());
		this.accesso = accesso;
	}
	
	//listener che alcuni dati statistici sul sistema.
	public class StatisticheAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			String statistica = "";
			UtenteDao userdao = new UtenteDao();
			try{
				statistica = userdao.statistiche();
			}catch(SQLException e){
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(null,statistica);
			return;
		}
	}
	//listener che apre la finestra della registrazione di un abbonamento.
	public class RegistrazioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			registrazione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Inserire i dati richiesti per effettuare la registrazione.");
		}
	}
	
	//listener che gestisce la registrazione di un abbonamento.
	public class ConfermaDatiAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			String nome = registrazione.getTxtNome();
			String cognome = registrazione.getTxtCognome();
			String password = registrazione.getTxtPassword();
			long numero_carta = Long.parseLong(registrazione.getTxtCarta());
			String scadenza_carta = registrazione.getTxtScadenza();
			String tipo_abbonamento = "";
			boolean studente = registrazione.getCheckStudente();
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
			accesso.setUtente(nome,cognome, password, studente,false,null,carta);
			accesso.getUtente().acquista(abbonamento);
			//ri-setto vuote le caselle di testo della interfaccia grafica.
			registrazione.setTxtNome("");
			registrazione.setTxtCognome("");
			registrazione.setTxtPassword("");
			registrazione.setTxtCarta("");
			registrazione.setTxtScadenza("");
			registrazione.setTxtAbbonamento("");
			registrazione.setCheckStudente(false);
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
	
	//listener che apre la finestra dell'accesso a una rastrelliera.
	public class RastrellieraAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			rastrelliera.setVisible(true);
			avvio.setVisible(false);
			JOptionPane.showMessageDialog(null, "Inserire le vostre credenziali per effettuare l'accesso.");
		}
	}
	
	//listener che gestisce il login presso una rastrelliera.
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
					//Se si tratta di un abbonamento occasionale, se la scadenza non esiste e quindi ?? il primo accesso, inizializzo la scadenza ora al primo accesso.
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
															"\nAbbonamento attivato!\nIl tuo abbonamento risulta valido fino a " + scadenza);
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
	
	//listener che gestisce il noleggio o ricolloccamento di una bicicletta normale.
	public class NormaleAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			//istanzio la classe dao per ottenere successivamente i dati della rastrelliera alla quale si vuole accedere.
			//dichiaro gli oggetti necessari per tutte le operazioni possibili.
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			Rastrelliera rastre_nuova = null;
			UtenteDao userdao = new UtenteDao();
			Utente staff = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
				staff = userdao.selectStaff(codice_utente);
				//controllo se l'utente non stia gi?? noleggiando una bicicletta.
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
				//se l'utente ha privilegio da staff, pu?? effettuare l'azione di ricollocamento di una bicicletta.
				if(staff.getStaff()){
					String input_rastrelliera = JOptionPane.showInputDialog("Inserire il numero di rastrelliera sulla quale spostare la bici.");
					int numero_nuova_rastrelliera = Integer.parseInt(input_rastrelliera);
					rastre_nuova = dao.selectRastrelliera(numero_nuova_rastrelliera);
					accesso.setRastrelliera(rastre_nuova.getNumeroPosti(),rastre_nuova.getNumeroRastrelliera(),rastre_nuova.getBiciclette());
					if(accesso.getRastrelliera().getNumeroPosti() > 0){
						try{
							dao.ricollocamentoBicicletta(numero_rastrelliera,numero_nuova_rastrelliera,"normale");
							JOptionPane.showMessageDialog(null,"La bicicletta risulta essere stata correttamente ricollocata dalla rastrelliera " + numero_rastrelliera +
															   " alla rastrelliera " + numero_nuova_rastrelliera);
							return;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"La rastrelliera selezionata non ha posti disponibili!");
						return;
					}
				}
			}
			catch(SQLException e){
				e.printStackTrace();
			}
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
	
	//listener che gestisce il noleggio o ricolloccamento di una bicicletta elettrica.
	public class ElettricaAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			//istanzio la classe dao per ottenere successivamente i dati della rastrelliera alla quale si vuole accedere.
			//dichiaro gli oggetti necessari per tutte le operazioni possibili.
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			Rastrelliera rastre_nuova = null;
			UtenteDao userdao = new UtenteDao();
			Utente staff = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
				staff = userdao.selectStaff(codice_utente);
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
				//se l'utente ha privilegio da staff, pu?? effettuare l'azione di ricollocamento di una bicicletta.
				if(staff.getStaff()){
					String input_rastrelliera = JOptionPane.showInputDialog("Inserire il numero di rastrelliera sulla quale spostare la bici.");
					int numero_nuova_rastrelliera = Integer.parseInt(input_rastrelliera);
					rastre_nuova = dao.selectRastrelliera(numero_nuova_rastrelliera);
					accesso.setRastrelliera(rastre_nuova.getNumeroPosti(),rastre_nuova.getNumeroRastrelliera(),rastre_nuova.getBiciclette());
					if(accesso.getRastrelliera().getNumeroPosti() > 0){
						try{
							dao.ricollocamentoBicicletta(numero_rastrelliera,numero_nuova_rastrelliera,"normale");
							JOptionPane.showMessageDialog(null,"La bicicletta risulta essere stata correttamente ricollocata dalla rastrelliera " + numero_rastrelliera +
															   " alla rastrelliera " + numero_nuova_rastrelliera);
							return;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"La rastrelliera selezionata non ha posti disponibili!");
						return;
					}
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
	
	//listener che gestisce il noleggio o ricolloccamento di una bicicletta elettrica con seggiolino.
	public class SeggiolinoAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			int numero_rastrelliera = Integer.parseInt(rastrelliera.getTxtRastrelliera());
			int codice_utente = Integer.parseInt(rastrelliera.getTxtCodice());
			//istanzio la classe dao per ottenere successivamente i dati della rastrelliera alla quale si vuole accedere.
			//dichiaro gli oggetti necessari per tutte le operazioni possibili.
			RastrellieraDao dao = new RastrellieraDao();
			Rastrelliera rastre = null;
			Rastrelliera rastre_nuova = null;
			UtenteDao userdao = new UtenteDao();
			Utente staff = null;
			try{
				rastre = dao.selectRastrelliera(numero_rastrelliera);
				accesso.setRastrelliera(rastre.getNumeroPosti(),rastre.getNumeroRastrelliera(),rastre.getBiciclette());
				staff = userdao.selectStaff(codice_utente);
				if(dao.controlloNoleggio(codice_utente)){
					JOptionPane.showMessageDialog(null,"Stai gia' noleggiando una bicicletta!");
					prelievobici.setVisible(false);
					avvio.setVisible(true);
					return;
				}
				//se l'utente ha privilegio da staff, pu?? effettuare l'azione di ricollocamento di una bicicletta.
				if(staff.getStaff()){
					String input_rastrelliera = JOptionPane.showInputDialog("Inserire il numero di rastrelliera sulla quale spostare la bici.");
					int numero_nuova_rastrelliera = Integer.parseInt(input_rastrelliera);
					rastre_nuova = dao.selectRastrelliera(numero_nuova_rastrelliera);
					accesso.setRastrelliera(rastre_nuova.getNumeroPosti(),rastre_nuova.getNumeroRastrelliera(),rastre_nuova.getBiciclette());
					if(accesso.getRastrelliera().getNumeroPosti() > 0){
						try{
							dao.ricollocamentoBicicletta(numero_rastrelliera,numero_nuova_rastrelliera,"normale");
							JOptionPane.showMessageDialog(null,"La bicicletta risulta essere stata correttamente ricollocata dalla rastrelliera " + numero_rastrelliera +
															   " alla rastrelliera " + numero_nuova_rastrelliera);
							return;
						}
						catch(SQLException e){
							e.printStackTrace();
						}
					}
					else{
						JOptionPane.showMessageDialog(null,"La rastrelliera selezionata non ha posti disponibili!");
						return;
					}
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
	
	//listener che apre la finestra della restituzione bicicletta.
	public class RestituzioneAscolto implements ActionListener{
		public void actionPerformed(ActionEvent arg0){
			restituzione.setVisible(true);
			JOptionPane.showMessageDialog(null,"Inserire i dati richiesti per effettuare la restituzione.");
		}
	}
	
	//Listener che gestisce la restituzione bicicletta.
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
				accesso.setUtente(utente.getNome(),utente.getCognome(),utente.getPassword(),utente.getStatus(),false,utente.getAbbonamento(),utente.getCarta());
				double importo = 0.0;
				//controllo se effettivamente l'utente ?? in possesso di una bicicletta da restituire.
				if(dao.controlloNoleggio(codice_utente)){
					//controllo se la rastrelliera selezionata ha almeno 1 posto disponibile per restituire la bicicletta.
					if(accesso.getRastrelliera().getNumeroPosti() > 0){
						bici = dao.selectBicicletta(codice_utente);
						if(bici instanceof Normale)
							((Normale)bici).setGratuita(utente.getStatus());
						//calcolo la tariffa relativa all'utilizzo della bicicletta. Se si tratta di uno studente e di una bici normale, il noleggio ?? gratuito.
						importo = accesso.calcolaTariffa(bici);
						accesso.getUtente().paga(importo);
						//aggiorno sul database il residuo della carta dell'utente e aggiorno la rastrelliera e i posti disponibili.
						userdao.updateResiduo(codice_utente,accesso.getUtente().getCarta().getResiduo());
						dao.restituzioneBicicletta(codice_utente,numero_rastrelliera);
						JOptionPane.showMessageDialog(null,"Utente: " + accesso.getUtente().getNome() + " " + accesso.getUtente().getCognome() + 
														   "\nRestituzione confermata! importo totale: " + importo + "\nResiduo: " + accesso.getUtente().getCarta().getResiduo() +
														   "\nSe l'importo supera i 150 euro significa che avete dovuto pagare una multa per eccesso di noleggio(2 ore).");
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