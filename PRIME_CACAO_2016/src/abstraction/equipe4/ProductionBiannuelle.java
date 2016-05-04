package abstraction.equipe4;

public class ProductionBiannuelle {
	private int capaciteMaximale; //Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private double perteProduction; //tableau retournant les pertes sur les six mois de la production
	private double productionFinale; //Renvoie la production finale (maximale soustraite des pertes)
	
	public ProductionBiannuelle(int capaciteMaximale) { //constructeur. Il sera appelé tous les six mois pour déterminer la production bi-annuelle.
		this.capaciteMaximale = capaciteMaximale;
		this.perteProduction = 0.0;
		this.productionFinale = capaciteMaximale;
	}
	public int getCapaciteMaximale() {
		return this.capaciteMaximale;
	}
	
	public void setCapaciteMaximale(int capaciteMaximale) {
		this.capaciteMaximale = capaciteMaximale;
	}
	
	public double getPertesMensuelles() {
		return this.perteProduction;
	}

	
	public void setPerteProduction(double perteProduction) {
	this.perteProduction = perteProduction;
	}
	
	public double getProductionFinale() {
		return this.productionFinale;
	}
	public void setProductionFinale(double productionFinale) {
		this.productionFinale=productionFinale;
	}	
	
	public double perteAleatoire() { //méthode renvoyant une perte aléatoire comprise entre 0% et 10%
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}		
	
	

}
