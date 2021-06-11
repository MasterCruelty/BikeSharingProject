import java.sql.SQLException;
public class SistemaBikeSharing{
	public static void main(String[] args){
		FinestraAvvio avvio = new FinestraAvvio();
		FinestraRegistrazione registrazione = new FinestraRegistrazione();
		FinestraAccessoRastrelliera rastrelliera = new FinestraAccessoRastrelliera();
		FinestraRestituzione restituzione = new FinestraRestituzione();
		
		ControlloAccessoSblocco accesso = null;
		
		Controller controller = new Controller(avvio, registrazione, rastrelliera, restituzione, accesso);

		avvio.setVisible(true);
	}
}