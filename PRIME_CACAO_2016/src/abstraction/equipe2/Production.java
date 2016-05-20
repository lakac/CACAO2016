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
		this.add(Constante.PRODUIT_50);
		this.add(Constante.PRODUIT_60);
		this.add(Constante.PRODUIT_70);
		
	}

	
	
	public void setProduction(Nestle nestle, CommandeDistri commandedistri) {
		if (nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit())>commandedistri.getQuantite()/2) {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()*Math.min(
					nestle.getStockcac().getStockcacao(), 
					commandedistri.getQuantite()));
		}
		else {
			this.production.put(commandedistri.getProduit(),commandedistri.getProduit().getRatioCacao()
					*Math.min(commandedistri.getQuantite()*(3/2)-nestle.getStockcac().getStockcacao()
							,nestle.getStockchoc().getStockschocolats().get(commandedistri.getProduit())));
		}
		
	}
	
	public double PrixdeventeDeBase(Production prod, Produit p) {
		return (1+Constante.MARGE)*prod.CoutTransformation(p);
	} 
	
	public double CoutTransformation(Produit p) {
		double couttransformation = 0.0;
		couttransformation+=this.getProduction().get(Constante.PRODUIT_50);
		return couttransformation;
	}
	
	public double PrixdeventeDeBase(Produit p) {
		return (1+Constante.MARGE)*this.CoutTransformation(p);
	}
	
}
