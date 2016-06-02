package abstraction.equipe3;


import java.util.ArrayList;

import abstraction.commun.CommandeDistri;


public class Ventes {
	
	/*classe qui répertorie les ventes de chaque produit pour pouvoir connaître environ le nombre de clients 
	 * l'année suivante*/

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
	
	/*methode qui initialise les ventes du static VENTES_ANNEE_ZERO*/
	
	public void initialiseAnneeZero(){
		for (int i = 0; i<VENTES_ANNEE_ZERO.length;i++){
			if (i==5){
				for (int j =0; j<3;j++){
					VENTES_ANNEE_ZERO[i][j]=1469.23;
				}
			} else {
				if (i==25){
					for (int j=0;j<3;j++){
						VENTES_ANNEE_ZERO[i][j]=2469.23;
					}
				} else {
					for (int j=0;j<3;j++){
						VENTES_ANNEE_ZERO[i][j]=669.24;
					}
				}
			}
		}
	}
	
	/*methode qui initialise les Ventes en se basant sur la demande de la v1*/
	
	public void initialiseVentes(){ 
		for (Double[] i : VENTES_ANNEE_ZERO){
			this.ventes.add(i);
		}
	}
	
	/*methode qui rajoute les ventes reelles du step a la variable*/
	
	public void actualiserVentes(ArrayList<CommandeDistri> venteEffective){
		Double[] x = {0.0,0.0,0.0};
		for (CommandeDistri co : venteEffective){
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
	}

}
