package abstraction.equipe4;
import abstraction.fourni.*;
import abstraction.commun.*;
import java.util.ArrayList;

public class Producteur implements Acteur,IProducteur{

	private String nom; 
	private Stock stock; 
	private Journal journal;
	private Tresorerie treso;
	private ProductionBiannuelle prodBiannu;
	private MarcheProd marcheProducteur;

	//Constructeur de l'acteur Producteur 2

	public Producteur(Monde monde) {
		this.nom = Constantes.NOM_PRODUCTEUR_2;
		this.treso = new Tresorerie(this);
		this.stock = new Stock(this);
		this.journal = new Journal("Journal de "+this.nom);
		this.prodBiannu=new ProductionBiannuelle(this,1200000);
		Monde.LE_MONDE.ajouterJournal(this.journal);
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


	public void ajoutMarche(MarcheProd m){
		this.marcheProducteur=m;
	}

	// le next du producteur 2	
	public void next(){

		//production semi annuelle
		if (Monde.LE_MONDE.getStep()%12==1){
			// actualisation de toutes les variables du à la récolte semestrielle.
			this.getProdBiannu().production();
			this.getJournal().ajouter("Production de semi annuelle de " + this.getProdBiannu().getProductionFinale() + " en comptant les pertes de "+ this.getProdBiannu().getPerteProduction());

		}
		// modifications des stocks pour causes naturelles et prise en compte des couts de stock
		this.getStock().gererLesStock();
	}

	public double offre() {

		//Premier ajustement de notre offre totale, en fonction du cours de cacao fixe par le marche.
		//calcul d'un coefficient nous indiquant l'interet de vendre beaucoup ou peu a la step actuelle
		double coeff = (this.getCoursCacao()-3000)/1000;
		double offreTotale = 0.0;
		if (coeff>=0) {
			offreTotale = this.venteAPriori()*(1+coeff);
		}
		else {
			offreTotale = this.venteAPriori()*(1+coeff/2);
		}

	
	
		//L'offre totale est comprise entre la moitie et le double de notre venteAPriori.



		// retourne un double valant la quantité disponible 
		// pour chaque transformateur a chaque step
		public double annonceQuantiteMiseEnVente() {
			return this.offre();
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
			this.venteRealisee(c);

		}

	}