
package abstraction.commun;



import java.util.List;

import abstraction.fourni.Acteur;

public interface IDistributeur extends Acteur {
		
	public List<CommandeDistri> Demande (ITransformateur t, Catalogue c);

	public List<CommandeDistri> ContreDemande (List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne);
	
	public Double getStock(Produit p);	
	
	public Double getPrixVente(Produit p);
		
}
