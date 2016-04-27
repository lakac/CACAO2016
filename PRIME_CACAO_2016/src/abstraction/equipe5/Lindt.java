package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Lindt implements Acteur, ITransformateur{
	
	private Historique_Commande_Dist hist;
	private Stock stock_cacao;
	private Stock stock_chocolat;
	private Indicateur etatStockCacao;
	private Indicateur etatStockChocolat;
	private Tresorerie treso;
	private AchatProd achatProd;
	
	public Lindt(){
		this.hist = new Historique_Commande_Dist();
		this.stock_cacao = new Stock(0);
		this.stock_chocolat = new Stock(0);
		this.etatStockCacao = new Indicateur("Stock de Cacao ", this, this.stock_cacao.getStock());
		this.etatStockChocolat = new Indicateur("Stock de Chocolat ", this, this.stock_chocolat.getStock());
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockCacao );
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockChocolat );
		this.treso=new Tresorerie(this.getHist());
	}
	
	public String getNom() {
		return "Lindt";}
	
	public Historique_Commande_Dist getHist(){
		return this.hist;
	}
	
	public void next() {
		IProducteur P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		IProducteur P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);

		IDistributeur D1= (IDistributeur)Monde.LE_MONDE.getActeur(Constantes.NOM_DETAILLANT_1);
		IDistributeur D2= (IDistributeur)Monde.LE_MONDE.getActeur(Constantes.NOM_DETAILLANT_2);

		D1.getDemande(this,Monde.LE_MONDE.getStep()); //demande quantité souhaitée par les distributeurs;
		D2.getDemande(this,Monde.LE_MONDE.getStep());

		//P1.annonceQuantiteMiseEnVente(ITransformateur t);
		//P2.annonceQuantiteMiseEnVente(Lindt);
		/*compt.ajouter(getQuantiteDist());*/
		stock_chocolat.ajouterStock(hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_2));
		stock_chocolat.retirerStock(hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_3));
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
	


	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	public void notificationVente(IProducteur p){
		stock_cacao.ajouterStock(0.6*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT));
		stock_cacao.retirerStock(0.6*hist.valeur(Historique_Commande_Dist.STEP_PRECEDENT_MOINS_2));
		System.out.println("Met à vous le stock et la tréso");
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.achatProd.annonceQuantiteDemandee(p);
	}

} 
