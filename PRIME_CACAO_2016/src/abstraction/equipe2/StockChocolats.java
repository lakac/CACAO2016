package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public class StockChocolats extends Stock {
	
	public StockChocolats(){
		super();
			super.getStock().put(Constante.PRODUIT_50, 0.);
			super.getStock().put(Constante.PRODUIT_60, 0.);
			super.getStock().put(Constante.PRODUIT_70, 0.);
	}
	
	public HashMap<Produit, Double> getStockchocolats() {
		return super.getStock();
	}

	@Override
	public void MiseAJourStockLivraison(Produit p,double quantite) {
		this.getStockchocolats().put(p, this.getStockchocolats().get(p)-quantite);
	}

	@Override
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		this.getStockchocolats().put(p, this.getStockchocolats().get(p)+quantite);		
	}




}
