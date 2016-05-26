package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.MarcheProducteur;
import abstraction.commun.MondeV1;

class CommandeInterne {
	private double quantite, prix;
	
	public CommandeInterne(double q, double p) {
		this.quantite=q;
		this.prix=p;
	}
	
	public double getQuantite() {
		return this.quantite;
	}
	
	public double getPrix() {
		return this.prix;
	}
}

public class AchatProd {
	private HistoriqueCommandeProduc histP;
	private HistoriqueCommandeDistri histD;
	private Lindt lindt;


	
	public AchatProd(HistoriqueCommandeProduc histP, HistoriqueCommandeDistri histD, Lindt lindt) {
		this.histP = histP;
		this.histD = histD;
		this.lindt = lindt;
	}

	public HistoriqueCommandeProduc getHistP() {
		return histP;}

	public HistoriqueCommandeDistri getHistD() {
		return histD;}
	

	// Création d'une fonction qui calcule la quantité demandée en comparant les 2prods
	public CommandeInterne calculQuantiteDemandee(HistoriqueCommandeProduc histP, HistoriqueCommandeDistri histD){
		double besoinCacao=0;
		// Creation de la liste des commandes au step n,n-1 et n-2
		List<CommandeDistri> listeCommandesDist= new ArrayList<CommandeDistri>();
		for (int i=0 ; i<histD.getHist().size(); i++){
			if (histD.getCommande(i).getStepLivraison()==Constante.STEP_COURANT
					||histD.getCommande(i).getStepLivraison()==Constante.STEP_PRECEDENT
					||histD.getCommande(i).getStepLivraison()==Constante.STEP_2 
					||histD.getCommande(i).getStepLivraison()==Constante.STEP_3 )
				listeCommandesDist.add(histD.getCommande(i));
		}
		// Calcul du besoin en cacao pour les 3 prochains step
		for (CommandeDistri c : listeCommandesDist){
			for (int i=0; i<3 ; i++){
				if (c.getProduit().equals(Constante.LISTE_PRODUIT[i]))
					besoinCacao += c.getQuantite()*Constante.LISTE_PRODUIT[i].getRatioCacao();
		}}
		
		double stockCacao=lindt.getStockCacao().getStock()
				- lindt.getStockChocolat50().getStock()
				- lindt.getStockChocolat60().getStock()
				- lindt.getStockChocolat70().getStock();
		if (stockCacao<besoinCacao){
			besoinCacao=besoinCacao-stockCacao - Constante.STOCK_MINIMAL;
		}
		double quantiteEnVente = 0;
		for (IProducteur p: lindt.getProducteurs()){
			quantiteEnVente += p.annonceQuantiteMiseEnVente(lindt);
		}
		double prixDemande;
		if (besoinCacao <= quantiteEnVente){
			if (besoinCacao <= 0.7*quantiteEnVente){
				besoinCacao += 0.2*besoinCacao;
				prixDemande=MarcheProducteur.LE_MARCHE.getCours();}
				if (besoinCacao <= 0.7*quantiteEnVente){
					prixDemande=0.85*MarcheProducteur.LE_MARCHE.getCours();
				}
				else{
					prixDemande=MarcheProducteur.LE_MARCHE.getCours();
					}
			}
		else {
			prixDemande=1.2*MarcheProducteur.LE_MARCHE.getCours();
		}
		return new CommandeInterne(besoinCacao, prixDemande);
	}
	
	/**
	 * Indique la quantite demandee au producteur p.
	 * 
	 */
	// TODO Ne pas oublier de faire un methode permettant de recup la listeCommande...
	public double annonceQuantiteDemandee(){ 
		return 0.6*this.calculQuantiteDemandee(this.getHistP(),this.getHistD()).getQuantite();
	}// On met *0.6 car on prend 60% au prod et 40% au reste du monde
	public double getPrix(){
		return this.calculQuantiteDemandee(this.getHistP(),this.getHistD()).getPrix();
	}
	
	public double quantiteProduc3() {
		return 0.4*this.calculQuantiteDemandee(this.getHistP(),this.getHistD()).getQuantite(); //on achète 40% de la quantité demandée au producteur 3
	}
}