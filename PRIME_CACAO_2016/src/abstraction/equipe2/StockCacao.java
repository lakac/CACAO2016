package abstraction.equipe2;


import java.util.HashMap;

import abstraction.commun.Produit;

public class StockCacao extends Stock {

	private HashMap<Produit,Double> stockCacao;
	
	public StockCacao(){
		super();
			this.stockCacao.put(Constante.CACAO, 0.);
	}
	
	
	
	public HashMap<Produit, Double> getStockcacao() {
		return this.stockCacao;
	}



	@Override
	public void MiseAJourStockLivraison(Produit p,double quantite) {
		this.stockCacao.put(p,this.stockCacao.get(p)+quantite);
	}
	
	
	// Dans MiseAJOurStockTransformation, p est un chocolat et quantite et une quantite de chocolat voulue
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		if (p.equals(Constante.PRODUIT_50)) {
			this.stockCacao.put(p,Constante.RATIO_TRANSFORMATION_50*quantite);
		}
		else if (p.equals(Constante.PRODUIT_60)) {
			this.stockCacao.put(p,Constante.RATIO_TRANSFORMATION_60*quantite);
		}
		else if (p.equals(Constante.PRODUIT_70)) {
			this.stockCacao.put(p,this.stockCacao.get(p)-Constante.RATIO_TRANSFORMATION_70*quantite);
		};
	}

}
