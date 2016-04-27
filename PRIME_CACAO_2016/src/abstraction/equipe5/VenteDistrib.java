
package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.equipe6.Carrefour;
import abstraction.fourni.Monde;

public class VenteDistrib {
	private HistoriqueCommandeDist hist;
	private double quantiteMiseEnVente;
	private Carrefour ca;
	private Lindt lindt;
	
	public VenteDistrib(HistoriqueCommandeDist hist, double q){
		this.hist=hist;
		this.quantiteMiseEnVente=q;
	}
	
	public double getDemandeTotale(){
		return (this.ca.getDemande(this.lindt));
		

}}

