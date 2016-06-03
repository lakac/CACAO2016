package abstraction.commun;

/**
 * Classe modelisant les plages de valeurs qui permettent d'établir les différents
 * rabais en fonction des plages de quantité achetée
 * 
 * @author equipe 5
 */

public class Plage {
	private double quantiteMin;
	private double quantiteMax;
	private double rabais;
	

	public Plage(double quantiteMin, double quantiteMax, double rabais) {
		this.quantiteMin = quantiteMin;
		this.quantiteMax = quantiteMax;
		this.rabais = rabais;
	}
	
	public Plage(double quantiteMin, double rabais){
		this.quantiteMin=quantiteMin;
		this.rabais=rabais;
	}
	
	public double getQuantiteMin() {
		return quantiteMin;
	}

	public void setQuantiteMin(double quantiteMin) {
		this.quantiteMin = quantiteMin;
	}

	public double getQuantiteMax() {
		return quantiteMax;
	}

	public void setQuantiteMax(double quantiteMax) {
		this.quantiteMax = quantiteMax;
	}

	public double getRabais() {
		return rabais;
	}

	public void setRabais(double rabais) {
		this.rabais = rabais;
	}	
	
	/** méthode qui permet de savoir si une quantité est comprise dans la plage de valeur */
	public boolean quantiteDansPlage(double quantite) {
		return (quantite >= this.getQuantiteMin() && quantite <= this.getQuantiteMax());
	}
}
