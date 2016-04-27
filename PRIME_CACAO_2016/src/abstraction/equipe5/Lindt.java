package abstraction.equipe5;

import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Lindt implements Acteur, ITransformateur{
	
	private Historique_Commande_Dist compt;
	private Stock stock_cacao;
	private Stock stock_chocolat;
	private Indicateur etatStockCacao;
	private Indicateur etatStockChocolat;
	
	public Lindt(){

		this.compt = new Compteur();
		this.stock_cacao = new Stock(120);
		this.stock_chocolat = new Stock(110);
		this.etatStockCacao = new Indicateur("Stock de Cacao ", this, this.stock_cacao.getStock());
		this.etatStockChocolat = new Indicateur("Stock de Chocolat ", this, this.stock_chocolat.getStock());
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockCacao );
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockChocolat );
	}
	
	public String getNom() {
		return "Lindt";}

	public void next() {
		/*compt.ajouter(getQuantiteDist());*/
		stock_cacao.ajouterStock(0.6*compt.valeur(Compteur.STEP_PRECEDENT));
		stock_chocolat.ajouterStock(compt.valeur(1));
		stock_cacao.retirerStock(0.6*compt.valeur(1));
		stock_chocolat.retirerStock(compt.valeur(0));
		//this.stock_cacao.ajouterStock(getQuantiteProd());
		//this.stock_chocolat.retirerStock(34);
		this.etatStockCacao.setValeur(this, this.stock_cacao.getStock());
		this.etatStockChocolat.setValeur(this, this.stock_chocolat.getStock());
		
		/* Mettre toutes les m�thodes que les autres ont cr��
		getQuantiteDist(); mondeV0.Le_Monde.getstep(); detaillant.getDemande(this, "step")
		getPrixDist();
		 
		quantiteSouhaitee(getQuantiteDist());
		
		getPrixProd();
		getQuantiteProd();*/
		
	}
	
	public double coutRevient() {
		int charges_fixes = 900980; // salaires+impots
		return charges_fixes + 0.6*compt.valeur(2) * (5000 /*+ ((p1.annoncePrix() + p2.annoncePrix())/2)*/); 	
	}
	
	public double marge(){
		return (15000*compt.valeur(0)-coutRevient());
	}
	
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){
		return 0.6*compt.valeur(3);
	}

	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	public void notificationVente(IProducteur p){
		System.out.println("Met à vous le stock et la tréso");
	}


}	 
	 
	 




