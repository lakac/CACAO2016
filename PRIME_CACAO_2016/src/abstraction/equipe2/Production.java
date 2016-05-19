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
	
	public Production() {
		this.production = new HashMap<Produit, Double>();
	}

	public void setProduction(CommandeDistri commandedistri) {
		if (Nestle.getStockchocolats().get(commandedistri.getProduit())>commandedistri.getQuantite()/2) {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()*Math.min(
					Nestle.getStockchocolats().get(commandedistri.getProduit()), 
					commandedistri.getQuantite()));
		}
		else {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()
					*Math.min(commandedistri.getQuantite()*(3/2)-Nestle.getStockchocolats().get(commandedistri.getProduit())
							,Nestle.getStockchocolats().get(commandedistri.getProduit())));
		}
		
	}
	
}
