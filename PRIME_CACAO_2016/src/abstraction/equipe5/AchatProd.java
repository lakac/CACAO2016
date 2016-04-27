package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private Historique_Commande_Dist hist;
	
	
	public AchatProd(Historique_Commande_Dist hist){
		this.hist=hist;
	}
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){
		if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)){
			return (0);
		}
		return 0.6*hist.valeur(Historique_Commande_Dist.STEP_COURANT);
	}

}
