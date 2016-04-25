package abstraction.equipe1;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Constantes;

import java.util.Map;
import java.util.HashMap;

public class Producteur implements Acteur, IProducteur {
	private String nom;
	private Indicateur stock;
	private Indicateur tresorerie;
	private double prixVente;
	private double productionAnnuelle;
	private double[] productionDeBase;
	private Map<String,Double> quantitesProposees;
	private Indicateur productionCourante;
	private Journal journal;
	
	/**
	 * Initialise notre producteur a partir d'un stock et d'une tresorerie initiaux
	 * @param nom
	 * @param stock_initial
	 * @param treso_initiale
	 * @param monde
	 */
	public Producteur(String nom, double stockInitial, double tresoInitiale, Monde monde) {
		this.nom = nom;
		this.stock = new Indicateur("Stock de "+this.nom, this, stockInitial);
		this.tresorerie = new Indicateur("Solde de "+this.nom, this, tresoInitiale);
		this.prixVente = 3000.0;
		this.productionAnnuelle = 24000.0;
		this.productionCourante = new Indicateur(Constantes.IND_PRODUCTION_P1, this, 100.0);
		
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		Monde.LE_MONDE.ajouterIndicateur(this.productionCourante);
		
		this.productionDeBase = new double[] {0.008, 0.012, 0.014, 0.017, 0.028, 0.03, 0.017, 0.014, 0.034, 0.047, 0.112,
			0.111, 0.044, 0.036, 0.017, 0.016, 0.035, 0.043, 0.111, 0.112, 0.048, 0.035, 0.024, 0.015, 0.012, 0.008}; 
		
		this.quantitesProposees = new HashMap<String,Double>();
		this.quantitesProposees.put(Constantes.NOM_TRANSFORMATEUR_1,0.0);
		this.quantitesProposees.put(Constantes.NOM_TRANSFORMATEUR_2,0.0);
		this.quantitesProposees.put("Autres",0.0);
		
		this.journal = new Journal("Journal de "+this.nom);
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	/**
	 * @param step
	 * @return la quantite de cacao produite au step actuel
	 * 
	 * Plus tard, on ajoutera une fluctuation autour de la quantite de base.
	 */
	private void produire(int step) {
		this.productionCourante.setValeur(this, this.getProductionDeBase(step)*this.getProductionAnnuelle());
	}
	
	public String getNom() {
		return "Producteur "+this.nom;
	}
	
	private double getStock() {
		return this.stock.getValeur();
	}
	
	private void setStock(double stock) {
		this.stock.setValeur(this, stock);
	}
	
	private double getTresorerie() {
		return this.tresorerie.getValeur();
	}
	
	private void setTresorerie(double tresorerie) {
		this.tresorerie.setValeur(this,tresorerie);
	}
	
	private double getPrixVente() {
		return this.prixVente;
	}
	
	private void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}
	
	private double getProductionAnnuelle() {
		return this.productionAnnuelle;
	}
	
	/**
	 * @param step
	 * @return la production de base de notre producteur a la periode de l'annee correspondant au step actuel
	 */
	private double getProductionDeBase(int step) {
		return this.productionDeBase[step%26];
	}
	
	private Indicateur getProductionCourante() {
		return this.productionCourante;
	}
	
	private double getQuantiteProposee(ITransformateur t) {
		return this.quantitesProposees.get(t);
	}
	
	/**
	 * Doublon avec getQuantiteProposee... Simplifier avec les conventions de nommage
	 */
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return this.getQuantiteProposee(t);
	}
	
	/**
	 * Doublon avec getPrixVente... Simplifier avec les conventions de nommage
	 */
	public double annoncePrix() {
		return this.getPrixVente();
	}
	
	/**
	 * Met a jour les stocks et tresoreries du transformateur t et de ce producteur
	 * @param t
	 */
	private void notificationVente(ITransformateur t) {
		double quantiteVendue = Math.min(t.annonceQuantiteDemandee(this), this.annonceQuantiteMiseEnVente(t));
		
		this.setStock(this.getStock() - quantiteVendue);
		this.setTresorerie(this.getTresorerie() + quantiteVendue*this.getPrixVente());
		t.notificationVente(this);
	}
	
	public void next() {
		// TODO Auto-generated method stub
		
		// Partie 1 : Transfo.notificationVente() pour T1 et T2
		
		// Partie 2 : mise a jour de la quantite disponible a la vente et du prix de vente du prochain step
		// pour que les transfos y aient acces
		
		produire(Monde.LE_MONDE.getStep());
		if (Monde.LE_MONDE.getStep()%3==0) {
			this.journal.ajouter("Production de "+this.getNom()+" = <font color=\"maroon\">"+this.getProductionCourante()+"</font> au <b>step</b> "+Monde.LE_MONDE.getStep());
		}
		
		// Plus tard, on ajoutera des fluctuations dans le prix de vente ; pour l'instant il est a 3000 euros par tonne
		// setPrixVente
	}
}
