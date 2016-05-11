package abstraction.equipe3;

import java.util.ArrayList;

import abstraction.commun.Produit;

public class Commande {
	
	private ArrayList<Double> commande;

	public Commande() {
		this.commande = new ArrayList<Double>();
		// TODO Auto-generated constructor stub
	}
	
	public double getCommande(Produit p){
		if (p.getNomProduit()=="50%"){
			return this.commande.get(0);
		}
		else{
			if (p.getNomProduit()=="60%"){
				return this.commande.get(1);
			}
			else{
				return this.commande.get(2);
			}
		}
	}
	
	public void setCommande(ArrayList<Double> commande){
		this.commande=commande;		
	}

}
