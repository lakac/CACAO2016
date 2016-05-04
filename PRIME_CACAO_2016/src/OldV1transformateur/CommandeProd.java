package OldV1transformateur;

public class CommandeProd {
	
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

	
	public CommandeProd() {
		Commandes = new double[4];
	}
	
	// Quantitée demandée au producteur 2
	
	public double quantiteDemandee (double p){
		double qdp = this.getCommandes()[3]*0.6*p;
		return qdp;
	}
	
	public double quantiteDemandeeMonde (double p){
		double qdp = this.getCommandes()[3]*0.6*p;
		return qdp;
	}
	

}
