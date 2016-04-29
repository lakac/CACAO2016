package abstraction.equipe5;

import abstraction.fourni.Acteur; 

public class Commande {
	private Acteur acteur;
	private double quantite;
	private double prixTonne;
	
	public Commande(Acteur acteur, double quantite, double prixTonne) {
		this.acteur = acteur;
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}
	
	public Acteur getActeur() {
		return this.acteur;
	}
	
	public double getQuantite(){
		return this.quantite;
	}
	
	public double getPrixTonne() {
		return this.prixTonne;
	}
}
