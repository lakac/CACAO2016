package abstraction.equipe4;

import abstraction.fourni.*;
import abstraction.commun.*;


public class ResteMonde implements Acteur,ITransformateur {
	private String nom;
	private double demande;
	private Producteur p;

	public ResteMonde(Producteur p) {
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
		this.getP().getTreso().getFond().setValeur(this, this.getP().getTreso().getFond().getValeur()+this.getP().getMarche().getCours()*demande);
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

	public void notificationVente(IProducteur p) {


	}





}
