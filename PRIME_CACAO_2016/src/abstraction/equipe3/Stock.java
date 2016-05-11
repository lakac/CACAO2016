package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.ITransformateur;
import abstraction.equipe3.Leclerc;

public class Stock {
	
	private ArrayList<Double> stock;
	private Leclerc leclerc;
	
	public Stock(Leclerc leclerc, ArrayList<Double> stock){
		this.leclerc=leclerc;
		this.stock=stock;
	}
	
	public void initialiseStock(){
		this.stock = new ArrayList<Double>();
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			this.stock.add(0.0);
		}
	}
	
	public double getStock(ITransformateur t){
		double stock=0;
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			if (t.equals(this.leclerc.getTransformateurs(i))){
				stock=this.stock.get(i);
			}
		} return stock;
	}
	
	public void setStock (ITransformateur t) {
		double x = this.getStock(t);
		x+=this.leclerc.getDemande(t)-this.leclerc.getVente(t);
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			if (t.equals(this.leclerc.getTransformateurs(i))){
				this.stock.remove(i);
				this.stock.add(i, x);
			}
		} 
	}
	
}
