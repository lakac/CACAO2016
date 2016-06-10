package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public class StockChocolats extends Stock {

	
private HashMap<Produit,Double> stockchocolats;
	
	public StockChocolats(){
		super();
			this.stockchocolats.put(Constante.PRODUIT_50, 0.);
			this.stockchocolats.put(Constante.PRODUIT_60, 0.);
			this.stockchocolats.put(Constante.PRODUIT_70, 0.);
	}
	
	
	@Override
	public void MiseAJourStockLivraison() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void MiseAJourStockTransformation() {
		// TODO Auto-generated method stub
		
	}

}
