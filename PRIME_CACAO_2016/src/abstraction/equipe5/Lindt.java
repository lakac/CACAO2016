package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.equipe6.Carrefour;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Lindt implements Acteur, ITransformateur{
	
	private HistoriqueCommandeDist hist;
	private Stock stock_cacao;
	private Stock stock_chocolat;
	private Indicateur etatStockCacao;
	private Indicateur etatStockChocolat;
	private Tresorerie treso;
	private AchatProd achatProd;
	private Indicateur venteChocolat;
	
	
	public Lindt(){
		this.hist = new HistoriqueCommandeDist();
		this.stock_cacao = new Stock(0);
		this.stock_chocolat = new Stock(0);
		this.etatStockCacao = new Indicateur("Stock de Cacao ", this, this.stock_cacao.getStock());
		this.etatStockChocolat = new Indicateur("Stock de Chocolat ", this, this.stock_chocolat.getStock());
		this.venteChocolat = new Indicateur("quantité de chocolat vendue ", this, this.stock_chocolat.getStock());
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockCacao );
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockChocolat );
		this.treso=new Tresorerie(this.getHist());
	
		
	}
	
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;}
	
	public HistoriqueCommandeDist getHist(){
		return this.hist;
	}
	
	public void next() {
		IProducteur P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		IProducteur P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);

		IDistributeur D1= (IDistributeur)Monde.LE_MONDE.getActeur(Constantes.NOM_DETAILLANT_1);
		IDistributeur D2= (IDistributeur)Monde.LE_MONDE.getActeur(Constantes.NOM_DETAILLANT_2);

		D1.getDemande(this); //demande quantité souhaitée par les distributeurs;
		D2.getDemande(this);

		//P1.annonceQuantiteMiseEnVente(ITransformateur t);
		//P2.annonceQuantiteMiseEnVente(Lindt);
		hist.ajouter(D1.getDemande(this) + D2.getDemande(this));
		stock_chocolat.ajouterStock(hist.valeur(Constante.STEP_PRECEDENT_MOINS_2));
		stock_chocolat.retirerStock(hist.valeur(Constante.STEP_PRECEDENT_MOINS_3));
		
		
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
		stock_cacao.ajouterStock(Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_PRECEDENT));
		stock_cacao.retirerStock(Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_PRECEDENT_MOINS_2));
		treso.retirerTresorerie(p.annoncePrix());

		System.out.println("Met à jour le stock et la tréso");
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.achatProd.annonceQuantiteDemandee(p);
	}

} 
