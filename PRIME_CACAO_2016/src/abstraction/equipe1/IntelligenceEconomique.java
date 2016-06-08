package abstraction.equipe1;

import java.util.HashMap;
import java.util.List;

import abstraction.commun.ITransformateurD;
import abstraction.commun.MarcheProducteur;

/**
 * Classe pour calculer la quantité mise en vente pour chaque transformateur
 */
public class IntelligenceEconomique {
	/** Liste des transformateurs en relation avec notre producteur */
	private List<ITransformateurD> transformateurs;
	/** Coefficients de somme unité correspondant à l'importance des ventes réalisées */
	private HashMap<ITransformateurD,Double> importanceTransformateurs;
	/** Stock de notre producteur */
	private Stock stock;
	/** Quantités mises en vente pour chaque transformateur pour le step en cours */
	private HashMap<ITransformateurD,Double> quantitesMisesEnVente;
	/**
	 * Coefficients associés au stock produit à différentes dates.
	 * Le cacao le plus ancien est en tête.
	 */
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	
	public IntelligenceEconomique(List<ITransformateurD> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateurD,Double>();
		this.stock = s;
		this.quantitesMisesEnVente = new HashMap<ITransformateurD,Double>();
	}
	
	/**
	 * Prend en compte l'existence du transformateur après son ajout
	 * à la liste des transformateurs pour l'ajouter aux HashMap internes.
	 */
	public void prendreEnCompte(ITransformateurD t) {
		this.quantitesMisesEnVente.put(t, 0.0);
		// mise à jour des coefficients d'importance pour avoir une somme unité
		for (ITransformateurD tr : this.transformateurs) {
			this.importanceTransformateurs.put(tr, 1.0/this.transformateurs.size());
		}
	}
	
	private double calculerEnvieDeVendre() {
		double rapport = (MarcheProducteur.LE_MARCHE.getCours()-MarcheProducteur.PRIX_MINIMUM)/(MarcheProducteur.PRIX_MAXIMUM-MarcheProducteur.PRIX_MINIMUM);
		return (1.0 + Math.sin((rapport - 0.5) * Math.PI)) / 2.0;
	}
	
	private double calculerBesoinDeVendre() {
		double besoin = 0.0;
		for (int i = 0; i<this.coeffPerissabilite.length; i++) {
			besoin += this.coeffPerissabilite[i]*this.stock.getStockParStep(i);
		}
		return besoin;
	}
	
	private double calculerOffreTotale() {
		double envie = calculerEnvieDeVendre();
		double besoin = calculerBesoinDeVendre();
		return besoin*(1.0-envie) + this.stock.getQuantite()*envie;
	}
	
	private void actualiserImportanceTransformateurs() {
		double totalDemandes = 0.0;
		for (ITransformateurD t : this.transformateurs) {
			totalDemandes += t.annoncePrix()*t.annonceQuantiteDemandee();
		}
		
		if(totalDemandes != 0.0) {
			for (ITransformateurD t : this.transformateurs) {
				double valeur = t.annoncePrix()*t.annonceQuantiteDemandee()/totalDemandes;
				double valeurAmortie = this.importanceTransformateurs.get(t)*0.8 + valeur*0.2;
				this.importanceTransformateurs.put(t, valeurAmortie);
			}
		}
	}
	
	private void actualiserQuantitesMisesEnVente() {
		double offreTotale = calculerOffreTotale();
		for (ITransformateurD t : this.transformateurs) {
			double valeur = offreTotale*this.importanceTransformateurs.get(t);
			this.quantitesMisesEnVente.put(t, valeur);
		}
	}
	
	public void actualiser() {
		this.actualiserImportanceTransformateurs();
		this.actualiserQuantitesMisesEnVente();
	}
	
	public double donnerQuantiteMiseEnVente(ITransformateurD t) {
		return this.quantitesMisesEnVente.get(t);
	}
}
