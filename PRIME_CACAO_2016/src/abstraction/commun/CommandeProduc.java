package abstraction.commun;

import abstraction.fourni.Acteur;

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

	public CommandeProduc(ITransformateur t,IProducteur p, double quantite,double prixTonne ){
		this(quantite,prixTonne);
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

	@Deprecated public ITransformateur getAcheteur(){
		return null;
	}
	
	@Deprecated public IProducteur getVendeur(){
		return null;
	}
}
