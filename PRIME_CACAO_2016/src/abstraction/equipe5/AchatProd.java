package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
import abstraction.fourni.Journal;

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
	private HistoriqueCommande histP;
	private HistoriqueCommande histD;
	private Lindt lindt;
	private Stock stockCacao;
	private Tresorerie treso;
	private Journal journal;
	//private double quantiteDemandee;
	//private double quantiteRecue;

	
	public AchatProd(HistoriqueCommande histP, HistoriqueCommande histD, Lindt lindt, Stock stockCacao, Tresorerie treso, Journal journal) {
		this.histP = histP;
		this.histD = histD;
		this.lindt = lindt;
		this.stockCacao = stockCacao;
		this.treso = treso;
		this.journal = journal;
	}
	
	public HistoriqueCommande getHistP() {
		return this.histP;
	}

	public HistoriqueCommande getHistD() {
		return this.histD;
	}
	
	public Tresorerie getTreso() {
		return this.treso;
	}
	
	public Stock getStock() {
		return this.stockCacao;
	}
	
	public Journal getJournal() {
		return this.journal;
	}
	
	
	/** Fonction qui calcul la quantite que l on va demander aux producteurs
	 * @return une CommandeInterne qui donne la quantite et le prix d'achat du cacao
	 */
	public CommandeInterne calculQuantiteDemandee(){
		double besoinCacao=0;
		// Creation de la liste des commandes qui doivent être livré au step n, n+1, n+2 et n+3
		List<CommandeDistri> listeCommandesDist= new ArrayList<CommandeDistri>();
		for (int i=0 ; i<this.getHistD().getHist().size(); i++){
			if (((CommandeDistri)this.getHistD().getCommande(i)).getStepLivraison()==Constante.stepCourant()
					||((CommandeDistri)this.getHistD().getCommande(i)).getStepLivraison()==Constante.stepSuivant()
					||((CommandeDistri)this.getHistD().getCommande(i)).getStepLivraison()==Constante.step2() 
					||((CommandeDistri)this.getHistD().getCommande(i)).getStepLivraison()==Constante.step3())
				listeCommandesDist.add(((CommandeDistri)this.getHistD().getCommande(i)));
		}
		// Calcul du besoin en cacao pour les 3 prochains step
		for (CommandeDistri c : listeCommandesDist){
			for (int i=0; i<Constante.LISTE_PRODUIT.length ; i++){
				if (c.getProduit().equals(Constante.LISTE_PRODUIT[i]))
					besoinCacao += c.getQuantite()*Constante.LISTE_PRODUIT[i].getRatioCacao();
		}}
		
		
		double commandeP=0; // on prend en compte la quantité de cacao qui va etre livree a ce step
		for (int i=0; i<lindt.getProducteurs().size() ; i++){
			commandeP+= this.getHistP().getHist().get(this.getHistP().getHist().size()-i-1).getQuantite();
		}
		double stockCacao=lindt.getStockCacao().getStock()+commandeP
				+ lindt.getStockChocolat50().getStock()*Constante.LISTE_PRODUIT[0].getRatioCacao()
				+ lindt.getStockChocolat60().getStock()*Constante.LISTE_PRODUIT[1].getRatioCacao()
				+ lindt.getStockChocolat70().getStock()*Constante.LISTE_PRODUIT[2].getRatioCacao();
		
		this.getJournal().ajouter("Quantite dans commande distributeur : " + besoinCacao);
		
		if (stockCacao-Constante.STOCK_MINIMAL_CACAO<besoinCacao){
			besoinCacao=besoinCacao-stockCacao+Constante.STOCK_MINIMAL_CACAO;
		}
		
		if (lindt.getStockCacao().getStock()+commandeP > Constante.STOCK_MAXIMAL_CACAO) {
				besoinCacao = 0;
		}
		
		this.getJournal().ajouter("Besoin Cacao Final : " + besoinCacao);
		
//		// Calcul du prix d'achat : si au step precedent on n'a pas eu ce qu'on veut, on achete plus cher
//		double prixDemande;
//		if (quantiteDemandee < quantiteRecue) {
//			prixDemande = MarcheProducteur.LE_MARCHE.getCours()*1.2;
//		}
//		else {
//			prixDemande=0.95*MarcheProducteur.LE_MARCHE.getCours();
//		}
		
		return new CommandeInterne(besoinCacao,0);
	}
	
	
	/**
	 * Indique la quantite demandee aux producteurs.
	 */
	public double annonceQuantiteDemandee(){ 
		this.getJournal().ajouter("Besoin en Cacao : " + this.calculQuantiteDemandee().getQuantite());
		return this.calculQuantiteDemandee().getQuantite();
	}
	
	
	/** Fonction qui permet de mettre à jour notre stock et notre treso lorsque l'on recoit une CommandeProduc
	 *  Ajoute également la commande à l'historique des commandes producteurs
	 */
	public void notificationVente(CommandeProduc c) {
		//this.quantiteRecue = c.getQuantite();
		
		this.getHistP().ajouter(c);
		
		this.getJournal().ajouter("\n");
		this.getJournal().ajouter("Quantite recue : " + c.getQuantite());
		this.getJournal().ajouter("Prix commande producteur :" + c.getPrixTonne());
		this.getJournal().ajouter("Stock avant ajout quantite recue : " + this.getStock().getStock());
		this.getJournal().ajouter("Quantite de cacao perdue : " + c.getQuantite()*Constante.perteCacao());
		
		this.getStock().ajouterStock(c.getQuantite());
		
		this.getJournal().ajouter("Stock apres : " + this.getStock().getStock());
		this.getJournal().ajouter("\n");
		this.getJournal().ajouter("Prix paye aux producteurs : " + c.getQuantite()*c.getPrixTonne());
		this.getJournal().ajouter("Treso avant de payer : " + this.treso.toString());
		
		this.getTreso().retrait(c.getQuantite()*c.getPrixTonne());
		
		this.getJournal().ajouter("Treso apres : " + this.treso.toString());
	}
	
//	/**
//	 * Indique le prix propose au producteur .
//	 */
//	public double getPrix(){
//		return this.calculQuantiteDemandee().getPrix();
//	}
}