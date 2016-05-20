
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
 * Acteur g�rant l'�volution du cours du cacao et r�alisant les �changes entre
 * producteurs et transformateurs.
 * 
 * @author �quipe 1
 */
public class MarcheProducteur implements Acteur {
	/** Prix initial avant toute transaction */
	public static final double PRIX_DE_BASE = 3000.0;
	
	/** Reference statique de l'unique instance du march� du cacao */
	public static MarcheProducteur LE_MARCHE;

	
	/** Historique du prix du march� */
	private Indicateur coursCacao;
	
	/** Variation du co�t par step */
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
	 * Renvoie la somme des quantit�s command�es durant le step courant par le transformateur t � tous les producteurs.
	 */
	private double getCommandeTotale(ITransformateur t) {
		double quantite = 0;
		for (IProducteur p : MarcheProducteur.producteurs) {
			quantite += this.commandesPassees.get(t).get(p);
		}
		return quantite;
	}
	
	/**
	 * Renvoie le nombre de producteurs auxquels le transformateur t a pour l'instant pass� une commande strictement
	 * inf�rieure � la quantit� qu'ils proposaient (individuellement).
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
	 * Renvoie true uniquement si la demande du transformateur t est satisfaite (la somme de ses commandes �tant alors
	 * �gale � sa demande initiale) ou s'il a d�j� command� absolument tout le cacao qui lui �tait propos� par l'ensemble
	 * des producteurs.
	 */
	private boolean satisfait(ITransformateur t) {
		return (this.getCommandeTotale(t) == t.annonceQuantiteDemandee()) || (this.getNombreVendeursDisponibles(t) == 0);
	}
	
	/**
	 * Cr�e une liste contenant tous les transformateurs non satisfaits au d�but de la phase d'�change. Autrement dit,
	 * seuls les transformateurs qui ne commandent rien ou ceux � qui aucun producteur ne propose de cacao ne seront pas
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
	 * M�thode appel�e au tout d�but d'une phase d'�change : toutes les commandes sont remises � z�ro et toutes les
	 * quantit�s disponibles sont r�cup�r�es aupr�s des producteurs.
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
	 * Modifie les deux HashMaps en fonction de la quantit� command�e par le transformateur t aupr�s du producteur p.
	 * Ainsi commandesPassees ajoute cette quantit� � celle qu'elle avait d�j� en m�moire, et quantitesDisponibles retire
	 * cette quantit� � celle qu'elle avait d�j� en m�moire.
	 */
	private void ajouterCommande(ITransformateur t, IProducteur p, double quantite) {
		this.commandesPassees.get(t).put(p,this.commandesPassees.get(t).get(p)+quantite);
		this.quantitesDisponibles.get(p).put(t,this.quantitesDisponibles.get(p).get(t)-quantite);
	}
	
	/**
	 * M�thode permettant de r�partir �quitablement les commandes d'un transformateur aupr�s des producteurs.
	 * Elle doit �tre appel�e plusieurs fois par une autre m�thode (prevoirCommandes) pour arriver � la r�partition finale.
	 * 
	 * Lors d'un appel, elle d�termine le besoin effectif du transformateur (sa demande initiale moins ce qu'il a d�j�
	 * command� lors des appels pr�c�dents) et le nombre de producteurs disposant encore de cacao pouvant �tre achet� par
	 * ce transformateur.
	 * Dans le while :
	 * En th�orie, le transformateur devrait commander besoinEffectif/vendeursDisponibles � chaque producteur : dans un cas
	 * id�al, les stocks disponibles permettent de r�pondre totalement � la demande.
	 * Dans le cas contraire, tant que le transformateur n'est pas satisfait (et que tous les producteurs n'ont pas �t�
	 * pass�s en revue), il commande le minimum entre ce qui est disponible chez le producteur actuel (accessible via
	 * MarcheProducteur.producteurs.get(i)) et la commande th�orique (besoinEffectif/vendeursDisponibles).
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
	 * M�thode principale g�rant les �changes au sein du march�. Tant qu'il existe des transformateurs non "satisfaits"
	 * (voir la m�thode satisfait(ITransformateur t)), donc pr�sents dans la liste d'attente, ceux-ci passent des commandes
	 * via la m�thode repartirCommandes. Une fois un transformateur satisfait, il quitte la liste d'attente.
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
	 * M�thode appel�e � la fin de la phase d'�change, donc une fois que tous les transformateurs sont "satisfaits".
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
	 * M�thode appel�e par le monde, au m�me niveau que les nexts des autres Acteurs.
	 * Le march� r�initialise ses commandes et r�cup�re les quantit�s propos�es par les producteurs aux transformateurs,
	 * puis d�cide de la r�partition des commandes entre les producteurs, puis notifie chaque acteur des commandes qui le
	 * concernent, puis actualise le cours du cacao.
	 */
	public void next() {
		this.actualiserStocksEtCommandes();
		this.prevoirCommandes();
		this.effectuerCommandes();
		this.actualiserCours();
	}
}
