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

	public void setProduction(Nestle nestle, CommandeDistri commandedistri) {
		if (nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit())>commandedistri.getQuantite()/2) {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()*Math.min(
					nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit()), 
					commandedistri.getQuantite()));
		}
		else {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()
					*Math.min(commandedistri.getQuantite()*(3/2)-nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit())
							,nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit())));
		}
		
	}
	
	public static double CoutTransformation(Achat achat) {
		return -1;
	}
	
}
