package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;

public class HistoriqueCommandeProduc {
	private List<CommandeProduc> hist = new ArrayList<CommandeProduc>();
	private ArrayList<IProducteur> producteurs;

	public HistoriqueCommandeProduc() {	
		this.hist = new ArrayList<CommandeProduc>();
	}

	public List<CommandeProduc> getHist() {
		return this.hist;
	}
	
	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}

	public CommandeProduc getCommande(int i) {
		return this.getHist().get(i);
	}

	public void ajouter(CommandeProduc commande) {
		this.getHist().add(commande);
	}
}	