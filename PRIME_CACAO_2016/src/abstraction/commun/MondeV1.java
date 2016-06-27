
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
		Nestle_new nestle = new Nestle_new();
		this.ajouterActeur(nestle);
		
		Lindt lindt = new Lindt();
		ajouterActeur(lindt);

		ResteDesTransformateursMondiaux t3 = new ResteDesTransformateursMondiaux();
		this.ajouterActeur(t3);
		
		// Marché Consommateur
		MarcheCons marcheConsommateurs = new MarcheCons("MarcheCons", this.produits);
		MarcheCons.LE_MARCHE_CONS = marcheConsommateurs;

		this.ajouterActeur(marcheConsommateurs);
		
		// Producteurs
		Producteur p1 = new Producteur(1000.0, 0.0, Monde.LE_MONDE);
		System.out.println(p1.getNom());
		this.ajouterActeur(p1);
		abstraction.equipe4.Producteur p2 = new abstraction.equipe4.Producteur(Monde.LE_MONDE);
		this.ajouterActeur(p2);
		
		// Marché Producteur
				MarcheProd marcheProducteur = new MarcheProd();
				MarcheProd.LE_MARCHE=marcheProducteur;
				this.ajouterActeur(marcheProducteur);
		
		// Ajout des acteurs dans les listes des acteurs

		Le.ajouterVendeur(nestle);
		Le.ajouterVendeur(lindt);
		
		Ca.ajouterVendeur(nestle);
		Ca.ajouterVendeur(lindt);
		Ca.setMaDi(MaDi);


		//nestle.ajouterClient(Le);
		nestle.ajouterClient(Ca);
		nestle.ajouterFournisseurs(p1);
		nestle.ajouterFournisseurs(p2);
		nestle.creer(Monde.LE_MONDE);

		lindt.ajouterDistributeur(Ca);
		lindt.ajouterDistributeur(Le);
		//lindt.ajouterProducteur(p1);
		lindt.ajouterProducteur(p2);
		lindt.creer();


		
		t3.ajouterTransformateur(nestle);
		t3.ajouterTransformateur(lindt);
		
		/*p1.ajouterTransformateur(nestle);
		p1.ajouterTransformateur(lindt);
		p1.ajouterTransformateur(t3);*/

		

		//marcheProducteur.AjoutProducteur(p1);;
		marcheProducteur.AjoutProducteur(p2);
		marcheProducteur.AjoutTransformateur(nestle);
		marcheProducteur.AjoutTransformateur(lindt);
		marcheProducteur.AjoutTransformateur(t3);
		//penser a ajouter la cote d'ivoire

		


		//marcheProducteur.ajouterProducteur(p1);
		//marcheProducteur.ajouterProducteur(p2);
		//marcheProducteur.ajouterTransformateur(lindt);

		

		//maj 31/05 Leclerc
		Le.getStock().initialiseStock(Le);	
		Le.initialiseRatio();
		Le.getPrixDeVente().initialisePrixDeVente(Le, produits);
		Le.getVentes().initialiseVentes();
		/*
		MarcheCons.LE_MARCHE_CONS.ajouterDistributeur(Le);
		MarcheCons.LE_MARCHE_CONS.ajouterDistributeur(Ca);
		MarcheCons.LE_MARCHE_CONS.ajouterTransformateur(nestle);
		MarcheCons.LE_MARCHE_CONS.ajouterTransformateur(lindt);
		*/
		MarcheCons.LE_MARCHE_CONS.initialiserDemandeAnnuelle();
		MarcheCons.LE_MARCHE_CONS.initialiserCalendrierDemande();
		MarcheCons.LE_MARCHE_CONS.initialiserFidelite();
		MarcheCons.LE_MARCHE_CONS.initialiserPourcentageIncertitudeVentes();
		MarcheCons.LE_MARCHE_CONS.initialiserRatio();
		
		
		MaDi.addDistributeur(Ca);
		//MaDi.addDistributeur(Le);
		MaDi.addTransformateur(lindt);
		MaDi.addTransformateur(nestle);
		for (Produit p : produits) {
			MaDi.addProduit(p);
		}
		MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR = MaDi;
		Ca.creer();

	}
}