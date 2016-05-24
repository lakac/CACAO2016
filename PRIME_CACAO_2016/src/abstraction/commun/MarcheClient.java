package abstraction.commun;

public class MarcheClient {
	
	private double demandeannuelle;
	private double demandeperstep;
	private ITransformateur transformateur;
	private Produit nomproduit;
	
	
	
	public MarcheClient(int demandeannuelle) {
      	this.demandeannuelle = demandeannuelle;
	}
	
	
	public double getDemandeAnnuel() {
		return this.demandeannuelle;
	}
	
	
	
	public void setdemandePerStep (int step ){
		if (step%26 == 6 ) {
			this.demandeperstep = (0.06*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		if (step%26 == 25) {
			this.demandeperstep = (0.12*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
		else {
			this.demandeperstep = (0.03416*(1+(Math.random()-0.5)*0.2))*this.getDemandeAnnuel();
		}
	}
	
}
