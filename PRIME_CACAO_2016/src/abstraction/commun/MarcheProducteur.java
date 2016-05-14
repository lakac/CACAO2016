 package abstraction.commun;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class MarcheProducteur implements Acteur {
	//Prix initial avant toute transaction
	public static final double PRIX_DE_BASE = 3000.0;
	public static MarcheProducteur LE_MARCHE;
	//Historique du prix du marché
	public Indicateur coursCacao;
	//Variation du coût par step
	public static final double VARIATION_PRIX = 0.02; 
	//Extrema des prix du marche
	public static final double PRIX_MINIMUM = 2000;
	public static final double PRIX_MAXIMUM = 4000;

	private static ArrayList<IProducteur> producteurs;
	private static ArrayList<ITransformateur> transformateurs;
	
	// Quantites de cacao proposees a chaque ITransformateur par chaque IProducteur
	private HashMap<IProducteur,HashMap<ITransformateur,Double>> quantitesDisponibles;
	// Quantites de cacao commandees a chaque IProducteur par chaque ITransformateur
	private HashMap<ITransformateur,HashMap<IProducteur,Double>> commandesPassees;
	
	public MarcheProducteur() {
		MarcheProducteur.producteurs = new ArrayList<IProducteur>();
		MarcheProducteur.transformateurs = new ArrayList<ITransformateur>();
		this.coursCacao = new Indicateur("Cours Cacao",this,PRIX_DE_BASE);
		Monde.LE_MONDE.ajouterIndicateur(this.coursCacao);
		this.commandesPassees = new HashMap<ITransformateur,HashMap<IProducteur,Double>>();
		this.quantitesDisponibles = new HashMap<IProducteur,HashMap<ITransformateur,Double>>();
	}

	public void ajouterTransformateur(ITransformateur transformateur){
		MarcheProducteur.transformateurs.add(transformateur);
	}

	public void ajouterProducteur(IProducteur producteur){
		MarcheProducteur.producteurs.add(producteur);
	}
	
	public void initialiserCommandes() {
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			this.commandesPassees.put(t, new HashMap<IProducteur,Double>());
			for (IProducteur p : MarcheProducteur.producteurs) {
				this.commandesPassees.get(t).put(p, 0.0);
			}
		}
		for (IProducteur p : MarcheProducteur.producteurs) {
			this.quantitesDisponibles.put(p, new HashMap<ITransformateur,Double>());
			for (ITransformateur t : MarcheProducteur.transformateurs) {
				this.quantitesDisponibles.get(p).put(t, 0.0);
			}
		}
	}
	
	//Renvoie la valeur actuelle du cout du cacao
	public double getCours() {
		return coursCacao.getValeur();
	}

	public Historique getHistorique(){
		return coursCacao.getHistorique();
	}

	public String getNom() {
		return "Marche Cacao";
	}
	
	private double getCommandeTotale(ITransformateur t) {
		double quantite = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			quantite += this.commandesPassees.get(t).get(p);
		}
		return quantite;
	}
	
	private int getNombreVendeursDisponibles(ITransformateur t) {
		int n = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			if (this.quantitesDisponibles.get(p).get(t) > 0) {
				n += 1;
			}
		}
		return n;
	}
	
	private boolean satisfait(ITransformateur t) {
		return (this.getCommandeTotale(t) == t.annonceQuantiteDemandee()) || (this.getNombreVendeursDisponibles(t) == 0);
	}

	private List<Boolean> creerListeAttente() {
		List<Boolean> resultat = new ArrayList<Boolean>();
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			resultat.add(this.satisfait(t));
		}
		return resultat;
	}

	private void actualiserStocksEtCommandes() {
		for (IProducteur p : MarcheProducteur.producteurs) {
			for (ITransformateur t : MarcheProducteur.transformateurs) {
				this.quantitesDisponibles.get(p).put(t, p.annonceQuantiteMiseEnVente(t));
				this.commandesPassees.get(t).put(p,0.0);
			}
		}
	}
	
	private void ajouterCommande(ITransformateur t, IProducteur p, double quantite) {
		this.commandesPassees.get(t).put(p,this.commandesPassees.get(t).get(p)+quantite);
		this.quantitesDisponibles.get(p).put(t,this.quantitesDisponibles.get(p).get(t)-quantite);
	}
	
	private void repartirCommandes(ITransformateur t) {
		double q = 0;
		double besoinEffectif = t.annonceQuantiteDemandee()-this.getCommandeTotale(t);
		double vendeursDisponibles = this.getNombreVendeursDisponibles(t);
		
		int i = 0;
		while (i<MarcheProducteur.producteurs.size() && !this.satisfait(t)) {
			q = Math.min(this.quantitesDisponibles.get(MarcheProducteur.producteurs.get(i)).get(t),besoinEffectif/vendeursDisponibles);
			this.ajouterCommande(t,MarcheProducteur.producteurs.get(i),q);
			i++;
		}
	}
	
	public void next() {
		// Toutes les quantites mises en vente
		double totalQuantitesEnVenteP = 0.0;
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			for (IProducteur p : MarcheProducteur.producteurs){
				totalQuantitesEnVenteP+=p.annonceQuantiteMiseEnVente(t);
			}
		}
		// Toutes les quantites demandees
		double totalQuantitesDemandeesT = 0.0;
		for (ITransformateur t: MarcheProducteur.transformateurs) {
			for (IProducteur p: MarcheProducteur.producteurs){
				totalQuantitesDemandeesT+=t.annonceQuantiteDemandee(p);
			}
		}
		// Si l'offre est superieure a la demande
		if (totalQuantitesEnVenteP > totalQuantitesDemandeesT){
			coursCacao.setValeur(this, Math.max(coursCacao.getValeur()*(1-VARIATION_PRIX),PRIX_MINIMUM));
		}
		// Si l'offre est inferieure a la demande
		else if (totalQuantitesEnVenteP < totalQuantitesDemandeesT){
			coursCacao.setValeur(this,Math.min(coursCacao.getValeur()*(1+VARIATION_PRIX),PRIX_MAXIMUM));
		}
		// Si l'offre est exactement la meme que la demande
		else {
			coursCacao.setValeur(this, coursCacao.getValeur()); //On affecte une valeur pour actualiser l'historique
		}
	}
}
