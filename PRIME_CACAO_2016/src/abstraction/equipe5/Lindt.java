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
	
	private HistoriqueCommande histCommandeDistrib;
	private HistoriqueCommande histCommandeProduc;
	private Stock stockCacao;
	private Stock stockChocolat;
	private Indicateur venteChocolat;
	private Tresorerie treso;
	private AchatProd achatProd;
	private IProducteur P1;
	private IProducteur P2;
	private IDistributeur D1;
	private IDistributeur D2;
	private ArrayList<IProducteur> producteurs;
	private ArrayList<IDistributeur> distributeurs;
	
	
	public Lindt(){
		this.histCommandeDistrib = new HistoriqueCommande();
		this.histCommandeProduc = new HistoriqueCommande();
		this.stockCacao = new Stock("cacao",this,0.0);
		this.stockChocolat = new Stock("chocolat",this,0.0);
		this.venteChocolat = new Indicateur("quantité de chocolat vendue Lindt", this, this.stockChocolat.getStock());
		this.treso = new Tresorerie(this.getHist(), this);
		Monde.LE_MONDE.ajouterIndicateur(venteChocolat);
		this.producteurs = new ArrayList<IProducteur>();
		this.distributeurs = new ArrayList<IDistributeur>();
		this.achatProd = new AchatProd(histCommandeProduc);		
	}
	
	public void ajouterProducteur(IProducteur p) {
		this.producteurs.add(p);
	}
	
	public void ajouterDistributeur(IDistributeur d) {
		this.distributeurs.add(d);
	}
	
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;}
	
	public HistoriqueCommande getHist(){
		return this.hist;
	}
	

	public void next() {
		P1.annonceQuantiteMiseEnVente(this);
		P2.annonceQuantiteMiseEnVente(this);
		this.getHist().ajouter(D1.getDemande(this)+ D2.getDemande(this));
		stockChocolat.ajouterStock(this.getHist().valeur(Constante.STEP_2));
		stockChocolat.retirerStock(this.getHist().valeur(Constante.STEP_3));
		stockCacao.ajouterStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT)); // stock lié au reste du monde
		stockCacao.retirerStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2)); // stock lié au reste du monde
		treso.depot(treso.marge());
		treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * 3000); // achat cacao au reste du monde
		this.venteChocolat.setValeur(this, this.stockChocolat.getStock());	
	}

	/**
	 * Met a� jour l'�tat interne de ce transformateur
	 * suite a� une vente aupr�s du producteur p.
	 * 
	 * Cette m�thode est appel�e par les producteurs.
	 */
	public void notificationVente(IProducteur p){ // on travaille avec chaque producteur d'où le ratio de 0.3 à chaque fois
		stockCacao.ajouterStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT));
		stockCacao.retirerStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2));
		treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * p.annoncePrix());
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		return this.achatProd.annonceQuantiteDemandee(p, p.annonceQuantiteMiseEnVente(this));
	}
	public double annonceQuantiteMiseEnVente(IDistributeur d){
		return 0.0 ;// Return notre stock :) 
	}
	
	public void arriveeCommandeDirtri(IDistributeur d) {
		CommandeDistrib nouvelleCommande = new CommandeDistrib(d, d.getDemande(this),15000); // faire méthode qui donne le prix de vente
	}
}
