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
			
		}
		
		return 0.0;
	}
	
	/**
	 * Indique la quantite demandee au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p,double annoncePrix, double annonceQuantiteMiseEnVente){ 
		return 0.0;
	}
}