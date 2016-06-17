  package abstraction.commun;
import java.util.List;

/**
 * Classe modelisant les tarifs
 * Elle associe un prix à la tonne (plus tard d'un produit) à différentes
 * plages de valeur qui permettent de savoir les rabais
 * 
 * @author equipe 5
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
	
	/** 
	 * méthode qui permet de connaitre le prix de vente lorsque l'on met en argument une quantité
	 * Cela permet de prendre en compte les rabais 
	 * @param quantite
	 * @return le prix de vente avec prise en compte des rabais
	 */
	public double prixDeVente(double quantite) {
		for (Plage p : this.getPlage()) {
			if (p.quantiteDansPlage(quantite)) {
				return (quantite*this.getPrixTonne() - p.getRabais()*this.getPrixTonne()*quantite);
			}
		}
		return quantite*this.getPrixTonne();
	}
}
