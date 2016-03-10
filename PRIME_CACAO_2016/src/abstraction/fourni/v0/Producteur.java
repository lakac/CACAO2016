package abstraction.fourni.v0;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;


/**
 * Classe fournie a titre d'exemple modelisant un Producteur.
 *
 * Dans cet exemple la valeur de l'indicateur cree par l'instance
 * evolue de facon aleatoire. La façon dont evoluera vos indicateurs
 * pourra etre fort different et notamment s'appuyer sur la valeur
 * d'autres indicateur et de variables internes a l'acteur.
 * 
 * @author Romuald Debruyne
 */
public class Producteur implements Acteur, IProducteur {

	private String nom;
	private Indicateur prod;
	private Indicateur pertes;
	private Indicateur solde;
	private Journal journal;
    
	
	
    public Producteur(String nom, Monde monde) {
    	this.nom = nom;
 	   this.pertes = new Indicateur("Pertes de "+this.nom, this, 0.0);
	   this.solde = new Indicateur("Solde de "+this.nom, this, 0.0);
    	if (this.nom.equals(Constantes.NOM_PRODUCTEUR_1)) {
    	   this.prod = new Indicateur(Constantes.IND_PRODUCTION_P1, this, 100.0);
    	   this.journal = new Journal("Journal de "+this.nom);
    	   Monde.LE_MONDE.ajouterJournal(this.journal);
    	}
    	else {
     	   this.prod = new Indicateur(Constantes.IND_PRODUCTION_P2, this, 100.0);
     	   this.journal = null; 
    	}  	
    	Monde.LE_MONDE.ajouterIndicateur( this.prod );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	Monde.LE_MONDE.ajouterIndicateur( this.pertes );
    }

	public String getNom() {
		return "Producteur "+this.nom;
	}
	

	public void next() {
	  int production = Constantes.PROD_MIN+(int)((Math.random()*(Constantes.PROD_MAX-Constantes.PROD_MIN)));
	  prod.setValeur( this, production);
	  if (Monde.LE_MONDE.getStep()%3==0 && this.journal!=null) {
		  this.journal.ajouter(" valeur de ???  =  <font color=\"maroon\"> ???</font> au <b>step</b> "+Monde.LE_MONDE.getStep());
	  }
	}

	public double quantiteMiseEnVente() {
		return this.prod.getValeur();
	}

	public void notificationVente(double quantite) {
		this.pertes.setValeur(this, this.prod.getValeur()-quantite);
		this.solde.setValeur(this ,this.solde.getValeur()+quantite*Marche.LE_MARCHE.getCours());
	}

}
