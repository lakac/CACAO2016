package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public abstract class Stock {
	
	private HashMap<Produit, Double> stock;
	
	
	public Stock() {
		this.stock = new HashMap<Produit, Double>();
	}
	
	 protected HashMap<Produit, Double> getStock() {
		return stock;
	}

	public abstract void MiseAJourStockLivraison(Produit p, double quantite);

	 
	 public abstract void MiseAJourStockTransformation(Produit p, double quantite);

}
