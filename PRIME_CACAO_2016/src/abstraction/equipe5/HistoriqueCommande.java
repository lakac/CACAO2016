package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;

public abstract class HistoriqueCommande {
	private List<CommandeDistri> hist = new ArrayList<CommandeDistri>();
	
	public HistoriqueCommande() {	
		this.hist = new ArrayList<CommandeDistri>();
	}
	
	public List<CommandeDistri> getHist() {
		return this.hist;
	}

	public CommandeDistri getCommande(int i) {
		return this.getHist().get(i);
	}
}
