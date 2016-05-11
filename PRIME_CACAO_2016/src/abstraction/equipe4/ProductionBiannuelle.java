package abstraction.equipe4;

public class ProductionBiannuelle {
	private int capaciteMaximale; //Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private double perteProduction; //tableau retournant les pertes sur les six mois de la production
	private double productionFinale; //Renvoie la production finale (maximale soustraite des pertes)
	private Producteur prod;
	
	public ProductionBiannuelle(Producteur p,int capaciteMaximale) { //constructeur. Il sera appelé tous les six mois pour déterminer la production bi-annuelle.
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
	
	public void setPerteProduction() {
	this.perteProduction = this.getCapaciteMaximale()*this.perteAleatoire();
	}
	
	public void setProductionFinale() {
		this.productionFinale=this.getCapaciteMaximale()-this.getPerteProduction();
	}	
	
	public double perteAleatoire() { //méthode renvoyant une perte aléatoire comprise entre 0% et 10%
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}

	public void coutProd(){
		this.getProd().getTreso().getFond().setValeur(this.getProd(), this.getProd().getTreso().getFond().getValeur()- Couts.coutProd*this.getProductionFinale());
	}
	
	public void production(){
		this.setPerteProduction();
		this.setProductionFinale();
		this.coutProd();
		this.prod.getStock().augmentationStock(this.getProductionFinale());
	}


}
