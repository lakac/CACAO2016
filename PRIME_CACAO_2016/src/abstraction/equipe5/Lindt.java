package abstraction.equipe5;

import java.util.ArrayList;

import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class Lindt implements Acteur, ITransformateur{
	
	private HistoriqueCommandeDist hist;
	private Stock stockCacao;
	private Stock stockChocolat;
	private Indicateur etatStockCacao;
	private Indicateur etatStockChocolat;
	private Indicateur venteChocolat;
	private Tresorerie treso;
	private AchatProd achatProd;
	private double quantiteMiseEnVenteProducteur1; 
	private double quantiteMiseEnVenteProducteur2; 
	private IProducteur P1;
	private IProducteur P2;
	private IDistributeur D1;
	private IDistributeur D2;
	private ArrayList<IProducteur> producteurs;
	private ArrayList<IDistributeur> distributeurs;
	
	
	public Lindt(){
		this.hist = new HistoriqueCommandeDist();
		this.stockCacao = new Stock(0);
		this.stockChocolat = new Stock(0);
		this.etatStockCacao = new Indicateur("Stock de Cacao Lindt", this, this.stockCacao.getStock());
		this.etatStockChocolat = new Indicateur("Stock de Chocolat Lindt", this, this.stockChocolat.getStock());
		this.venteChocolat = new Indicateur("quantit√© de chocolat vendue Lindt", this, this.stockChocolat.getStock());
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockCacao );
		Monde.LE_MONDE.ajouterIndicateur( this.etatStockChocolat );
		Monde.LE_MONDE.ajouterIndicateur(venteChocolat);
		this.treso=new Tresorerie(this.getHist());
		this.achatProd = new AchatProd(hist, quantiteMiseEnVenteProducteur1, quantiteMiseEnVenteProducteur2);
		P1 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1);
		P2 = (IProducteur)Monde.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2);
		D1 = (IDistributeur)Monde.LE_MONDE.getActeur("Carrefour");
		D2 = (IDistributeur)Monde.LE_MONDE.getActeur("Leclerc");
		
	}
	public IProducteur setProducteur(IProducteur p){
		return p;
	}
	
	// Pour ajouter les distributeurs et producteurs ‡ nos listes
	public void ajouterDistributeur(IDistributeur t) {
		this.distributeurs.add(t);
	}
	public void ajouterProducteur(IProducteur t) {
		this.producteurs.add(t);
	}
	
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;}
	
	public HistoriqueCommandeDist getHist(){
		return this.hist;
	}
	

	public void next() {
		quantiteMiseEnVenteProducteur1 = P1.annonceQuantiteMiseEnVente(this);
		quantiteMiseEnVenteProducteur2 = P2.annonceQuantiteMiseEnVente(this);
		this.getHist().ajouter(D1.getDemande(this)+ D2.getDemande(this));
		stockChocolat.ajouterStock(this.getHist().valeur(Constante.STEP_PRECEDENT_MOINS_2));
		stockChocolat.retirerStock(this.getHist().valeur(Constante.STEP_PRECEDENT_MOINS_3));
		stockCacao.ajouterStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT)); // stock li√© au reste du monde
		stockCacao.retirerStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT_MOINS_2)); // stock li√© au reste du monde
		treso.ajouterTresorerie(treso.marge());
		treso.retirerTresorerie(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * 3000); // achat cacao au reste du monde
		
		this.etatStockCacao.setValeur(this, this.stockCacao.getStock());
		this.etatStockChocolat.setValeur(this, this.stockChocolat.getStock());
		this.venteChocolat.setValeur(this, this.stockChocolat.getStock());	
	}

	/**
	 * Met a† jour l'Ètat interne de ce transformateur
	 * suite a† une vente auprËs du producteur p.
	 * 
	 * Cette mÈthode est appelÈe par les producteurs.
	 */
	public void notificationVente(IProducteur p){ // on travaille avec chaque producteur d'o√π le ratio de 0.3 √† chaque fois
		stockCacao.ajouterStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT));
		stockCacao.retirerStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT_MOINS_2));
		treso.retirerTresorerie(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * p.annoncePrix());
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.achatProd.annonceQuantiteDemandee(p);
	}
} 
