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
	
	public void AjouterStock(Achat achat) {
		this.stock = this.stock + achat.getDerniereommandeachetee().getCommandesProd();
	}
	
	public void RetirerStock(Vente ventes) {
		this.stock -= ventes.getDernierecommandevendue().getCommandeDis();
	}
	public Historique getStockprecedents() {
		return stockprecedents;
	}
	
	public void MiseAJourHistorique(int etape) {
		this.stockprecedents.ajouter(Nestle, etape, this.stock);
	}
	//Ne connaît pas encore Nestlé
	public CoutDeStock() {
		
	}
	

}
