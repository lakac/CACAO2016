package abstraction.equipe2;

import abstraction.commun.Produit;

public class StockCacao_old  {
	private double stockcacao;

	public double getStockcacao() {
		return stockcacao;
	} 
	
	public void AjouterStockCacao(Achat_old achat) {
		this.stockcacao += achat.getCacaoachete();
	}
	
	public void RetirerStockCacao(Produit p, Production production) {
		if (p.equals(Constante.PRODUIT_50)) {
			this.stockcacao -= Constante.RATIO_TRANSFORMATION_50*production.getProduction().get(p);
		}
		else if (p.equals(Constante.PRODUIT_60)) {
			this.stockcacao -= Constante.RATIO_TRANSFORMATION_60*production.getProduction().get(p);
		}
		else if (p.equals(Constante.PRODUIT_70)) {
			this.stockcacao -= Constante.RATIO_TRANSFORMATION_70*production.getProduction().get(p);
		}
	}
	
	public StockCacao_old() {
		this.stockcacao = 0.;
	}

	public double CoutStockCacao() {
		return Constante.COUT_STOCK_UNITAIRE*this.getStockcacao();
	}
}
