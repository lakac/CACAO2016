package abstraction.equipe5;

import java.util.ArrayList;
import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;

public class Lindt implements Acteur, ITransformateur{
	
	private HistoriqueCommandeDistri histCommandeDistri;
	private HistoriqueCommandeProduc histCommandeProduc;
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
		this.histCommandeDistri = new HistoriqueCommandeDistri();
		this.histCommandeProduc = new HistoriqueCommandeProduc();
		this.stockCacao = new Stock("cacao",this,0.0);
		this.stockChocolat = new Stock("chocolat",this,0.0);
		this.venteChocolat = new Indicateur("quantitﾃｩ de chocolat vendue Lindt", this, this.stockChocolat.getStock());
		this.treso = new Tresorerie(this.histCommandeDistri, this.histCommandeProduc, this);
		Monde.LE_MONDE.ajouterIndicateur(venteChocolat);
		this.producteurs = new ArrayList<IProducteur>();
		this.distributeurs = new ArrayList<IDistributeur>();
		this.achatProd = new AchatProd(this.histCommandeProduc);		
	}
	
	public HistoriqueCommandeDistri getHistCommandeDistri() {
		return histCommandeDistri;
	}

	public HistoriqueCommandeProduc getHistCommandeProduc() {
		return histCommandeProduc;
	}
	
	public void ajouterProducteur(IProducteur p) {
		this.producteurs.add(p);
	}
	
	public void ajouterDistributeur(IDistributeur d) {
		this.distributeurs.add(d);
	}
	
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;
		}

	public void next() {

		//P1.annonceQuantiteMiseEnVente(this);
		//P2.annonceQuantiteMiseEnVente(this);
		//this.hist.ajouter(D1.getDemande(this)+ D2.getDemande(this));
		//stockChocolat.ajouterStock(this.getHist().valeur(Constante.STEP_2));
		//stockChocolat.retirerStock(this.getHist().valeur(Constante.STEP_3));
		//stockCacao.ajouterStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT)); // stock liﾃｩ au reste du monde
		//stockCacao.retirerStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2)); // stock liﾃｩ au reste du monde
		//treso.depot(treso.marge());
		//treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * 3000); // achat cacao au reste du monde
		//this.venteChocolat.setValeur(this, this.stockChocolat.getStock());	
	}

	/**
	 * Met a jour l'etat interne de ce transformateur
	 * suite a une vente aupres du producteur p.
	 * 
	 * Cette methode est appelee par les producteurs.
	 */
	public void notificationVente(IProducteur p){ // on travaille avec chaque producteur d'oﾃｹ le ratio de 0.3 ﾃ� chaque fois

		//stockCacao.ajouterStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT));
		//stockCacao.retirerStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2));
		//treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * p.annoncePrix());
	}

	public double annonceQuantiteDemandee(IProducteur p) {
		//return this.achatProd.annonceQuantiteDemandee(p, p.annonceQuantiteMiseEnVente(this));
		return 0.0;
	}
	public double annonceQuantiteMiseEnVente(IDistributeur d){
		return this.stockCacao.getStock();
	}
	
	public void arriveeCommandeDistri(IDistributeur d) {
		//CommandeDistri nouvelleCommande = new CommandeDistri(d, this, d.getDemande(this),15000); // faire mﾃｩthode qui donne le prix de vente
	}
	
	public void arriveeCommandeProduc(IProducteur p) {
		CommandeProduc nouvelleCommande = new CommandeProduc(this, p, annonceQuantiteDemandee(p), p.annoncePrix());
		this.getHistCommandeProduc().ajouter(nouvelleCommande);
	}
}
