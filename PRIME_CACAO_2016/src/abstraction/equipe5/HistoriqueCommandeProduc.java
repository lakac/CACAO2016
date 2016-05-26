package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.MarcheProducteur;

public class HistoriqueCommandeProduc {
	private List<CommandeProduc> hist = new ArrayList<CommandeProduc>();
	private ArrayList<IProducteur> producteurs;
	private Lindt lindt;

	public HistoriqueCommandeProduc() {	
		this.hist = new ArrayList<CommandeProduc>();
		// voir avec le prof comment initialiser des commandes
//		this.hist.add(new CommandeProduc(lindt, this.getProducteurs().get(0), 100.0, MarcheProducteur.LE_MARCHE.getCours()));
//		this.hist.add(new CommandeProduc(lindt, this.getProducteurs().get(1), 100.0, MarcheProducteur.LE_MARCHE.getCours()));
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