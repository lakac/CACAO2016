package abstraction.equipe6;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import abstraction.commun.CommandeDistri;
import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;

public class PrixVente {
	private Double prix;
	private ITransformateur transformateur;
	private Produit nomproduit;
	
	public PrixVente(Double prix, Produit nomproduit, ITransformateur transf ){
		this.prix=prix;
		this.nomproduit=nomproduit;
		this.transformateur =transf;
	}

	public Double getPrixVente(){
		return this.prix;
	}
}


	/*public HashMap<ITransformateur,List<CommandeDistri>> setPrix (HashMap<ITransformateur,List<CommandeDistri>> CommandeEffective) {
		HashMap<ITransformateur,List<CommandeDistri>> PrixdeVente = new HashMap<ITransformateur,List<CommandeDistri>> ;
		for (ITransformateur t : transfo) {
		String value = map.get(key);
		    for (CommandeDistri c : commande) {
		   
		prixréf=1.2*(CommandeEffective.getPrixTonne());
			;
		return PrixdeVente;
		
	
	}*/
	

	
	


