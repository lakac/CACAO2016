package abstraction.equipe5;

import abstraction.fourni.Monde;
import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.fourni.Indicateur;
import abstraction.equipe5.Lindt;

import java.util.ArrayList;
import java.util.Collections;
import abstraction.commun.MarcheProducteur;

public class Tresorerie {
	private HistoriqueCommandeDistri histDistri;
	private HistoriqueCommandeProduc histProduc;
	private Indicateur treso;	
	private ArrayList<IProducteur> listeProducteurs;
	private Lindt lindt;

	public Tresorerie(HistoriqueCommandeDistri histDistri, HistoriqueCommandeProduc histProduc, Lindt lindt, ArrayList<IProducteur> P){
		this.histDistri = histDistri;
		this.histProduc = histProduc;
		this.lindt = lindt;
		this.listeProducteurs=P;
		this.treso = new Indicateur("Tresorerie Lindt", lindt, 100000);
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
	// verifier l'ordre P1,P2 et des commandes
	// TODO Constante (expliquer ce que represente 5000);
	public double coutRevient() {
		double quantiteCacaoAchetee=0;
		double prix=0;
		int chargesFixes = 900980; // salaires+impots
		for (int i = 0; i<listeProducteurs.size() ; i++){
			double quantiteDemandee= this.histProduc.commande(this.histProduc.getHist().size()-i-1).getQuantite();
			quantiteCacaoAchetee += quantiteDemandee; //quantite de cacao commandee au step precedent
			prix += this.histProduc.commande(this.histProduc.getHist().size()-i-1).getQuantite()*this.histProduc.commande(this.histProduc.getHist().size()-i-1).getPrixTonne();
		}
		double quantiteDemandeeP3= 0.0; //voir avec le reste du monde
		quantiteCacaoAchetee += quantiteDemandeeP3;
		return chargesFixes + quantiteCacaoAchetee * 5000 + 
				(prix + MarcheProducteur.LE_MARCHE.getCours()*quantiteDemandeeP3)/quantiteCacaoAchetee;	
		
		
		//cout de revient d'une tonne= charges fixes+ quantite de cacao commandé aux producteurs * cout de transformation d'une tonne.
		//Cout de transformation d'une tonne= 5000+pourcentage de quantite de cacao demandee a� chaque producteur multiplie par leur prix, afin d'avoir un prix de transfo d'environ 8000€/t
	
	
	}
//	
//	public double marge(){
//	return (15000*hist.valeur(Constante.STEP_3)-coutRevient());
//	}

}