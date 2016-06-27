package abstraction.equipe1;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateurP;

import java.util.Random;

public class Producteur implements Acteur, IProducteur {
	// Constantes de classe
	private static final double PRODUCTION_ANNUELLE = 24000;
	// Pourcentages de la production annuelle effectivement produits theoriquement a chaque step.
	// Des fluctuations autour de ces valeurs sont implementees dans la methode produire().
	private static final double[] PRODUCTION_DE_BASE = {0.008, 0.012, 0.014, 0.017, 0.028, 0.03, 0.017, 0.014, 0.034, 0.047, 0.112,
		0.111, 0.044, 0.036, 0.017, 0.016, 0.035, 0.043, 0.111, 0.112, 0.048, 0.035, 0.024, 0.015, 0.012, 0.008};
	private static final double COUT_PROD_MIN = 1500;
	private static final double COUT_PROD_MAX = 2100;
	public static final int NOMBRE_STEP_PAR_AN = 26;

	static final String NOM_PRODUCTEUR = "Afrique de l'Ouest";
	private static final String NOM_JOURNAL = "Journal de l'"+NOM_PRODUCTEUR;
	private static final String IND_STOCK = "Stock de l'"+NOM_PRODUCTEUR;
	private static final String IND_PRODUCTION = "Production de l'"+NOM_PRODUCTEUR;
	private static final String IND_SOLDE = "Solde de l'"+NOM_PRODUCTEUR;

	// Attributs identitaires
	private Journal journal;
	private IntelligenceEconomique intelligenceEconomique;

	// Variables de flux (stock et tresorerie)
	private Stock stock;
	private Indicateur productionCourante;
	private Indicateur tresorerie;
	private double coutProduction;
	
	//juste un constructeur pour les test de Nestle
	public Producteur(String nom) {
	}
	
	// Variable d'echange
	private double quantiteProposee;

	/**
	 * Initialise notre producteur a partir d'un stock et d'une tresorerie initiaux.
	 */
	public Producteur(double stockInitial, double tresoInitiale, Monde monde) {
		this.journal = new Journal(Producteur.NOM_JOURNAL);
		Monde.LE_MONDE.ajouterJournal(this.journal);

		this.stock = new Stock(this, stockInitial, Producteur.IND_STOCK);
		this.productionCourante = new Indicateur(Producteur.IND_PRODUCTION, this, 100.0);
		this.tresorerie = new Indicateur(Producteur.IND_SOLDE, this, tresoInitiale);
		this.coutProduction = 2100.0;

		Monde.LE_MONDE.ajouterIndicateur(this.productionCourante);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);

		this.quantiteProposee = 0.0;

		this.intelligenceEconomique = new IntelligenceEconomique(this.stock);
	}

	// Methodes de l'interface Acteur

	public String getNom() {
		return Producteur.NOM_PRODUCTEUR;
	}

	public void next() {
		this.produire();
		this.actualiserQuantiteProposee();
		this.journal.ajouter("Production = <font color=\"maroon\">"+this.getProductionCourante()+"</font> t; Cout de production = <font color=\"maroon\">"+this.getCoutProduction()+"</font> euros/t au step "+Monde.LE_MONDE.getStep());
	}

	// Methodes de l'interface IProducteur

	public double annonceQuantiteProposee() {
		return this.getQuantiteProposee();
	}

	public void notificationVente(CommandeProduc c) {
		this.stock.retirerVente(this, c.getQuantite());
		this.setTresorerie(this.getTresorerie() + c.getQuantite()*c.getPrixTonne());
	}

	// Methodes privees

	private double getTresorerie() {
		return this.tresorerie.getValeur();
	}

	private void setTresorerie(double tresorerie) {
		this.tresorerie.setValeur(this,tresorerie);
	}

	private double getProductionCourante() {
		return this.productionCourante.getValeur();
	}

	private void setProductionCourante(double valeur) {
		this.productionCourante.setValeur(this, valeur);
	}

	private double getCoutProduction() {
		return this.coutProduction;
	}

	/**
	 * Actualise le cout de production en fonction de la quantite produite : plus elle est elevee, plus le cout sera bas.
	 * Il reste en pratique compris entre COUT_PROD_MIN et COUT_PROD_MAX euros par tonne.
	 */
	private void actualiserCoutProduction() {
		double productionMoyenne = Producteur.PRODUCTION_ANNUELLE/NOMBRE_STEP_PAR_AN;
		double cout = Producteur.COUT_PROD_MIN + (Producteur.COUT_PROD_MAX-Producteur.COUT_PROD_MIN)/(1 + this.getProductionCourante()/productionMoyenne);
		this.coutProduction = Math.floor(cout*100.0)/100.0; // arrondi au centime
	}

	/**
	 * Renvoie la production de base (theorique) de notre producteur a la periode de l'annee correspondant au step actuel.
	 */
	private double getProductionDeBaseCourante() {
		return Producteur.PRODUCTION_DE_BASE[Monde.LE_MONDE.getStep()%NOMBRE_STEP_PAR_AN];
	}

	/**
	 * Actualise productionCourante, la quantite de cacao produite au step actuel.
	 * Le cout de production, le stock et la tresorerie sont egalement mis a jour.
	 */
	private void produire() {
		Random fluctuations = new Random();
		this.setProductionCourante(Math.floor(this.getProductionDeBaseCourante()*PRODUCTION_ANNUELLE*(90+20*fluctuations.nextDouble()))/100.0);

		this.actualiserCoutProduction();

		this.stock.ajouterProd(this.getProductionCourante());
		this.setTresorerie(this.getTresorerie()-this.getCoutProduction()*this.getProductionCourante());
	}
	
	private void actualiserQuantiteProposee() {
		this.quantiteProposee = this.intelligenceEconomique.calculerOffreTotale();
	}

	private double getQuantiteProposee() {
		return this.quantiteProposee;
	}
}
