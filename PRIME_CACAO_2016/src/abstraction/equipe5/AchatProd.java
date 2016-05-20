package abstraction.equipe5;

import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.Constantes;
import abstraction.commun.IDistributeur;
import abstraction.commun.IProducteur;
import abstraction.commun.MondeV1;

public class AchatProd {
	private HistoriqueCommandeProduc hist;
	private Lindt lindt;

	public AchatProd(HistoriqueCommandeProduc hist, Lindt lindt){
		this.hist = hist;
		this.lindt = lindt;
	}
	
	
	// Création d'une fonction qui calcule la quantité demandée en comparant les 2prods
	public double calculQuantiteDemandee(List<CommandeDistri> listeCommandesDist){
		double besoinCacao=0;
		for (CommandeDistri c : listeCommandesDist){
			for (int i=0; i<3 ; i++){
				if (c.getProduit().getNomProduit()==Constante.listeProduit[i].getNomProduit())
					besoinCacao += c.getQuantite()*Constante.listeProduit[i].getRatioCacao();
		}}
		double quantiteEnVente = 0;
		for (IProducteur p: lindt.getProducteurs()){
			quantiteEnVente += p.annonceQuantiteMiseEnVente(lindt);
		}
		if (besoinCacao <= quantiteEnVente){
			for (CommandeDistri c : listeCommandesDist){
				for (int i=0; i<lindt.getProducteurs().size(); i++){
					
				}
			}
		}
		
		return 0.0;
	}
	
	/**
	 * Indique la quantite demandee au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p,double annoncePrix, double annonceQuantiteMiseEnVente){ 
//	 // le reste du monde est pris en compte manuellement dans le next
//		double quantiteTotale = Constante.RATIO_CACAO_CHOCOLAT*hist.valeur(Constante.STEP_COURANT);
//		if (p==MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)) {
//			return (Math.min(0.3*quantiteTotale, annonceQuantiteMiseEnVente));
//		}
//		else {
//			return (Math.min(0.3*quantiteTotale, annonceQuantiteMiseEnVente));
//		}
		return 0.0;
	}
}