package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;

public class Tresorerie {
	// argent totale dans la cagnote
	private Indicateur fond;
	// nous
	private Producteur prod;
	
	
	//constructeur
	public Tresorerie(Producteur p) {
		this.fond = new Indicateur("Fond de Asie Amerique",p,0.0);
		this.prod= p;
		Monde.LE_MONDE.ajouterIndicateur( this.fond );
	}
	
	//geter
	
	public Producteur getProd(){
		return this.prod;
	}


	public Indicateur getFond(){
		return this.fond;
	}	
		
}
	
