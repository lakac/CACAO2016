package abstraction.commun;

public class CommandeProduc extends Commande{

	/**
	 * Classe modelisant les commandes entre producteur et transformateur
	 * 
	 * @author equipe 5
	 */
	
	private ITransformateurD acheteur;
	private IProducteur vendeur;
	
	public CommandeProduc(double quantite, double prixTonne) {
		super(quantite, prixTonne);
	}
	
	
	// Toutes les méthodes depraciated
	
	/**
	 * @depreciated
	 */
	public CommandeProduc(ITransformateurD acheteur, IProducteur vendeur, double quantite, double prixTonne) {
		super(quantite, prixTonne);
		this.acheteur = acheteur;
		this.vendeur = vendeur;
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
}
