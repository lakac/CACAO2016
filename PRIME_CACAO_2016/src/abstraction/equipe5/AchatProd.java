package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private HistoriqueCommandeDist hist;
	private double quantiteMiseEnVente;

	
	public AchatProd(HistoriqueCommandeDist hist, double q){
		this.hist=hist;
		this.quantiteMiseEnVente=q;
	}
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){
		double quantiteTotale = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_COURANT);
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