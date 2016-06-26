
package abstraction.commun;



import java.util.List;


import abstraction.fourni.Acteur;


public interface IDistributeur extends Acteur {
		
	
	public Double getStock(Produit p, ITransformateurD t);	

	public List<CommandeDistri> demande (ITransformateurD t, Catalogue c);



	public List<CommandeDistri> contreDemande (List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne);

	
	public Double getPrixVente(Produit p, ITransformateurD t);
		

}

