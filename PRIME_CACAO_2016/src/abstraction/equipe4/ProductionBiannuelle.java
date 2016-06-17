package abstraction.equipe4;

public class ProductionBiannuelle {
	//Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private int capaciteMaximale; 
	//Quantit� de cacao perdu avant r�colte (due aux al�as)
	private double perteProduction; 
	//Renvoie la production finale (maximale soustraite des pertes)
	private double productionFinale; 
	private Producteur prod;
	
	//constructeur. Il sera appelé tous les six mois pour déterminer la production bi-annuelle.
	public ProductionBiannuelle(Producteur p,int capaciteMaximale) { 
		this.capaciteMaximale = capaciteMaximale;
		this.perteProduction = 0.0;
		this.productionFinale = capaciteMaximale;
		this.prod=p;
	}
	
	public int getCapaciteMaximale() {
		
		
		return this.capaciteMaximale;
	}
	
	public double getPerteProduction() {
		return this.perteProduction;
	}
	
	public double getProductionFinale() {
		return this.productionFinale;
	}
	
	public Producteur getProd() {
		return this.prod;
	}
	
	public void setCapaciteMaximale(int capaciteMaximale) {
		this.capaciteMaximale = capaciteMaximale;
	}
	
	// calcul le totale de perte de production sur le semestre.
	public void setPerteProduction() {
	this.perteProduction = this.getCapaciteMaximale()*this.perteAleatoire();
	}

	// calcul la quantit� totale de f�ve de cacao r�colt� � la fin des r�coltes (pour un semestre).
	public void setProductionFinale() {
		this.productionFinale=this.getCapaciteMaximale()-this.getPerteProduction();
	}	
	
	// calcule un pourcentage de perte ici compris entre 0 et 10%,
	// ces pertes sont al�atoire et repr�sente les effets des al�as m�th�orologiques sur les r�coltes.
	public double perteAleatoire() { 
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}

	// modification de la tr�so � cause des couts de productions.
	public void coutProd(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()- Couts.COUTPROD*this.getProductionFinale());
	}
	
	// actualise toute les variables li�es � la production uniquement:
	// l'augmentation de stock, baisse de la tr�sorerie (cout de prod)
	public void production(){
		this.setPerteProduction();
		this.setProductionFinale();
		this.coutProd();
		this.getProd().getStock().augmentationStock(this.getProductionFinale());
	}


}
