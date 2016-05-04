package abstraction.equipe5;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.fourni.Indicateur;
import abstraction.equipe5.Lindt;

public class Tresorerie {
	private HistoriqueCommande hist;
	private Indicateur treso;	
	private IProducteur P1;
	private IProducteur P2;
	private Lindt lindt;

	public Tresorerie(HistoriqueCommande hist, Lindt lindt){
		this.hist= hist;
		this.lindt = lindt;
		this.treso = new Indicateur("Trésorerie Lindt", lindt, 100000);
		Monde.LE_MONDE.ajouterIndicateur(this.treso);
	}
	
	public double getTresorerie() {
		return this.treso.getValeur();
	}
	
	private void setTresorerie(double treso) { 
			this.treso.setValeur(this.lindt, treso);
	}
	

	public void depot(double d) {
		if (d > 0) {
			this.setTresorerie(this.getTresorerie()+d);}
	}
	
	public void retrait(double d) {
		if (d > 0) {
			this.setTresorerie(this.getTresorerie()-d);	
		}
	}

	public double coutRevient() {
		P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
		int chargesFixes = 900980; // salaires+impots
//		double quantiteCacaoAchetee = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_PRECEDENT);
//		return chargesFixes + quantiteCacaoAchetee * 5000 + (P1.annoncePrix()*0.3 + P2.annoncePrix()*0.3 + 3000*0.4);	
		//cout de revient d'une tonne= charges fixes+ ratioCacao*quantité demandée par les distributeurs* cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+quantité de cacao demandée à chaque producteur multiplié par leur prix
	
	return 0.0;
	}
	
	public double marge(){
	//	return (15000*hist.valeur(Constante.STEP_3)-coutRevient());
	return 0.0 ;
	}
}

