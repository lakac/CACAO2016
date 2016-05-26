package abstraction.equipe5;
import abstraction.equipe5.Lindt;
import abstraction.commun.Constantes;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;

import abstraction.commun.Produit;


public class VenteDist {


	private Lindt lindt;

	private Tresorerie treso;

	public VenteDist(Lindt lindt, Tresorerie treso){

		this.lindt=lindt;
		this.treso=treso;

	}

	public Tresorerie getTreso() {
		return this.treso;
	}

	//creation d'une fonction qui renvoie le prix d'un produit 
	public double prixProduit(Produit p) {
		double r = 0;
		for (int i=0; i<Constante.LISTE_PRODUIT.length; i++) {
			if (p.equals(Constante.LISTE_PRODUIT[i])) {
				r= this.getTreso().coutRevient() + Constante.MARGE_PRODUIT[i];
			}
		}
		return r;
	}
	
	
	//creation d'une fonction qui calcule la quantité totale demandée par les 3 distrib pour chacun des produits (dans l'ordre 50%,60%,70%)
	public List <Double> QuantiteDemandeeProduit( List<CommandeDistri> listeCommandesDist){
		List <Double> quantiteTotale = new ArrayList <Double> ();

		for (int i=0; i<Constante.LISTE_PRODUIT.length ; i++){
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
	
	public List<CommandeDistri> Offre(List<CommandeDistri> listeCommandesDist){
		
		for(int i=0; i<lindt.getDistributeurs().size(); i++){
			
			double stockChocolatI=lindt.getStocksChocolat().get(i).getStock(); //stock de chocolat i
			double QteDemandeeChocolatI=this.QuantiteDemandeeProduit(listeCommandesDist).get(i).doubleValue();// quantite totale de chocolat i demandée par les 3 dist
			
			if(QteDemandeeChocolatI <= stockChocolatI){ //ok on peut fournir aux distrib la quantité de chocolats i qu'ils demandent donc on valide les commandes
					//lindt.getStocksChocolat().get(i).setStock(stockChocolatI-QteDemandeeChocolatI); //mise à jour du stock de chocolat i
					// a faire varier au step n+3
				
				 for(CommandeDistri c : listeCommandesDist){
					 if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){
						 c.setValidation(true);}}} //on valide les commandes de produit i puisqu'on a assez de chocolats i
			
			else{
				double quantiteRepartie=lindt.getStocksChocolat().get(i).getStock()/(lindt.getDistributeurs().size()); //Répartition équitable, donc si 3 dist, on divise la quantité totale par 3)
				
				for (CommandeDistri c : listeCommandesDist){
					if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){
						while(stockChocolatI>0.5){// tant qu'il me reste du stock de chocolat i (limite à 0,5 tonne)
							int j=0; 
							if(c.getQuantite()<=quantiteRepartie){ //si la quantite demandee dans la commande est inférieure à quantiteRepartie
								c.setValidation(true); //on valide la commande
								//lindt.getStocksChocolat().get(i).setStock(stockChocolatI-quantiteRepartie); // on met à jour le stock de chocolat i
								stockChocolatI -= c.getQuantite();
								quantiteRepartie=stockChocolatI/(lindt.getDistributeurs().size()-j);
								j++;	
							}
							else{
								c.setQuantite(quantiteRepartie);
								}}}}}
		}return listeCommandesDist; //liste de commandes pour le premier echange => offre		
	}
	
	
	//Creation d'une fonction qui répartie le chocolat pour le 2eme échange (échange final) Ne pas oublier le boolean validation
	
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf){
		
		List<CommandeDistri> CommandesNonValidees= new ArrayList<CommandeDistri>(); //liste contenant les commandes non validées
		for (CommandeDistri c:cf){
			if (c.getValidation()==false){
				CommandesNonValidees.add(c);
			}
		}
		
		
		List<Double> stockFictif= new ArrayList<Double>(); //liste contenant les stocks fictifs
			for(int i=0; i<Constante.LISTE_PRODUIT.length ; i++){ 
				double valeurStock=lindt.getStocksChocolat().get(i).getStock(); 
				for(CommandeDistri c : cf ){
					if(c.getValidation()==true && c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){ //si la commande a été validée et que le produit de la commande correspond au produit i
					valeurStock-=c.getQuantite(); //on enleve de notre stock fictif la quantité de cacao que l'on s'engage à livrer 
					}
				} 
				stockFictif.add(valeurStock);	
				}
		
			
			for(int i=0; i<Constante.LISTE_PRODUIT.length ; i++){
				if (stockFictif.get(i)>0.5){ // si la quantite de stock fictif est suffisante (ie la quantité qu'il nous restera après avoir validé les commandes)
					
					if(QuantiteDemandeeProduit(CommandesNonValidees).get(i) <= stockFictif.get(i)){ //ok on peut fournir aux distrib la quantité de chocolats i qu'ils demandent donc on valide les commandes
							
						 for(CommandeDistri c : CommandesNonValidees){
							 if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){
								 c.setValidation(true);}}} //on valide les commandes de produit i puisqu'on a assez de chocolats i
					
					else{
					double quantiteRepartie=stockFictif.get(i)/(lindt.getDistributeurs().size());
					for (CommandeDistri c : CommandesNonValidees){
						if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){
							while(stockFictif.get(i)>0.5){ 
								int j=0;
								if(c.getQuantite()<=quantiteRepartie){
									c.setValidation(true);
									stockFictif.set(i, stockFictif.get(i)-c.getQuantite());	
									quantiteRepartie=stockFictif.get(i)/(lindt.getDistributeurs().size()-j);
									j++;	
								}
								else{
									c.setQuantite(quantiteRepartie);
									}
							}
						}
					}
				}
			}}
			for( CommandeDistri c: cf){	
			lindt.getHistCommandeDistri().ajouter(c); 	
			}
		return cf;
	}
	
	
    //exemple avec une hashmap
	//	for (abstraction.commun.Produit p : Constante.listeProduit) {
	//		if(this.QuantiteDemandeeProduit(listeCommandesDist).get(0).doubleValue() <= lindt.getStocks().get(p).getStock()){
	
}
			

	
