package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public abstract class Stock {
	
	private HashMap<Produit, Double> stock;
	
	
	public Stock() {
		this.stock = new HashMap<Produit, Double>();
		this.stock.put(Constante.PRODUIT_50, 0.);
		this.stock.put(Constante.PRODUIT_60, 0.);
		this.stock.put(Constante.PRODUIT_70, 0.);
		this.stock.put(Constante.CACAO, 0.);
	}
	
	 public abstract void MiseAJourStockLivraison();
	 
	 public abstract void MiseAJourStockTransformation();

}
