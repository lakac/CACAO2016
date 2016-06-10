package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public abstract class Stock {
	
	private HashMap<Produit, Double> stock;
	
	
	public Stock() {
		this.stock = new HashMap<Produit, Double>();
	}
	
	 public abstract void MiseAJourStockLivraison();
	 
	 public abstract void MiseAJourStockTransformation();

}
