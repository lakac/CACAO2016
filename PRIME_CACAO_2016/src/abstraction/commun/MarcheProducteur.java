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

/**
 * Acteur gérant l'évolution du cours du cacao et réalisant les échanges entre
 * producteurs et transformateurs.
 * 
 * @author équipe 1
 */
public class MarcheProducteur implements Acteur {
	/** Prix initial avant toute transaction */
	public static final double PRIX_DE_BASE = 3000.0;
	
	/** Reference statique de l'unique instance du marché du cacao */
	public static MarcheProducteur LE_MARCHE;
	
	/** Historique du prix du marché */
	public Indicateur coursCacao;
	
	/** Variation du coût par step */
	public static final double VARIATION_PRIX = 0.02; 
	
	//Extrema des prix du marche
	/** Prix minimum du cacao sur le marche */
	public static final double PRIX_MINIMUM = 2000;
	/** Prix maximum du cacao sur le marche */
	public static final double PRIX_MAXIMUM = 4000;

	private static ArrayList<IProducteur> producteurs;
	private static ArrayList<ITransformateur> transformateurs;
	
	/** Quantites de cacao proposees a chaque ITransformateur par chaque IProducteur */
	private HashMap<IProducteur,HashMap<ITransformateur,Double>> quantitesDisponibles;
	/** Quantites de cacao commandees a chaque IProducteur par chaque ITransformateur */
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
		this.commandesPassees.put(transformateur, new HashMap<IProducteur,Double>());
		for (IProducteur p : MarcheProducteur.producteurs) {
			this.commandesPassees.get(transformateur).put(p, 0.0);
		}
	}

	public void ajouterProducteur(IProducteur producteur){
		MarcheProducteur.producteurs.add(producteur);
		this.quantitesDisponibles.put(producteur, new HashMap<ITransformateur,Double>());
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			this.quantitesDisponibles.get(producteur).put(t, 0.0);
		}
	}
	
	/**
	 * Renvoie la valeur actuelle du cout du cacao
	 */
	public double getCours() {
		return coursCacao.getValeur();
	}

	public Historique getHistorique(){
		return coursCacao.getHistorique();
	}
	
	/**
	 * Modifie le cours du cacao selon l'offre et la demande du step courant.
	 */
	private void actualiserCours() {
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
			totalQuantitesDemandeesT+=t.annonceQuantiteDemandee();
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
	
	public String getNom() {
		return "Marche Cacao";
	}
	
	/**
	 * Renvoie la somme des quantités commandées durant le step courant par le transformateur t à tous les producteurs.
	 */
	private double getCommandeTotale(ITransformateur t) {
		double quantite = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			quantite += this.commandesPassees.get(t).get(p);
		}
		return quantite;
	}
	
	/**
	 * Renvoie le nombre de producteurs auxquels le transformateur t a pour l'instant passé une commande strictement
	 * inférieure à la quantité qu'ils proposaient (individuellement).
	 */
	private int getNombreVendeursDisponibles(ITransformateur t) {
		int n = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			if (this.quantitesDisponibles.get(p).get(t) > 0) {
				n += 1;
			}
		}
		return n;
	}
	
	/**
	 * Renvoie true uniquement si la demande du transformateur t est satisfaite (la somme de ses commandes étant alors
	 * égale à sa demande initiale) ou s'il a déjà commandé absolument tout le cacao qui lui était proposé par l'ensemble
	 * des producteurs.
	 */
	private boolean satisfait(ITransformateur t) {
		return (this.getCommandeTotale(t) == t.annonceQuantiteDemandee()) || (this.getNombreVendeursDisponibles(t) == 0);
	}
	
	/**
	 * Crée une liste contenant tous les transformateurs non satisfaits au début de la phase d'échange. Autrement dit,
	 * seuls les transformateurs qui ne commandent rien ou ceux à qui aucun producteur ne propose de cacao ne seront pas
	 * dans cette liste.
	 */
	private List<Boolean> creerListeAttente() {
		List<Boolean> resultat = new ArrayList<Boolean>();
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			resultat.add(this.satisfait(t));
		}
		return resultat;
	}
	
	/**
	 * Méthode appelée au tout début d'une phase d'échange : toutes les commandes sont remises à zéro et toutes les
	 * quantités disponibles sont récupérées auprès des producteurs.
	 */
	private void actualiserStocksEtCommandes() {
		for (IProducteur p : MarcheProducteur.producteurs) {
			for (ITransformateur t : MarcheProducteur.transformateurs) {
				this.quantitesDisponibles.get(p).put(t, p.annonceQuantiteMiseEnVente(t));
				this.commandesPassees.get(t).put(p,0.0);
			}
		}
	}
	
	/**
	 * Modifie les deux HashMaps en fonction de la quantité commandée par le transformateur t auprès du producteur p.
	 * Ainsi commandesPassees ajoute cette quantité à celle qu'elle avait déjà en mémoire, et quantitesDisponibles retire
	 * cette quantité à celle qu'elle avait déjà en mémoire.
	 */
	private void ajouterCommande(ITransformateur t, IProducteur p, double quantite) {
		this.commandesPassees.get(t).put(p,this.commandesPassees.get(t).get(p)+quantite);
		this.quantitesDisponibles.get(p).put(t,this.quantitesDisponibles.get(p).get(t)-quantite);
	}
	
	/**
	 * Méthode permettant de répartir équitablement les commandes d'un transformateur auprès des producteurs.
	 * Elle doit être appelée plusieurs fois par une autre méthode (prevoirCommandes) pour arriver à la répartition finale.
	 * 
	 * Lors d'un appel, elle détermine le besoin effectif du transformateur (sa demande initiale moins ce qu'il a déjà
	 * commandé lors des appels précédents) et le nombre de producteurs disposant encore de cacao pouvant être acheté par
	 * ce transformateur.
	 * Dans le while :
	 * En théorie, le transformateur devrait commander besoinEffectif/vendeursDisponibles à chaque producteur : dans un cas
	 * idéal, les stocks disponibles permettent de répondre totalement à la demande.
	 * Dans le cas contraire, tant que le transformateur n'est pas satisfait (et que tous les producteurs n'ont pas été
	 * passés en revue), il commande le minimum entre ce qui est disponible chez le producteur actuel (accessible via
	 * MarcheProducteur.producteurs.get(i)) et la commande théorique (besoinEffectif/vendeursDisponibles).
	 */
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
	
	/**
	 * Méthode principale gérant les échanges au sein du marché. Tant qu'il existe des transformateurs non "satisfaits"
	 * (voir la méthode satisfait(ITransformateur t)), donc présents dans la liste d'attente, ceux-ci passent des commandes
	 * via la méthode repartirCommandes. Une fois un transformateur satisfait, il quitte la liste d'attente.
	 */
	private void prevoirCommandes() {
		List<Boolean> attente = creerListeAttente();
		int i = 0;
		while (attente.contains(false)) {
			i = 0;
			while (attente.contains(false) && i < MarcheProducteur.transformateurs.size()) {
				if (!this.satisfait(MarcheProducteur.transformateurs.get(i))) {
					this.repartirCommandes(MarcheProducteur.transformateurs.get(i));
					attente.set(i,this.satisfait(MarcheProducteur.transformateurs.get(i)));
				}
				i++;
			}
		}
	}
	
	/**
	 * Méthode appelée à la fin de la phase d'échange, donc une fois que tous les transformateurs sont "satisfaits".
	 * Notifie chaque ITransformateur et chaque IProducteur des commandes qui le concernent. 
	 */
	private void effectuerCommandes() {
		for (ITransformateur t : MarcheProducteur.transformateurs) {
			for (IProducteur p : MarcheProducteur.producteurs) {
				CommandeProduc c = new CommandeProduc(t,p,this.commandesPassees.get(t).get(p),t.annoncePrix());
				t.notificationVente(c);
				p.notificationVente(c);
			}
		}
	}
	
	/**
	 * Méthode appelée par le monde, au même niveau que les nexts des autres Acteurs.
	 * Le marché réinitialise ses commandes et récupère les quantités proposées par les producteurs aux transformateurs,
	 * puis décide de la répartition des commandes entre les producteurs, puis notifie chaque acteur des commandes qui le
	 * concernent, puis actualise le cours du cacao.
	 */
	public void next() {
		this.actualiserStocksEtCommandes();
		this.prevoirCommandes();
		this.effectuerCommandes();
		this.actualiserCours();
	}
}
