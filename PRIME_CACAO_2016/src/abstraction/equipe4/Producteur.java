package abstraction.equipe4;

import abstraction.fourni.*;;

public class Producteur implements Acteur{
	
	private String nom;
	private Indicateur stock; 
	private Journal journal;
	private Indicateur prod;
	private Indicateur pertes;
	private Tresorerie treso;
	
    public Producteur(String nom, Monde monde) {
       this.nom = nom;
 	   this.pertes = new Indicateur("Pertes de "+this.nom, this, 0.0);
	   this.treso = new Tresorerie(this,2000,3000);
       this.prod = new Indicateur("Production de "+this.nom, this, 0);
       this.journal = new Journal("Journal de "+this.nom);
    	Monde.LE_MONDE.ajouterJournal(this.journal);
    	Monde.LE_MONDE.ajouterIndicateur( this.prod );
    	Monde.LE_MONDE.ajouterIndicateur( this.solde );
    	Monde.LE_MONDE.ajouterIndicateur( this.pertes );
    }
	
}
