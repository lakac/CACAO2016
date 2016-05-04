package abstraction.equipe4;
import abstraction.fourni.*;

public class Tresorerie {
	// argent totale dans la cagnote
	private Indicateur fond;
	// nous
	private Producteur prod;
	
	
	public Tresorerie(Producteur p) {
		this.fond = new Indicateur("Fond de" + p.getNom(),p,0.0);
		this.prod= p;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}
	
	
	public Acteur getProd(){
		return this.prod;
	}


	public Indicateur getFond(){
		return this.fond;
	}


	
	
	public void setFond(double qtVendu){		
		
	}
		
}
	
