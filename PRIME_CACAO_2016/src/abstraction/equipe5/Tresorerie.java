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
	// dire aux producteurs que meme si ils ont rien ils doivent mettre 0 dans commande
	public double coutRevient() {
		P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
		int chargesFixes = 900980; // salaires+impots
		double quantiteCacaoAchetee = this.histProduc.commande(-1).getQuantite() + this.histProduc.commande(-2).getQuantite() + this.histProduc.commande(-3).getQuantite(); //quantité de cacao commandée au step précédent	
		double 
		
		return chargesFixes + quantiteCacaoAchetee * 5000 + (*this.histProduc.commande(-1).getPrixTonne() + P2.annoncePrix()*0.3 + getCours() *0.4);	
		
		//cout de revient d'une tonne= charges fixes+ quantité de cacao commandé aux producteurs* cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+quantité de cacao demandée à chaque producteur multiplié par leur prix
	
	
	}
	
	public double marge(){
	return (15000*hist.valeur(Constante.STEP_3)-coutRevient());
	}
}

