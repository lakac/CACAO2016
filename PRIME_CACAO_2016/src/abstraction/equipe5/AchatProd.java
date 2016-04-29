package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private HistoriqueCommandeDist hist;
	private double quantiteMiseEnVenteProducteur1;
	private double quantiteMiseEnVenteProducteur2;

	public AchatProd(HistoriqueCommandeDist hist, double q1, double q2){
		this.hist=hist;
		this.quantiteMiseEnVenteProducteur1 = q1;
		this.quantiteMiseEnVenteProducteur1 = q2;
	}
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){ // le reste du monde est pris en compte manuellement dans le next
		double quantiteTotale = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_COURANT);
		if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)) {
			return (Math.min(0.3*quantiteTotale, this.quantiteMiseEnVenteProducteur1));
		}
		else {
			return (Math.min(0.3*quantiteTotale, this.quantiteMiseEnVenteProducteur2));
		}	
	}
}