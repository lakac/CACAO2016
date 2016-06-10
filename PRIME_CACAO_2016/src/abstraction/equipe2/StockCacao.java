package abstraction.equipe2;


import java.util.HashMap;

import abstraction.commun.Produit;

public class StockCacao extends Stock {

	
	public StockCacao(){
		super();
			super.getStock().put(Constante.CACAO, 0.);
	}
	
	
	
	public HashMap<Produit, Double> getStockcacao() {
		return super.getStock();
	}


	@Override
	public void MiseAJourStockLivraison(Produit p,double quantite) {
		this.getStockcacao().put(p,this.getStockcacao().get(p)+quantite);
	}
	
	
	// Dans MiseAJOurStockTransformation, p est un chocolat et quantite et une quantite de chocolat voulue
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		if (p.equals(Constante.PRODUIT_50)) {
			this.getStockcacao().put(p,Constante.RATIO_TRANSFORMATION_50*quantite);
		}
		else if (p.equals(Constante.PRODUIT_60)) {
			this.getStockcacao().put(p,Constante.RATIO_TRANSFORMATION_60*quantite);
		}
		else if (p.equals(Constante.PRODUIT_70)) {
			this.getStockcacao().put(p,this.getStockcacao().get(p)-Constante.RATIO_TRANSFORMATION_70*quantite);
		};
	}

}
