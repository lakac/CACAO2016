package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.IDistributeur;

public class distributeur3{
	
	//distributeur 3 en interne, on lui vend 75% de notre stock de chocolat
	
	private int quantite;
	private List<CommandeDistri> commande;
	private Lindt lindt;
	private VenteDist venteDist;
	
		
	public distributeur3(int quantite, Lindt lindt, VenteDist venteDist) {
		this.quantite = quantite;
		this.commande = new ArrayList<CommandeDistri>();
		this.lindt = lindt;
		this.venteDist=venteDist;
	}
	
	// prendre les commandes finales de leclerc et carrefour et les multiplier par 3 pour obtenir les commandes du 3eme distributeur.
	//Ne pas oublier de les rajouter à l'historique de commande distri!
	
	public List<CommandeDistri> commandesDistributeurRestant(){
		List<CommandeDistri> commandesResteDuMonde= new ArrayList<CommandeDistri>();
		int i=0;
		double ratioCommandeResteDuMonde= 3;
		for(IDistributeur d: lindt.getDistributeurs()){ 
			for (CommandeDistri cd : MarcheDistributeur.LE_MARCHE_DISTRIBUTEUR.obtenirCommandeFinale(lindt,d)){// commandes finales de leclerc et carrefour
				commandesResteDuMonde.add(cd);
				commandesResteDuMonde.get(i).setAcheteur(distributeur3);
				//il faut créer le distributeur restant (reste du monde) dans l'interface IDistributeur
				double qteCommandeResteDuMonde=ratioCommandeResteDuMonde*cd.getQuantite();//quantité que le distributeur restant doit commander
				commandesResteDuMonde.get(i).setQuantite(qteCommandeResteDuMonde); //mise à jour de la bonne quantité dans la commande
				i++;
			}
		}
		///venteDist.QuantiteDemandeeProduit(commandesResteDuMonde); //Possibilité de l'utilisé pour regrouper toutes les commandes en 1 commande par produit
		return commandesResteDuMonde;
	}
	
	
	
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
