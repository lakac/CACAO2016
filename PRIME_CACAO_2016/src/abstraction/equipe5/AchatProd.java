package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private Historique_Commande_Dist hist;
	private double quantiteMiseEnVente;
	private double ratioCacao;

	
	public AchatProd(Historique_Commande_Dist hist, double q){
		this.hist=hist;
		this.quantiteMiseEnVente=q;
		this.ratioCacao=0.6;
	}
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){
		double quantiteTotale = ratioCacao*hist.valeur(Historique_Commande_Dist.STEP_COURANT);
		if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)){
			return (Math.min(0.3*quantiteTotale, this.quantiteMiseEnVente));
		}
		else{
			if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2)){
				return (Math.min(0.3*quantiteTotale, this.quantiteMiseEnVente));
			}
			else{
				return 0;
			}
	}}

}