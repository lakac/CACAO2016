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

	//creation d'une fonction qui calcule la quantité totale demandée par les 3 distrib pour chacun des produits (dans l'ordre 50%,60%,70%)
	public List <Double> QuantiteDemandeeProduit( List<CommandeDistri> listeCommandesDist){
		List <Double> quantiteTotale = new ArrayList <Double> ();
		
		for (int i=0; i<3 ; i++){
			double quantiteProduit=0.0;
			for (CommandeDistri c : listeCommandesDist ){
				if (Constante.listeProduit[i].getNomProduit()==c.getProduit().getNomProduit()) {
					quantiteProduit += c.getQuantite();
				}
			}
		quantiteTotale.add(quantiteProduit);
		}
		return quantiteTotale;
	}

	
	
	
	//Creation d'une fonction qui calcule la quantité que l'on vend d'un produit (Répartition équitable, donc si 3 dist, on divise la quantité totale par 3). 
	public double repartitionChocolat(List<CommandeDistri> listeCommandesDist){
		if(QuantiteDemandeeProduit(listeCommandesDist).get(0) <= lindt.){
			
		}
			
		
		
		//verifier si on a la quantité totale demandée par produit de disponible 
		
		
		return QuantiteDemandeeProduit(p, listeCommandesDist) / lindt.getDistributeurs().size(); // faire attention à si c'est divisible par 3
		
	}


}
