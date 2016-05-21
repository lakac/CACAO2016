package abstraction.equipe1;

import java.util.HashMap;
import java.util.List;

import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheProducteur;

public class IntelligenceEconomique {
	private List<ITransformateur> transformateurs;
	private HashMap<ITransformateur,Double> importanceTransformateurs;
	private Stock stock;
	private HashMap<ITransformateur,Double> quantitesMisesEnVente;
	
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	
	public IntelligenceEconomique(List<ITransformateur> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateur,Double>();
		this.stock = s;
		this.quantitesMisesEnVente = new HashMap<ITransformateur,Double>();
		
		for (ITransformateur tr : this.transformateurs) {
			this.importanceTransformateurs.put(tr, 0.0);
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
		double importance = 0.0;
		for (ITransformateur t : this.transformateurs) {
			importance += this.importanceTransformateurs.get(t);
		}
		
		double offreTotale = calculerOffreTotale();
		for (ITransformateur t : this.transformateurs) {
			double valeur = offreTotale*this.importanceTransformateurs.get(t)/importance;
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
