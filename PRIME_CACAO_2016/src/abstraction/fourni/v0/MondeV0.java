package abstraction.fourni.v0;

import abstraction.fourni.Monde;

public class MondeV0 extends Monde {
	/**
	 * Methode appelee juste a la creation de l'unique 
	 * instance du Monde. C'est le bon endroit pour creer 
	 * vos instances d'acteurs et les ajouter au monde.
	 * 
	 * Vous aurez bien evidemment a modifier 
	 * le corps de cette methode
	 */
	public void peupler() {
		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, this);
		Producteur p2 = new Producteur(Constantes.NOM_PRODUCTEUR_2, this);
		
		this.ajouterActeur(p1);
		this.ajouterActeur(p2);
		
		Transformateur t1 = new Transformateur(Constantes.NOM_TRANSFORMATEUR_1, this);
		Transformateur t2 = new Transformateur(Constantes.NOM_TRANSFORMATEUR_2, this);

		this.ajouterActeur(t1);
		this.ajouterActeur(t2);
		
		Marche.LE_MARCHE = new Marche();
		Marche.LE_MARCHE.addProducteur(p1);
		Marche.LE_MARCHE.addProducteur(p2);
		Marche.LE_MARCHE.addTransformateur(t1);
		Marche.LE_MARCHE.addTransformateur(t2);

		this.ajouterActeur(Marche.LE_MARCHE);
		
		Detaillant d1 = new Detaillant(Constantes.NOM_DETAILLANT_1, this, 120.0, 6000.0);
		Detaillant d2 = new Detaillant(Constantes.NOM_DETAILLANT_2, this, 130.0, 5500.0);
		
		d1.ajouterVendeur(t1);
		d1.ajouterVendeur(t2);
		d2.ajouterVendeur(t1);
		// d2 n'achete pas a t2...
		
		this.ajouterActeur(d1);
		this.ajouterActeur(d2);
		
	}

}
