package abstraction.equipe2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	public double PrixdeventeDeBase(Produit p) {
		return (Constante.ACHAT_SANS_PERTE+Constante.MARGE)*this.CoutTransformation(p);
	} 
	
	public double CoutTransformation(Produit p) {
		double couttransformation = 0.0;
		couttransformation+=this.getProduction().get(p)*Constante.COUT_DE_TRANSFORMATION;
		return couttransformation;
	}
	
	public double CoutTransformationGlobal() {
		double Couttot = 0.0;
		for (Produit p : this.production.keySet()) {
			Couttot+=this.CoutTransformation(p);
		}
		return Couttot;
	}
	
	public PlageInterne plageinterne() {
		// Initialisation des plages de réduction
				List<Plage> listplageproduit = new ArrayList<Plage>();
				Plage plage1= new Plage(0,500,0);
				Plage plage2= new Plage(500.1,1500,0.03);
				Plage plage3= new Plage(1500.1,4000,0.07);
				Plage plage4= new Plage(4 000.1,0.10);
				listplageproduit.add(plage1);
				listplageproduit.add(plage2);
				listplageproduit.add(plage3);
				listplageproduit.add(plage4);
				//Dictionnaire des prix de base de la step d'avant
				HashMap<Produit, Double> prixdebase = new HashMap<Produit, Double>();
				prixdebase.put(Constante.PRODUIT_50, this.PrixdeventeDeBase(Constante.PRODUIT_50));
				prixdebase.put(Constante.PRODUIT_60, this.PrixdeventeDeBase(Constante.PRODUIT_60));
				prixdebase.put(Constante.PRODUIT_70, this.PrixdeventeDeBase(Constante.PRODUIT_70));
				//plage interne des tarifs
				PlageInterne plageinterne = new PlageInterne();
				Tarif tarifproduit1 = new Tarif(prixdebase.get(Constante.PRODUIT_50),listplageproduit);
				Tarif tarifproduit2 = new Tarif(prixdebase.get(Constante.PRODUIT_60),listplageproduit);
				Tarif tarifproduit3 = new Tarif(prixdebase.get(Constante.PRODUIT_70),listplageproduit);
				plageinterne.setTarifproduit(Constante.PRODUIT_50, tarifproduit1);
				plageinterne.setTarifproduit(Constante.PRODUIT_60, tarifproduit2);
				plageinterne.setTarifproduit(Constante.PRODUIT_70, tarifproduit3);
				System.out.println(plageinterne);
				return plageinterne;
	}
	
}
