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
		return chargesFixes + quantiteCacaoAchetee * 5000 /*+ (p1.annoncePrix()*0.3 + p2.annoncePrix()*0.3 + 3000*0.4)*/ ;	
		//cout de revient d'une tonne= charges fixes+ ratioCacao*quantité demandée par les distributeurs* cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+quantité de cacao demandée à chaque producteur multiplié par leur prix
	}
	
	public double marge(){
		double coutAchatCacao= hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)*0.6*(0.3*P1.annoncePrix()+0.3*P2.annoncePrix()+0.4*3000); //coût d'achat du cacao aux 3 producteurs 
				return (15000*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)-coutRevient()-coutAchatCacao);
	}
	
	
}
