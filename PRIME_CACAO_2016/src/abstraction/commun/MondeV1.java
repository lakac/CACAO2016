package abstraction.commun;

import abstraction.equipe1.Producteur;
import abstraction.fourni.Monde;
import abstraction.commun.Constantes;

public class MondeV1 extends Monde {
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs
		
		// Transformateurs
		
		// Producteurs
		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		this.ajouterActeur(p1);
	}
}
