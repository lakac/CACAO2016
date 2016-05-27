
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
		Carrefour Ca = new Carrefour();
		this.ajouterActeur(Le);
		this.ajouterActeur(Ca);
		
		// Transformateurs
		Nestle t1 = new Nestle(this);

		ResteDesTransformateursMondiaux t3 = new ResteDesTransformateursMondiaux();

		this.ajouterActeur(t1);
		Lindt lindt = new Lindt();
		ajouterActeur(lindt);
		lindt.ajouterDistributeur(Ca);
		lindt.ajouterDistributeur(Le);

		this.ajouterActeur(t3);

		
		
		// Marché Producteur
		MarcheProducteur marcheProducteur = new MarcheProducteur();
		MarcheProducteur.LE_MARCHE = marcheProducteur;
		this.ajouterActeur(marcheProducteur);
		
		// Producteurs
		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		abstraction.equipe4.Producteur p2 = new abstraction.equipe4.Producteur(Monde.LE_MONDE);
		this.ajouterActeur(p1);
		this.ajouterActeur(p2);
		
		// Ajout des acteurs dans les listes des acteurs

		lindt.ajouterProducteur(p1);
		lindt.ajouterProducteur(p2);

		t3.ajouterTransformateur(t1);
		t3.ajouterTransformateur(lindt);

		
		
		Le.ajouterVendeur(t1);
		Le.ajouterVendeur(lindt);
		Ca.ajouterVendeur(t1);
		Ca.ajouterVendeur(lindt);
		
		p1.ajouterTransformateur(t1);
		p1.ajouterTransformateur(lindt);

		p1.creerIntelligenceEconomique();
		
		marcheProducteur.ajouterProducteur(p1);
		marcheProducteur.ajouterProducteur(p2);
		marcheProducteur.ajouterTransformateur(t1);
		marcheProducteur.ajouterTransformateur(lindt);
		
		p2.ajoutClient(t1);
		p2.ajoutClient(lindt);

		p2.ajoutClient(t3);		
		p2.AjoutVariableVente();

	}
}
