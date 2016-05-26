package abstraction.equipe4;

public class ProductionBiannuelle {
	//CapacitÃ© maximale de production mensuelle, sans prendre en compte les alÃ©as
	private int capaciteMaximale; 
	//Quantité de cacao perdu avant récolte (due aux aléas)
	private double perteProduction; 
	//Renvoie la production finale (maximale soustraite des pertes)
	private double productionFinale; 
	private Producteur prod;
	
	//constructeur. Il sera appelÃ© tous les six mois pour dÃ©terminer la production bi-annuelle.
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

	// calcul la quantité totale de fève de cacao récolté à la fin des récoltes (pour un semestre).
	public void setProductionFinale() {
		this.productionFinale=this.getCapaciteMaximale()-this.getPerteProduction();
	}	
	
	// calcule un pourcentage de perte ici compris entre 0 et 10%,
	// ces pertes sont aléatoire et représente les effets des aléas méthéorologiques sur les récoltes.
	public double perteAleatoire() { 
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}

	// modification de la tréso à cause des couts de productions.
	public void coutProd(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()- Couts.COUTPROD*this.getProductionFinale());
	}
	
	// actualise toute les variables liées à la production uniquement:
	// l'augmentation de stock, baisse de la trésorerie (cout de prod)
	public void production(){
		this.setPerteProduction();
		this.setProductionFinale();
		this.coutProd();
		this.getProd().getStock().augmentationStock(this.getProductionFinale());
	}


}
