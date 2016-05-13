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
	private double[] demande;
	
	
	//Constructeurs
	public Vente () {
		this.stock = stock;
		this.demande = new double[3];
		for (int i=0; i<3; i++) {
			this.demande[i] = this.getProducteur().getTransformateurs().get(i).annonceQuantiteDemandee(this.getProducteur());
		}
		
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
	
	//Retourne notre offre totale.
	public double offreTotale () {
		double coeff = this.getPrixMarche()/4000;
		if (coeff>=0) {
			return this.venteAPriori()*(1+coeff);
		}
		else {
			return this.venteAPriori()*(1+coeff/2);
		}
	}
		
	//Vente effective aux transformateurs et notifications de celles-ci
	public double[] ventesStep () {
		double[] R = new double[3];
		double offreRestante = 0.0;
		//vente à notre meilleur client (équipe 2)
		if (this.demande[1]<=this.offreTotale()) {
			R[1] = this.demande[1];
			offreRestante = this.offreTotale()-R[1];
			if (this.demande[0]<=offreRestante) {
				R[0] = this.demande[0];
				offreRestante = offreRestante - R[0];
				R[2] = Math.min(offreRestante, this.demande[2]);
			}
			else {
				R[0] = offreRestante;
				R[2] = 0.0;
			}
		}
		else {
			R[1] = this.offreTotale();
			R[0] = 0.0;
			R[2] = 0.0;
		}
		return R;
	}
}
