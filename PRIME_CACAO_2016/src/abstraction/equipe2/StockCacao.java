package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public class StockCacao extends Stock {

	private HashMap<Produit,Double> stockcacao;
	
	public StockCacao(){
		super();
			this.stockcacao.put(Constante.CACAO, 0.);
	}
	
	
	
	public HashMap<Produit, Double> getStockcacao() {
		return stockcacao;
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
