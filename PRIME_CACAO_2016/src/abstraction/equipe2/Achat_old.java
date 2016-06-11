package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.commun.*;

public class Achat_old {

	private double cacaoachete;
	private Indicateur historiqueachats;
	
	public double getCacaoachete(){
		return cacaoachete;
	}
	
	

	public void setCacaoAchete(Nestle_old nestle, IProducteur p) {
/*	public void setCacaoAchete(Nestle nestle, IProducteur p) {
>>>>>>> branch 'master' of https://github.com/AlexandreMARTY/CACAO2016.git
		this.cacaoachete = Math.min(p.annonceQuantiteMiseEnVente(nestle), nestle.annonceQuantiteDemandee(p));
		System.out.println("jjjj "+p.toString()+"---"+p.annonceQuantiteMiseEnVente(nestle));
		System.out.println("patate");
		System.out.println(nestle.annonceQuantiteDemandee(p));
	}*/
	}
	
	public Indicateur getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat_old(Acteur acteur) {
		this.cacaoachete = 0.0;
		this.historiqueachats = new Indicateur(acteur.getNom(), acteur, this.cacaoachete);
	}
	
	public Achat_old(double quantite) {
		this.cacaoachete = quantite;
	}//
	
	public void MiseAJourHistorique(Nestle_old nestle, int etape) {
		this.historiqueachats.getHistorique().ajouter(nestle, etape, this.cacaoachete);
	}
}
