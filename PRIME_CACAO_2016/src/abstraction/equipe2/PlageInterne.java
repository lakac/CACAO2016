package abstraction.equipe2;
import abstraction.commun.*;
import java.util.HashMap;


public class PlageInterne {
	
	private HashMap<Produit,Tarif> tarifproduit;

	public PlageInterne() {
	this.tarifproduit=new HashMap<Produit,Tarif>();
	}

	public HashMap<Produit, Tarif> getTarifproduit(){
		return tarifproduit;
	}

	public void setTarifproduit(Produit produit, Tarif tarif){
		this.add(produit, tarif);
		
	}
	
	public void add(Produit p, Tarif tarif){
		this.tarifproduit.put(p, tarif);
	}
}
