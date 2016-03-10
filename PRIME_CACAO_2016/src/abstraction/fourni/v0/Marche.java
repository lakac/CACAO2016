package abstraction.fourni.v0;

import java.util.ArrayList;

import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class Marche implements Acteur {
	public static Marche LE_MARCHE;	
	public static final double COURS = 3000.0;
	
	private double quantiteAchetable;

	private ArrayList<Producteur> producteurs;
	private ArrayList<Transformateur> transformateurs;

	public Marche() {
		this.producteurs = new ArrayList<Producteur>();
		this.transformateurs = new ArrayList<Transformateur>();
		this.quantiteAchetable = 0.0;
	}
	
	public void addProducteur(Producteur p) {
		this.producteurs.add(p);
	}
	public void addTransformateur(Transformateur t) {
		this.transformateurs.add(t);
	}
	public double getCours() {
		return Marche.COURS;
	}
	public double vendre(double quantite) {
		this.quantiteAchetable += quantite;
		return quantite*Marche.COURS;
	}
	public String getNom() {
		return "Marche du cacao";
	}
	public void next() {
		double[] quantitesEnVente = new double[this.producteurs.size()];
		for (int i=0; i<this.producteurs.size(); i++) {
			quantitesEnVente[i] = this.producteurs.get(i).quantiteMiseEnVente();
		}
		double totalQuantitesEnVente =0.0;
		for (double q : quantitesEnVente) {
			totalQuantitesEnVente+=q;
		}
		double[] quantitesDemandees = new double[this.transformateurs.size()];
		for (int i=0; i<this.transformateurs.size(); i++) {
			quantitesDemandees[i] = this.transformateurs.get(i).quantiteSouhaitee();
		}
		double totalQuantitesDemandees =0.0;
		for (double d : quantitesDemandees) {
			totalQuantitesDemandees+=d;
		}
		double[] quantitesReellementAchettes  = new double[this.transformateurs.size()]; 
		double[] quantitesReellementVendues  = new double[this.producteurs.size()]; 
		if (totalQuantitesDemandees>totalQuantitesEnVente) { // demande > offre
			for (int i=0; i<this.transformateurs.size(); i++) {
				quantitesReellementAchettes[i]=quantitesDemandees[i]*totalQuantitesEnVente/totalQuantitesDemandees;
			}
			for (int i=0; i<this.producteurs.size(); i++) {
				quantitesReellementVendues[i]=quantitesEnVente[i];
			}
			
		} else {// offre >= demande
			for (int i=0; i<this.transformateurs.size(); i++) {
				quantitesReellementAchettes[i]=quantitesDemandees[i];
			}
			for (int i=0; i<this.producteurs.size(); i++) {
				quantitesReellementVendues[i]=quantitesEnVente[i]*totalQuantitesDemandees/totalQuantitesEnVente;
			}
		}
		
		for (int i=0; i<this.transformateurs.size(); i++) {
			this.transformateurs.get(i).notificationVente(quantitesReellementAchettes[i]);
		}
		for (int i=0; i<this.producteurs.size(); i++) {
			this.producteurs.get(i).notificationVente(quantitesReellementVendues[i]);
		}
		
		
	}
}	

