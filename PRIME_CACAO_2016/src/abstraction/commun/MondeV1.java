package abstraction.commun;

import abstraction.equipe1.Producteur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Detaillant;
import abstraction.commun.Constantes;


import abstraction.equipe3.Leclerc;

import abstraction.equipe2.*;


import abstraction.equipe6.Carrefour;


public class MondeV1 extends Monde {
	
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs

		Leclerc Le = new Leclerc("Leclerc", this, 1673.08, 15.0);

		Carrefour Ca = new Carrefour("Carrefour", this, 15, 20, 50000);

		// Transformateurs
		Transformateur2 t1 = new Transformateur2(Constantes.NOM_TRANSFORMATEUR_1, this);
		
		this.ajouterActeur(t1);
		
		// Producteurs

		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		this.ajouterActeur(p1);
	}
}
