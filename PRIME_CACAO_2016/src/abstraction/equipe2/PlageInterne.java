package abstraction.equipe2;
import abstraction.commun.*;
import java.util.HashMap;
import java.util.List;

public class PlageInterne {
	
	private HashMap<Produit,List<Plage>> tarifproduit;

	public PlageInterne(){
	this.tarifproduit=new HashMap<Produit,List<Plage>>();
	}

	public HashMap<Produit, List<Plage>> getTarifproduit(){
		return tarifproduit;
	}

	public void setTarifproduit(Produit produit, List<Plage> listplage){
		this.add(produit, listplage);
		
	}
	
	public void add(Produit p, List<Plage> listplage){
		this.tarifproduit.put(p, listplage);
	}
	
	
	
}
