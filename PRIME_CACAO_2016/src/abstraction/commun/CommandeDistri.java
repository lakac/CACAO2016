package abstraction.commun;

/**
 * Classe modelisant les commandes entre distributeur et transformateur
 * 
 *
 */

public class CommandeDistri {
	private IDistributeur acheteur;
	private ITransformateur vendeur;
	private Produit produit;
	private double quantite;
	private double prix;
	private double prixTonne;
	private boolean validation;
	private int stepLivraison;

	public CommandeDistri(IDistributeur acheteur, ITransformateur vendeur, Produit produit, double quantite, double prix, int stepLivraison, boolean validation) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		this.prix=prix;
		this.stepLivraison = stepLivraison;
		this.validation = validation;
	}

	public Produit getProduit() {
		return this.produit;
	}
	

	public void setProduit(Produit produit) {
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
	
	public void setPrix(double prix){
		this.prix=prix;
	}
	public void setPrixTonne(double prixTonne) {
		this.prixTonne = prixTonne;
	}
	
	public void setValidation(boolean b) {
		this.validation = b;
	}
	
	public void setStepLivraison(int i) {
		this.stepLivraison = i;
	}

	public double getQuantite(){
		return this.quantite;
	}
	
	public double getPrix(){
		return this.prix;
	}

	public double getPrixTonne() {
		return this.prixTonne;
	}
	
	public int getStepLivraison() {
		return this.stepLivraison;
	}
	
	public boolean getValidation() {
		return this.validation;
	}
}
