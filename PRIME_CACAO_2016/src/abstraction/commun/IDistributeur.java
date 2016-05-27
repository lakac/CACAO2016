 package abstraction.commun;
import java.util.List;

public interface IDistributeur {	
	
	public List<CommandeDistri> demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> contreDemande (List<CommandeDistri> cd);


	/**
	 * 
	 * @return
	 */
	public double getPrix();
	
	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> liste);
	

}
