package abstraction.equipe4;


import abstraction.fourni.*;


import java.util.ArrayList;

import abstraction.commun.*;

public class Producteur implements Acteur,IProducteur{

	
	private String nom; 
	private Indicateur stock; 
	private Journal journal;
	private double prod; //production semi annuelle
	private Tresorerie treso;
	private Indicateur pertes; //perte dans la production totale
	private Indicateur prod_tot_utilisable;
	
	private ArrayList<ITransformateur> transformateurs;
	
	//Constructeur de l'acteur Producteur 2
	
    public Producteur(Monde monde) {
       this.nom = Constantes.NOM_PRODUCTEUR_2;
	   this.treso = new Tresorerie(this,2000.0,3000.0);
	   this.stock = new Indicateur("Stock de "+ this.nom,this,0.0);
       this.prod_tot_utilisable = new Indicateur(Constantes.IND_PRODUCTION_P2, this, 0.0);
       this.journal = new Journal("Journal de "+this.nom);
       this.pertes = new Indicateur("Pertes de "+this.nom,this,0.0);
       this.prod=0.0;
       this.transformateurs= new ArrayList<ITransformateur>();
    	Monde.LE_MONDE.ajouterJournal(this.journal);
    	Monde.LE_MONDE.ajouterIndicateur( this.prod_tot_utilisable );
    	Monde.LE_MONDE.ajouterIndicateur(this.pertes);
    	Monde.LE_MONDE.ajouterIndicateur(this.stock);
    }


    //return un String : le nom du producteur 2
    
	public String getNom() {
		return this.nom;
	}

	//Réduction du stock d'une valeur de value
	
	public void Reduc_Stock(double value){
		stock.setValeur(this, this.stock.getValeur()- value);
	}
	
	//Augmentation du stock d'une valeur de value
	
	public void Augment_Stock(double value){
		stock.setValeur(this, this.stock.getValeur()+value);
	}
	
	// le next de producteur 2
	
	public void next(){
		
		
		if (Monde.LE_MONDE.getStep()%12==1){
			this.pertes.setValeur(this, Math.random()*200000);
			this.prod_tot_utilisable.setValeur(this,this.prod-this.pertes.getValeur());
			this.Augment_Stock(this.prod_tot_utilisable.getValeur());
			this.journal.ajouter("Production de semi annuelle de " + this.prod_tot_utilisable);
		}
		if ((Monde.LE_MONDE.getStep()%3==0)&&(this.journal!=null)){
			this.journal.ajouter("Stock restant de " + this.stock.getValeur());
		}
		if (Monde.LE_MONDE.getStep()%3==2){
			for (ITransformateur t : this.transformateurs){
				double commande = t.annonceQuantiteDemandee(this);
				t.notificationVente(this);
				this.notificationVente(commande);
			}
		}
		}

	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return (this.prod_tot_utilisable.getValeur()/8);
	}
	
	public double annoncePrix() {
		return this.treso.getPrix();
	}
	
	public void notificationVente(double quantite) {
		this.treso.modiftreso(quantite);
		this.Reduc_Stock(quantite);
		this.journal.ajouter("Vente de " + quantite );
	}

}
