package abstraction.equipe1;

import java.util.HashMap;
import java.util.List;

import abstraction.commun.ITransformateur;
import abstraction.commun.MarcheProducteur;

public class IntelligenceEconomique {
	private List<ITransformateur> transformateurs;
	private HashMap<ITransformateur,Double> importanceTransformateurs;
	private double envieVendre;
	private double besoinVendre;
	private Stock stock;
	private double offreTotale;
	private HashMap<ITransformateur,Double> quantitesMisesEnVente;
	
	private final double[] coeffPerissabilite = {1.0, 0.9, 0.8, 0.7, 0.6, 0.5, 0.4, 0.3, 0.2, 0.1};
	
	public IntelligenceEconomique(List<ITransformateur> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateur,Double>();
		this.envieVendre = 0;
		this.besoinVendre = 0;
		this.stock = s;
		this.offreTotale = 0;
		this.quantitesMisesEnVente = new HashMap<ITransformateur,Double>();
		
		for (ITransformateur tr : this.transformateurs) {
			this.importanceTransformateurs.put(tr, 0.0);
			this.quantitesMisesEnVente.put(tr, 0.0);
		}
	}
	
	private void actualiserEnvieVendre() {
		this.envieVendre = Math.sin((MarcheProducteur.LE_MARCHE.getCours()-2000.0)/2000.0);
	}
	
	private void actualiserBesoinVendre() {
		this.besoinVendre = 0;
		for (int i = 0; i<this.coeffPerissabilite.length; i++) {
			this.besoinVendre += this.coeffPerissabilite[i]*this.stock.getStockParStep(i);
		}
	}
	
	private void actualiserOffreTotale() {
		this.offreTotale = this.besoinVendre*(1-this.envieVendre) + this.stock.getQuantite()*this.envieVendre;
	}
	
	private void actualiserImportanceTransformateurs() {
		double beneficeTotal = 0;
		for (ITransformateur t : this.transformateurs) {
			beneficeTotal += t.annoncePrix()*t.annonceQuantiteDemandee();
		}
		
		double valeur = 0;
		for (ITransformateur t : this.transformateurs) {
			valeur = this.importanceTransformateurs.get(t)*0.8 + t.annoncePrix()*t.annonceQuantiteDemandee()*0.2/beneficeTotal;
			this.importanceTransformateurs.put(t, valeur);
		}
	}
	
	private void actualiserQuantitesMisesEnVente() {
		double importance = 0;
		for (ITransformateur t : this.transformateurs) {
			importance += this.importanceTransformateurs.get(t);
		}
		
		double valeur = 0;
		for (ITransformateur t : this.transformateurs) {
			valeur = this.offreTotale*this.importanceTransformateurs.get(t)/importance;
			this.quantitesMisesEnVente.put(t, valeur);
		}
	}
	
	public void actualiser() {
		this.actualiserEnvieVendre();
		this.actualiserBesoinVendre();
		this.actualiserOffreTotale();
		this.actualiserImportanceTransformateurs();
		this.actualiserQuantitesMisesEnVente();
	}
	
	public double donnerQuantiteMiseEnVente(ITransformateur t) {
		return this.quantitesMisesEnVente.get(t);
	}
}
