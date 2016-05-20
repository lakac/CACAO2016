
package abstraction.commun;

import java.util.List;

public interface IDistributeur {	
	public double getDemande(ITransformateur t);
	// Rajouter une variable delais de livraisons
	public double getPrix();
	
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste);
}
