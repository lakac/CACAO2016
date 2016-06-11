package abstraction.equipe2;


import java.util.HashMap;

import abstraction.commun.Produit;

public class StockCacao extends Stock {

	
	public StockCacao(){
		super();
			super.getStock().put(Constante.CACAO, 0.);
			super.getStock().put(Constante.PRODUIT_50, 0.);
			super.getStock().put(Constante.PRODUIT_60, 0.);
			super.getStock().put(Constante.PRODUIT_70, 0.);
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
			if(this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_50*quantite<0){
				this.getStockcacao().put(Constante.CACAO, 0.);
			}else{
				this.getStockcacao().put(Constante.CACAO, this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_50*quantite);
			}
		}
		else if (p.equals(Constante.PRODUIT_60)) {
			if(this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_60*quantite<0){
				this.getStockcacao().put(Constante.CACAO, 0.);
			}else{
				this.getStockcacao().put(Constante.CACAO, this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_60*quantite);
			}
		}
		else if (p.equals(Constante.PRODUIT_70)) {
			if(this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_70*quantite<0){
				this.getStockcacao().put(Constante.CACAO, 0.);
			}else{
				this.getStockcacao().put(Constante.CACAO, this.getStockcacao().get(Constante.CACAO)-Constante.RATIO_TRANSFORMATION_70*quantite);
			}
		}
		
	}
	
	public static void main(String[] args) {
		StockCacao stockcac = new StockCacao();
		//Test constructeur / getter / Initialisation
		if(stockcac.getStockcacao().get(Constante.CACAO)==0.){
			System.out.println("Le constructeur et les getter sont bons");
		}else{
			System.out.println("Problème inconnu");
		}
		//Test de la methode MiseAJourStockLivraison
		stockcac.MiseAJourStockLivraison(Constante.CACAO, 50);
		if(stockcac.getStockcacao().get(Constante.CACAO)==50.){
			System.out.println("La méthode MiseAJourStockLivraison est bonne");
		}else{
			System.out.println("Problème inconnu");
		}
		//Test de la methode MiseAJourStockTransformation
		stockcac.MiseAJourStockTransformation(Constante.PRODUIT_50, 25.);
		if(stockcac.getStockcacao().get(Constante.CACAO)==37.5){
			System.out.println("La methode MiseAJourStockTransformation est bonne");
		}else{
			if(stockcac.getStockcacao().get(Constante.CACAO)==50.){
				System.out.println("La methode MiseAJourStockTransformation ne fait rien");
			}else{
				System.out.println("Problème inconnu");
			}
		}
		stockcac.MiseAJourStockTransformation(Constante.PRODUIT_50, 100.);
		if(stockcac.getStockcacao().get(Constante.CACAO)==0.){
			System.out.println("La methode MiseAJourStockTransformation est bonne, le stock ne peut pas être négatif");
		}else{
			if(stockcac.getStockcacao().get(Constante.CACAO)==37.5){
				System.out.println("La methode ne fait rien du tout");
			}else{
				if(stockcac.getStockcacao().get(Constante.CACAO)==-12.5){
					System.out.println("Le stock peut être négatif");
				}else{
					System.out.println("Problème de stock");
				}
			}
		}
	}
		

}
