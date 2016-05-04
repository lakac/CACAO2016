package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	
	private Indicateur stock;
	private double perteStock;
	private Producteur prod;
	
	public Stock(Producteur p) {
		this.stock = new Indicateur("Stock de "+ p.getNom(),p,0.0);
    	Monde.LE_MONDE.ajouterIndicateur(this.stock);
    	this.perteStock=0.0;
    	this.prod=p;

	}

	public Indicateur getStock() {
		return this.stock;
	}

	public double getPerteStock() {
		return this.perteStock;
	}

	public Acteur getProd() {
		return this.prod;
	}

	public void setPerteStock() {
		this.perteStock = this.getStock().getValeur()*Math.random()*0.05;
	}
	
	//Réduction du stock d'une valeur de value
	public void reductionStock(double value){
		if (value>0){
		this.getStock().setValeur(this.prod, this.getStock().getValeur()- value);	
		}
	}
	
	//Augmentation du stock d'une valeur de value
	public void augmentationStock(double value){
		if (value>0){
			this.getStock().setValeur(this.prod, this.getStock().getValeur()+value);
		}
	}

	public void perteDeStock(){
		this.stock.setValeur(this.prod,this.stock.getValeur()-this.perteStock);
	}
	

	
}
