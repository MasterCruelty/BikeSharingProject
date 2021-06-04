import java.sql.SQLException;

public interface UtenteDao{
	
	public void registra(Utente utente)
		throws SQLException;
	public void delete(int codiceutente)
		throws SQLException;
	public Utente getUtente(int codiceutente)
		throws SQLException;
	public void update(Utente utente)
		throws SQLException;
}