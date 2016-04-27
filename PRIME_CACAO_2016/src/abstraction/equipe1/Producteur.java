package abstraction.equipe1;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Journal;
import abstraction.fourni.Monde;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.commun.Constantes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Random;

public class Producteur implements Acteur, IProducteur {
	private String nom;
	private Indicateur stock;
	private Indicateur tresorerie;
	private double coutProduction;
	private double prixVente;
	private double productionAnnuelle;
	private double[] productionDeBase;
	private Map<ITransformateur,Double> quantitesProposees;
	private Indicateur productionCourante;
	private Journal journal;
	
	/**
	 * Initialise notre producteur a partir d'un stock et d'une tresorerie initiaux.
	 */
	public Producteur(String nom, double stockInitial, double tresoInitiale, Monde monde) {
		this.nom = nom;
		this.stock = new Indicateur("Stock de "+this.nom, this, stockInitial);
		this.tresorerie = new Indicateur("Solde de "+this.nom, this, tresoInitiale);
		this.coutProduction = 2100.0;
		this.prixVente = 3000.0;
		this.productionAnnuelle = 24000.0;
		this.productionCourante = new Indicateur(Constantes.IND_PRODUCTION_P1, this, 100.0);
		
		Monde.LE_MONDE.ajouterIndicateur(this.stock);
		Monde.LE_MONDE.ajouterIndicateur(this.tresorerie);
		Monde.LE_MONDE.ajouterIndicateur(this.productionCourante);
		
		this.productionDeBase = new double[] {0.008, 0.012, 0.014, 0.017, 0.028, 0.03, 0.017, 0.014, 0.034, 0.047, 0.112,
			0.111, 0.044, 0.036, 0.017, 0.016, 0.035, 0.043, 0.111, 0.112, 0.048, 0.035, 0.024, 0.015, 0.012, 0.008}; 
		
		this.quantitesProposees = new HashMap<ITransformateur,Double>();
		for (ITransformateur t : this.getTransformateurs()) {
			this.quantitesProposees.put(t,0.0);
		}
		
		this.journal = new Journal("Journal de "+this.nom);
		Monde.LE_MONDE.ajouterJournal(this.journal);
	}
	
	/**
	 * @return la quantite de cacao produite au step actuel.
	 * 
	 * Plus tard, on ajoutera une fluctuation autour de la quantite de base.
	 */
	private void produire(int step) {
		Random fluctuations = new Random();
		this.setProductionCourante(this.getProductionDeBase(step)*this.getProductionAnnuelle()*(0.98+0.4*fluctuations.nextDouble()));
		this.setStock(this.getStock()+this.getProductionCourante());
		for (ITransformateur t : this.getTransformateurs()) {
			this.quantitesProposees.put(t,this.getProductionCourante()*0.5);
			// Pour l'instant, on a seulement deux clients
		}
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
	
	public double getCoutProduction() {
		return this.coutProduction;
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
	 * @return la production de base de notre producteur a la periode de l'annee correspondant au step actuel.
	 */
	private double getProductionDeBase(int step) {
		return this.productionDeBase[step%26];
	}
	
	private double getProductionCourante() {
		return this.productionCourante.getValeur();
	}
	
	public void setProductionCourante(double valeur) {
		this.productionCourante.setValeur(this, valeur);
	}
	
	private double getQuantiteProposee(ITransformateur t) {
		return this.quantitesProposees.get(t);
	}
	
	/**
	 * Doublon avec getQuantiteProposee... Simplifier avec les conventions de nommage !
	 */
	public double annonceQuantiteMiseEnVente(ITransformateur t) {
		return this.getQuantiteProposee(t);
	}
	
	/**
	 * Doublon avec getPrixVente... Simplifier avec les conventions de nommage !
	 */
	public double annoncePrix() {
		return this.getPrixVente();
	}
	
	/**
	 * Met a jour les stocks et tresoreries du transformateur t et de notre producteur.
	 */
	private void notificationVente(ITransformateur t) {
		double quantiteVendue = Math.min(t.annonceQuantiteDemandee(this), this.annonceQuantiteMiseEnVente(t));
		
		this.setStock(this.getStock() - quantiteVendue);
		this.setTresorerie(this.getTresorerie() + quantiteVendue*(this.getPrixVente()-this.getCoutProduction()));
		t.notificationVente(this);
		// Il faudra que les transformateurs aient bien acces a la quantite reellement vendue...
	}
	
	private List<ITransformateur> getTransformateurs() {
		List<ITransformateur> transfos = new ArrayList<ITransformateur>();
		for (Acteur a : Monde.LE_MONDE.getActeurs()) {
			if (a instanceof ITransformateur) {
				transfos.add((ITransformateur)(a));
			}
		}
		return transfos;
	}
	
	public void next() {
		// Partie 1 : Gestion des ventes (Transfo.notificationVente() pour T1 et T2)
		for (ITransformateur t : this.getTransformateurs()) {
			this.notificationVente(t);
		}
		
		// Partie 2 : mise a jour de la quantite disponible a la vente et du prix de vente du prochain step
		// pour que les transfos y aient acces
		
		this.produire(Monde.LE_MONDE.getStep());
		this.journal.ajouter("Production de "+this.getNom()+" = <font color=\"maroon\">"+this.getProductionCourante()+"</font> au <b>step</b> "+Monde.LE_MONDE.getStep());
		
		// Plus tard, on ajoutera des fluctuations dans le prix de vente ; pour l'instant il est a 3000 euros par tonne
		// [setPrixVente]
	}
}
