package abstraction.equipe4;

public class ProductionBiannuelle {
	private int capaciteMaximale; //Capacité maximale de production mensuelle, sans prendre en compte les aléas
	private double[] pertesMensuelles; //tableau retournant les pertes sur les six mois de la production
	private double productionFinale; //Renvoie la production finale (maximale soustraite des pertes)
	
	public ProductionBiannuelle(int capaciteMaximale) { //constructeur. Il sera appelé tous les six mois pour déterminer la production bi-annuelle.
		this.capaciteMaximale = capaciteMaximale;
		this.pertesMensuelles = new double[6]; //tableau vide à six éléments
		for (int i=0; i<6; i++) {
			pertesMensuelles[i] = this.PerteAleatoire(); //on remplit les pertes mensuelles de façon aléatoire (voir méthode plus bas)
		}
		this.productionFinale = capaciteMaximale;
		for (int i=0; i<6; i++) {
			productionFinale = productionFinale - pertesMensuelles[i]*capaciteMaximale; //on calcule de façon récursive la production finale, en prenant en compte les pertes mensuelles
		}	
	}
	
	public double PerteAleatoire() { //méthode renvoyant une perte aléatoire comprise entre 0% et 10%
		double PerteAleatoire = Math.random()*0.1;
		return PerteAleatoire;	
	}		

}