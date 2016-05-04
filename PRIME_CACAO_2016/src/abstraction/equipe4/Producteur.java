package abstraction.equipe4;
import abstraction.fourni.*;
import java.util.ArrayList;
import abstraction.commun.*;
public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Indicateur stock; 
	private Journal journal;
	//production semi annuelle
	private double prod; 
	private Tresorerie treso;
	 //perte dans la production totale
	private Indicateur pertes;
	//production totale sans les pertes
	private Indicateur prodTotaleUtilisable; 
	
	private ArrayList<ITransformateur> transformateurs;
	
	//Constructeur de l'acteur Producteur 2
	
    public Producteur(Monde monde) {
       this.nom = Constantes.NOM_PRODUCTEUR_2;
	   this.treso = new Tresorerie(this);
	   this.stock = new Indicateur("Stock de "+ this.nom,this,0.0);
       this.prodTotaleUtilisable = new Indicateur(Constantes.IND_PRODUCTION_P2, this, 0.0);
       this.journal = new Journal("Journal de "+this.nom);
       this.pertes = new Indicateur("Pertes de "+this.nom,this,0.0);
       this.prod=1200000;
       this.transformateurs= new ArrayList<ITransformateur>();
       for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof ITransformateur) {
				this.transformateurs.add((ITransformateur)(a));
			}
		}       Monde.LE_MONDE.ajouterJournal(this.journal);
    	Monde.LE_MONDE.ajouterIndicateur( this.prodTotaleUtilisable );
    	Monde.LE_MONDE.ajouterIndicateur(this.pertes);
    	Monde.LE_MONDE.ajouterIndicateur(this.stock);
    }


    // getter
    
	public String getNom() {
		return this.nom;
	}
	
	public Indicateur getPertes(){
		return this.pertes;
	}
	
	public Indicateur getProdTotaleUtilisable(){
		return this.prodTotaleUtilisable;
	}
	
	public Tresorerie getTreso(){
		return this.treso;
	}
	
	
	public Indicateur getStock(){
		return this.stock;
	}
	
	//Réduction du stock d'une valeur de value
	public void reductionStock(double value){
		if (value>0){
		this.getStock().setValeur(this, this.getStock().getValeur()- value);	
		}
	}
	
	
	//Augmentation du stock d'une valeur de value
	public void augmentationStock(double value){
		if (value>0){
			this.getStock().setValeur(this, this.getStock().getValeur()+value);
		}
	}
	

	// le next du producteur 2	
	public void next(){
		
		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){ 
			this.getPertes().setValeur(this, Math.random()*200000);
			this.getProdTotaleUtilisable().setValeur(this,this.prod-this.pertes.getValeur());
			this.augmentationStock(this.getProdTotaleUtilisable().getValeur());
			this.journal.ajouter("Production de semi annuelle de " + this.getProdTotaleUtilisable());
		}
		//premier step sans commande
		if(Monde.LE_MONDE.getStep()==1){ 
			this.journal.ajouter("Debut des ventes et premiere production");
		}else{
			for (ITransformateur t : this.transformateurs){
				double qtVendu = t.annonceQuantiteDemandee(this);
				t.notificationVente(this);
				this.notificationVente(qtVendu);
			}
		}
	}

	// return un double valant la quantité disponible 
	//pour chaque transformateur a chaque step
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return (this.getProdTotaleUtilisable().getValeur()/24);
	}
	

	//Modification du stock et de la tresorerie suite a une vente
	public void notificationVente(double qtVendue) {
		this.getTreso().setFond(qtVendue);
		this.reductionStock(qtVendue);
		this.journal.ajouter("Vente de " + qtVendue );
	}


}
