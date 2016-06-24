package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;

public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Stock stock; 
	private Journal journal;
	private Tresorerie treso;
	private ProductionBiannuelle prodBiannu;
	private MarcheProd marcheProducteur;
	private Offre offre;


	//Constructeur de l'acteur Producteur 2

	public Producteur(Monde monde) {
		this.nom = Constantes.NOM_PRODUCTEUR_2;
		this.treso = new Tresorerie(this);
		this.stock = new Stock(this);
		this.journal = new Journal("Journal de Asie Amerique");
		this.prodBiannu=new ProductionBiannuelle(this,1200000);
		Monde.LE_MONDE.ajouterJournal(this.journal);
		this.offre = new Offre(this, this.stock);
		this.marcheProducteur=MarcheProd.LE_MARCHE;
	}

	// getter

	public Journal getJournal() {
		return this.journal;
	}

	public String getNom() {
		return this.nom;
	}


	public ProductionBiannuelle getProdBiannu() {
		return this.prodBiannu;
	}

	public Stock getStock() {
		return this.stock;
	}



	public Tresorerie getTreso() {
		return this.treso;
	}

	public MarcheProd getMarcheProducteur() {
		return this.marcheProducteur;
	}


	public Offre getOffre() {
		return this.offre;
	}


	// le next du producteur 2	
	public void next(){

		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){
			// actualisation de toutes les variables du a la recolte semestrielle.
			this.getProdBiannu().production();
			this.getJournal().ajouter("Production de semi annuelle de " + this.getProdBiannu().getProductionFinale() + " en comptant les pertes de "+ this.getProdBiannu().getPerteProduction());

		}
		// modifications des stocks pour causes naturelles et prise en compte des couts de stock
		this.getStock().gererLesStock();
	}


	// retourne un double valant la quantité disponible 
	// pour le marche
	public double annonceQuantiteProposee() {
		return this.getOffre().offre();

	}

	//Modification du stock et de la tresorerie suite a une vente
	public void venteRealisee(CommandeProduc c) {
		// modifie la tresorerie
		this.vente(c.getQuantite(), c.getPrixTonne());
		// modife les stocks
		this.getStock().reductionStock(c.getQuantite());
		// le note dans le journal
		this.getJournal().ajouter("Vente de " + c.getQuantite() + " au step numéro "+ Monde.LE_MONDE.getStep());
	}


	// ajout de le somme récolté à la trésorerie après une vente
	public void vente(double qtVendue, double prix){		
		this.getTreso().getFond().setValeur(this, this.getTreso().getFond().getValeur()+ qtVendue*prix);
	}

	public void notificationVente(CommandeProduc c) {
		if (c.getPrixTonne()>0 && c.getQuantite()>0){
			this.venteRealisee(c);
		}
	}


	//Methode utile pour la V2, n'est plus d'actualité pour la V3.
	public double annonceQuantiteMiseEnVente(ITransformateurP t) {
		// TODO Auto-generated method stub
		return 0;
	}


}
