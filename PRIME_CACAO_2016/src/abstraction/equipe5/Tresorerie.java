package abstraction.equipe5;
import abstraction.equipe5.Lindt;
import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;

public class Tresorerie {
	private Historique_Commande_Dist hist;
	private double ratioCacao;
	
	

	public Tresorerie(Historique_Commande_Dist hist){
		this.hist= hist;
		this.ratioCacao=0.6;
	}

	public double coutRevient() {
		int charges_fixes = 900980; // salaires+impots
		return charges_fixes + ratioCacao*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT) * 5000* (P1.annoncePrix()*0.3 + P2.annoncePrix()*0.3 + 3000*0.4) ;	
		//cout de revient d'une tonne= charges fixes+ ratioCacao*quantité demandée par les distributeurs* cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+quantité de cacao demandée à chaque producteur multiplié par leur prix
		
	}
	

	public double marge(){
		double coutAchatCacao= hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)*ratioCacao*(0.3*P1.annoncePrix()+0.3*P2.annoncePrix()+0.4*3000); //coût d'achat du cacao aux 3 producteurs 
				return (15000*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3)-coutRevient()-coutAchatCacao);
	}
}
