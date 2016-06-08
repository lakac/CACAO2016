package abstraction.commun;

/**
 * Classe qui caractérise le catalogue des produits et des prix échangé entre 
 * les transformateurs et les distributeurs
 * Elle associe différents produits à leur tarif
 * 
 * @author equipe 5
 */

import java.util.HashMap;
import java.util.Set;

public class Catalogue {
	private HashMap<Produit,Tarif> cata;

	public Catalogue()  {
		this.cata = new HashMap<Produit,Tarif>();
	}
	
	public void add(Produit p, Tarif t) {
		this.cata.put(p, t);
	}

	public HashMap<Produit, Tarif> getCata() {
		return cata;
	}// Rajout� par groupe 2 le 19/05/2016 a 11h20

	/** méthode qui permet d'avoir le tarif d'un produit */
	public Tarif getTarif(Produit p) {
		return this.cata.get(p);
		
	}
	
	/** méthode qui permet d'avoir l'ensemble des produits du catalogue */
	public Set<Produit> getProduits() {
		return this.cata.keySet();
	}
}
