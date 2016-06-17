package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	
	// notre stock de cacao actuelle
	private Indicateur stockCacao;
	// pertes de stock bi-mensuelle.
	private double perteStock;
	// Nous
	private Producteur prod;
	
	public Stock(Producteur p) {
		this.stockCacao = new Indicateur("Stock de Asie Amerique",p,0.0);
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
	
	// definition des perte de stock semi-mensuelle (a chaque step on perd du stock)
	public void setPerteStock() {
		this.perteStock = this.getStockCacao().getValeur()*Math.random()*0.05;
	}
	
	//Reduction du stock d'une valeur value
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
	
	// Modification du stock en enlevant les pertes de stock
	public void perteDeStock(){
		this.getStockCacao().setValeur(this.getProd(),this.getStockCacao().getValeur()-this.getPerteStock());
	}
	
	// Modification de la treso en enlevant les couts de stock
	public void coutStock(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()-Couts.COUTSTOCK*this.getProd().getStock().getStockCacao().getValeur());
	}
	
	// actualise des variables liees au stock :
	// Diminution des stock a cause des pertes naturelles et diminution de la tresorerie a cause des couts de stock
	public void gererLesStock(){
		this.setPerteStock();
		this.perteDeStock();
		this.coutStock();
	}
	
}
