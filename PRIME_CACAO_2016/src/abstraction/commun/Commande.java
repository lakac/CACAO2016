package abstraction.commun;

/**
 * Classe abstraite qui permet de factoriser le code des deux classes CommandeDistri et CommandeProduc
 * 
 * @author equipe 5
 */

public abstract class Commande {
	private double quantite;
	private double prixTonne;
	
	public Commande(double quantite, double prixTonne) {
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}
	
	public double getQuantite(){
		return this.quantite;
	}

	public double getPrixTonne() {
		return this.prixTonne;
	}
	
	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}
	
	public void setPrixTonne(double prixTonne) {
		this.prixTonne = prixTonne;
	}
	
	public String toString() {
		return ("Quantite Commande Distributeur : " + this.getQuantite() + "\n");
	}
}
