 package abstraction.commun;

public class CommandeProduc {

	/**
	 * Classe modelisant les commandes entre producteur et transformateur
	 * 
	 * @author equipe 5
	 */
	
	private double quantite;
	private double prixTonne;

	public CommandeProduc(double quantite, double prixTonne) {
		this.quantite = quantite;
		this.prixTonne = prixTonne;
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
