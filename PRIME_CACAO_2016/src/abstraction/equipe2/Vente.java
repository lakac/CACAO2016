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


	public void setquantitevendue(Nestle t, IDistributeur d, Produit p) {
		//this.quantitevendue.put(p, Math.min(d t.getStockchoc().getStockschocolats().get(p)+t.getProd().getProduction().get(p)));
	}
	
	
	public void MiseAJourHistorique50(Nestle nestle, int etape) {
		this.historiquedesventesproduit50.ajouter(nestle, etape, this.quantitevendue.get(50));
	}
		
	public void MiseAJourHistorique60(Nestle nestle, int etape) {
		this.historiquedesventesproduit60.ajouter(nestle, etape, this.quantitevendue.get(60));
	}
			
	public void MiseAJourHistorique70(Nestle nestle, int etape) {
		this.historiquedesventesproduit70.ajouter(nestle, etape, this.quantitevendue.get(70));
	}
	
	public double Prixdevente(Production prod, Produit p, PlageInterne plage, double quantite) {
		//Tarif tarif = new Tarif(this.PrixdeventeDeBase(prod, p), plage.getTarifproduit().get(p));
		return tarif.prixDeVente(quantite);
	}

}
