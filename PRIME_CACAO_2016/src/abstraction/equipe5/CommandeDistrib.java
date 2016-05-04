package abstraction.equipe5;

import abstraction.commun.IDistributeur;

public class CommandeDistrib /*implements ICommande*/ {
	private IDistributeur distributeur;
	private double quantite;
	private double prixTonne;
	
	public CommandeDistrib(IDistributeur distributeur, double quantite, double prixTonne) {
		this.distributeur = distributeur;
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}
	
	public IDistributeur getDistributeur() {
		return this.distributeur;
	}
	
	public double getQuantite(){
		return this.quantite;
	}
	
	public double getPrixTonne() {
		return this.prixTonne;
	}
}
