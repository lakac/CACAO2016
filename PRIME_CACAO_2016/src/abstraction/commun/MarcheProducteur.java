package abstraction.commun;

/**
 * Classe modelisant le marché du côté producteur
 * 
 * @author Floriane Dahman
 */

import java.util.ArrayList;
import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheProducteur implements Acteur {
	public static MarcheProducteur LE_MARCHE;	
	public static final double COURS_CACAO = 3000.0;

	private double quantiteMiseEnVente;

	private ArrayList<IProducteur> producteurs;
	private ArrayList<ITransformateur> transformateurs;

	public MarcheProducteur() {
		this.producteurs = new ArrayList<IProducteur>();
		this.transformateurs = new ArrayList<ITransformateur>();
		this.quantiteMiseEnVente = 0.0;
	}

	public double getCours() {
		return MarcheProducteur.COURS_CACAO;
	}
	public double vendre(double quantite) {
		this.quantiteMiseEnVente += quantite;
		return quantite*MarcheProducteur.COURS_CACAO;
	}
	public String getNom() {
		return "Marche du cacao";
	}
	public void next() {
		// Toutes les quantites mise en vente
		double[] quantitesEnVenteP = new double[this.producteurs.size()];
		double totalQuantitesEnVenteP = 0.0;
		for (ITransformateur t : this.transformateurs) {
			int i =0;
			for (IProducteur p : this.producteurs){
				quantitesEnVenteP[i] = p.annonceQuantiteMiseEnVente(t);
				i++;
				totalQuantitesEnVenteP+=p.annonceQuantiteMiseEnVente(t);
			}}
		// Toutes les quantites demandees
		double[] quantitesDemandeesT = new double[this.transformateurs.size()];
		double totalQuantitesDemandeesT = 0.0;
		for (ITransformateur t0: this.transformateurs) {
			int i =0;
			for (IProducteur p0: this.producteurs){
				quantitesDemandeesT[i] = t0.annonceQuantiteDemandee(p0);
				i++;
				totalQuantitesDemandeesT+=t0.annonceQuantiteDemandee(p0);
			}}
		//			double[] quantitesReellementAchetees  = new double[this.transformateurs.size()]; 
		//			double[] quantitesReellementVendues  = new double[this.producteurs.size()]; 
		//			if (totalQuantitesDemandeesT>totalQuantitesEnVente) { // demande > offre
		//				for (int i=0; i<this.transformateurs.size(); i++) {
		//					quantitesReellementAchetees[i]=quantitesDemandeesT[i]*totalQuantitesEnVente/totalQuantitesDemandeesT;
		//				}
		//				for (int i=0; i<this.producteurs.size(); i++) {
		//					quantitesReellementVendues[i]=quantitesEnVente[i];
		//				}
		//				
		//			} else {// offre >= demande
		//				for (int i=0; i<this.transformateurs.size(); i++) {
		//					quantitesReellementAchetees[i]=quantitesDemandeesT[i];
		//				}
		//				for (int i=0; i<this.producteurs.size(); i++) {
		//					quantitesReellementVendues[i]=quantitesEnVente[i]*totalQuantitesDemandeesT/totalQuantitesEnVente;
		//				}
		//			}
		//			//////////////////////////////////////////////
		//			for (int i=0; i<this.transformateurs.size(); i++) {
		//				this.transformateurs.get(i).notificationVente(quantitesReellementAchetees[i]);
		//			}
		//			for (int i=0; i<this.producteurs.size(); i++) {
		//				this.producteurs.get(i).notificationVente(quantitesReellementVendues[i]);
		//			}
		//			
		//			
	}
}	




