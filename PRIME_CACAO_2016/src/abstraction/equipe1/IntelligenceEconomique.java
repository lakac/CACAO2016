package abstraction.equipe1;

import java.util.ArrayList;
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
	private double quantiteMiseEnVente;
	private final double[] coeffPerissabilite = {0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
	
	public IntelligenceEconomique(List<ITransformateur> t, Stock s) {
		this.transformateurs = t;
		this.importanceTransformateurs = new HashMap<ITransformateur,Double>();
		this.envieVendre = 0;
		this.besoinVendre = 0;
		this.stock = s;
		this.quantiteMiseEnVente = 0;
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
	
	private void actualiserQuantiteMiseEnVente() {
		this.quantiteMiseEnVente = this.besoinVendre*(1-this.envieVendre) + this.stock.getQuantite()*this.envieVendre;
	}
	
	public void actualiser() {
		this.actualiserEnvieVendre();
		this.actualiserBesoinVendre();
		this.actualiserImportanceTransformateurs();
		this.actualiserQuantiteMiseEnVente();
	}
	
	public double donnerQuantiteMiseEnVente() {
		this.actualiser();
		return this.quantiteMiseEnVente;
	}
}
