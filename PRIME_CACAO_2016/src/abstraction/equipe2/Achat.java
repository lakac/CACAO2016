package abstraction.equipe2;

import java.util.ArrayList;

import abstraction.fourni.Historique;
import abstraction.commun.*;

public class Achat {

	private double cacaoachete;
	private Historique historiqueachats;
	

		public double getCacaoachete() {
		return cacaoachete;
	}

	public void setCacaoAchete(Nestle nestle, IProducteur p) {
		this.cacaoachete = Math.min(p.annonceQuantiteMiseEnVente(nestle), nestle.annonceQuantiteDemandee(p)*(Constante.ACHAT_SANS_PERTE+Constante.MARGE_DE_SECURITE));
	}

	public Historique getHistoriqueachats() {
		return this.historiqueachats;
	}
	
	public Achat() {
		this.cacaoachete = 0.0;
		this.historiqueachats = new Historique();
	}
	
	public static double CoutAchat () {
		return MarcheProducteur.LE_MARCHE.getCours();
	}
	
	
	public void MiseAJourHistorique(Nestle nestle, int etape) {
		this.historiqueachats.ajouter(nestle, etape, this.cacaoachete);
	}
	
	
	public double CacaoDemande(ArrayList<CommandeDistri> l){
		double quantite=0.0;
		for(CommandeDistri c: l){
			quantite=quantite+c.getQuantite()*c.getProduit().getRatioCacao();
		}
		return quantite;
	}

}
