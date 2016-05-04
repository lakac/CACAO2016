package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	private Indicateur fond;
	// prix de vente
	private Acteur producteur;
	
	
	public Tresorerie(Acteur a) {
		this.fond = new Indicateur("Fond de" + a.getNom(),a,0.0);
		this.producteur= a;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}


	public void ModifTresorerie(double quantiteVendue){
		
		this.fond.setValeur(this.producteur, PrixVente(quantiteVendue) + this.fond.getValeur() - CoutProduction(QuantiteVendue));
	
	}
		
	}
	
