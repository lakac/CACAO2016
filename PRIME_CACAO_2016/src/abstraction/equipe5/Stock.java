package abstraction.equipe5;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.equipe5.Lindt;

public class Stock {
	private Indicateur stock;
	private Lindt lindt;
	private String nom;
	
	public Stock (double d, Lindt lindt, String nom) {
		this.lindt=lindt;
		this.nom=nom;
		this.stock = new Indicateur("stock de "+ this.nom +"de Lindt", this.lindt, d);
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
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
