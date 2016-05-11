package abstraction.equipe1;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Constantes;
import abstraction.commun.MarcheProducteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Producteur implements Acteur, IProducteur {
	private String nom;
	private Stock stock;
	private Indicateur tresorerie;
	private double coutProduction;
	private double productionAnnuelle;
	private double[] productionDeBase;
	private Map<ITransformateur,Double> quantitesProposees;
	private Indicateur productionCourante;
	private Journal journal;
	private List<ITransformateur> transformateurs;
	
	/**
	 * Initialise notre producteur a partir d'un stock et d'une tresorerie initiaux.
	 */
	public Producteur(String nom, double stockInitial, double tresoInitiale, Monde monde) {
		this.nom = nom;
		this.stock = new Stock(this, stockInitial);
		this.tresorerie = new Indicateur("Solde de "+this.nom, this, tresoInitiale);
		this.coutProduction = 2100.0;
		this.productionAnnuelle = 24000.0;
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
		this.quantitesProposees = new HashMap<ITransformateur,Double>();
		
		this.journal = new Journal("Journal de "+this.nom);
		Monde.LE_MONDE.ajouterJournal(this.journal);
		
		this.transformateurs = new ArrayList<ITransformateur>();
	}
	
	public void ajouterTransformateur(ITransformateur transformateur) {
		this.transformateurs.add(transformateur);
		this.quantitesProposees.put(transformateur, 0.0);
	}
	
	/**
	 * Modifie productionCourante, la quantite de cacao produite au step actuel.
	 * 
	 * Pour l'instant, on sait que l'on a seulement deux clients, donc la repartition est moitie-moitie.
	 */
	private void produire() {
		Random fluctuations = new Random();
		this.setProductionCourante(Math.floor(this.getProductionDeBaseCourante()*this.getProductionAnnuelle()*(98+4*fluctuations.nextDouble()))/100.0);
		this.stock.ajouterProd(this.getProductionCourante());
		this.setTresorerie(this.getTresorerie()-this.getCoutProduction()*this.getProductionCourante());
		for (ITransformateur t : this.getTransformateurs()) {
			this.quantitesProposees.put(t,this.getStock()*0.5);
		}
	}
	
	public String getNom() {
		return this.nom;
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
	
	private double getPrixVente() {
		return MarcheProducteur.LE_MARCHE.getCours();
	}
	
	private double getProductionAnnuelle() {
		return this.productionAnnuelle;
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
	
	private double getQuantiteProposee(ITransformateur t) {
		return this.quantitesProposees.get(t);
	}
	
	/**
	 * @return la quantite mise en vente pour le transformateur t.
	 * 
	 * Methode de l'interface IProducteur.
	 */
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return this.getQuantiteProposee(t);
	}
	
	/**
	 * @return le prix de vente.
	 * 
	 * Methode de l'interface IProducteur.
	 */
	public double annoncePrix() {
		return this.getPrixVente();
	}
	
	/**
	 * Met a jour les stocks et tresoreries du transformateur t et de notre producteur.
	 * 
	 * Warning : il faut absolument que les transformateurs aient bien acces a la quantite reellement vendue...
	 * Version prochaine : passer cette quantite en argument de ITransformateur.notificationVente ?
	 */
	private void vendre(ITransformateur t) {
		double quantiteVendue = Math.min(t.annonceQuantiteDemandee(this), this.annonceQuantiteMiseEnVente(t));
		
		this.stock.retirerVente((Acteur)t, quantiteVendue);
		this.setTresorerie(this.getTresorerie() + quantiteVendue*this.getPrixVente());
		t.notificationVente(this);
	}
	
	/**
	 * @return l'ensemble des transformateurs du Monde.
	 */
	private List<ITransformateur> getTransformateurs() {
		return this.transformateurs;
	}
	
	public void next() {
		// Partie 1 : Gestion des ventes
		for (ITransformateur t : this.getTransformateurs()) {
			this.vendre(t);
		}
		
		// Partie 2 : Mise a jour de la quantite disponible a la vente (et plus tard du prix de vente) du prochain step
		
		this.produire();
		this.journal.ajouter("Production de "+this.getNom()+" = <font color=\"maroon\">"+this.getProductionCourante()+"</font> au <b>step</b> "+Monde.LE_MONDE.getStep());
		
		// Plus tard, on ajoutera des fluctuations dans le prix de vente ; pour l'instant il est a 3000 euros par tonne.
	}
}
