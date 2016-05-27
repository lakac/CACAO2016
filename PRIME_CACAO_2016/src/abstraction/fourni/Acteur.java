package abstraction.fourni;

import abstraction.commun.IProducteur;

/**
 * Tout acteur doit implementer cette interface
 * 
 * Vous aurez donc a creer une/des implementation(s) de cette classe
 * 
 * @author Romuald Debruyne
 *
 */
public interface Acteur {
	/**
	 * @return Le nom de l'acteur
	 */
	public String getNom();
	
	/**
	 * Méthode de l'acteur invoquée suite a l'appui sur le bouton NEXT de la fenêtre principale
	 */
	public void next();

}
