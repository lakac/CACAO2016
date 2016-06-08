package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;

public class HistoriqueCommandeProduc extends HistoriqueCommande {
	private ArrayList<IProducteur> producteurs;

	public HistoriqueCommandeProduc() {	
		this.hist = new ArrayList<CommandeProduc>();
	}
	
	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}

	public void ajouter(CommandeProduc commande) {
		this.getHist().add(commande);
	}
}	