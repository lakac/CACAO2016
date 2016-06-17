package abstraction.commun;

/**
 * Classe modelisant les commandes entre distributeur et transformateur
 * 
 * @author equipe 5
 */

public class CommandeDistri implements Comparable {
	private IDistributeur acheteur;
	private ITransformateur vendeur;
	private Produit produit;
	private double quantite;
	private double prixTonne;
	private boolean validation;
	private int stepLivraison;
	
	//Ajout d'un constructeur simple pour les test (11/06, équipe 2)
	public CommandeDistri(Produit produit, double quantite, double prixtonne) {
		this.quantite = quantite;
		this.prixTonne = prixtonne;
	}

	public CommandeDistri(IDistributeur acheteur, ITransformateur vendeur, Produit produit, double quantite, double prixTonne, int stepLivraison, boolean validation) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.produit = produit; //Change string en produit 19/05 A.MARTY
		this.quantite = quantite;
		this.prixTonne=prixTonne;
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

	public double getPrixTonne() {
		return this.prixTonne;
	}
	
	public int getStepLivraison() {
		return this.stepLivraison;
	}
	
	public boolean getValidation() {
		return this.validation;
	}
	
	/** pas codÃ©e par l'Ã©quipe 5, donc on ne sait pas ce qu'elle fait et Ã  quoi elle sert */
	public boolean equals(Object o){
		return (o!=null && o instanceof CommandeDistri && ((CommandeDistri)o).getAcheteur()==this.getAcheteur()
				&& ((CommandeDistri)o).getVendeur()==this.getVendeur() && ((CommandeDistri)o).getProduit()==this.getProduit()
					&& ((CommandeDistri)o).getStepLivraison()==this.getStepLivraison());
	}

	///Equipe 2, le 17/06 J'ai besoin de rendre une commande comparable
	//Un commande est supérieure à une autre si la quantité commandée est plus grande.
	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof CommandeDistri) {
			return ((int) (this.getQuantite()-((CommandeDistri) arg0).getQuantite()));
		}
		else {
			return -1;
		}
	}
}
