package abstraction.equipe2;

import abstraction.fourni.Historique;

public class Stock {
	private double stock;
	private Historique stockprecedents;
		
	
	public double getStock() {
		return stock;
	}
	
	public Stock () {
		this.stock = 0.0;
		this.stockprecedents = new Historique();
	}
	
	public void AjouterStockAchat(Achat achat) {
		this.stock = this.stock + achat.getDernierecommandeachetee().getCommandesProd()
				   - (0.2+Math.random()*0.1)* achat.getDernierecommandeachetee().getCommandesProd();
	}
	
	public void AjouterStockProduction(Production production){
		this.stock = this.stock + production.getProduction();
	}
	
	public void RetirerStockProduction(Production production) {
		this.stock = this.stock + production.getProduction();
	}
	
	public void RetirerStockVente(Vente ventes) {
		this.stock -= ventes.getDernierecommandevendue().getCommandeDis();
	}
	public Historique getStockprecedents() {
		return stockprecedents;
	}
	
	//public void MiseAJourHistorique(int etape) {
	//	this.stockprecedents.ajouter(Nestle, etape, this.stock);
	//}
	//Ne connaît pas encore Nestlé
	public double CoutStock() {
		return Constante.COUT_STOCK_UNITAIRE*this.getStock();
	}
	

}
