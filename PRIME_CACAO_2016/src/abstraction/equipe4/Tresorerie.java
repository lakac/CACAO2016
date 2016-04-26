package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	private Indicateur fond;
	private double prix;
	private double CoutProd;
	private Acteur Prod;
	
	
	public Acteur getProd() {
		return Prod;
	}

	public void setProd(Acteur prod) {
		Prod = prod;
	}

	public Tresorerie(Acteur a, double prix, double cout) {
		this.fond = new Indicateur("Fond de" + a.getNom(),a,0.0);
		this.prix = prix;
		CoutProd = cout;
		this.Prod= a;
	}
	

	public Indicateur getFond() {
		return this.fond;
	}


	public double Vente(double commande){
		return commande*this.prix;
	}

	
	public void modiftreso(double commande){
		
		this.fond.setValeur(this.Prod, Vente(commande) + this.fond.getValeur()- this.CoutProd);
	
	}
		
	}
	