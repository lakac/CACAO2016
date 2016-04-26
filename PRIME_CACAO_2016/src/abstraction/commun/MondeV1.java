package abstraction.commun;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.equipe5.Lindt;

public class MondeV1 extends Monde {
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs
		
		// Transformateurs
		Lindt lindt = new Lindt();
		ajouterActeur(lindt);
		// Producteurs
		
	}
}
