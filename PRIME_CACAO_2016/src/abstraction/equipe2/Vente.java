package abstraction.equipe2;
import java.util.HashMap;
import abstraction.commun.*;
import abstraction.fourni.*;

public class Vente {
	private HashMap<Produit, Double> quantitevendue;
	private Historique historiquedesventesproduit50;
	private Historique historiquedesventesproduit60;
	private Historique historiquedesventesproduit70;
	
	private HashMap<IProducteur,Double> prixdevente;
	
	public void addProduit(Produit p) {
		this.quantitevendue.put(p, 0.0);
	}
	
	public HashMap<Produit, Double> getQuantitevendue() {
		return quantitevendue;
	}

	public Vente() {
		this.quantitevendue = new HashMap<Produit, Double>();
		this.historiquedesventesproduit50 = new Historique();
		this.historiquedesventesproduit60 = new Historique();
		this.historiquedesventesproduit70 = new Historique();
	}
	
	public HashMap<IProducteur, Double> getPrixdevente() {
		return prixdevente;
	}	
	
	
	public HashMap<Produit, Double> getVentes() {
		return this.quantitevendue;
	}


	public void setquantitevendue(Nestle nestle, CommandeDistri d, Produit p) {
		this.quantitevendue.put(p, Math.min(d.getQuantite(), nestle.getStockchoc().getStockschocolats().get(p)+nestle.getProd().getProduction().get(p)));
	}
	
	public void MiseAJourHistorique(Nestle nestle, int etape, Produit produit) {
		if (produit.equals(Constante.PRODUIT_50)) {
			this.historiquedesventesproduit50.ajouter(nestle, etape, this.quantitevendue.get(Constante.PRODUIT_50));
		}
		else if (produit.equals(Constante.PRODUIT_60)) {
			this.historiquedesventesproduit60.ajouter(nestle, etape, this.quantitevendue.get(Constante.PRODUIT_60));
		}
		else {
			this.historiquedesventesproduit70.ajouter(nestle, etape, this.quantitevendue.get(Constante.PRODUIT_70));
		}
	}
	
	public double Prixdevente(Tarif tarif, double quantite) {
		return tarif.prixDeVente(quantite);
	}

}
