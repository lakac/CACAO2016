
package abstraction.commun;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.equipe5.Lindt;
import abstraction.equipe1.Producteur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Detaillant;
import abstraction.commun.Constantes;


import abstraction.equipe3.Leclerc;
import abstraction.equipe2.*;
import abstraction.equipe6.Carrefour;

import abstraction.equipe4.*;
import abstraction.equipe6.Carrefour;


public class MondeV1 extends Monde {
	
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		// Distributeurs

		Leclerc Le = new Leclerc("Leclerc", this, 1673.08, 15.0);

		Carrefour Ca = new Carrefour("Carrefour", this, 15, 20, 50000);
		
		this.ajouterActeur(Le);


		this.ajouterActeur(Ca);
		// Transformateurs
		Transformateur2 t1 = new Transformateur2(this);
		this.ajouterActeur(t1);
		Lindt lindt = new Lindt();
		ajouterActeur(lindt);
		
		// Producteurs

		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		abstraction.equipe4.Producteur p2 = new abstraction.equipe4.Producteur(Monde.LE_MONDE);
		this.ajouterActeur(p1);
		this.ajouterActeur(p2);
		
		p1.ajouterTransformateur(t1);
		p1.ajouterTransformateur(lindt);
		
		//Ca.ajouterVendeur(lindt);
		Le.ajouterVendeur(t1);
		Le.ajouterVendeur(lindt);
		Ca.ajouterVendeur(t1);
		Ca.ajouterVendeur(lindt);
	}
}
