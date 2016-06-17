package abstraction.equipe4;

public class ProductionBiannuelle {
	//Capacite maximale de production mensuelle, sans prendre en compte les aleas
	private int capaciteMaximale; 
	//Quantite de cacao perdu avant recolte (due aux aleas)
	private double perteProduction; 
	//Renvoie la production finale (maximale soustraite des pertes)
	private double productionFinale; 
	private Producteur prod;
	
	//constructeur. Il sera appele tous les six mois pour determiner la production bi-annuelle.
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

	// calcul la quantite totale de feve de cacao recolte a la fin des recoltes (pour un semestre).
	public void setProductionFinale() {
		this.productionFinale=this.getCapaciteMaximale()-this.getPerteProduction();
	}	
	
	// calcule un pourcentage de perte ici compris entre 0 et 10%,
	// ces pertes sont aleatoire et represente les effets des aleas metheorologiques sur les recoltes.
	public double perteAleatoire() { 
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}

	// modification de la treso a cause des couts de productions.
	public void coutProd(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()- Couts.COUTPROD*this.getProductionFinale());
	}
	
	// actualise toute les variables liees a la production uniquement:
	// l'augmentation de stock, baisse de la tresorerie (cout de prod)
	public void production(){
		this.setPerteProduction();
		this.setProductionFinale();
		this.coutProd();
		this.getProd().getStock().augmentationStock(this.getProductionFinale());
	}


}
