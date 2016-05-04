package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	
	private Indicateur stockCacao;
	private double perteStock;
	private Producteur prod;
	private double coutStock;
	
	public Stock(Producteur p) {
		this.stockCacao = new Indicateur("Stock de "+ p.getNom(),p,0.0);
    	Monde.LE_MONDE.ajouterIndicateur(this.stockCacao);
    	this.perteStock=0.0;
    	this.prod=p;
    	this.coutStock=0.001;

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
	
	public double getCoutStock() {
		return this.coutStock;
	}

	public void setPerteStock() {
		this.perteStock = this.getStockCacao().getValeur()*Math.random()*0.05;
	}
	
	//Réduction du stock d'une valeur de value
	public void reductionStock(double value){
		if (value>0){
		this.getStockCacao().setValeur(this.prod, this.getStockCacao().getValeur()- value);	
		}
	}
	
	//Augmentation du stock d'une valeur de value
	public void augmentationStock(double value){
		if (value>0){
			this.getStockCacao().setValeur(this.prod, this.getStockCacao().getValeur()+value);
		}
	}

	public void perteDeStock(){
		this.stockCacao.setValeur(this.prod,this.stockCacao.getValeur()-this.perteStock);
	}
	

	
}
