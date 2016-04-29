package abstraction.commun;


import java.util.ArrayList;
import abstraction.commun.IDistributeur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;
import abstraction.fourni.Monde;

public class MarcheDistributeur implements Acteur{

		public static MarcheDistributeur LE_MARCHE_DISTRIBUTEUR;	
		public static final double COURS_CHOCOLAT = 3000.0;
		
		private double quantiteMiseEnVente;

		private ArrayList<ITransformateur> transformateurs;
		private ArrayList<IDistributeur> distributeurs;

		public MarcheDistributeur() {
			this.transformateurs = new ArrayList<ITransformateur>();
			this.distributeurs = new ArrayList<IDistributeur>();
			this.quantiteMiseEnVente = 0.0;
		}
		
		public double getCours() {
			return MarcheDistributeur.COURS_CHOCOLAT;
		}
		public double vendre(double quantite) {
			this.quantiteMiseEnVente += quantite;
			return quantite*MarcheDistributeur.COURS_CHOCOLAT;
		}
		public String getNom() {
			return "Marche du chocolat";
		}
		public void next() {
		// Toutes les quantites mise en vente
			double[] quantitesEnVenteT = new double[this.transformateurs.size()];
			double totalQuantitesEnVenteT =0.0;
			for (IDistributeur d : this.distributeurs) {
				int i =0;
				for (ITransformateur t : this.transformateurs){
					quantitesEnVenteT[i] =+ t.annonceQuantiteMiseEnVente(d);
					i++;
					totalQuantitesEnVenteT+=t.annonceQuantiteMiseEnVente(d);
			}}
			// Toutes les quantites demandees
			double[] quantitesDemandeesD = new double[this.distributeurs.size()];
			double totalQuantitesDemandeesD =0.0;
			for (IDistributeur d0 : this.distributeurs) {
			int j =0;
			for (ITransformateur t0 : this.transformateurs){
				quantitesDemandeesD[j] = d0.getDemande(t0);
				j++;
				totalQuantitesDemandeesD+=d0.getDemande(t0);
				}
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
			
			
		}
	}	



}

