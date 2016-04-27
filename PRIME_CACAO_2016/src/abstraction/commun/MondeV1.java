package abstraction.commun;

import abstraction.equipe1.Producteur;
import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.equipe2.*;

import abstraction.equipe6.Carrefour;
import abstraction.equipe4.*;

public class MondeV1 extends Monde {
	
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs
		Carrefour Ca = new Carrefour("Carrefour", this, 15, 20, 50000);
		this.ajouterActeur(Ca);
		// Transformateurs
		Transformateur2 t1 = new Transformateur2(Constantes.NOM_TRANSFORMATEUR_1, this);
		
		this.ajouterActeur(t1);
		// Producteurs

		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		abstraction.equipe4.Producteur p2 = new abstraction.equipe4.Producteur(Monde.LE_MONDE);
		this.ajouterActeur(p1);
		this.ajouterActeur(p2);
	}
}
