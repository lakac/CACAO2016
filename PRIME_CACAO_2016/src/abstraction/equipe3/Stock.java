package abstraction.equipe3;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.ITransformateur;

import abstraction.commun.Produit;

public class Stock {
	
	private ArrayList<Double[]> stock;
	private Leclercv2 leclerc;
	private double fraisDeStock;
	
	public Stock(Leclercv2 leclerc, ArrayList<Double[]> stock, double fraisDeStock){
		this.leclerc=leclerc;
		this.stock=stock;
		this.fraisDeStock=fraisDeStock;
	}
	
	public double getFraisDeStockTotal(){
		double fraisDeStockTotal= 0.0;
		for (ITransformateur t : this.leclerc.getTransformateurs()){
			for (int i=0; i<3;i++){
				fraisDeStockTotal += this.getStock(t,i)*this.fraisDeStock;
			}
		} return fraisDeStockTotal;
	}
	public void setFraisDeStock(double fraisDeStock){
		this.fraisDeStock=fraisDeStock;
	}
	
	public void initialiseStock(){
		this.stock = new ArrayList<Double[]>();
		Double[] l = {0.0,0.0,0.0};
		for (int i=0;i<this.leclerc.getTransformateurs().size();i++){
			this.stock.add(l);
		}
	}
	
	public double getStock(ITransformateur t, int  indexproduit){
		double stock=0;
		for (int i=0;i<this.leclerc.getTransformateurs().size();i++){
			if (t.equals(this.leclerc.getTransformateurs().get(i))){
				stock=this.stock.get(i)[indexproduit];
			}
		} return stock;
	}
	public Double[] getStock(ITransformateur t){
		Double[] stock = {0.0, 0.0, 0.0};
		for (int i=0;i<this.leclerc.getTransformateurs().size();i++){
			if (t.equals(this.leclerc.getTransformateurs().get(i))){
				stock=this.stock.get(i);
			}
		} return stock;
	}
	
	public void ajouterStock (CommandeDistri com) {
		Double[] x;
		for (int i=0;i<this.leclerc.getTransformateurs().size();i++){
			if (com.getVendeur().equals(this.leclerc.getTransformateurs().get(i))){
				x=this.stock.get(i);
				this.stock.remove(i);
				if (com.getProduit().getNomProduit()=="50%"){
					x[0]+=com.getQuantite();
				}
				else{
					if (com.getProduit().getNomProduit()=="60%"){
						x[1]+=com.getQuantite();
					}
					else{
						x[2]+=com.getQuantite();
					}
				}
				this.stock.add(i, x);
			}
		} 
	}
	public void retirerStock (CommandeDistri com) {
		Double[] x;
		for (int i=0;i<this.leclerc.getTransformateurs().size();i++){
			if (com.getVendeur().equals(this.leclerc.getTransformateurs().get(i))){
				x=this.stock.get(i);
				this.stock.remove(i);
				if (com.getProduit().getNomProduit()=="50%"){
					x[0]-=com.getQuantite();
				}
				else{
					if (com.getProduit().getNomProduit()=="60%"){
						x[1]-=com.getQuantite();
					}
					else{
						x[2]-=com.getQuantite();
					}
				}
				this.stock.add(i, x);
			}
		} 
	}
	public void rajoutStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.ajouterStock(com);
		}
	}
	public void retraitStock(List<CommandeDistri> l){
		for (CommandeDistri com : l){
			this.retirerStock(com);
		}
	}
}
