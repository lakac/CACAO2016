package abstraction.equipe2;
import abstraction.commun.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlageInterne {
	
	private HashMap<Produit,Tarif> tarifproduit;
	private List<Plage> plageinterne;

	public PlageInterne(List<Plage> plageinterne) {
	Plage plage1 = new Plage(0,500,0.);
	Plage plage2 = new Plage(500.1,1000,0.04);
	Plage plage3 = new Plage(1000.1,2000,0.07);
	List<Plage> plageint = new ArrayList();
	plageint.add(plage1);
	plageint.add(plage2);
	plageint.add(plage3);
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
