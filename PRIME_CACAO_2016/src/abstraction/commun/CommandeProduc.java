package abstraction.commun;

public class CommandeProduc {

	/**
	 * Classe modelisant les commandes entre producteur et transformateur
	 * 
	 * @author equipe 5
	 */
	
	private ITransformateurD acheteur;
	private IProducteur vendeur;
	private double quantite;
	private double prixTonne;

	
	/**
	 * @depreciated
	 */
	public CommandeProduc(ITransformateurD acheteur, IProducteur vendeur, double quantite, double prixTonne) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}
	
	public CommandeProduc(double quantite, double prixTonne) {
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}
	
	/**
	 * @depreciated
	 */
	public ITransformateurD getAcheteur() {
		return this.acheteur;
	}

	/**
	 * @depreciated
	 */
	public IProducteur getVendeur() {
		return this.vendeur;
	}

	/**
	 * @depreciated
	 */
	public void setAcheteur(ITransformateurD acheteur) {
		this.acheteur = acheteur;
	}

	/**
	 * @depreciated
	 */
	public void setVendeur(IProducteur vendeur) {
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
