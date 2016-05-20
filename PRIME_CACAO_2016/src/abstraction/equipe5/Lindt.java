package abstraction.equipe5;

import java.util.ArrayList;
import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.commun.Catalogue;
import abstraction.commun.CommandeDistri;
import abstraction.commun.CommandeProduc;
import abstraction.commun.MarcheProducteur;
import abstraction.commun.Catalogue;
import abstraction.commun.Produit;
import abstraction.commun.Plage;
import abstraction.commun.Tarif;
import abstraction.commun.*;
import abstraction.fourni.*;
import java.util.List;



public class Lindt implements Acteur, ITransformateur{
	
	private HistoriqueCommandeDistri histCommandeDistri;
	private HistoriqueCommandeProduc histCommandeProduc;
	private Stock stockCacao;
	private Stock stockChocolat50;
	private Stock stockChocolat60;
	private Stock stockChocolat70;
	private Tresorerie treso;
	private AchatProd achatProd;
	private ArrayList<IProducteur> producteurs;
	private ArrayList<IDistributeur> distributeurs;
	private Catalogue catalogue;
	
	public Lindt(){
		this.histCommandeDistri = new HistoriqueCommandeDistri();
		this.histCommandeProduc = new HistoriqueCommandeProduc();
		this.stockCacao = new Stock("cacao",this,0.0);
		this.stockChocolat50 = new Stock(Constante.listeProduit[0].getNomProduit(),this,0.0);
		this.stockChocolat60 = new Stock(Constante.listeProduit[1].getNomProduit(),this,0.0);
		this.stockChocolat70 = new Stock(Constante.listeProduit[2].getNomProduit(),this,0.0);
		this.treso = new Tresorerie(this.histCommandeDistri, this.histCommandeProduc, this);
		this.producteurs = new ArrayList<IProducteur>();
		this.distributeurs = new ArrayList<IDistributeur>();
		//this.achatProd = new AchatProd(this.histCommandeProduc);	
		this.catalogue = new Catalogue();
	}
	
	// Voil� tout les getters !
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
	public ArrayList<IProducteur> getProducteurs() {
		return this.producteurs;
	}
	public ArrayList<IDistributeur> getDistributeurs() {
		return this.distributeurs;
	}
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_2;
		}
	

	// Ne pas oublier d'acheter 30% de plus à cause des pertes!!!!!!
	public void next() {
		//P1.annonceQuantiteMiseEnVente(this);
		//P2.annonceQuantiteMiseEnVente(this);
		
		//this.hist.ajouter(D1.getDemande(this)+ D2.getDemande(this));
		
		//stockChocolat50.ajouterStock(this.getHist().valeur(Constante.STEP_2));
		//stockChocolat60.ajouterStock(this.getHist().valeur(Constante.STEP_2));
		//stockChocolat70.ajouterStock(this.getHist().valeur(Constante.STEP_2));
		
		//stockChocolat50.retirerStock(this.getHist().valeur(Constante.STEP_3));
		//stockChocolat60.retirerStock(this.getHist().valeur(Constante.STEP_3));
		//stockChocolat70.retirerStock(this.getHist().valeur(Constante.STEP_3));
		
		//stockCacao.ajouterStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT)); // stock lié au reste du monde
		//stockCacao.retirerStock(0.4 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2)); // stock lié au reste du monde
		
		//treso.depot(treso.marge());
		//treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * 3000); // achat cacao au reste du monde
		//this.venteChocolat.setValeur(this, this.stockChocolat.getStock());
		//treso.retrait(18*(this.stockChocolat.getStock() + this.stockCacao.getStock())); //cout de stock (18€ la tonne/step)
		//treso.retrait(0.01*(5000*quantiteDemandeeP1+9000*quantiteDemandeeP2+5000*quantiteDemandeeP3)); //cout de livraison côté producteur (0,01€/km et P1 à 5000km et P2 à 9000km)
	}

	/**
	 * Met a jour l'etat interne de ce transformateur
	 * suite a une vente aupres du producteur p.
	 * 
	 * Cette methode est appelee par les producteurs.
	 */
	
	
	public double annonceQuantiteMiseEnVente(IDistributeur d){
		return this.stockCacao.getStock();
	}
	
	public void arriveeCommandeDistri(IDistributeur d) {
		//CommandeDistri nouvelleCommandeeDistri = new CommandeDistri(d, this, d.getDemande(this)); // faire méthode qui donne le prix de vente
	}
	
	public void arriveeCommandeProduc(IProducteur p) {
		//CommandeProduc nouvelleCommandeProduc = new CommandeProduc(this, p, annonceQuantiteDemandee(p), MarcheProducteur.LE_MARCHE.getCours());
		//this.getHistCommandeProduc().ajouter(nouvelleCommandeProduc);
	}


	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// TODO Auto-generated method stub
		return null;
	}


	public double annonceQuantiteDemandee() {
		// TODO Auto-generated method stub
		return 0;
	}

	public double annoncePrix() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void notificationVente(CommandeProduc c) {
		// TODO Auto-generated method stub
		
	}
	

	//public void arriveeCommandeProduc(IProducteur p) {
		//CommandeProduc nouvelleCommandeProduc = new CommandeProduc(this, p, annonceQuantiteDemandee(p), MarcheProducteur.LE_MARCHE.getCours());
		//this.getHistCommandeProduc().ajouter(nouvelleCommandeProduc);
	//}
	
	public Catalogue getCatalogue() {
		List<Plage> listePlage = new ArrayList<Plage>();
		listePlage.add(new Plage(100, 150, 0.05));
		listePlage.add(new Plage(151, 200, 0.07));
		listePlage.add(new Plage(201, 600, 0.12));
		this.catalogue.add(new Produit("50%", 0.5), new Tarif(15000, listePlage));
		this.catalogue.add(new Produit("60%", 0.6), new Tarif(20000, listePlage));
		this.catalogue.add(new Produit("70%", 0.5), new Tarif(25000, listePlage));
		return this.catalogue;
	}
	

	// Ne plus coder celles la, elle va disparaitre!
	public double annonceQuantiteDemandee(IProducteur p) {
		// TODO Auto-generated method stub
		return 0;
	}
	public void notificationVente(IProducteur p){ // on travaille avec chaque producteur d'où le ratio de 0.3 à chaque fois
		//stockCacao.ajouterStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT));
		//stockCacao.retirerStock(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_2));
		//treso.retrait(0.3 * Constante.RATIO_CACAO_CHOCOLAT * hist.valeur(Constante.STEP_PRECEDENT) * p.annoncePrix());
	}

}
