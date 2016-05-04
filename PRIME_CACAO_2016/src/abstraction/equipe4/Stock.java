package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	
	private Indicateur stock;
	private double perteStock;
	private Acteur prod;
	
	public Stock(Acteur a) {
		this.stock = new Indicateur("Stock de "+ a.getNom(),a,0.0);
    	Monde.LE_MONDE.ajouterIndicateur(this.stock);
    	this.perteStock=0.0;
    	this.prod=a;

	}

	public Indicateur getStock() {
		return this.stock;
	}

	public void setStock(Indicateur stock) {
		this.stock = stock;
	}

	public double getPerteStock() {
		return this.perteStock;
	}

	public void setPerteStock(double perteStock) {
		this.perteStock = perteStock;
	}
	
	
	public Acteur getProd() {
		return this.prod;
	}

	public void setProd(Acteur prod) {
		this.prod = prod;
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
	

	
}
