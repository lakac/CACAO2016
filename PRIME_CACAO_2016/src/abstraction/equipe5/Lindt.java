package abstraction.equipe5;

import java.util.ArrayList;
import abstraction.commun.*;
import abstraction.fourni.*;
import java.util.List;

public class Lindt implements Acteur, ITransformateurD, ITransformateurP{
	
	private HistoriqueCommande histCommandeDistri;
	private HistoriqueCommande histCommandeProduc;
	private HistoriqueCommande commandeDistriLivree;
	private Stock stockCacao;
	private Stock stockChocolat50;
	private Stock stockChocolat60;
	private Stock stockChocolat70;
	private Tresorerie treso;
	private AchatProd achatProd;
	private VenteDist venteDist;
	private ArrayList<IProducteur> producteurs;
	private ArrayList<IDistributeur> distributeurs;
	private ArrayList<Stock> stocksChocolat;
	private TransformationCacaoChocolat transfo;
	

	public Lindt(){
		this.histCommandeDistri = new HistoriqueCommande();
		this.histCommandeProduc = new HistoriqueCommande();
		this.commandeDistriLivree = new HistoriqueCommande();
		this.stockCacao = new Stock("cacao",this,200.0);
		this.stockChocolat50 = new Stock(Constante.LISTE_PRODUIT[0].getNomProduit(),this,0.0);
		this.stockChocolat60 = new Stock(Constante.LISTE_PRODUIT[1].getNomProduit(),this,0.0);
		this.stockChocolat70 = new Stock(Constante.LISTE_PRODUIT[2].getNomProduit(),this,0.0);
		this.producteurs = new ArrayList<IProducteur>();
		this.distributeurs = new ArrayList<IDistributeur>();
		this.stocksChocolat= new ArrayList<Stock>();
		this.stocksChocolat.add(this.stockChocolat50);
		this.stocksChocolat.add(this.stockChocolat60);
		this.stocksChocolat.add(this.stockChocolat70);
		this.transfo=new TransformationCacaoChocolat(this);
	}

	public void creer() {
		//this.histCommandeProduc.ajouter(new CommandeProduc(100.0, ));
		//this.histCommandeProduc.ajouter(new CommandeProduc(100.0, ));
		this.treso = new Tresorerie(this.histCommandeDistri, this.histCommandeProduc, this, this.getProducteurs());
		this.achatProd = new AchatProd(this.histCommandeProduc,this.histCommandeDistri, this, this.stockCacao, this.treso);	
		this.venteDist = new VenteDist(this, this.getTreso());

	}


	/** Voila tout les getters*/

	public HistoriqueCommande getHistCommandeDistri() {
		return this.histCommandeDistri;
	}
	public HistoriqueCommande getHistCommandeProduc() {
		return this.histCommandeProduc;
	}
	public HistoriqueCommande getCommandeDistriLivree() {
		return this.commandeDistriLivree;
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
	public Stock getStockCacao() {
		return this.stockCacao;
	}
	public Stock getStockChocolat50() {
		return this.stockChocolat50;
	}
	public Stock getStockChocolat60() {
		return this.stockChocolat60;
	}
	public Stock getStockChocolat70() {
		return this.stockChocolat70;
	}
	public ArrayList<Stock> getStocksChocolat(){
		return this.stocksChocolat;
	}
	public Tresorerie getTreso() {
		return this.treso;
	}
	public VenteDist getVenteDist() {
		return this.venteDist;
	}
	public TransformationCacaoChocolat getTransformationCacaoChocolat(){
		return this.transfo;
	}

	
	public void next() {
		this.getTransformationCacaoChocolat().Transformation(); // transforme le cacao en chocolat et met à jour les stocks (retire pour cacao et ajoute pour chocolat)
		
		// si on commente ça, pas de rouge --> il y a surement une erreur dans MarcheDistri obtenirCommandeFinale
//		for(IDistributeur d: this.getDistributeurs()){ // ajout des commandes finales à notre historique
//			for (CommandeDistri cd : MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(this,d)){ 
//				//tant que les distributeurs ne créent pas dans le marche une variable d'instance static 
//				// (public static MarcheDistributeur LE_MARCHE_DISTRIBUTEUR;), on ne pourra pas appeler cette méthode
//				this.getHistCommandeDistri().ajouter(cd);
//		}}
//		System.out.println(getHistCommandeDistri());
		stockChocolat50.retirerStockChocolat(Monde.LE_MONDE.getStep());
		stockChocolat60.retirerStockChocolat(Monde.LE_MONDE.getStep());
		stockChocolat70.retirerStockChocolat(Monde.LE_MONDE.getStep());
		venteDist.MiseAJourHistCommandeDistri();
		treso.retrait(treso.coutStock()+treso.coutLivraison()+Constante.CHARGES_FIXES_STEP);
		treso.depot(treso.payeParDistrib());	
	}

	
	
	// Fonctions finies
	public List<CommandeDistri> offre(List<CommandeDistri> o) {
		return this.venteDist.offre(o);
	}
	
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> livraison){
		return this.venteDist.offre (livraison);
	}

	public void notificationVente(CommandeProduc c) {
		this.achatProd.notificationVente(c);
	}

	public double annonceQuantiteDemandee() {
		return this.achatProd.annonceQuantiteDemandee();
	}
	
	public Catalogue getCatalogue() {
		Catalogue catalogue = new Catalogue();
		List<Plage> listePlage = new ArrayList<Plage>();
		listePlage.add(new Plage(100, 150, 0.05));
		listePlage.add(new Plage(151, 200, 0.07));
		listePlage.add(new Plage(201, 0.12));
		catalogue.add(new Produit("50%", 0.5), new Tarif(this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[0]), listePlage));
		catalogue.add(new Produit("60%", 0.6), new Tarif(this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[1]), listePlage));
		catalogue.add(new Produit("70%", 0.5), new Tarif(this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[2]), listePlage));
		return catalogue;
	}

	
	// Ne plus coder celles la, elles vont disparaitre!
	public double annonceQuantiteDemandee(IProducteur p) {	return 0;}
	public void notificationVente(IProducteur p){ 	}
	public double annonceQuantiteMiseEnVente(IDistributeur d){ return 0;}
	public List<CommandeDistri> Offre(List<CommandeDistri> o) { return null;}
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list) {return null;}
	public double annoncePrix() {return 0.0;}
}
