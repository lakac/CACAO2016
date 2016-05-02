package abstraction.equipe5;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueCommande {
	private List<ICommande> hist = new ArrayList<ICommande>();
		
	public HistoriqueCommande() {	
		this.hist = new ArrayList<ICommande>();
		}
	 
	 public ICommande valeur(int i) {
		 return hist.get(i);
	 }
	 
	 public void ajouter(ICommande commande) {
		 hist.add(commande);
	 }
}	 
	 
// ICommande : comment avoir côté distrib et côté produc	 
			
		
