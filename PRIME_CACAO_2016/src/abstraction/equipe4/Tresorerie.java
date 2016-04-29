package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	private Indicateur fond;
	// prix de vente
	private double prix; 
	// cout de prod
	private double CoutProd; 
	private Acteur Prod;
	
	
	public Tresorerie(Acteur a, double prix, double cout) {
		this.fond = new Indicateur("Fond de" + a.getNom(),a,0.0);
		this.prix = prix;
		this.CoutProd = cout;
		this.Prod= a;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	
	}


	public double getCoutProd() {
		return this.CoutProd;
	}
	
	public double getPrix() {
		return this.prix;
	}



	public double PrixVente(double commande){
		return commande*this.prix;
	}

	
	public void modiftreso(double commande){
		
		this.fond.setValeur(this.Prod, PrixVente(commande) + this.fond.getValeur()- this.CoutProd);
	
	}
		
	}
	
