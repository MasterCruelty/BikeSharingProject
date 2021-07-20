import View.*;
import Model.*;
import Controller.*;
import java.sql.SQLException;
public class SistemaBikeSharing{
	public static void main(String[] args){
		FinestraAvvio avvio = new FinestraAvvio();
		FinestraRegistrazione registrazione = new FinestraRegistrazione();
		FinestraAccessoRastrelliera rastrelliera = new FinestraAccessoRastrelliera();
		FinestraRestituzione restituzione = new FinestraRestituzione();
		FinestraPrelievoBici prelievobici = new FinestraPrelievoBici();
		
		ControlloAccessoSblocco accesso = new ControlloAccessoSblocco();
		
		Controller controller = new Controller(avvio, registrazione, rastrelliera, prelievobici, restituzione, accesso);

		avvio.setVisible(true);
	}
}