package abstraction.equipe4;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Constantes;

public class Producteur implements Acteur{
	
	private String nom;
	private Indicateur stock; 
	private Journal journal;
	private Indicateur prod;
	private Indicateur pertes;
	private Indicateur solde;
	
    public Producteur(String nom, Monde monde) {
       this.nom = nom;
 	   this.pertes = new Indicateur("Pertes de "+this.nom, this, 0.0);
	   this.solde = new Indicateur("Solde de "+this.nom, this, 0.0);
       this.prod = new Indicateur(Constantes.IND_PRODUCTION_P1, this, 100.0);
       this.journal = new Journal("Journal de "+this.nom);
    	Monde.LE_MONDE.ajouterJournal(this.journal);
    	Monde.LE_MONDE.ajouterIndicateur( this.prod );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	Monde.LE_MONDE.ajouterIndicateur( this.pertes );
    }
	
}
