import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteDao{

	static Connection connessione = ConnessioneDB.getConnessione();
	
	
	public void registra(Utente utente, int codice, Abbonamento abbonamento, CartaDiCredito carta) throws SQLException {
		String query = "INSERT INTO utenti(codiceutente,nome,cognome,password,studente,staff) " + 
					   "VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1, codice);
		preparato.setString(2, utente.getNome());
		preparato.setString(3, utente.getCognome());
		preparato.setString(4, utente.getPassword());
		preparato.setBoolean(5, utente.getStatus());
		preparato.setBoolean(6, utente.getStaff());
		preparato.executeUpdate();
		
		query = "INSERT INTO abbonamento(codiceutente,prezzo,scadenza_abbo,tipologia) " +
				"VALUES (?, ?, ?, ?)";
		String tipologia = "";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1, codice);
		preparato.setDouble(2, abbonamento.getPrezzo());
		preparato.setString(3, abbonamento.getScadenza());
		if(abbonamento.getPrezzo() == 36)
			tipologia = "Annuale";
		else
			tipologia = "Occasionale";
		preparato.setString(4, tipologia);
		preparato.executeUpdate();
		
		query = "INSERT INTO cartadicredito(codiceutente,numerocarta,scadenza_carta,residuo) " +
				"VALUES (?, ?, ?, ?)";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1, codice);
		preparato.setLong(2, carta.getNumero());
		preparato.setString(3, carta.getScadenza());
		preparato.setDouble(4,carta.getResiduo());
		preparato.executeUpdate();
	}

	
	public void cancella(int codiceutente) throws SQLException{
		String query = "DELETE FROM utenti where codiceutente =?";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,codiceutente);
		preparato.executeUpdate();
	}
	
	public Utente selectStaff(int codiceutente) throws SQLException{
		String query = "SELECT * " +
					   "FROM utenti " +
					   "WHERE utenti.codiceutente =?";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,codiceutente);
		ResultSet rs = preparato.executeQuery();
		boolean check = false;
		String nome, cognome, password;
		nome = cognome = password = "";
		boolean studente,staff;
		studente = staff = false;
		while(rs.next()){
			check = true;
			nome = rs.getString("nome");
			cognome = rs.getString("cognome");
			password = rs.getString("password");
			studente = rs.getBoolean("studente");
			staff = rs.getBoolean("staff");
		}
		if(check == true){
			Utente utente = new Utente(nome,cognome,password,studente,staff,null,null);
			return utente;
		}
		else
			return null;
	}
	
	public Utente selectUtente(int codiceutente) throws SQLException{
		String query = "SELECT * " +
					   "FROM utenti " +
					   "JOIN abbonamento ON utenti.codiceutente = abbonamento.codiceutente " +
					   "JOIN cartadicredito ON utenti.codiceutente = cartadicredito.codiceutente " +
					   "WHERE utenti.codiceutente =?";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,codiceutente);
		ResultSet rs = preparato.executeQuery();
		boolean check = false;
		String nome, cognome, password, scadenzaabbo,scadenzacarta,tipologia;
		nome = cognome = password = scadenzaabbo = scadenzacarta = tipologia = "";
		boolean studente,staff;
		studente = staff = false;
		double prezzo, residuo;
		prezzo = residuo = 0.0;
		int numero = 0;
		while(rs.next()){
			check = true;
			nome = rs.getString("nome");
			cognome = rs.getString("cognome");
			password = rs.getString("password");
			studente = rs.getBoolean("studente");
			staff = rs.getBoolean("staff");
			prezzo = rs.getDouble("prezzo");
			scadenzaabbo = rs.getString("scadenza_abbo");
			tipologia = rs.getString("tipologia");
			numero = rs.getInt("numerocarta");
			scadenzacarta = rs.getString("scadenza_carta");
			residuo = rs.getDouble("residuo");
		}
		Abbonamento abbonamento;
		if(check == true){
			CartaDiCredito carta = new CartaDiCredito(numero,scadenzacarta,residuo);
			if(tipologia.equals("Occasionale")){
				boolean settimanale = false;
				if(prezzo == 9){
					settimanale = true;
				}
				abbonamento = new Occasionale(prezzo,scadenzaabbo,settimanale);
			}
			else
				abbonamento = new Annuale(prezzo,scadenzaabbo);
			Utente utente = new Utente(nome,cognome, password, studente,staff, abbonamento, carta);
			return utente;
		}
		else
			return null;
	}
}