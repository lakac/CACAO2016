package abstraction.equipe2;

import abstraction.fourni.Historique;

public class Vente {
	private CommandeDis dernierecommandevendue;
	private Historique historiqueventes;
	
	public Vente() {
		this.dernierecommandevendue = new CommandeDis(0.0);
		this.historiqueventes = new Historique();
	}
	
	public void setDernierecommandevendue(CommandeDis dernierecommandevendue) {
		this.dernierecommandevendue = dernierecommandevendue;
	}

	public CommandeDis getDernierecommandevendue() {
		return dernierecommandevendue;
	}
	
	public Historique getHistoriqueventes() {
		return historiqueventes;
	}
	
	//public void MiseAJourHistorique(int etape) {
	//	this.historiqueventes.ajouter(Nestle, etape, this.dernierecommandevendue);
	//}
	//Ne connaît pas encore Nestlé
	
	

}
