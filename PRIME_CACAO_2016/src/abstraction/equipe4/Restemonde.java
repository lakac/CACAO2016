package abstraction.equipe4;

import abstraction.fourni.*;
import abstraction.commun.*;


public class Restemonde implements Acteur,ITransformateur {
	private String nom;
	private double demande;
	private Producteur p;
	
	public Restemonde(Producteur p) {
		this.nom="Reste du monde transoformateurs";
		this.demande=0.0;
		this.p=p;
	}
	
	public double getDemande() {
		return demande;
	}
	
	public Producteur getP() {
		return this.p;
	}
	
	
	public void setDemande(double demande) {
		this.demande = demande;
	}
	
	public void venteResteMonde (double demande){
		this.Fond.setFond(this.getFond()+this.setDemande()*getCours());
		this.stockCacao.getStock().perteDeStock(this.demande);
	}


	public String getNom() {
		return this.nom;
	}


	public void next() {
		// TODO Auto-generated method stub
		
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.stockCacao/12*85/100;
	}

	@Override
	public void notificationVente(IProducteur p) {
		
		
	}
	
	
	
	

}
