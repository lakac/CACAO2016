package abstraction.equipe5;
import abstraction.equipe5.Lindt;
import abstraction.fourni.Monde;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.Commande;
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
		System.out.println("cout de revient "+this.getTreso().coutRevient());
		for (int i=0; i<Constante.LISTE_PRODUIT.length; i++) {
			if (p.equals(Constante.LISTE_PRODUIT[i])) {
				r= this.getTreso().coutRevient() / (1 - Constante.MARGE_PRODUIT[i]); // formule pour avoir le prix de vente quand on veut une marge spécifique
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
	// On considère que 25% de notre stock de chocolat est pour Leclerc+Carrefour et 75% pour un 3eme distributeur
	public List<CommandeDistri> offre(List<CommandeDistri> listeCommandesDist){
		
		for(int i=0; i<lindt.getDistributeurs().size(); i++){
			
			double stockChocolatI=0.25*(lindt.getStocksChocolat().get(i).getStock()-Constante.STOCK_MINIMAL_CHOCO); //stock de chocolat i disponible pour Leclerc+Carrefour (25%), on se reserve un stock minimal
			double QteDemandeeChocolatI=this.QuantiteDemandeeProduit(listeCommandesDist).get(i).doubleValue();// quantite totale de chocolat i demandée par les 3 dist
			
			if(QteDemandeeChocolatI <= stockChocolatI){ //ok on peut fournir aux distrib la quantité de chocolats i qu'ils demandent donc on valide les commandes
				
				 for(CommandeDistri c : listeCommandesDist){
					 if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){ 
						 c.setValidation(true);}}} //on valide les commandes de produit i puisqu'on a assez de chocolats i
			else{
				double quantiteRepartie=lindt.getStocksChocolat().get(i).getStock()/(lindt.getDistributeurs().size()); //Répartition équitable, donc si 3 dist, on divise la quantité totale par 3)
				
				for (CommandeDistri c : listeCommandesDist){
					if(c.getProduit().getNomProduit()==Constante.LISTE_PRODUIT[i].getNomProduit()){// si les commandes concernent le chocolat I
						if(c.getValidation()==true){ // si les commandes avaient déjà été validées on ne les modifie pas et on retire fictivement du chocolat des stocks 
							stockChocolatI=stockChocolatI-c.getQuantite();
						}
						else{
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
									c.setValidation(true);
									}
								}
							}
						}
					}
				}
			}return listeCommandesDist; 
	}
	
	


	/**
	 * Fonction qui met à jour l'historique ie qui va changer dans l'historique de commande 
	 * distri la quantité des commandes si ce n'est pas la meme que dans commande finale,
	 *  c'est à dire qu'on livre moins que prévu, et qui va enlever les commandes livrées 
	 *  de l'historique HistCommandeDistri pour les mettre dans l'historique CommandeDistriLivree
 	 */
	public void MiseAJourHistCommandeDistri (){//XXX
		List<CommandeDistri> Commandeslivrees = new ArrayList<CommandeDistri>();
		for (Commande c: lindt.getHistCommandeDistri().getHist()){
			if(((CommandeDistri)c).getStepLivraison()==Monde.LE_MONDE.getStep() && !((CommandeDistri)c).getAcheteur().getNom().equals("Ditributeur restant de Lindt")){
				Commandeslivrees.add((CommandeDistri)c);
			}		
		}
		offre(Commandeslivrees);
		for (CommandeDistri c: Commandeslivrees){
			lindt.getCommandeDistriLivree().ajouter(c);
			lindt.getHistCommandeDistri().supprimer(c); 	
		}	
		for (Commande c: lindt.getHistCommandeDistri().getHist()){
			if(((CommandeDistri)c).getStepLivraison()==Monde.LE_MONDE.getStep()){ //Les commandes restantes dans HistCommandeDistri au step courant sont celles du distributeur restant que l'on doit mettre dans CommandeDistriLivree
				lindt.getCommandeDistriLivree().ajouter(c);
				lindt.getHistCommandeDistri().supprimer(c); 	
			}
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
			

	
