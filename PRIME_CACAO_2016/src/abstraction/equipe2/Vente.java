package abstraction.equipe2;
import java.util.HashMap;
import abstraction.commun.*;
import abstraction.fourni.*;

public class Vente {
	private HashMap<Produit, Double> quantitevendue;
	private Historique historiquedesventesproduit1;
	private Historique historiquedesventesproduit2;
	private Historique historiquedesventesproduit3;
	
	public void add(Produit p) {
		this.quantitevendue.put(p, 0.0);
	}
	
	public Vente() {
		this.quantitevendue = new HashMap<Produit, Double>();
		this.historiquedesventesproduit1 = new Historique();
		this.historiquedesventesproduit2 = new Historique();
		this.historiquedesventesproduit3 = new Historique();
	}
	
	
	public HashMap<Produit, Double> getVentes() {
		return this.quantitevendue;
	}


	public void setquantitevendue(Nestle t, IDistributeur d, Produit p) {
		this.quantitevendue.put(p, Math.min(d.getDemande(t), t.getStockchoc().getStockschocolats().get(p)+t.getProd().getProduction().get(p)));
	}
	
	
	public void MiseAJourHistorique50(Nestle nestle, int etape) {
		this.historiquedesventesproduit1.ajouter(nestle, etape, this.quantitevendue.get(50));
	}
		
	public void MiseAJourHistorique60(Nestle nestle, int etape) {
		this.historiquedesventesproduit2.ajouter(nestle, etape, this.quantitevendue.get(60));
	}
			
	public void MiseAJourHistorique70(Nestle nestle, int etape) {
		this.historiquedesventesproduit3.ajouter(nestle, etape, this.quantitevendue.get(70));
	}
				
	//}
	//Ne connaît pas encore Nestlé
	
	

}
