package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.commun.CommandeDistri;

public class HistoriqueCommandeDistri {
	private List<CommandeDistri> hist = new ArrayList<CommandeDistri>();

	public HistoriqueCommandeDistri() {	
		this.hist = new ArrayList<CommandeDistri>();
	}

	public List<CommandeDistri> getHist() {
		return this.hist;
	}

	public CommandeDistri getCommande(int i) {
		return this.getHist().get(i);
	}

	public void ajouter(CommandeDistri commande) {
		this.getHist().add(commande);
	}
	
	public void supprimer(CommandeDistri commande) {
		this.getHist().remove(commande);
	}
}	 