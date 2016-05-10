package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private HistoriqueCommandeProduc hist;

	public AchatProd(HistoriqueCommandeProduc hist){
		this.hist = hist;
	}
	
	/**
	 * Indique la quantit� demand�e au producteur p.
	 */
	/*public double annonceQuantiteDemandee(IProducteur p, double quantiteMiseEnVente){ // le reste du monde est pris en compte manuellement dans le next
		double quantiteTotale = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_COURANT);
		if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)) {
			return (Math.min(0.3*quantiteTotale, quantiteMiseEnVente));
		}
		else {
			return (Math.min(0.3*quantiteTotale, quantiteMiseEnVente));
		}	
	}*/
}