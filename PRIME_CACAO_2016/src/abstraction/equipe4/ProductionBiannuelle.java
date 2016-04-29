package abstraction.equipe4;

public class ProductionBiannuelle {
	private int capaciteMaximale; //Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private double[] pertesMensuelles;
	private double productionFinale;
	
	public ProductionBiannuelle(int capaciteMaximale) {
		this.capaciteMaximale = capaciteMaximale;
		this.pertesMensuelles = new double[6];
		(int i=0, i<6, i++) {
			
		}
	}
	
		
		
	public double PerteAleatoire() {
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;
		
	}
		
}
	


