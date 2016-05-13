package abstraction.equipe2;
import java.util.HashMap;

import abstraction.commun.*;

public class Production {
	private HashMap<Produit,Double> production;
	
	public HashMap<Produit, Double> getProduction() {
		return production;
	}
	
	public void add(Produit p) {
		this.production.put(p, 0.0);
	}

	public void setProduction(CommandeDistri commandedistri) {
		if (commandeistri.getProduit())
	}
	
}
