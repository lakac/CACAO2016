package abstraction.equipe4;
import abstraction.fourni.*;
import java.util.ArrayList;
import abstraction.commun.*;

public class Producteur implements Acteur,IProducteur {
	private String nom; 
	private Indicateur stock; 
	private Journal journal;
	private double prod; //production semi annuelle
	private Tresorerie treso;
	private Indicateur pertes; //perte dans la production totale
	private Indicateur prod_tot_utilisable; //production totale sans les pertes
	
	private ArrayList<ITransformateur> transformateurs;
	
	//Constructeur de l'acteur Producteur 2
	
    public Producteur(Monde monde) {
       this.nom = Constantes.NOM_PRODUCTEUR_2;
	   this.treso = new Tresorerie(this,3000.0,10.0);
	   this.stock = new Indicateur("Stock de "+ this.nom,this,0.0);
       this.prod_tot_utilisable = new Indicateur(Constantes.IND_PRODUCTION_P2, this, 0.0);
       this.journal = new Journal("Journal de "+this.nom);
       this.pertes = new Indicateur("Pertes de "+this.nom,this,0.0);
       this.prod=1200000;
       this.transformateurs= new ArrayList<ITransformateur>();
       for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof ITransformateur) {
				this.transformateurs.add((ITransformateur)(a));
			}
		}       Monde.LE_MONDE.ajouterJournal(this.journal);
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
	
	// le next du producteur 2
	
	public void next(){
		
		if (Monde.LE_MONDE.getStep()%12==1){ //production semi annuelle
			this.pertes.setValeur(this, Math.random()*200000);
			this.prod_tot_utilisable.setValeur(this,this.prod-this.pertes.getValeur());
			this.Augment_Stock(this.prod_tot_utilisable.getValeur());
			this.journal.ajouter("Production de semi annuelle de " + this.prod_tot_utilisable);
		}
		if(Monde.LE_MONDE.getStep()==1){ //premier step sans commande
			this.journal.ajouter("Debut des ventes et premiere production");
		}else{
			for (ITransformateur t : this.transformateurs){
				double commande = t.annonceQuantiteDemandee(this);
				t.notificationVente(this);
				this.notificationVente(commande);
			}
		}
		}

	// return un double valant la quantité disponible 
	//pour chaque transformateur a chaque step
	
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return (this.prod_tot_utilisable.getValeur()/24);
	}
	
	//return un double valant le prix a la tonne du cacao en vente
	
	public double annoncePrix() {
		return this.treso.getPrix();
	}
	
	//Modification du stock et de la tresorerie suite a une vente
	
	public void notificationVente(double quantite) {
		this.treso.modiftreso(quantite);
		this.Reduc_Stock(quantite);
		this.journal.ajouter("Vente de " + quantite );
	}

}
