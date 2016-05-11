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
		this.getP().getTreso().getFond().setFond(this.getP().getTreso().getFond()+this.getDemande()*marcheProducteur.getCours());
		this.getP().getStock().reductionStock(demande);
	}


	public String getNom() {
		return this.nom;
	}


	public void next() {
		// TODO Auto-generated method stub
		
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return (this.getP().getTransformateurs().get(1).annonceQuantiteDemandee(this.getP())
				+this.getP().getTransformateurs().get(2).annonceQuantiteDemandee(this.getP()))*(82.0+Math.random()*2.0)/14.0;
	}

	@Override
	public void notificationVente(IProducteur p) {
		
		
	}
	
	
	
	

}
