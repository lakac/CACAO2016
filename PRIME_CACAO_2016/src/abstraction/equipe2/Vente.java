package abstraction.equipe2;
import java.util.HashMap;
import abstraction.commun.*;
import abstraction.fourni.*;

public class Vente {
	private HashMap<Produit, Double> quantitevendue;
	private Historique historiquedesventes50;
	private Historique historiquedesventes60;
	private Historique historiquedesventes70;
	
	public void add(Produit p) {
		this.quantitevendue.put(p, 0.0);
	}
	
	public Vente() {
		this.quantitevendue = new HashMap<Produit, Double>();
		this.historiquedesventes50 = new Historique();
		this.historiquedesventes60 = new Historique();
		this.historiquedesventes70 = new Historique();
	}
	
	
	public HashMap<Produit, Double> getVentes() {
		return this.quantitevendue;
	}


	public void setquantitevendue(Nestle t, IDistributeur d, Produit p) {
		this.quantitevendue.put(p, Math.min(d.getDemande(t), t.getStockchocolats().getStockschocolats().get(p)+t.getProd().getProduction().get(p)));
	}
	
	
	public void MiseAJourHistorique50(int etape) {
		this.historiquedesventes50.ajouter(Nestle, etape, this.quantitevendue.get(50%));
	}
		
	public void MiseAJourHistorique60(int etape) {
		this.historiquedesventes60.ajouter(Nestle, etape, this.quantitevendue.get(60%));
	}
			
	public void MiseAJourHistorique70(int etape) {
		this.historiquedesventes70.ajouter(Nestle, etape, this.quantitevendue.get(70%));
	}
				
	//}
	//Ne connaît pas encore Nestlé
	
	

}
