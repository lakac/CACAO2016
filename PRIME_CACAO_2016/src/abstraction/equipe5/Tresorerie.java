package abstraction.equipe5;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.fourni.Indicateur;
import abstraction.equipe5.Lindt;
import java.util.Collections;
import abstraction.commun.MarcheProducteur;

public class Tresorerie {
	private HistoriqueCommandeDistri histDistri;
	private HistoriqueCommandeProduc histProduc;
	private Indicateur treso;	
	private IProducteur P1;
	private IProducteur P2;
	private Lindt lindt;

	public Tresorerie(HistoriqueCommandeDistri histDistri, HistoriqueCommandeProduc histProduc, Lindt lindt){
		this.histDistri = histDistri;
		this.histProduc = histProduc;
		this.lindt = lindt;
		this.treso = new Indicateur("Trﾃｩsorerie Lindt", lindt, 100000);
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


	// ATTENTION changer commande(-3).getQuantite avec l'intermédiaire car le 3eme producteur ne passe pas de commande
	// vérifier l'ordre P1,P2 et des commandes
	public double coutRevient() {
		P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
		int chargesFixes = 900980; // salaires+impots
		double quantiteDemandeeP1= this.histProduc.commande(-1).getQuantite();
		double quantiteDemandeeP2= this.histProduc.commande(-2).getQuantite();
		double quantiteDemandeeP3= 0.0; //voir avec l'intermediaire
		double quantiteCacaoAchetee = quantiteDemandeeP1 + quantiteDemandeeP2 + quantiteDemandeeP3; //quantité de cacao commandée au step précédent	
		return chargesFixes + quantiteCacaoAchetee * 5000 + (this.histProduc.commande(-1).getPrixTonne()*quantiteDemandeeP1+ this.histProduc.commande(-2).getPrixTonne()*quantiteDemandeeP2 + MarcheProducteur.LE_MARCHE.getCours()*quantiteDemandeeP3)/quantiteCacaoAchetee;	
		
		
		//cout de revient d'une tonne= charges fixes+ quantité de cacao commandé aux producteurs * cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+pourcentage de quantité de cacao demandée à chaque producteur multiplié par leur prix, afin d'avoir un prix de transfo d'environ 8000€/t
	
	
	}
//	
//	public double marge(){
//	return (15000*hist.valeur(Constante.STEP_3)-coutRevient());
//	}

}