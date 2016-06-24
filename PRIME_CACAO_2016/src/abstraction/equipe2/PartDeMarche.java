package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.IDistributeur;

/** Fonctionnement de la classe PartDeMarche:
 * 
 * Recuperer l'historique des commandes de chaque distributeur
 * 
 * chaque distributeur represente un certain pourcentage du Marche variable a chaque step
 * 		
 * ResteDesDistributeurs = Monde - PartLeclerc% - PartCarrefour%
 * 
 * normaliser ces parts de Marche afin de determiner quel est notre meilleur Client
 * 
 * Faire un ordre de priorite
 * */

public class PartDeMarche {
	
	/**Variables d'instance*/
	public static PartDeMarche DICO_PARTS;
	
	private HashMap<IDistributeur, Double> partdemarche;
	
	
	
	public double getPartDeMarche(IDistributeur d) {
		return partdemarche.get(d);
	}
	
	public void Ajouter(IDistributeur d, double part) {
		this.partdemarche.put(d, part);
	}



	public PartDeMarche() {
		this.partdemarche = new HashMap<IDistributeur, Double>();
	}
		
}

