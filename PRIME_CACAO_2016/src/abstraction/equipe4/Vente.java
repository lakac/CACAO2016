package abstraction.equipe4;

import abstraction.commun.MarcheProducteur;
import abstraction.fourni.*;

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
	private double prixMarche;
	
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
	//Retourne le prix actuel du marché.
	public double getPrixMarche() {
		return MarcheProducteur.LE_MARCHE.coursCacao.getValeur();
	}

	//AUTRES MÉTHODES
	//Retourne la moyenne des prix de vente sur les précédentes step.
	public double moyennePrixDeVente () {
		Historique coursCacao = MarcheProducteur.LE_MARCHE.coursCacao.getHistorique();
		//longueur du tableau regroupant les précédents prix de vente
		int l = coursCacao.getTaille();
		double M = coursCacao.get(0).getValeur();
		for (int i=1; i<l; i++) {
			M=M+coursCacao.get(i).getValeur();
			M=M/l;
		}
		return M;
	}
	//Retourne le stock disponible divisé par le nombre de steps restantes avant le nouvel arrivage de production.
	public double venteAPriori () {
		//nombre de steps restantes avant l'arrivage de la nouvelle production
		int n=12-this.getStep()%12;
		return this.getStock().getStockCacao().getValeur()/n;
	}
	
	//Retourne une sorte d'indicateur d'intérêt de vendre ou non.
	public double indicInteretVente () {
		double coeffPrix = this.getPrixMarche()/4000;
		double 
		
	}
	
	
	
	public venteStep () {
		
	}
}
