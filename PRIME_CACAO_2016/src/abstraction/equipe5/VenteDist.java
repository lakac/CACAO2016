package abstraction.equipe5;
import abstraction.equipe5.Lindt;
import abstraction.fourni.Monde;
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

	/**
	 *  creation d'une fonction qui renvoie le prix d'un produit
	 *  @param produit
	 */ 
	public double prixProduit(Produit p) {
		double r = 0;
		for (int i=0; i<Constante.LISTE_PRODUIT.length; i++) {
			if (p.equals(Constante.LISTE_PRODUIT[i])) {
				r= this.getTreso().coutRevient() + Constante.MARGE_PRODUIT[i];
			}
		}
		return r;
	}
	
	/**
	  *creation d'une fonction qui calcule la quantité totale demandée 
	  *par les 3 distrib pour chacun des produits (dans l'ordre 50%,60%,70%)
	  *@param listeCommandesDist
	  */
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

	/**
	 * Creation d'une fonction qui calcule la quantité de chocolat à mettre dans chaque commande
	 * @param listeCommandesDist
	 */
	
	//Cette fonction ne prend pas en compte le fait qu'on pourrait avoir un stock plus important au step n+3 grâce à la transformation
	public List<CommandeDistri> offre(List<CommandeDistri> listeCommandesDist){
		
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
		}return listeCommandesDist; 
	}
	
	


	/**
	 * Fonction qui met à jour l'historique ie qui va changer dans l'historique de commande 
	 * distri la quantité des commandes si ce n'est pas la meme que dans commande finale,
	 *  c'est à dire qu'on livre moins que prévu, et qui va enlever les commandes livrées 
	 *  de l'historique HistCommandeDistri pour les mettre dans l'historique CommandeDistriLivree
 	 */
	public void MiseAJourHistCommandeDistri (){
		List<CommandeDistri> Commandeslivrees = new ArrayList<CommandeDistri>();
		for (CommandeDistri c: lindt.getHistCommandeDistri().getHist()){
			if(c.getStepLivraison()==Monde.LE_MONDE.getStep()){
				Commandeslivrees.add(c);
			}		
		}
		offre(Commandeslivrees);
		for (CommandeDistri c: Commandeslivrees){
			lindt.getCommandeDistriLivree().ajouter(c);
			lindt.getHistCommandeDistri().supprimer(c); 	
		}	
	}
	
	/**
	 *fonction que l'intermédiaire va appeler pour savoir les commandes que l'on livre réellement
	 *@param liste des livraisons
	 */
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> livraison){
			return offre (livraison);	
	}
	
}
			

	
