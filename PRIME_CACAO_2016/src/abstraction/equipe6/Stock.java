package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;


import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Stock {
	
	private List<Indicateur> stockparproduit;
	private List<Double> capacitemax;
	
	public Stock() {
		this.stockparproduit = new ArrayList<Indicateur>();
		this.capacitemax = new ArrayList<Double>();
	}
	
	public Stock(List<Indicateur> I, List<Double> D) {
		this.stockparproduit = I;
		this.capacitemax = D;
	}
	
	public List<Indicateur> getstockParProduit() {
		return this.stockparproduit;
	}
	
	public List<Double> getCapaciteMax() {
		return this.capacitemax;
	}
	
	public void setStockParProduit(int i,double q, Acteur Carrefour) {
		this.stockparproduit.get(i).setValeur(Carrefour , q);
	}
	
	public void setCapaciteMax(int i,double q) {
		this.getCapaciteMax().set(i, q);
	}
	
}