package abstraction.equipe5;

import abstraction.commun.IProducteur;

public class CommandeProduc /*implements ICommande*/ {
	private IProducteur producteur;
	private double quantite;
	private double prixTonne;

	public CommandeProduc(IProducteur producteur, double quantite, double prixTonne) {
		this.producteur = producteur;
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}

	public IProducteur getProducteur() {
		return this.producteur;
	}

	public double getQuantite(){
		return this.quantite;
	}

	public double getPrixTonne() {
		return this.prixTonne;
	}
}

/* une seule classe plutôt que deux, pb avec IProducteur différent de IDistributeur */
