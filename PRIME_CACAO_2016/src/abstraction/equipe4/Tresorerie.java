package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	// argent totale dans la cagnote
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
	
	
	public Acteur getProd(){
		return this.Prod;
	}

	public double getCoutProd() {
		return this.CoutProd;
	}
	
	public double getPrix() {
		return this.prix;
	}

	public Indicateur getFond(){
		return this.fond;
	}

	public double PrixVente(double commande){
		return commande*this.getPrix();
	}

	
	public void modiftreso(double commande){
		
		this.getFond().setValeur(this.getProd(), PrixVente(commande) + this.getFond().getValeur()- this.getCoutProd());
		
	}
		
	}
	
