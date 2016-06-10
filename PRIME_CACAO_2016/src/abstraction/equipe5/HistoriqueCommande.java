package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Commande;
import abstraction.commun.IProducteur;

public class HistoriqueCommande {
	private ArrayList<IProducteur> producteurs;
	private List<Commande> hist = new ArrayList<Commande>();
	
	public HistoriqueCommande() {	
		this.hist = new ArrayList<Commande>();
	}
	
	public List<Commande> getHist() {
		return this.hist;
	}

	public Commande getCommande(int i) {
		return this.getHist().get(i);
	}
	
	public void ajouter(Commande commande) {
		this.getHist().add(commande);
	}
	
	public void supprimer(Commande commande) {
		this.getHist().remove(commande);
	}
	
	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}
}
