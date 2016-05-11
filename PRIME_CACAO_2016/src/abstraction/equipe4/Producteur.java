package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;
import java.util.ArrayList;

public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Stock stock; 
	private Journal journal;
	private Tresorerie treso;
	private ProductionBiannuelle prodBiannu;
	private MarcheProducteur marche;
	
	private ArrayList<ITransformateur> transformateurs;
	
	//Constructeur de l'acteur Producteur 2
    public Producteur(Monde monde) {
       this.nom = Constantes.NOM_PRODUCTEUR_2;
	   this.treso = new Tresorerie(this);
	   this.stock = new Stock(this);
       this.journal = new Journal("Journal de "+this.nom);
       this.transformateurs= new ArrayList<ITransformateur>();
       for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof ITransformateur) {
				this.transformateurs.add((ITransformateur)(a));
			}
		}
       this.marche=new MarcheProducteur();
       Monde.LE_MONDE.ajouterJournal(this.journal);
    }


    // getter
    
    
    public Journal getJournal() {
		return this.journal;
	}
    
    public String getNom() {
		return this.nom;
	}
    

    public ProductionBiannuelle getProdBiannu() {
		return this.prodBiannu;
	}
    
    public Stock getStock() {
		return this.stock;
	}
    
    public ArrayList<ITransformateur> getTransformateurs() {
		return this.transformateurs;
	}
    
    public Tresorerie getTreso() {
		return this.treso;
	}

    public MarcheProducteur getMarche() {
		return this.marche;
	}
    
	// le next du producteur 2	
	public void next(){
		
		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){ 
			this.getProdBiannu().production();
			this.journal.ajouter("Production de semi annuelle de " + this.getProdBiannu().getProductionFinale() + " en comptant les pertes de "+ this.getProdBiannu().getPerteProduction());
		}
		//Commandes			
		this.getStock().setPerteStock();
		this.getStock().perteDeStock();
		this.stock
		for (ITransformateur t : this.transformateurs){
			double qtVendu = t.annonceQuantiteDemandee(this);
			t.notificationVente(this);
			this.venteRealisee(qtVendu, (Acteur)t);
		}
	}


	// return un double valant la quantité disponible 
	//pour chaque transformateur a chaque step
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return (this.stock.getStockCacao().getValeur()/(13-Monde.LE_MONDE.getStep()%12));
	}
	

	//Modification du stock et de la tresorerie suite a une vente
	public void venteRealisee(double qtVendue,Acteur a) {
		this.getTreso().setFond(qtVendue);
		this.stock.reductionStock(qtVendue);
		this.journal.ajouter("Vente de " + qtVendue+" auprès de " + a.getNom() + " au step numéro "+ Monde.LE_MONDE.getStep());
	}

	public void vente(double qtVendue){		
		this.getTreso().getFond().setValeur(this, this.getTreso().getFond().getValeur()+ qtVendue*this.getMarche().getCours());
	}

	
	
	//Ventes réalisées auprès du transformateur "Le reste du Monde"
	
	public void venteResteMonde(){
		double alea = Math.random()*(0.9-0.87)+0.87;
	}
		
}
	
