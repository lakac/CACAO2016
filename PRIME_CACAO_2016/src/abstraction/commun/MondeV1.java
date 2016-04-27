package abstraction.commun;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.equipe2.*;

public class MondeV1 extends Monde {
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs
		
		// Transformateurs
		Transformateur2 t1 = new Transformateur2(Constantes.NOM_TRANSFORMATEUR_1, this);
		
		this.ajouterActeur(t1);
		
		// Producteurs
		
		

		
	}
}
