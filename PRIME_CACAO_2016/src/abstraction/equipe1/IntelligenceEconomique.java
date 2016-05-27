package abstraction.equipe1;

import java.util.HashMap;
import java.util.List;

import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheProducteur;

/**
 * Classe pour calculer la quantité mise en vente pour chaque transformateur
 */
public class IntelligenceEconomique {
	/** Liste des transformateurs en relation avec notre producteur */
	private List<ITransformateur> transformateurs;
	/** Coefficients de somme unité correspondant à l'importance des ventes réalisées */
	private HashMap<ITransformateur,Double> importanceTransformateurs;
	/** Stock de notre producteur */
	private Stock stock;
	/** Quantités mises en vente pour chaque transformateur pour le step en cours */
	private HashMap<ITransformateur,Double> quantitesMisesEnVente;
	/**
	 * Coefficients associés au stock produit à différentes dates.
	 * Le cacao le plus ancien est en tête.
	 */
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	
	public IntelligenceEconomique(List<ITransformateur> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateur,Double>();
		this.stock = s;
		this.quantitesMisesEnVente = new HashMap<ITransformateur,Double>();
		
		for (ITransformateur tr : this.transformateurs) {
			this.importanceTransformateurs.put(tr, 1.0/this.transformateurs.size());
			this.quantitesMisesEnVente.put(tr, 0.0);
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
		for (ITransformateur t : this.transformateurs) {
			totalDemandes += t.annoncePrix()*t.annonceQuantiteDemandee();
		}
		
		if(totalDemandes != 0.0) {
			for (ITransformateur t : this.transformateurs) {
				double valeur = t.annoncePrix()*t.annonceQuantiteDemandee()/totalDemandes;
				double valeurAmortie = this.importanceTransformateurs.get(t)*0.8 + valeur*0.2;
				this.importanceTransformateurs.put(t, valeurAmortie);
			}
		}
	}
	
	private void actualiserQuantitesMisesEnVente() {
		double offreTotale = calculerOffreTotale();
		for (ITransformateur t : this.transformateurs) {
			double valeur = offreTotale*this.importanceTransformateurs.get(t);
			this.quantitesMisesEnVente.put(t, valeur);
		}
	}
	
	public void actualiser() {
		this.actualiserImportanceTransformateurs();
		this.actualiserQuantitesMisesEnVente();
	}
	
	public double donnerQuantiteMiseEnVente(ITransformateur t) {
		return this.quantitesMisesEnVente.get(t);
	}
}
