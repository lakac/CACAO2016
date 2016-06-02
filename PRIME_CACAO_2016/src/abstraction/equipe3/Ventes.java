package abstraction.equipe3;


import java.util.ArrayList;

import abstraction.commun.CommandeDistri;


public class Ventes {
	
	/*classe qui r�pertorie les ventes de chaque produit pour pouvoir conna�tre environ le nombre de clients 
	 * l'ann�e suivante*/

	private ArrayList<Double[]> ventes;
	private static Double[][] VENTES_ANNEE_ZERO = new Double[26][3];
	
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
	
	public void initialiseVentes(){ 
		for (int i=0; i<VENTES_ANNEE_ZERO.length; i++) {
			for (int j=0; j<3; j++) {
				if (i==5) {
					VENTES_ANNEE_ZERO[i][j] = 3673.08;
				}
				else {
					if (i==22) {
						VENTES_ANNEE_ZERO[i][j] = 6173.08;
					}
					else {
						VENTES_ANNEE_ZERO[i][j] = 1673.08;
					}
				}
			}
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
		//a compl�ter : prendre le nombre de clients pour chaque produit du marche consommateurs et les rajouter aux ventes
	}

}
