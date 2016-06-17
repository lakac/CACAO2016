package abstraction.commun;

/**
 * Classe modelisant les commandes entre distributeur et transformateur
 * 
 * @author equipe 5
 */

public class CommandeDistri extends Commande {
	private IDistributeur acheteur;
	private ITransformateurD vendeur;
	private Produit produit;
	private boolean validation;
	private int stepLivraison;
	
	//Ajout d'un constructeur simple pour les test (11/06, �quipe 2)
	public CommandeDistri(Produit produit, double quantite, double prixtonne) {
		this.quantite = quantite;
		this.prixTonne = prixtonne;
	}

	public CommandeDistri(IDistributeur acheteur, ITransformateurD vendeur, Produit produit, double quantite, double prixTonne, int stepLivraison, boolean validation) {
		super(quantite, prixTonne);
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.produit = produit;
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

	public ITransformateurD getVendeur() {
		return this.vendeur;
	}

	public void setAcheteur(IDistributeur acheteur) {
		this.acheteur = acheteur;
	}

	public void setVendeur(ITransformateurD vendeur) {
		this.vendeur = vendeur;
	}
	
	public void setValidation(boolean b) {
		this.validation = b;
	}
	
	public void setStepLivraison(int i) {
		this.stepLivraison = i;
	}

	public int getStepLivraison() {
		return this.stepLivraison;
	}
	
	public boolean getValidation() {
		return this.validation;
	}
	
	/** pas codée par l'équipe 5, donc on ne sait pas ce qu'elle fait et à quoi elle sert */
	public boolean equals(Object o){
		return (o!=null && o instanceof CommandeDistri && ((CommandeDistri)o).getAcheteur()==this.getAcheteur()
				&& ((CommandeDistri)o).getVendeur()==this.getVendeur() && ((CommandeDistri)o).getProduit()==this.getProduit()
					&& ((CommandeDistri)o).getStepLivraison()==this.getStepLivraison());
	}
}
