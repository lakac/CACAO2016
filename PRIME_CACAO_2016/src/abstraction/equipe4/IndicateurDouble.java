package abstraction.equipe4;

import abstraction.fourni.*;

/** Classe IndicateurDouble permettant de faire des comparaison entre deux 
 * indicateurs sous la forme d'un seul comparateur
 *
 * (Extension de la classe Indicateur deja fournie avec peu de modifications)
 *
 * @author Equipe4
 *
 */
public class IndicateurDouble extends abstraction.fourni.Indicateur{

	private Courbe courbe2;
	private Historique historique2;
	private String nom2;

	//constructeur
	
	public IndicateurDouble(String nomIndic, String nom1,String nom2, Acteur createur, double valInit,double valInit2) {
		super(nomIndic, createur, valInit);
		this.getCourbe().setTitre(nom1);
		this.nom2=nom2;
		this.historique2 = new Historique();
		this.historique2.ajouter(createur, Monde.LE_MONDE.getStep(), valInit2);
		this.courbe2 = new Courbe(nom2);
		this.courbe2.ajouter(Monde.LE_MONDE.getStep(), valInit2);
	}

	//getters
	
	
	public String getNom2() {
		return nom2;
	}
	
	public Courbe getCourbe2() {
		return this.courbe2;
	}
	
	public Historique getHistorique2() {
		return this.historique2;
	}
	
	public double getValeur2() {
		return this.historique2.getValeur();
	}
	

	//setter
	
	public void setValeur2(Acteur auteur, double valeur) {
		this.historique2.ajouter(auteur, Monde.LE_MONDE.getStep(), valeur);
		this.courbe2.ajouter(Monde.LE_MONDE.getStep(), this.getValeur2());
		this.setChanged();
		this.notifyObservers("setValeur2");
	}

}
