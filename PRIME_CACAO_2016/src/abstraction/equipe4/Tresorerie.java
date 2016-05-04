package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	// argent totale dans la cagnote
	private Indicateur fond;
	// nous
	private Acteur producteurEquipe4;
	
	
	public Tresorerie(Acteur a) {
		this.fond = new Indicateur("Fond de" + a.getNom(),a,0.0);
		this.producteurEquipe4= a;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}
	
	
	public Acteur getProd(){
		return this.producteurEquipe4;
	}


	public Indicateur getFond(){
		return this.fond;
	}

	// calcule du résultat de la vente
	public double resultatVente(double qtVendue){
		return qtVendue; //*this.getPrix();
	}
	
	
	public void setFond(double qtVendu){		
		this.getFond().setValeur(this.getProd(), this.resultatVente(qtVendu) + this.getFond().getValeur() ) ; //- this.getCoutProd());
		
	}
		
}
	
