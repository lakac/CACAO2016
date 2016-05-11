package abstraction.equipe3;

public class Stock {
	
	private double[] stock;
	
	public Stock(double[] stock){
		this.stock=stock;
	}
	public double getStock(int i){
		return this.stock[i];
	}
	public void setStock(int i, double stock){
		this.stock[i]=stock;
	}
	public void ajouterStock(int i, double stock){
		this.setStock(i, stock+this.getStock(i));
	}
	public void retirerStock(int i, double stock){
		this.setStock(i, stock-this.getStock(i));
	}
	
}
