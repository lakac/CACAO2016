package abstraction.equipe3;


import java.util.ArrayList;

import abstraction.commun.CommandeDistri;


public class Ventes {
	
	/*classe qui répertorie les ventes de chaque produit pour pouvoir connaître environ le nombre de clients 
	 * l'année suivante*/

	private ArrayList<Double[]> ventes;
	
	public Ventes(){
		this.ventes = new ArrayList<Double[]>();
	}
	
	public Double[] getVentes(int step){
		return this.ventes.get(step+25);
	}
	
	public void addVentes(Double[] vente){
		this.ventes.add(vente);
	}
	
	/*methode qui initialise les Ventes en se basant sur la demande de la v1*/
	
	public void InitialiseVentes(Double[][] ventes){ 
		//a completer : creer la base de ventes
		for (Double[] i : ventes){
			this.ventes.add(i);
		}
	}
	
	/*methode qui rajoute les ventes reelles du step a la variable*/
	
	public void actualiserVentes(ArrayList<CommandeDistri> livraisonEffective){
		Double[] x = {0.0,0.0,0.0};
		for (CommandeDistri co : livraisonEffective){
			if (co.getProduit().getNomProduit()=="50%"){
				x[0]+=co.getQuantite();
			} else {
				if (co.getProduit().getNomProduit()=="60%"){
					x[1]+=co.getQuantite();
				} else {
					x[2]+=co.getQuantite();
				}
			}
		} this.addVentes(x);
		//a compléter : prendre le nombre de clients pour chaque produit du marche consommateurs et les rajouter aux ventes
	}

}
