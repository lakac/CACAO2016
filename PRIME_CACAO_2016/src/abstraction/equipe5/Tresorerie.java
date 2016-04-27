package abstraction.equipe5;
import abstraction.equipe5.Lindt;

public class Tresorerie {
	private Historique_Commande_Dist hist;
	
	public Tresorerie(Historique_Commande_Dist hist){
		this.hist= hist;
	}

	public double coutRevient() {
		int charges_fixes = 900980; // salaires+impots
		return charges_fixes + 0.6*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT) * (5000 /*+ ((p1.annoncePrix() + p2.annoncePrix())/2)*/); 	
	}
	

	public double marge(){
		return (15000*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)-coutRevient());
	}
}
