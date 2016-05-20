package abstraction.commun;
import java.util.HashMap;
import java.util.List;

public interface IDistributeur {
	
	public List<CommandeDistri> demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> contreDemande (List<CommandeDistri> cd);

	public List<CommandeDistri> commandeFinale(List<CommandeDistri> list);
	
}
