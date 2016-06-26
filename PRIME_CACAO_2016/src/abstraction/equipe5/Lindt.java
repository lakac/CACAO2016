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
	private ResteDesDistributeurs resteDesDistributeurs;
	private Journal journal;

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
		this.transfo = new TransformationCacaoChocolat(this);
		this.journal = new Journal("Journal de Lindt");
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}

	public void creer() {
		this.histCommandeProduc.ajouter(new CommandeProduc(100.0, MarcheProd.LE_MARCHE.getCoursCacao().getValeur()));
		this.histCommandeProduc.ajouter(new CommandeProduc(100.0, MarcheProd.LE_MARCHE.getCoursCacao().getValeur()));
		this.treso = new Tresorerie(this.histCommandeDistri, this.histCommandeProduc, this, this.getProducteurs(), this.getJournal());
		this.achatProd = new AchatProd(this.histCommandeProduc,this.histCommandeDistri, this, this.stockCacao, this.getTreso(), this.getJournal());	
		this.venteDist = new VenteDist(this, this.getTreso(), this.getJournal());
		this.resteDesDistributeurs= new ResteDesDistributeurs(this, venteDist);
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
	public Journal getJournal() {
		return this.journal;
	}

	
	public void next() {
//		this.getJournal().ajouter("\n");
//		this.getJournal().ajouter("---------------");
//		this.getJournal().ajouter("\n");
//		this.getJournal().ajouter("Step : " + Monde.LE_MONDE.getStep());
		
		this.getTransformationCacaoChocolat().Transformation(); // transforme le cacao en chocolat et met ﾃ� jour les stocks (retire pour cacao et ajoute pour chocolat)
		
		// si on commente ﾃｧa, pas de rouge --> il y a surement une erreur dans MarcheDistri obtenirCommandeFinale
//		for(IDistributeur d: this.getDistributeurs()){ // ajout des commandes finales ﾃ� notre historique
//			for (CommandeDistri cd : MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(this,d)){ 
//				//tant que les distributeurs ne crﾃｩent pas dans le marche une variable d'instance static 
//				// (public static MarcheDistributeur LE_MARCHE_DISTRIBUTEUR;), on ne pourra pas appeler cette mﾃｩthode
//				this.getHistCommandeDistri().ajouter(cd);
//		}}
//		System.out.println(getHistCommandeDistri());
		//resteDesDistributeurs.commandesDistributeurRestant(); //calcul les commandes du reste des distributeurs et les ajoute à l'historique CommandeDistri
		//System.out.println(getHistCommandeDistri());
		
		// commandes fictives du cote distributeur pour voir si notre code fonctionne
		Commande commande1 = new CommandeDistri(this.getDistributeurs().get(0), this, Constante.LISTE_PRODUIT[0], 30, this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[0]), Monde.LE_MONDE.getStep()+3, true);
		Commande commande2 = new CommandeDistri(this.getDistributeurs().get(0), this, Constante.LISTE_PRODUIT[1], 20, this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[1]), Monde.LE_MONDE.getStep()+3, true);
		Commande commande3 = new CommandeDistri(this.getDistributeurs().get(0), this, Constante.LISTE_PRODUIT[2], 10, this.getVenteDist().prixProduit(Constante.LISTE_PRODUIT[2]), Monde.LE_MONDE.getStep()+3, true);
		
		this.getHistCommandeDistri().ajouter(commande1);
		this.getHistCommandeDistri().ajouter(commande2);
		this.getHistCommandeDistri().ajouter(commande3);
		
		// test pour voir si les commandes passent bien à chaque step
//		this.getJournal().ajouter("Informations liées à la commande du produit 50% :");
//		this.getJournal().ajouter("prix : " + commande1.getPrixTonne());
//		this.getJournal().ajouter("quantite : " + commande1.getQuantite());
//		this.getJournal().ajouter("Informations liées à la commande du produit 60% :");
//		this.getJournal().ajouter("prix : " + commande2.getPrixTonne());
//		this.getJournal().ajouter("quantite : " + commande2.getQuantite());
//		this.getJournal().ajouter("Informations liées à la commande du produit 70% :");
//		this.getJournal().ajouter("prix : " + commande3.getPrixTonne());
//		this.getJournal().ajouter("quantite : " + commande3.getQuantite());
		
		// variation de stock du aux commandes livrees et mise a jour de l'historique
		this.getStockChocolat50().retirerStockChocolat(Monde.LE_MONDE.getStep());
		this.getStockChocolat60().retirerStockChocolat(Monde.LE_MONDE.getStep());
		this.getStockChocolat70().retirerStockChocolat(Monde.LE_MONDE.getStep());
		this.getVenteDist().MiseAJourHistCommandeDistri();
		
		// mise a jour de la tresorerie due aux couts en interne
		this.getTreso().retrait(this.getTreso().coutStock()+Constante.CHARGES_FIXES_STEP);
//		this.getJournal().ajouter("\n");
//		this.getJournal().ajouter("Cout de stock : " + this.getTreso().coutStock());
//		this.getJournal().ajouter("Tréso avant paie distributeurs : " + this.getTreso());
		this.getTreso().depot(this.getTreso().payeParDistrib());
//		this.getJournal().ajouter("Paye par distributeur " + this.getTreso().payeParDistrib());
//		this.getJournal().ajouter("Tréso apres paie distributeurs : " + this.getTreso());
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
