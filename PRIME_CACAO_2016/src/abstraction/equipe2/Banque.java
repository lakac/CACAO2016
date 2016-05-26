package abstraction.equipe2;

import abstraction.fourni.Historique;

public class Banque {
	
	private double banque;
	private Historique tresorerie;
		
	
	public Banque() {
	this.banque=Constante.TRESORERIE_INITIALE;;
	this.tresorerie = new Historique();
	}
	
	public void MiseAJourHistorique(Nestle nestle, int etape) {
		this.tresorerie.ajouter(nestle, etape, this.banque);
	}
	
	public double getBanque() {
		return banque;
	}
	
	public Historique getTresorerie() {
		return tresorerie;
	}

	public double CoutsFixes() {
		return Constante.CHARGES_FIXES;
	}
	
	public void ajouter(double quantité) {
		this.banque+=quantité;
	}

	public void retirer(double quantite) {
		this.banque-=quantite;
	}
}

