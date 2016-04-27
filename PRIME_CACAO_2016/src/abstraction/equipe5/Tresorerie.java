package abstraction.equipe5;

import abstraction.equipe5.Lindt;
import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;

public class Tresorerie {
	private HistoriqueCommandeDist hist;
	private double treso;	
	IProducteur P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
	IProducteur P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
	
	public Tresorerie(HistoriqueCommandeDist hist){
		this.hist= hist;
		this.treso = 0;
	}
	
	public double getTresorerie() {
		return this.treso;
	}
	
	public void setTresorerie(double treso) {
		this.treso = treso;
	}
	
	public void ajouterTresorerie(double d) {
		this.setTresorerie(this.getTresorerie()+d);
	}
	
	public void retirerTresorerie(double d) {
		this.setTresorerie(this.getTresorerie()-d);	
	}

	public double coutRevient() {
		int chargesFixes = 900980; // salaires+impots
		double quantiteCacaoAchetee = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_PRECEDENT);
		return chargesFixes + quantiteCacaoAchetee * 5000 /*+ (p1.annoncePrix()*0.3 + p2.annoncePrix()*0.3 + 3000*0.4)*/ ;	
		//cout de revient d'une tonne= charges fixes+ ratioCacao*quantité demandée par les distributeurs* cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+quantité de cacao demandée à chaque producteur multiplié par leur prix
	}
	
	public double marge(){
		double coutAchatCacao= hist.valeur(Constante.STEP_PRECEDENT_MOINS_3)*Constante.RATIO_CACAO_CHOCOLAT*(0.3*P1.annoncePrix()+0.3*P2.annoncePrix()+0.4*3000); //coût d'achat du cacao aux 3 producteurs 
				return (15000*hist.valeur(Constante.STEP_PRECEDENT_MOINS_3)-coutRevient()-coutAchatCacao);
	}
}

