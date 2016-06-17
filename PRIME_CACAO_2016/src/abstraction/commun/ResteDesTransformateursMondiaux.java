package abstraction.commun;


import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;

/**
 * Cette classe represente le reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux fait office de transformateur.
 * Les producteurs vendent du cacao au reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux ont une demande proportionnelle a la demande des transformateurs reelle
 *
 * @author Equipe 4 avec l'aide de l'equipe 1 (+ Petites modifs de l'equipe 5)
 */

public class ResteDesTransformateursMondiaux implements Acteur, ITransformateurP {

	private List<ITransformateurP> transformateurs;
	
	
	// constructeur
	public ResteDesTransformateursMondiaux(){
		this.transformateurs = new ArrayList<ITransformateurP>();
		
	}


	/**
	 * Ajoute un transformateur reel a la liste des transformateurs
	 * servant a calculer la quantite mise en vente.
	 */
	public void ajouterTransformateur(ITransformateurP transformateur) {
		if (!this.transformateurs.contains(transformateur) && transformateur != this) {
			this.transformateurs.add(transformateur);
		}
	}

	public double annonceQuantiteDemandee() {
		double qt =0;
		for (ITransformateurP t : this.transformateurs) {
			qt += t.annonceQuantiteDemandee();
		}

		// le reste du monde represente 83% du marche
		// on rajoute un peu d'aleatoire pour mieux coller a la realite.
		double pourcentage = 82.0+Math.random()*2.0;
		// 17% correspond aux transformateurs simules� par les autres groupe.
		// Donc en respectant les ratio on a 
		return qt / 17.0 * pourcentage;
	}

	
	public void notificationVente(CommandeProduc c) {
		// On ne simule aucun etat concernant le reste du monde
	}

	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_3;
	}

	
	public void next() {
		// On ne simule aucun etat concernant le reste du monde

	}
	public double annonceQuantiteDemandee(IProducteur p) {
		return 0.0; // méthode dépréciée
	}


	public double annoncePrix() {
		return MarcheProducteur.LE_MARCHE.getCours();
	}

	
	public Catalogue getCatalogue() {
		// On ne simule aucun etat concernant le reste du monde pour les distributeurs
		return null;
	}

	
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// On ne simule aucun etat concernant le reste du monde	pour les distributeurs
		return null;
	}


	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf) {
		return cf;
		// On ne simule aucun etat concernant le reste du monde	pour les distributeurs
	}
	
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list) {
		// On ne simule aucun etat concernant le reste du monde	pour les distributeurs
		return null;
	}

	public void notificationVente(IProducteur p) {
		// méthode dépréciée
	}

	public List<CommandeDistri> offre(List<CommandeDistri> list) {
		// On ne simule aucun etat concernant le reste du monde	pour les distributeurs
		return null;
	}

}
