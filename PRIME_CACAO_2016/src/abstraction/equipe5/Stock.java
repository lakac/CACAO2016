package abstraction.equipe5;
import abstraction.fourni.Indicateur;
import abstraction.equipe5.Lindt;

public class Stock {
	private Indicateur stock;
	private Lindt lindt;
	
	public Stock (double d, Lindt lindt) {
		this.lindt=lindt;
		this.stock = new Indicateur("Stock de Lindt", this.lindt, d);
	}
	
	public double getStock() {
		return this.stock.getValeur();
	}

	public void setStock(double stock) {
		this.stock.setValeur(this.lindt, stock); 
	}
	
	
	public void ajouterStock(double d) {
		this.setStock(this.getStock()+d);
	}
	
	public void retirerStock(double d) {
		if (this.getStock()-d >= 0) {
			this.setStock(this.getStock()-d);
		}	
	}
}
