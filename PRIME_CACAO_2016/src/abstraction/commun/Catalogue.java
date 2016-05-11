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
	public Tarif getTarif(Produit p) {
		return this.cata.get(p);

	}
	public Set<Produit> getProduits() {
		return this.cata.keySet();
	}
}
