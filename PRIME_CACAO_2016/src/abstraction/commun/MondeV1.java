package abstraction.commun;

import abstraction.fourni.Monde;
import abstraction.fourni.v0.Detaillant;
import abstraction.commun.Constantes;
import abstraction.equipe3.Leclerc;

public class MondeV1 extends Monde {
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs
		Leclerc Le = new Leclerc("Leclerc", this, 1812.5, 15.0);
		// Transformateurs
		
		// Producteurs
		
	}
}
