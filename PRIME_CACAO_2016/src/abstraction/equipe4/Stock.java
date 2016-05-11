package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	
	private Indicateur stockCacao;
	// pertes de stock bi-mensuelle.
	private double perteStock;
	private Producteur prod;
	
	public Stock(Producteur p) {
		this.stockCacao = new Indicateur("Stock de "+ p.getNom(),p,0.0);
    	Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
    	this.perteStock=0.0;
    	this.prod=p;

	}

	public Indicateur getStockCacao() {
		return this.stockCacao;
	}

	public double getPerteStock() {
		return this.perteStock;
	}

	public Producteur getProd() {
		return this.prod;
	}
	
	// perte de stock semi-mensuelle (à chaque step on perd du stock)
	public void setPerteStock() {
		this.perteStock = this.getStockCacao().getValeur()*Math.random()*0.05;
	}
	
	//Réduction du stock d'une valeur value
	public void reductionStock(double value){
		if (value>0){
		this.getStockCacao().setValeur(this.getProd(), this.getStockCacao().getValeur()- value);	
		}
	}
	
	//Augmentation du stock d'une valeur value
	public void augmentationStock(double value){
		if (value>0){
			this.getStockCacao().setValeur(this.getProd(), this.getStockCacao().getValeur()+value);
		}
	}
	
	// Perte de stock 
	public void perteDeStock(){
		this.getStockCacao().setValeur(this.getProd(),this.getStockCacao().getValeur()-this.getPerteStock());
	}
	
	public void coutStock(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()-Couts.COUTSTOCK*this.getProd().getStock().getStockCacao().getValeur());
	}
	

	
}
