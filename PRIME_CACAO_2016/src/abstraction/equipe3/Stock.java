package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.ITransformateur;
import abstraction.equipe3.Leclerc;

public class Stock {
	
	private ArrayList<Double[]> stock;
	private Leclerc leclerc;
	private double fraisDeStock;
	
	public Stock(Leclerc leclerc, ArrayList<Double[]> stock){
		this.leclerc=leclerc;
		this.stock=stock;
	}
	
	public double getFraisDeStockTotal(double fraisDeStock){
		this.fraisDeStock = 0.0;
		for (ITransformateur t : this.leclerc.getTransformateurs()){
			for (int i=0; i<3;i++){
				this.fraisDeStock += this.getStock(t,i)*fraisDeStock;
			}
		} return this.fraisDeStock;
	}
	
	public void initialiseStock(){
		this.stock = new ArrayList<Double[]>();
		Double[] l = {0.0,0.0,0.0};
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			this.stock.add(l);
		}
	}
	
	public double getStock(ITransformateur t, int  indexproduit){
		double stock=0;
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			if (t.equals(this.leclerc.getTransformateurs(i))){
				stock=this.stock.get(i)[indexproduit];
			}
		} return stock;
	}
	
	public void setStock (ITransformateur t, int indexproduit) {
		Double[] x;
		for (int i=0;i<this.leclerc.nombreTransformateur();i++){
			if (t.equals(this.leclerc.getTransformateurs(i))){
				x=this.stock.get(i);
				this.stock.remove(i);
				x[indexproduit]+=this.leclerc.getDemande(t)-this.leclerc.getVente(t);
				this.stock.add(i, x);
			}
		} 
	}
	
}
