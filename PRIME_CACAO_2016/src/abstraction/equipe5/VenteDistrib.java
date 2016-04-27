package abstraction.equipe5;

import abstraction.commun.Constantes;
import abstraction.commun.ITransformateur;
import abstraction.commun.MondeV1;
import abstraction.fourni.Monde;

public class VenteDistrib {
	private HistoriqueCommandeDist hist;
	private double quantiteMiseEnVente;
	
	
	public VenteDistrib(HistoriqueCommandeDist hist, double q){
		this.hist=hist;
		this.quantiteMiseEnVente=q;
	}
	
	public double getDemandeTotale(){
		return (getDemande((Monde.LE_MONDE.getActeur(Constantes.NOM_TRANSFORMATEUR_2)))+getDemande(Constantes.NOM_TRANSFORMATEUR_2));


}}
