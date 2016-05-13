package abstraction.equipe4;

import abstraction.fourni.Monde;

public class Vente {
	//vente pour chacun des trois transformateurs
	private int[] ventes;
	//stock disponible
	private Stock stock;
	//numéro de la step
	private int step;
	//on choisit de ne pas dépasser un certain stock, dans la mesure du possible. C'est-à-dire que si notre stock est supérieur à cette valeur, on veut le plus possible.
	private int maximumStockAutorise;
	private Producteur producteur;
	
	//Constructeurs
	public Vente () {
		this.stock = stock;
	}

	//GETTERS AND SETTERS
	//numéro de la step
	public int getStep() {
		return Monde.LE_MONDE.getStep();
	}
	public Producteur getProducteur() {
		return this.producteur;
	}
	public Stock getStock() {
		return this.getProducteur().getStock();
	}

	//AUTRES MÉTHODES
	//Retourne la moyenne des prix de vente sur les (12?) dernières step.
	public double moyennePrixDeVente () {
		return 0.0;
	}
	//Retourne le stock disponible divisé par le nombre de steps restantes avant le nouvel arrivage de production.
	public double venteAPriori () {
		//nombre de steps restantes avant l'arrivage de la nouvelle production
		int n=12-this.getStep()%12;
		return this.getStock().getStockCacao().getValeur()/n;
	}
	//Retourne une sorte d'indicateur d'intérêt de vendre ou non.
	public double indicInteretVente () {
		
	}
	
	
	
	public venteStep () {
		
	}
}
