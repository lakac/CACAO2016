package abstraction.commun;

import java.util.HashMap;
import java.util.List;

import abstraction.fourni.Acteur;

public interface IDistributeur extends Acteur {
		
	public List<CommandeDistri> Demande (ITransformateur t, Catalogue c);
	
	public List<CommandeDistri> LivraisonEffective(List<CommandeDistri> liste);

	public List<CommandeDistri> Demande (HashMap<ITransformateur, Catalogue > d);
	
	public List<CommandeDistri> ContreDemande (List<CommandeDistri> cd);

	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> cf);

	public Double getStock(Produit p);
	public Double getPrixVente(Produit p);
		
}
