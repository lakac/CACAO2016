package abstraction.commun;

/**
 * Classe modelisant les tarifs
 * On a une variable d'instance prix et 3 variables d'instance pour avoir des plages de valeurs où l'on fixe un rabais
 * 
 */

public class Tarif {
	private double prixTonne;
	private double quantiteMin;
	private double quantiteMax;
	private double rabais;
	
	public Tarif(double quantiteMin, double quantiteMax, double rabais) {
		this.quantiteMin = quantiteMin;
		this.quantiteMax = quantiteMax;
		this.rabais = rabais;
	}
	
	public double getPrixTonne() {
		return prixTonne;
	}
	
	public void setPrixTonne(double d) {
		this.prixTonne = d;
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
		return this.rabais;
	}

	public void setRabais(double rabais) {
		this.rabais = rabais;
	}


	
}
