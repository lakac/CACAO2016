package abstraction.equipe5;
import abstraction.equipe5.Lindt;
import abstraction.commun.Constantes;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.IProducteur;


public class VenteDist {

	private String produit;
	private Lindt lindt;

	public VenteDist(String p, Lindt lindt){
		this.produit=p;
		this.lindt=lindt;
	}

	
	//creation d'une fonction qui renvoie le prix d'un produit 
//	public double prixProduit(String p){
//		double marge=0.0;
//		if (p=='50%'){
//			marge=7;
//		}
//		else{
//			if(p)
//		}
//	
//		return Tresorerie.coutRevientParProduit() + marge ;
//	}
	
	
	
	//creation d'une fonction qui calcule la quantité totale demandée par les 3 distrib pour chacun des produits (dans l'ordre 50%,60%,70%)
	public List <Double> QuantiteDemandeeProduit( List<CommandeDistri> listeCommandesDist){
		List <Double> quantiteTotale = new ArrayList <Double> ();

		for (int i=0; i<3 ; i++){
			double quantiteProduit=0.0;
			for (CommandeDistri c : listeCommandesDist ){
				if (Constante.LISTE_PRODUIT[i].getNomProduit()==c.getProduit().getNomProduit()) {
					quantiteProduit += c.getQuantite();
				}
			}
			quantiteTotale.add(quantiteProduit);
		}
		return quantiteTotale;
	}

	//Creation d'une fonction qui calcule la quantité de chocolat à mettre dans chaque commande pour le 1er échange (offre) 
	//Ne pas oublier de changer le prix!!!!
	
	public List<CommandeDistri> Offre (List<CommandeDistri> listeCommandesDist){

		for(int i=0; i<3; i++){
			if(this.QuantiteDemandeeProduit(listeCommandesDist).get(i).doubleValue() <= lindt.getStocksChocolat().get(i).getStock()){
				//ok on peut fournir aux distrib la quantité de chocolats i qu'ils demandent donc on ne modifie pas ces commandes
				 
			}
			else{
				double quantiteRepartie=lindt.getStocksChocolat().get(i).getStock()/lindt.getDistributeurs().size()+1; //Répartition équitable, donc si 3 dist, on divise la quantité totale par 3)
				for (CommandeDistri c : listeCommandesDist){
					if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){
						c.setQuantite(quantiteRepartie);
					}
				}
			}
		}return listeCommandesDist; //liste de commandes pour le premier echange => offre
	}
	
	
	
	
	//Creation d'une fonction qui répartie le chocolat pour le 2eme échange (échange final) Ne pas oublier le boolean validation
	
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf){
		
		
		return cf;
	}
	
	
	
	
	
    //exemple avec une hashmap
	//	for (abstraction.commun.Produit p : Constante.listeProduit) {
	//		if(this.QuantiteDemandeeProduit(listeCommandesDist).get(0).doubleValue() <= lindt.getStocks().get(p).getStock()){
				//ok on peut fournir les distrib la quantité de chocolat de 50% qu'ils demandent
	//		}
}
			

	
