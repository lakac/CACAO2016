package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.Produit;

public class StockChocolats extends Stock {
	
	//Classe fini et Tests finis
	
	//Créer un stock de chocolat, qui est une hashmap contenant trois produits, les trois chocolats
	//On initialise chaque stock à 0
	public StockChocolats(){
		super();
			super.getStock().put(Constante.PRODUIT_50, 0.);
			super.getStock().put(Constante.PRODUIT_60, 0.);
			super.getStock().put(Constante.PRODUIT_70, 0.);
	}
	
	//getter de stockchocolat, retourne une hashmap de produit et de double
	public HashMap<Produit, Double> getStockchocolats() {
		return super.getStock();
	}

	//Methode utilisee lorsqu'on livre les distributeurs, retire donc au stock de chocolat le produit p, d'une quantite quantite
	@Override
	public void MiseAJourStockLivraison(Produit p,double quantite) {
		if(this.getStockchocolats().get(Constante.PRODUIT_50)-quantite<0){
			this.getStockchocolats().put(p, 0.);
			System.out.println("Il n'y a pas assez de chocolat pour tout livrer");
		}else{
			this.getStockchocolats().put(p, this.getStockchocolats().get(p)-quantite);
		}
	}
	
	// Somme le chocolat total dont l'on dispose en stock -> Necessaire pour le calcul des couts de stock
	public double Quantitetotalchoc() {
		double quantitetot = 0.;
		for (Produit p : this.getStock().keySet()) {
			quantitetot+=this.getStock().get(p);
		}
		return quantitetot;
	}

	// Met a jour le stock de chocolat ( ajout du chocolat qui est transformé )
	// Ajoute
	@Override
	public void MiseAJourStockTransformation(Produit p, double quantite) {
		this.getStockchocolats().put(p, this.getStockchocolats().get(p)+quantite);		
	}
	
	
	public static void main(String[] args) {
		StockChocolats stockchoc = new StockChocolats();
		//Test Constructeur / Initialisation à 0 / methode getStockchocolats
		System.out.println(stockchoc.getStockchocolats().get(Constante.PRODUIT_50));
		System.out.println(stockchoc.getStockchocolats().get(Constante.PRODUIT_60));
		System.out.println(stockchoc.getStockchocolats().get(Constante.PRODUIT_70));
		//Test methode MiseAJourStockTransformation
		stockchoc.MiseAJourStockTransformation(Constante.PRODUIT_50, 100);
		if (stockchoc.getStockchocolats().get(Constante.PRODUIT_50)==100){
			System.out.println("La methode MiseAJourStockTransformation fonctionne");
		}else{
			System.out.println("La méthode MiseAJourStockTransformation ne fonctionne pas");
		}
		
		//Test methode MiseAJourStockLivraison
		stockchoc.MiseAJourStockLivraison(Constante.PRODUIT_50, 50);
		if(stockchoc.getStockchocolats().get(Constante.PRODUIT_50)==50){
			System.out.println("La methode MiseAJourLivraison bonne");
		}else{
			System.out.println("La méthode MiseAJourLivraison ne fonctionne pas");
		}
		
		stockchoc.MiseAJourStockLivraison(Constante.PRODUIT_50, 100);
		if(stockchoc.getStockchocolats().get(Constante.PRODUIT_50)==0){
			System.out.println("Test validé : Le stock ne peut pas être négatif");
		}else{
			if(stockchoc.getStockchocolats().get(Constante.PRODUIT_50)<0){
				System.out.println("Erreur dans la méthode : Le stock peut être négatif");
			}else{
				System.out.println("Je ne sais pas d'où vient l'erreur");
			}
		}

	
	}




}
