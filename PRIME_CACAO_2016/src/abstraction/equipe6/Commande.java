package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.equipe6.Carrefour;

public class Commande {
	private List<Double> commandeparproduit;
	

	public Commande(List<Double> cpp) {
		this.commandeparproduit = cpp;
		
	}
	
	public Commande() {
		this.commandeparproduit = new ArrayList<Double>();
		
	}
	
	public List<Double> getCommandeParProduit() {
		return this.commandeparproduit;
	}
	
	
	
	
	
	public void setCommandeBasique(int step, Carrefour carrefour, List<Indicateur> I) {
			int c = 0;
			for (Double d : this.commandeparproduit) {
				if (step%26 == 6 ) {
					d = carrefour.getDemandeAnnuel()*0.06;
					
				}
				
				else {
					if (step%26 == 25) {
						d = 0.12*carrefour.getDemandeAnnuel();
					}
					else {
						d = 0.03416*carrefour.getDemandeAnnuel();
					}
				}
				d = d*(1+(Math.random()*0.2 - 0.1)); // fluctuation al�atoire de 10% de la commandeparstep
			    if ( d > 0.5*I.get(c).getValeur()){
			    	d=0.75*d;
			    }else{
			    	d=0.5*d;
			    	
			    }
			    c++;
			}
			
	}	
}
