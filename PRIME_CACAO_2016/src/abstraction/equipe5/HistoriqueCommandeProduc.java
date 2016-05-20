package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.commun.CommandeProduc;

public class HistoriqueCommandeProduc {
	private List<CommandeProduc> hist = new ArrayList<CommandeProduc>();

	public HistoriqueCommandeProduc() {	
		this.hist = new ArrayList<CommandeProduc>();
	}

	public List<CommandeProduc> getHist() {
		return this.hist;
	}

	public CommandeProduc commande(int i) {
		return this.getHist().get(i);
	}

	public void ajouter(CommandeProduc commande) {
		this.getHist().add(commande);
	}
}	