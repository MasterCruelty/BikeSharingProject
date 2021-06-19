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
		Bicicletta[] biciclette = null;
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
		}
		if(check == true){
			Rastrelliera rastrelliera = new Rastrelliera(numeroposti,numerorastrelliera, biciclette);
			return rastrelliera;
		}
		else{
			return null;
		}
	}
	
	public void updateRastrelliera(String tipologia,int codiceutente,int numerorastrelliera,String orarioprelievo) throws SQLException{
		String query = "UPDATE bicicletta "  +
					   "JOIN rastrelliera ON bicicletta.bicirastrelliera =? " +
					   "SET biciutente=?, orarioprelievo =? "  +
					   "WHERE tipologia=? " +
					   "AND biciutente IS NULL" +
					   "LIMIT 1";
		PreparedStatement preparato = connessione.prepareStatement(query);
		preparato.setInt(1,numerorastrelliera);
		preparato.setInt(2,codiceutente);
		preparato.setString(3,orarioprelievo);
		preparato.setString(4,tipologia);
		preparato.executeUpdate();
	}
}