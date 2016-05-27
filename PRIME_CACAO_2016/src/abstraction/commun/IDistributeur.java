package abstraction.commun;

import java.util.HashMap;
import java.util.List;

public interface IDistributeur implements acteur {
		
	public List<CommandeDistri> Demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste);

	public List<CommandeDistri> Demande (HashMap<ITransformateur, Catalogue > d);
	
	public List<CommandeDistri> ContreDemande (List<CommandeDistri> cd);

	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf);

	public Double getStock(Produit p);
	public Double getPrix(Produit p);
		
}
