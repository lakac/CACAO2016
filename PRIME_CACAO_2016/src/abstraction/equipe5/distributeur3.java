package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;

public class distributeur3{
	
	//distributeur 3 en interne, on lui vend 75% de notre stock de chocolat
	
	private int quantite;
	private List<CommandeDistri> commande;
	private Lindt lindt;
	
		
	public distributeur3(int quantite, Lindt lindt) {
		this.quantite = quantite;
		this.commande = new ArrayList<CommandeDistri>();
		this.lindt = lindt;
	}
	
	// prendre les commandes finales de leclerc et carrefour et les multiplier par 3 pour obtenir les commandes du 3eme distributeur.
	//Ne pas oublier de les rajouter à l'historique de commande distri!
	
	//for(IDistributeur d: this.getDistributeurs()){ // ajout des commandes finales à notre historique
//		for (CommandeDistri cd : MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(this,d))
	
	
	
	public Lindt getLindt() {
		return lindt;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public List<CommandeDistri>getCommande() {
		return commande;
	}
	public void setCommande(List<CommandeDistri> commande) {
		this.commande = commande;
	}
	
}
