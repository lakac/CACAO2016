package abstraction.equipe1;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.CommandeProduc;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateurD;
import abstraction.commun.ITransformateurP;
import abstraction.commun.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Producteur implements Acteur, IProducteur {
	private static final double productionAnnuelle = 24000;
	
	private String nom;
	private Stock stock;
	private Indicateur tresorerie;
	private double coutProduction;
	private double[] productionDeBase;
	private Map<ITransformateurP,Double> quantitesProposees;
	private Indicateur productionCourante;
	private Journal journal;
	private List<ITransformateurP> transformateurs;
	private IntelligenceEconomique intelligenceEconomique;
	
	/**
	 * Initialise notre producteur a partir d'un stock et d'une tresorerie initiaux.
	 */
	public Producteur(String nom, double stockInitial, double tresoInitiale, Monde monde) {
		this.nom = nom;
		this.stock = new Stock(this, stockInitial);
		this.tresorerie = new Indicateur("Solde de "+this.nom, this, tresoInitiale);
		this.coutProduction = 2100.0;
		this.productionCourante = new Indicateur(Constantes.IND_PRODUCTION_P1, this, 100.0);
		
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		Monde.LE_MONDE.ajouterIndicateur(this.productionCourante);
		
		// Pourcentages de la production annuelle effectivement produits theoriquement a chaque step.
		// Des fluctuations autour de ces valeurs sont implementees dans la methode produire().
		this.productionDeBase = new double[] {0.008, 0.012, 0.014, 0.017, 0.028, 0.03, 0.017, 0.014, 0.034, 0.047, 0.112,
			0.111, 0.044, 0.036, 0.017, 0.016, 0.035, 0.043, 0.111, 0.112, 0.048, 0.035, 0.024, 0.015, 0.012, 0.008}; 
		
		// Afin d'eviter d'avoir une demande effective de la somme des transformateurs superieure a ce que nous pouvons
		// mettre en vente, nous proposons a chaque transformateur une quantite specifique que nous sommes surs de pouvoir
		// lui fournir.
		this.quantitesProposees = new HashMap<ITransformateurP,Double>();
		
		this.journal = new Journal("Journal de "+this.nom);
		Monde.LE_MONDE.ajouterJournal(this.journal);
		
		this.transformateurs = new ArrayList<ITransformateurP>();
		
		this.intelligenceEconomique = new IntelligenceEconomique(this.transformateurs,this.stock);
	}
		
	// Mﾃｩthodes de l'interface Acteur
	
	public String getNom() {
		return this.nom;
	}
	
	public void next() {
		this.intelligenceEconomique.actualiser();
		this.produire();
		this.repartirQuantites();
		this.journal.ajouter("Production de "+this.getNom()+" = <font color=\"maroon\">"+this.getProductionCourante()+"</font> au <b>step</b> "+Monde.LE_MONDE.getStep());
	}
	
	// Mﾃｩthodes de l'interface IProducteur
	
	public double annonceQuantiteMiseEnVente(ITransformateurD t) {
		return this.getQuantiteProposee(t);
	}
	
	public void notificationVente(CommandeProduc c) {
		this.stock.retirerVente((Acteur)c.getAcheteur(), c.getQuantite());
		this.setTresorerie(this.getTresorerie() + c.getQuantite()*c.getPrixTonne());
	}
	
	// mﾃｩthode dﾃｩprﾃｩciﾃｩe
	public double annoncePrix() {
		return 0.0;
	}
	
	// Mﾃｩthodes publiques
	
	public void ajouterTransformateur(ITransformateurP transformateur) {
		this.transformateurs.add(transformateur);
		this.quantitesProposees.put(transformateur, 0.0);
		this.intelligenceEconomique.prendreEnCompte(transformateur);
	}
	
	// Mﾃｩthodes privﾃｩes
	
	/**
	 * @return l'ensemble des transformateurs.
	 */
	private List<ITransformateurP> getTransformateurs() {
		return this.transformateurs;
	}
	
	/**
	 * Modifie productionCourante, la quantite de cacao produite au step actuel.
	 * 
	 * Pour l'instant, on sait que l'on a seulement deux clients, donc la repartition est moitie-moitie.
	 */
	private void produire() {
		Random fluctuations = new Random();
		this.setProductionCourante(Math.floor(this.getProductionDeBaseCourante()*productionAnnuelle*(90+20*fluctuations.nextDouble()))/100.0);
		this.stock.ajouterProd(this.getProductionCourante());
		this.setTresorerie(this.getTresorerie()-this.getCoutProduction()*this.getProductionCourante());
	}
	
	private void repartirQuantites() {
		for (ITransformateurP t : this.getTransformateurs()) {
			this.quantitesProposees.put(t,this.intelligenceEconomique.donnerQuantiteMiseEnVente(t));
		}
	}
	
	private double getStock() {
		return this.stock.getQuantite();
	}
	
	private double getTresorerie() {
		return this.tresorerie.getValeur();
	}
	
	private void setTresorerie(double tresorerie) {
		this.tresorerie.setValeur(this,tresorerie);
	}
	
	private double getCoutProduction() {
		return this.coutProduction;
	}
	
	/**
	 * @return la production de base (theorique) de notre producteur a la periode de l'annee correspondant au step actuel.
	 */
	private double getProductionDeBaseCourante() {
		return this.productionDeBase[Monde.LE_MONDE.getStep()%26];
	}
	
	private double getProductionCourante() {
		return this.productionCourante.getValeur();
	}
	
	private void setProductionCourante(double valeur) {
		this.productionCourante.setValeur(this, valeur);
	}
	
	private double getQuantiteProposee(ITransformateurD t) {
		return this.quantitesProposees.get(t);
	}


	public double annonceQuantiteMiseEnVente(ITransformateurP t) {
		return 0.0;
	}

	//R�ｿｽunion du 03/06 Ajout par l'�ｿｽquipe 2 le 8/06
	public double annonceQuantiteProposee() {
		// TODO Auto-generated method stub
		return 0;
	}
}
