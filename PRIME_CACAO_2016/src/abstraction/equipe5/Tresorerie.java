package abstraction.equipe5;

public class Tresorerie {
	private Historique_Commande_Dist hist;
	private double treso;
	
	public Tresorerie(Historique_Commande_Dist hist){
		this.hist= hist;
		this.treso = 0;
	}

	public double coutRevient() {
		int chargesFixes = 900980; // salaires+impots
		double quantiteCacaoAchetee = Lindt.RATIO_CACAO_CHOCOLAT*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT);
		return chargesFixes + quantiteCacaoAchetee * (5000 /*+ ((p1.annoncePrix() + p2.annoncePrix())/2)*/); 	
	}
	
	public double marge(){
		return (15000*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)-coutRevient());
	}
	
	
}
