package abstraction.commun;

import java.util.ArrayList;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.equipe1.Producteur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Historique;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;

public class MarcheProducteur implements Acteur {
	//Prix initial avant toute transaction
	public static double PRIX_DE_BASE = 3000.0;
	public static MarcheProducteur LE_MARCHE;
	//Historique du prix du marché
	public Indicateur coursCacao;
	//Variation du coût par step
	public static double VARIATION_PRIX = 0.02; 
	//Extremum des prix
	public static double PRIX_MINIMUM = 2000;
	public static double PRIX_MAXIMUM = 4000;

	private ArrayList<IProducteur> producteurs;
	private ArrayList<ITransformateur> transformateurs;

	public MarcheProducteur() {
		this.producteurs = new ArrayList<IProducteur>();
		this.transformateurs = new ArrayList<ITransformateur>();
		this.coursCacao = new Indicateur("Cours Cacao",this,PRIX_DE_BASE);
		Monde.LE_MONDE.ajouterIndicateur(this.coursCacao);
	}
	
	public void ajouterTransformateur(ITransformateur transformateur){
		this.transformateurs.add(transformateur);
	}
	
	public void ajouterProducteur(IProducteur producteur){
		this.producteurs.add(producteur);
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

	public void next() {
		// Toutes les quantites mises en vente
		double totalQuantitesEnVenteP = 0.0;
		for (ITransformateur t : this.transformateurs) {
			for (IProducteur p : this.producteurs){
				totalQuantitesEnVenteP+=p.annonceQuantiteMiseEnVente(t);
			}}
		// Toutes les quantites demandees
		double totalQuantitesDemandeesT = 0.0;
		for (ITransformateur t0: this.transformateurs) {
			for (IProducteur p0: this.producteurs){
				totalQuantitesDemandeesT+=t0.annonceQuantiteDemandee(p0);
			}}
		// Si l'offre est superieur a la demande
		if (totalQuantitesEnVenteP > totalQuantitesDemandeesT){
			if (coursCacao.getValeur()*(1-VARIATION_PRIX)>PRIX_MINIMUM){
				coursCacao.setValeur(this, coursCacao.getValeur()*(1-VARIATION_PRIX));
			}
		}
		else {
			//Si l'offre est inferieur a la demande
			if (totalQuantitesEnVenteP < totalQuantitesDemandeesT){
				if (coursCacao.getValeur()*(1+VARIATION_PRIX)<PRIX_MAXIMUM){
					coursCacao.setValeur(this, coursCacao.getValeur()*(1+VARIATION_PRIX));	
				}
			}
			//Si l'offre est exactement la meme que la demande
			else {
				coursCacao.setValeur(this, coursCacao.getValeur());
			}
		}
	}
}	




