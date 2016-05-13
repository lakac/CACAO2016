package abstraction.equipe2;
import java.util.HashMap;
import abstraction.commun.*;
import abstraction.fourni.*;

public class Vente {
	private HashMap<Produit, Double> quantitevendue;
	
	public Vente() {
		this.ventes = new HashMap<Produit, Double>();
	}
	
	public HashMap<Produit, Double> getVentes() {
		return ventes;
	}


	public void setquantitevendue(IDistributeur d, Produit p) {
		this.quantitevendue.put(p, Math.min(d.getDemande(Nestle)))
	
	
	
	//public void MiseAJourHistorique(int etape) {
	//	this.historiqueventes.ajouter(Nestle, etape, this.dernierecommandevendue);
	//}
	//Ne connaît pas encore Nestlé
	
	

}
