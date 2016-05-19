package abstraction.equipe2;

import abstraction.fourni.Historique;
import abstraction.commun.*;

public class Achat {
<<<<<<< HEAD

	private CommandeProd dernierecommandeachetee;
=======
	private double cacaoachete;
>>>>>>> branch 'master' of https://github.com/AlexandreMARTY/CACAO2016.git
	private Historique historiqueachats;
	
	
<<<<<<< HEAD
	public CommandeProd getDernierecommandeachetee() {
		return dernierecommandeachetee;
=======
	public double getCacaoachete() {
		return cacaoachete;
>>>>>>> branch 'master' of https://github.com/AlexandreMARTY/CACAO2016.git
	}
<<<<<<< HEAD
	
	public void setDernierecommandeachetee(CommandeProd dernierecommandeachetee) {
		this.dernierecommandeachetee = dernierecommandeachetee;
=======

	public void setCacaoAchete(IProducteur p) {
		this.cacaoachete = Math.min(p.annonceQuantiteMiseEnVente(Nestle), Nestle.annonceQuantiteDemandee(p));
>>>>>>> branch 'master' of https://github.com/AlexandreMARTY/CACAO2016.git
	}

	public Historique getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat() {
<<<<<<< HEAD
		this.dernierecommandeachetee = new CommandeProd(0.0);
=======
		this.cacaoachete = 0.0;
>>>>>>> branch 'master' of https://github.com/AlexandreMARTY/CACAO2016.git
		this.historiqueachats = new Historique();
	}
	
	//public void MiseAJourHistorique(int etape) {
	//	this.historiqueachats.ajouter(Nestle, etape, this.dernierecommandeachetee);
	//}
	//Ne connaît pas encore Nestlé
	
	
	
	

}
