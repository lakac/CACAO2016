package abstraction.equipe4;

import abstraction.fourni.*;

public class Stock {
	private Indicateur stock;
	private double perte;
	
	public Stock (Acteur a) {
		this.stock = new Indicateur ("Stock de "+a.getNom(),a,0.0);
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
		this.perte = 0.0;
	}
	public Indicateur getStock() {
		return this.stock;
	}
	public void setStock(Indicateur stock) {
		this.stock = stock;
	}
	public double getPerte() {
		return this.perte;
	}
	public void setPerte(double perte) {
		this.perte = perte;
	}
	
	//Réduction du stock d'une valeur de value
	public void reductionStock(double value){
		if (value>0){
		this.getStock().setValeur(this, this.getStock().getValeur()- value);	
		}
	}
	//Augmentation du stock d'une valeur de value
	public void augmentationStock(double value){
		if (value>0) {				
			this.getStock().setValeur(this, this.getStock().getValeur()+value);
		}
	}

	

}
