package abstraction.equipe2;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Produit;
import abstraction.fourni.Historique;

public class Stock {
	public static final double COUT_STOCK_UNITAIRE = 18.0;
	private List<Double> stock;
	private Historique stockprecedents;
	
	public List<Double> getStock() {
		return stock;
	}
	
	public Stock() {
		this.stock = new ArrayList<Double>();
		this.stockprecedents = new Historique();
	}
	
	public void AjouterStockProduit(Produit p, Production prod) {
		if (p.equals(produit1)) {
			this.stock.set(1,this.stock.get(1)+prod.getProduction());
		}
		else if (p.equals(produit2)) {
			this.stock.set(2,this.stock.get(2)+prod.getProduction());
		}
		else if (p.equals(produit3)) {
			this.stock.set(3,this.stock.get(3)+prod.getProduction());
		}
	
	public void RetirerStockProduit(Produit p, Vente vente) {
		if (p.equals(produit1)) {
			this.stock.set(1,this.stock.get(1)-vente.getDernierecommandevendue().getCommandeDis());
		}
		else if (p.equals(produit2)) {
			this.stock.set(2,this.stock.get(2)-vente.getDernierecommandevendue().getCommandeDis());
		}
		else if (p.equals(produit3)) {
			this.stock.set(3,this.stock.get(3)-vente.getDernierecommandevendue().getCommandeDis());
		}
	}
	
	public void AjouterStockCacao(Achat achat) {
		this.stock.set(0, this.stock.get(0)+achat.getDernierecommandeachetee().getCommandesProd()*(1- (0.2+Math.random()*0.1)));
	}
	
	public void RetirerStockCacao(Produit p, Production production) {
		if (p.equals(produit1)) {
			this.stock.set(0,this.stock.get(0)-0.5*production.getProduction());
		}
		else if (p.equals(produit2)) {
			this.stock.set(0,this.stock.get(0)-0.6*production.getProduction());
		}
		else if (p.equals(produit3)) {
			this.stock.set(0,this.stock.get(0)-0.7*production.getProduction());
		}
	}
	
	//public void MiseAJourHistorique(int etape) {
	//	this.stockprecedents.ajouter(Nestle, etape, this.stock);
	//}
	//Ne connaît pas encore Nestlé
	
	public double CoutStock() {
		return Constante.COUT_STOCK_UNITAIRE*this.getStock().get(0);
	}
}
