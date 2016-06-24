
package abstraction.commun;

import abstraction.fourni.Monde;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Constantes;
import abstraction.equipe5.Lindt;
import abstraction.equipe1.Producteur;

import abstraction.equipe3.Leclercv2;
import abstraction.equipe2.*;
import abstraction.equipe6.Carrefour;

public class MondeV1 extends Monde {
	
	private static ArrayList<Produit> produits = new ArrayList<Produit>();
	
	
	public void peupler() {
		// Il faut créer les acteurs et les ajouter au monde ici.
		
		//Initialisation de la liste produits
		produits.add(new Produit("50%",50));
		produits.add(new Produit("60%",60));
		produits.add(new Produit("70%",70));
		
		// Marche distributeur
		
		MarcheDistributeur MaDi = new MarcheDistributeur();
		this.ajouterActeur(MaDi);

		// Distributeurs



		Leclercv2 Le = new Leclercv2("Leclerc", this,produits);
		Carrefour Ca = new Carrefour("Carrefour", MondeV1.produits);

		this.ajouterActeur(Le);

		this.ajouterActeur(Ca);
		
		
		
		// Transformateurs
		Nestle nestle = new Nestle();
		this.ajouterActeur(nestle);
		
		Lindt lindt = new Lindt();
		ajouterActeur(lindt);

		ResteDesTransformateursMondiaux t3 = new ResteDesTransformateursMondiaux();
		this.ajouterActeur(t3);
		
		
		
		// Marché Producteur
		MarcheProd marcheProducteur = new MarcheProd();
		MarcheProd.LE_MARCHE=marcheProducteur;
		this.ajouterActeur(marcheProducteur);
		
		
		// Marché Consommateur
		//MarcheConsommateurs marcheConsommateurs = new MarcheConsommateurs();
		//MarcheConsommateurs.LE_MARCHE_CONSOMMATEURS = marcheConsommateurs;
		//this.ajouterActeur(marcheConsommateurs);
		
		// Producteurs
		Producteur p1 = new Producteur(Constantes.NOM_PRODUCTEUR_1, 1000.0, 0.0, Monde.LE_MONDE);
		this.ajouterActeur(p1);
		abstraction.equipe4.Producteur p2 = new abstraction.equipe4.Producteur(Monde.LE_MONDE);
		this.ajouterActeur(p2);
		

		
		// Ajout des liens necessaires entre les acteurs
		Le.ajouterVendeur(nestle);
		Le.ajouterVendeur(lindt);
		
		Ca.ajouterVendeur(nestle);
		Ca.ajouterVendeur(lindt);
		Ca.setMaDi(MaDi);

		nestle.AjouterClient(Le);
		nestle.AjouterClient(Ca);
		nestle.AjouterFournisseur(p1);
		nestle.AjouterFournisseur(p2);

		nestle.creer();
		
		lindt.ajouterDistributeur(Ca);
		lindt.ajouterDistributeur(Le);
		lindt.ajouterProducteur(p1);
		lindt.ajouterProducteur(p2);
		lindt.creer();


		
		t3.ajouterTransformateur(nestle);
		t3.ajouterTransformateur(lindt);
		t3.ajouterTransformateur(nestle);
		
		p1.ajouterTransformateur(nestle);
		p1.ajouterTransformateur(lindt);
		p1.ajouterTransformateur(t3);
		

		marcheProducteur.AjoutProducteur(p1);;
		marcheProducteur.AjoutProducteur(p2);
		marcheProducteur.AjoutTransformateur(nestle);
		marcheProducteur.AjoutTransformateur(lindt);
		marcheProducteur.AjoutTransformateur(t3);
		//penser a ajouter la cote d'ivoire

		


		marcheProducteur.ajouterProducteur(p1);
		marcheProducteur.ajouterProducteur(p2);
		marcheProducteur.ajouterTransformateur(lindt);

		

		//maj 31/05 Leclerc
		Le.getStock().initialiseStock(Le);	

		Le.getPrixDeVente().initialisePrixDeVente(Le, produits);
		Le.getVentes().initialiseVentes();
		
		Ca.creer();

	}
}