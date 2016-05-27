 package abstraction.commun;
import java.util.HashMap;
import java.util.List;
import java.util.List;

public interface IDistributeur {	
	
	public List<CommandeDistri> demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> contreDemande (List<CommandeDistri> nouvelle, List<CommandeDistri> ancienne);


	/**
	 * @deprecated
	 * @return
	 */
	public HashMap<Produit, Double> getPrix();
	
}
