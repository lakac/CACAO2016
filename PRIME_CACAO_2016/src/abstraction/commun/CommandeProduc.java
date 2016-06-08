 package abstraction.commun;

public class CommandeProduc {

	/**
	 * Classe modelisant les commandes entre producteur et transformateur
	 * 
	 * @author equipe 5
	 */
	
	private ITransformateur acheteur;
	private IProducteur vendeur;
	private double quantite;
	private double prixTonne;

	public CommandeProduc(ITransformateur acheteur, IProducteur vendeur, double quantite, double prixTonne) {
		this.acheteur = acheteur;
		this.vendeur = vendeur;
		this.quantite = quantite;
		this.prixTonne = prixTonne;
	}

	public ITransformateur getAcheteur() {
		return this.acheteur;
	}

	public IProducteur getVendeur() {
		return this.vendeur;
	}

	public void setAcheteur(ITransformateur acheteur) {
		this.acheteur = acheteur;
	}

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
