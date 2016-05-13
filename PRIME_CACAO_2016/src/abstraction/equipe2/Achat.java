package abstraction.equipe2;

import abstraction.fourni.Historique;
import abstraction.commun.*;

public class Achat {
	private double cacaoachete;
	private Historique historiqueachats;
	
	
	public double getCacaoachete() {
		return cacaoachete;
	}

	public void setCacaoAchete(IProducteur p) {
		this.cacaoachete = Math.min(p.annonceQuantiteMiseEnVente(Nestle), Nestle.annonceQuantiteDemandee(p));
	}

	public Historique getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat() {
		this.cacaoachete = 0.0;
		this.historiqueachats = new Historique();
	}
	
	//public void MiseAJourHistorique(int etape) {
	//	this.historiqueachats.ajouter(Nestle, etape, this.dernierecommandeachetee);
	//}
	//Ne connaît pas encore Nestlé
	
	
	
	

}
