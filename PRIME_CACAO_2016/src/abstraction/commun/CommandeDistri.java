package abstraction.commun;

/**
 * Classe modelisant les commandes entre distributeur et transformateur
 * 
 *
 */

public class CommandeDistri {
	private IDistributeur acheteur;
	private ITransformateur vendeur;
	private String produit; // A changer!!!
	private double quantite;
	private double prixTonne;

	public CommandeDistri(IDistributeur acheteur, ITransformateur vendeur, String produit, double quantite) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
	}

	public String getProduit() {
		return this.produit;
	}

	public void setProduit(String produit) {
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
