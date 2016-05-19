package abstraction.equipe2;

import abstraction.fourni.Historique;
import abstraction.commun.*;

public class Achat {
	private double cacaoachete;
	private Historique historiqueachats;
	
	
	public double getCacaoachete() {
		return cacaoachete;
	}

	public void setCacaoAchete(ITransformateur t, IProducteur p) {
		this.cacaoachete = Math.min(p.annonceQuantiteMiseEnVente(t), t.annonceQuantiteDemandee(p));
	}

	public Historique getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat() {
		this.cacaoachete = 0.0;
		this.historiqueachats = new Historique();
	}
	
	public static double CoutAchat (IProducteur p, Achat achat) {
		return p.annoncePrix()*achat.getCacaoachete();
	}
	//public void MiseAJourHistorique(int etape) {
	//	this.historiqueachats.ajouter(Nestle, etape, this.dernierecommandeachetee);
	//}
	//Ne connaît pas encore Nestlé
	
	
	
	

}
