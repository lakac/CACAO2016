package abstraction.equipe4;

public class ProductionBiannuelle {
	private int capaciteMaximale; //Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private double[] pertesMensuelles; //tableau retournant les pertes sur les six mois de la production
	private double productionFinale; //Renvoie la production finale (maximale soustraite des pertes)
	
	public ProductionBiannuelle(int capaciteMaximale) { //constructeur. Il sera appelé tous les six mois pour déterminer la production bi-annuelle.
		this.capaciteMaximale = capaciteMaximale;
		this.pertesMensuelles = setPertesMensuelles();
		this.productionFinale = setProductionFinale();
	}
	public int getCapaciteMaximale() {
		return capaciteMaximale;
	}
	public void setCapaciteMaximale(int capaciteMaximale) {
		this.capaciteMaximale = capaciteMaximale;
	}
	public double[] getPertesMensuelles() {
		return pertesMensuelles;
	}
	public double[] setPertesMensuelles() {
		this.pertesMensuelles = new double[6]; //tableau vide à six éléments
		for (int i=0; i<6; i++) {
			this.pertesMensuelles[i] = this.perteAleatoire(); //on remplit les pertes mensuelles de façon aléatoire (voir méthode plus bas)
		}
		return this.pertesMensuelles;
	}
	public double getProductionFinale() {
		return productionFinale;
	}
	public double setProductionFinale() {
		for (int i=0; i<6; i++) {
			this.productionFinale = this.productionFinale - this.pertesMensuelles[i]*this.capaciteMaximale; //on calcule de façon récursive la production finale, en prenant en compte les pertes mensuelles
		}
		return this.productionFinale;
	}	
	public double perteAleatoire() { //méthode renvoyant une perte aléatoire comprise entre 0% et 10%
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}		
	
	
	public String toString(double[] pertesMensuelles) {
		return "mois 1 : "+this.getPertesMensuelles()[0]+" ; mois 2 : "+this.getPertesMensuelles()[1]+" ; mois 3 : "+this.getPertesMensuelles()[2]+" mois 4 : "+this.getPertesMensuelles()[3]+" ; mois 5 : "+this.getPertesMensuelles()[4]+" ; mois 6 : "+this.getPertesMensuelles()[5];
	}
	
	public static void main(String[] args) {
		ProductionBiannuelle P1 = new ProductionBiannuelle(1200000);
		ProductionBiannuelle P2 = new ProductionBiannuelle(1200000);
		System.out.println(P1.getProductionFinale()+" avec les pertes mensuelles suivantes : "+P1.toString(P1.pertesMensuelles));
		System.out.println(P2.getProductionFinale()+" avec les pertes mensuelles suivantes : "+P2.toString(P2.pertesMensuelles));
	}

}
