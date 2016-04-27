package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;

public class VenteDistrib {
	private Historique_Commande_Dist hist;
	
	
	public VenteDistrib(Historique_Commande_Dist hist){
		this.hist=hist;
	}
	
	public double getDemande(ITransformateur t){
		if (t==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)){
			return (0);
		}
		return 0.6*hist.valeur(Historique_Commande_Dist.STEP_COURANT);
	}


}
