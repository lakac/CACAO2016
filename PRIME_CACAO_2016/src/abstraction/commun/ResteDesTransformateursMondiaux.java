package abstraction.commun;


import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;

/**
 * Cette classe représente le reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux fait office de transformateur.
 * Les producteurs vendent du cacao au reste des tansformateurs mondiaux.
 * le reste des tansformateurs mondiaux ont une demande proportionnelle à la demande des transformateurs réels
 *
 * @author Equipe 4 avec l'aide de l'équipe 1.
 */

public class ResteDesTransformateursMondiaux implements Acteur, ITransformateur {

	private List<ITransformateur> transformateurs;
	
	
	// constructeur
	public ResteDesTransformateursMondiaux(){
		this.transformateurs = new ArrayList<ITransformateur>();
		
	}

	/**
	 * Ajoute un transformateur réels à la liste des transformateurs
	 * servant à calculer la quantité mise en vente.
	 */
	public void ajouterTransformateur(ITransformateur transformateur) {
		if (!this.transformateurs.contains(transformateur) && transformateur != this) {
			this.transformateurs.add(transformateur);
		}
	}

	@Override
	public double annonceQuantiteDemandee() {
		double qt =0;
		for (ITransformateur t : this.transformateurs) {
			qt += t.annonceQuantiteDemandee();
		}

		// le reste du monde représente 83% du marché
		// on rajoute un peu d'aléatoire pour mieux coller à la réalité.
		double pourcentage = 82.0+Math.random()*2.0;
		// 17% correspond aux transformateurs simulés par les autres groupe.
		// Donc en respectant les ratio on a 
		return qt / 17.0 * pourcentage;
	}

	@Override
	public void notificationVente(CommandeProduc c) {
		// On ne simule aucun état concernant le reste du monde
	}

	@Override
	public String getNom() {
		return Constantes.NOM_TRANSFORMATEUR_3;
	}

	@Override
	public void next() {
		// On ne simule aucun état concernant le reste du monde
	}

	@Override
	public double annonceQuantiteDemandee(IProducteur p) {
		double qt =0;
		for (ITransformateur t : this.transformateurs) {
			qt += t.annonceQuantiteDemandee();
		}

		// le reste du monde représente 83% du marché
		// on rajoute un peu d'aléatoire pour mieux coller à la réalité.
		double pourcentage = 82.0+Math.random()*2.0;
		// 17% correspond aux transformateurs simulés par les autres groupe.
		// Donc en respectant les ratio on a 
		return qt / 17.0 * pourcentage;
	}

	@Override
	public void notificationVente(IProducteur p) {
		// On ne simule aucun état concernant le reste du monde		
	}

	@Override
	public double annoncePrix() {
		return MarcheProducteur.LE_MARCHE.getCours();
	}

	@Override
	public Catalogue getCatalogue() {
		// On ne simule aucun état concernant le reste du monde	pour les distributeurs
		return null;
	}

	@Override
	public List<CommandeDistri> Offre(List<CommandeDistri> o) {
		// On ne simule aucun état concernant le reste du monde	pour les distributeurs
		return null;
	}

}
