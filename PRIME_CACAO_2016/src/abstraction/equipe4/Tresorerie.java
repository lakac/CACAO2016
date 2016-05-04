package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	// argent totale dans la cagnote
	private Indicateur fond;
	// prix de vente
	private double prix; 
	// cout de prod
	private double coutProd; 
	// nous
	private Acteur producteurEquipe4;
	
	
	public Tresorerie(Acteur a, double prix, double cout) {
		this.fond = new Indicateur("Fond de" + a.getNom(),a,0.0);
		this.prix = prix;
		this.coutProd = cout;
		this.producteurEquipe4= a;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}
	
	
	public Acteur getProd(){
		return this.producteurEquipe4;
	}

	public double getCoutProd() {
		return this.coutProd;
	}
	
	public double getPrix() {
		return this.prix;
	}

	public Indicateur getFond(){
		return this.fond;
	}

<<<<<<< HEAD
	// calcule du resultat de la vente
	public double PrixVente(double commande){
		return commande*this.getPrix();
=======
	// calcule du résultat de la vente
	public double resultatVente(double qtVendu){
		return qtVendu*this.getPrix();
>>>>>>> branch 'master' of https://github.com/SofianeSalort/CACAO2016.git
	}
	
	
	public void setFond(double qtVendu){		
		this.getFond().setValeur(this.getProd(), this.resultatVente(qtVendu) + this.getFond().getValeur()- this.getCoutProd());
		
	}
		
}
	
