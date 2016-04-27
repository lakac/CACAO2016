package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;

public class VenteDistrib {
	private Historique_Commande_Dist hist;
	private double quantiteMiseEnVente;
	
	
	public VenteDistrib(Historique_Commande_Dist hist, double q){
		this.hist=hist;
		this.quantiteMiseEnVente=q;
	}
	
	public double getDemande(ITransformateur t){
		double quantiteTotale = 
		if (t==MondeV1.LE_MONDE.getActeur(Constantes.NOM_TRANSFORMATEUR_1)){
			return (0);
		}
		else{
			if (t==MondeV1.LE_MONDE.getActeur(Constantes.NOM_TRANSFORMATEUR_2)){
				return (0.3*Math.min(quantiteTotale, this.quantiteMiseEnVente));
			}
			else{
				return 0;
			}

}}
