package abstraction.equipe2;

import abstraction.commun.Produit;

public class StockCacao {
	private double stockcacao;

	public double getStockcacao() {
		return stockcacao;
	} 
	
	public void AjouterStockCacao(Achat achat) {
		this.stockcacao += achat.getDernierecommandeachetee().getCommandesProd();
	}
	
	public void RetirerStockCacao(Produit p, Production production) {
		if (p.equals(50%)) {
			this.stockcacao -= 0.5*production.getProduction().get(p);
		}
		else if (p.equals(60%)) {
			this.stockcacao -= 0.6*production.getProduction().get(p);
		}
		else if (p.equals(70%)) {
			this.stockcacao -= 0.7*production.getProduction().get(p);
		}
	}

}
