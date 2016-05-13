package abstraction.equipe6;

import java.util.ArrayList;
import java.util.List;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.equipe6.Carrefour;

public class Commande {
	private List<Double> commandeparproduit;
	private List<Indicateur> historiquedescommandes;

	public Commande(List<Double> cpp, List<Indicateur> hdc) {
		this.commandeparproduit = cpp;
		this.historiquedescommandes = hdc;
	}
	
	public Commande() {
		this.commandeparproduit = new ArrayList<Double>();
		this.historiquedescommandes = new ArrayList<Indicateur>();
	}
	
	public List<Double> getCommandeParProduit() {
		return this.commandeparproduit;
	}
	
	public List<Indicateur> getHistoriquedesCommandes() {
		return this.historiquedescommandes;
	}
	
	public void setCommandeBasique(int step, Carrefour carrefour) {
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
				d = d*(1+(Math.random()*0.2 - 0.1)); // fluctuation aléatoire de 10% de la commandeparstep
			}
	}	
}
