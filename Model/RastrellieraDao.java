package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
* classe DAO che contiene tutte le query relative alla classe rastrelliera.
*/
public class RastrellieraDao {

	static Connection connessione = ConnessioneDB.getConnessione();
	
	public Rastrelliera selectRastrelliera(int numerorastrelliera) throws SQLException{
		String query = "SELECT * " +
					   "FROM rastrelliera " +
					   "JOIN bicicletta " +
					   "ON rastrelliera.numerorastrelliera = bicicletta.bicirastrelliera " + 
					   "WHERE rastrelliera.numerorastrelliera =?";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,numerorastrelliera);
		ResultSet rs = preparato.executeQuery();
		boolean check = false;
		boolean seggiolino = false;
		int numeroposti, numero_rastrelliera, i;
		numeroposti = i = 0;
		double tariffa = 0.0;
		String tipologia = "";
		String orarioprelievo = "";
		Bicicletta[] biciclette = new Bicicletta[15];
		while(rs.next()){
			check = true;
			numeroposti = rs.getInt("postidisponibili");
			tariffa = rs.getDouble("tariffa");
			tipologia = rs.getString("tipologia");
			seggiolino = rs.getBoolean("seggiolino");
			orarioprelievo = rs.getString("orarioprelievo");
			if(orarioprelievo == null)
				orarioprelievo = "";
			if(tipologia.equals("normale")){
				biciclette[i] = new Normale(tariffa,orarioprelievo,true);
			}
			else if(tipologia.equals("elettrica")){
				biciclette[i] = new Elettrica(tariffa,orarioprelievo,seggiolino);
			}
			i++;
		}
		if(check == true){
			Rastrelliera rastrelliera = new Rastrelliera(numeroposti,numerorastrelliera, biciclette);
			return rastrelliera;
		}
		else{
			return null;
		}
	}
	
	public Bicicletta selectBicicletta(int codiceutente) throws SQLException{
		String query = "SELECT * " +
					   "FROM bicicletta " +
					   "WHERE  biciutente=?";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,codiceutente);
		ResultSet rs = preparato.executeQuery();
		boolean check = false;
		String tipologia = "";
		double tariffa = 0.0;
		String orarioprelievo = "";
		boolean seggiolino = false;
		while(rs.next()){
			check = true;
			tariffa = rs.getDouble("tariffa");
			orarioprelievo = rs.getString("orarioprelievo");
			tipologia = rs.getString("tipologia");
			seggiolino = rs.getBoolean("seggiolino");
		}
		if(check == true){
			if(tipologia.equals("normale")){
				return new Normale(tariffa,orarioprelievo,false);
			}
			else{
				return new Elettrica(tariffa,orarioprelievo,seggiolino);
			}
		}
		else{
			return null;
		}
	}
	
	public boolean controlloNoleggio(int codice) throws SQLException{
		String query = "SELECT biciutente " +
					   "FROM bicicletta " +
					   "WHERE biciutente IS NOT NULL";
		PreparedStatement preparato = connessione.prepareStatement(query);
		ResultSet rs = preparato.executeQuery();
		boolean check = false;
		int biciutente = 0;
		while(rs.next()){
			check = true;
			biciutente = rs.getInt("biciutente");
			if(biciutente == codice){
				return true;
			}
		}
		return false;
	}
	
	public void updateRastrelliera(String tipologia,int codiceutente,int numerorastrelliera,String orarioprelievo,boolean seggiolino) throws SQLException{
		String query = "UPDATE bicicletta "  +
					   "SET biciutente=?, orarioprelievo=?"  +
					   "WHERE tipologia=? " +
					   "AND biciutente IS NULL " +
					   "AND orarioprelievo IS NULL " +
					   "AND bicicletta.bicirastrelliera=? " +
					   "AND seggiolino=? " +
					   "LIMIT 1 ";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,codiceutente);
		preparato.setString(2,orarioprelievo);
		preparato.setString(3,tipologia);
		preparato.setInt(4,numerorastrelliera);
		preparato.setBoolean(5,seggiolino);
		preparato.executeUpdate();
		//aggiorno la rastrelliera modificando il numero di posti disponibili.
		query = "UPDATE rastrelliera " +
				"SET postidisponibili=postidisponibili+1 " +
				"WHERE numerorastrelliera=? ";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1,numerorastrelliera);
		preparato.executeUpdate();
	}
	
	public void restituzioneBicicletta(int codiceutente,int numero_rastrelliera) throws SQLException{
		String query = "UPDATE bicicletta " +
					   "SET biciutente=NULL, orarioprelievo=NULL, bicirastrelliera=? " +
					   "WHERE biciutente=? ";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,numero_rastrelliera);
		preparato.setInt(2,codiceutente);
		preparato.executeUpdate();
		
		//aggiorno la rastrelliera modificando il numero di posti disponibili.
		query = "UPDATE rastrelliera " +
				"SET postidisponibili=postidisponibili-1 " +
				"WHERE numerorastrelliera=? ";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1,numero_rastrelliera);
		preparato.executeUpdate();
	}
	
	public void ricollocamentoBicicletta(int vecchia_rastrelliera,int nuova_rastrelliera,String tipologia) throws SQLException {
		String query = "UPDATE bicicletta " +
					   "SET bicirastrelliera=? " +
					   "WHERE bicirastrelliera=? " +
					   "AND tipologia=? " +
					   "LIMIT 1";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,nuova_rastrelliera);
		preparato.setInt(2,vecchia_rastrelliera);
		preparato.setString(3,tipologia);
		preparato.executeUpdate();
		
		//aggiorno i posti disponibili nella rastrelliera da cui ho prelevato la bicicletta e quella su cui l'ho depositata.
		query = "UPDATE rastrelliera " +
				"SET postidisponibili=postidisponibili-1 " +
				"WHERE numerorastrelliera=?";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1,nuova_rastrelliera);
		preparato.executeUpdate();
		
		query = "UPDATE rastrelliera " +
				"SET postidisponibili=postidisponibili+1 " +
				"WHERE numerorastrelliera=?";
		preparato = connessione.prepareStatement(query);
		preparato.setInt(1,vecchia_rastrelliera);
		preparato.executeUpdate();
	}
}