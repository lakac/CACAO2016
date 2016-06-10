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
	public void MiseAJourStockLivraison() {
		// TODO Auto-generated method stub
		
	}
	// 

	@Override
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		this.stockCacao.put(p,this.stockCacao.get(p)-quantite);
	}

}
