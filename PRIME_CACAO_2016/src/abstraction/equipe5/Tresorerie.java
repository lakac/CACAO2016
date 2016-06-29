package abstraction.equipe5;

import abstraction.fourni.Monde;
import abstraction.commun.Commande;
import abstraction.commun.CommandeDistri;
import abstraction.commun.IProducteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.equipe5.Lindt;
import java.util.ArrayList;

public class Tresorerie {
	private HistoriqueCommande histDistri;
	private HistoriqueCommande histProduc;
	private Indicateur treso;	
	private ArrayList<IProducteur> listeProducteurs;
	private Lindt lindt;
	private Journal journal;

	public Tresorerie(HistoriqueCommande histDistri, HistoriqueCommande histProduc, Lindt lindt, ArrayList<IProducteur> P, Journal journal){
		this.histDistri = histDistri;
		this.histProduc = histProduc;
		this.lindt = lindt;
		this.listeProducteurs=P;
		this.treso = new Indicateur("Solde de Lindt", lindt, 1000000);
		Monde.LE_MONDE.ajouterIndicateur(this.treso);
		this.journal = journal;
	}
	
	public double getTresorerie() {
		return this.treso.getValeur();
	}

	public Journal getJournal() {
		return this.journal;
	}
	
	public String toString() {
		String res = "";
		res += this.treso.getValeur();
		return res;
	}
	
	private void setTresorerie(double treso) { 
		this.treso.setValeur(this.lindt, treso);
	}
	

	public void depot(double d) {
		if (d > 0) {
//			this.getJournal().ajouter("Depot sur treso de "+d);
			this.setTresorerie(this.getTresorerie()+d);}
	}
	
	public void retrait(double d) {
		if (d > 0) {
//			this.getJournal().ajouter("Retrait sur treso de "+d);
			this.setTresorerie(this.getTresorerie()-d);	
		}
	}

	/** methode qui donne les depenses totales pour produire 1t de cacao
	 * Elle permet de calculer le prix de vente de chaque produit en prenant ces depenses auquelles on ajoute une marge 
	 * pour chaque produit
	 * le cout de revient s'actualise a chaque step car il depend des cout d'achat
	 * @return double : depenses totales pour produire 1t
	 */
	public double coutRevient(){
		double chargesFixes = Constante.CHARGES_FIXES_STEP;
		double quantiteCacaoAchetee=0;
		double coutTransformation = 0;
		double quantiteDemandee = 0;
		double coutAchat = 0;
		double coutStock = 0;
		for (int i = 0; i<listeProducteurs.size() ; i++){
			quantiteDemandee= this.histProduc.getCommande(this.histProduc.getHist().size()-i-1).getQuantite();
			quantiteCacaoAchetee += quantiteDemandee;
			coutAchat += this.histProduc.getCommande(this.histProduc.getHist().size()-i-1).getQuantite()*this.histProduc.getCommande(this.histProduc.getHist().size()-i-1).getPrixTonne();
		}
		coutTransformation = quantiteCacaoAchetee * Constante.COUT_TRANSFORMATION;
		coutStock = quantiteCacaoAchetee * 18;
		return (coutTransformation + chargesFixes + coutStock + coutAchat)/quantiteCacaoAchetee;
	} 

	
// on ne prend plus en compte les couts de livraison : on considere qu'ils sont inclus dans le prix car on ne sait pas a qui on achete
	
//	public double coutLivraison(){
//		double coutLivraison=0;
//		double quantiteAchetee=0;
//		int[] kilometre = {5000,9000,5000};
//		for (int i = 0; i<listeProducteurs.size() ; i++){
//			quantiteAchetee = this.histProduc.getCommande(this.histProduc.getHist().size()-i-1).getQuantite();
//			coutLivraison += quantiteAchetee*0.01*kilometre[i]; }
//		return coutLivraison;
//	}
	
	
	//cout de stock (18 euros la tonne/step)
	public double coutStock(){
		return(Constante.COUT_STOCK_TONNE_STEP*(lindt.getStockChocolat50().getStock()
				+lindt.getStockChocolat60().getStock()
				+lindt.getStockChocolat70().getStock() 
				+ lindt.getStockCacao().getStock())); 
	}	
	
	
	/**
	 * fonction qui calcule combien les distributeurs nous payent au step courant
	 */
	public double payeParDistrib(){
		double paye=0;
		for (Commande c: lindt.getCommandeDistriLivree().getHist()){ //si il s'agit des livrées
			if (((CommandeDistri)c).getStepLivraison()==Monde.LE_MONDE.getStep()) {
				paye+=c.getQuantite()*c.getPrixTonne();	 //on ne prend pas en compte les rabais finalement
			}
		}
		return paye;
	}


}
