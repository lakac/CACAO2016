package abstraction.commun;

/**
 * Classe modelisant les commandes entre distributeur et transformateur
 * 
 *
 */

public class CommandeDistri {
	private IDistributeur acheteur;
	private ITransformateur vendeur;
	private Produit produit; // Change A.Marty 19/05
	private double quantite;
	private double prixTonne;

	public CommandeDistri(IDistributeur acheteur, ITransformateur vendeur, Produit produit, double quantite) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.produit = produit; //Cange string en produit 19/05 A.MARTY
		this.quantite = quantite;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) { //Change string-->produit A.MARTY g2 19/05
		this.produit = produit;
	}

	public IDistributeur getAcheteur() {
		return this.acheteur;
	}

	public ITransformateur getVendeur() {
		return this.vendeur;
	}

	public void setAcheteur(IDistributeur acheteur) {
		this.acheteur = acheteur;
	}

	public void setVendeur(ITransformateur vendeur) {
		this.vendeur = vendeur;
	}

	public void setQuantite(double quantite) {
		this.quantite = quantite;
	}

	public void setPrixTonne(double prixTonne) {
		this.prixTonne = prixTonne;
	}

	public double getQuantite(){
		return this.quantite;
	}

	public double getPrixTonne() {
		return this.prixTonne;
	}
}
