package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

public class Banque {
	
	private double banque;
	private Indicateur tresorerie;
		
	
	public Banque(Acteur acteur) {
	this.banque=Constante.TRESORERIE_INITIALE;;
	this.tresorerie = new Indicateur("tresorerie", acteur, this.banque);
	}
	
	public void MiseAJourHistorique(Nestle nestle, int etape) {
		this.tresorerie.getHistorique().ajouter(nestle, etape, this.banque);
	}
	
	public double getBanque() {
		return banque;
	}
	
	public Indicateur getTresorerie() {
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

