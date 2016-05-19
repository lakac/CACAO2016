package abstraction.commun;

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
	}// Rajouté par groupe 2 le 19/05/2016 a 11h20

	public Tarif getTarif(Produit p) {
		return this.cata.get(p);

	}
	public Set<Produit> getProduits() {
		return this.cata.keySet();
	}
}
