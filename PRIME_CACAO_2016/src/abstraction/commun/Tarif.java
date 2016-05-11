package abstraction.commun;
import java.util.List;

/**
 * Classe modelisant les tarifs
 * On a une variable d'instance prix et 3 variables d'instance pour avoir des plages de valeurs où l'on fixe un rabais
 * 
 */

public class Tarif {
	private double prixTonne;
	private List<Plage> plage;
	
	
	public Tarif(double prixTonne, List<Plage> plage) {
		this.prixTonne = prixTonne;
		this.plage = plage;
	}
	
	public double getPrixTonne() {
		return prixTonne;
	}
	
	public void setPrixTonne(double d) {
		this.prixTonne = d;
	}

	public List<Plage> getPlage() {
		return plage;
	}

	public void setPlage(List<Plage> plage) {
		this.plage = plage;
	}
<<<<<<< HEAD
=======

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


>>>>>>> branch 'master' of https://github.com/florianed/CACAO2016.git
	
	public double prixDeVente(double quantite) {
		for (Plage p : this.getPlage()) {
			if (p.quantiteDansPlage(quantite)) {
				return (quantite*this.getPrixTonne() - p.getRabais()*this.getPrixTonne()*quantite);
			}
		}
		return quantite*this.getPrixTonne();
	}
}
