package abstraction.equipe4;

import abstraction.fourni.Historique;
import abstraction.fourni.Monde;

public class Offre {
	private Producteur producteur;
	private Stock stock;

	public Offre(Producteur producteur, Stock stock) {
		this.producteur = producteur;
		this.stock = stock;
	}

//GETTERS
	public Producteur getProducteur() {
		return this.producteur;
	}
	public int getStep() {
		return Monde.LE_MONDE.getStep();
	}
	public Stock getStock() {
		return this.stock;
	}
	
//AUTRES METHODES
	
	//Retourne la moyenne du cours de cacao sur les precedentes step.
	public double moyenneCoursCacao() {
		Historique coursCacao = this.getProducteur().getMarcheProducteur().getHistorique();
		//longueur du tableau regroupant les precedents cours
		int l = coursCacao.getTaille();
		//somme des valeurs du tableau
		double M = coursCacao.get(0).getValeur();
		for (int i=1; i<l; i++) {
			M=M+coursCacao.get(i).getValeur();
		}
		return M/l;
	}
	
	//Retourne le stock disponible divise par le nombre de steps restantes
	//avant le nouvel arrivage de production, pour savoir quelle quantite
	//mettre en vente theoriquement, sans prendre en compte le cours actuel 
	//du cacao.
	public double venteAPriori () {
		//nombre de steps restantes avant l'arrivage de la nouvelle production
		int n=12-this.getStep()%12;
		return this.getStock().getStockCacao().getValeur()/n;
	}

	//Retourne notre offre finale
	//On ajuste notre offre totale en fonction du cours de cacao fixe par le marche.
	public double offre() {
		//calcul d'un coefficient nous indiquant l'interet de vendre beaucoup ou peu a la step actuelle
		double coeff = (this.moyenneCoursCacao()-3000)/1000;
		double offre = 0.0;
		if (coeff>=0) {
			offre = this.venteAPriori()*(1+coeff);
		}
		else {
			offre = this.venteAPriori()*(1+coeff/2);
		}
		//L'offre totale est comprise entre la moitie et le double de notre venteAPriori.
		return offre;
	}

}
