package abstraction.equipe2;

import abstraction.fourni.Historique;

public class Achat {

	private CommandesProd dernierecommandeachetee;
	private Historique historiqueachats;
	
	
	public CommandesProd getDernierecommandeachetee() {
		return dernierecommandeachetee;
	}
	
	public void setDernierecommandeachetee(CommandesProd dernierecommandeachetee) {
		this.dernierecommandeachetee = dernierecommandeachetee;
	}

	public Historique getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat() {
		this.dernierecommandeachetee = new CommandesProd(0.0);
		this.historiqueachats = new Historique();
	}
	
	public void MiseAJourHistorique(int etape) {
		this.historiqueachats.ajouter(Nestle, etape, this.dernierecommandeachetee);
	}
	//Ne connaît pas encore Nestlé
	
	
	
	

}
