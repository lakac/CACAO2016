package abstraction.equipe2;

public class Commandes {
	
	private double[] Commandes;
	
	public double[] getCommandes() {
		return this.Commandes;
	}
	
	public double[] setCommandes(double qdd) {
		for (int i=1; i<4; i++) {
			Commandes[i-1]=Commandes[i];
		}
		Commandes[3] = qdd;
		return Commandes;
	}

	
	public Commandes() {
		Commandes = new double[4];
	}

	// Quantitée demandée au producteur 1 
	
	public double quantiteDemandeeP1 (double p){
		double qdp = this.getCommandes()[3]*0.6*p;
		return qdp;
	}
	
	// Quantitée demandée au producteur 2
	
	public double quantiteDemandeeP2 (double p){
		double qdp = this.getCommandes()[3]*0.6*p;
		return qdp;
	}
	
}
