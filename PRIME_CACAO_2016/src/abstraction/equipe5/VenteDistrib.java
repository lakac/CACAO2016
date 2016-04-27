
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
	
	public double getDemandeTotale(){
		return (getDemande((ITransformateur))+getDemande(ITransformateur));


}}

