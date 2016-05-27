package abstraction.equipe4;

import abstraction.commun.MarcheProducteur;
import abstraction.fourni.*;

public class Vente {
	//stock disponible
	private Stock stock;
	//on choisit de ne pas dﾃｩpasser un certain stock, dans la mesure du possible. C'est-ﾃ�-dire que si notre stock est supﾃｩrieur ﾃ� cette valeur, on veut le plus possible.
	private Producteur producteur;
	private double prixMarche;
	private double[] prixDemandes;
	private double offreTotale;

	//Constructeurs
	public Vente (Stock stock, Producteur producteur) {
		this.stock = stock;
		this.prixDemandes = new double[3];
		for (int i=0; i<3; i++) {
			this.prixDemandes[i] = producteur.getTransformateurs().get(i).annoncePrix();
		}
		this.producteur = producteur;
		this.prixMarche = this.getPrixMarche();
	}

	//GETTERS AND SETTERS
	public int getStep() {
		return Monde.LE_MONDE.getStep();
	}
	public Producteur getProducteur() {
		return this.producteur;
	}
	public Stock getStock() {
		return this.getProducteur().getStock();
	}
	//Retourne le prix actuel du marchﾃｩ.
	public double getPrixMarche() {
		return MarcheProducteur.LE_MARCHE.getCours();
	}
	public double[] getPrixDemandes() {
		return this.prixDemandes;
	}


	//AUTRES Mﾃ欝HODES
	//Retourne la moyenne des prix de vente sur les prﾃｩcﾃｩdentes step.
	public double moyennePrixDeVente () {
		Historique coursCacao = MarcheProducteur.LE_MARCHE.getHistorique();
		//longueur du tableau regroupant les prﾃｩcﾃｩdents prix de vente
		int l = coursCacao.getTaille();
		double M = coursCacao.get(0).getValeur();
		for (int i=1; i<l; i++) {
			M=M+coursCacao.get(i).getValeur();
			M=M/l;
		}
		return M;
	}
	//Retourne le stock disponible divisﾃｩ par le nombre de steps restantes avant le nouvel arrivage de production.
	public double venteAPriori () {
		//nombre de steps restantes avant l'arrivage de la nouvelle production
		int n=12-this.getStep()%12;
		return this.getStock().getStockCacao().getValeur()/n;
	}



	//Retourne notre offre totale.
	public double offreTotale () {
		//Premier ajustement de notre offre totale, en fonction du cours de cacao fixﾃｩ par le marchﾃｩ.
		double coeff = (this.getPrixMarche()-3000)/1000;
		double offreTotale = 0.0;
		if (coeff>=0) {
			offreTotale = this.venteAPriori()*(1+coeff);
		}
		else {
			offreTotale = this.venteAPriori()*(1+coeff/2);
		}
		//Moyenne des prix fixﾃｩs par chaque transformateur.
		double moyennePrixDemandes = (this.getPrixDemandes()[0]+this.getPrixDemandes()[1]+this.getPrixDemandes()[2])/3;
		//Deuxiﾃｨme ajustement de notre offre totale, en fonction des prix fixﾃｩs par les transformateurs eux-mﾃｪmes.
		if (moyennePrixDemandes<=this.getPrixMarche()) {
			offreTotale = offreTotale*moyennePrixDemandes/this.getPrixMarche();
		}
		else {
			offreTotale = offreTotale*(1+moyennePrixDemandes/this.getPrixMarche());
		}
		return offreTotale;
	}

	//Intention de vente aux diffﾃｩrents transformateurs
	public double[] ventesStep() {
		double[] ventesStep = new double[3];
		ventesStep[2] = 0.04*this.offreTotale()+((this.getPrixDemandes()[2]-this.prixMarche)/this.prixMarche)*this.offreTotale();
		ventesStep[1] = 0.13*this.offreTotale();
		ventesStep[0] = this.offreTotale()-ventesStep[1]-ventesStep[2];
		return ventesStep;
	}


	/*
	 * NE PAS EFFACER LA SUITE (PEUT ﾃ概RE UTILE DANS LA V3)
	public double[] ventesStep () {
		double[] R = new double[3];
		double offreRestante = 0.0;
		//vente ﾃ� notre meilleur client (ﾃｩquipe 2)
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
	 */
}