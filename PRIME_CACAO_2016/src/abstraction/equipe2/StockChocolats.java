package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public class StockChocolats extends Stock {

	
private HashMap<Produit,Double> stockChocolats;
	
	public StockChocolats(){
		super();
			this.stockChocolats.put(Constante.PRODUIT_50, 0.);
			this.stockChocolats.put(Constante.PRODUIT_60, 0.);
			this.stockChocolats.put(Constante.PRODUIT_70, 0.);
	}
	
	public HashMap<Produit, Double> getStockchocolats() {
		return stockChocolats;
	}

	@Override
	public void MiseAJourStockLivraison(Produit p,double quantite) {
		this.stockChocolats.put(p, this.stockChocolats.get(p)-quantite);
	}

	@Override
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		this.stockChocolats.put(p, this.stockChocolats.get(p)+quantite);		
	}




}
