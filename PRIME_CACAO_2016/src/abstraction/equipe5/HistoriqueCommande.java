package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;
import abstraction.equipe5.Commande;

public class HistoriqueCommande {
	private List<Commande> hist = new ArrayList<Commande>();
		
	public HistoriqueCommande() {	
		this.hist = new ArrayList<Commande>();
		}
	 
	 public Commande valeur(int i) {
		 return hist.get(i);
	 }
	 
	 public void ajouter(Commande commande) {
		 hist.add(commande);
	 }
}	 
	 
	 
			
		
