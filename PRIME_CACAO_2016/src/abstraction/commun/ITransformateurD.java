  package abstraction.commun;

import java.util.List;

public interface ITransformateurD {
	/**Interface consacrée aux distributeurs
	 * @author equipe5
	 */
	
	/**
	 * Indique le prix d'achat proposé pour ce transformateur. Fluctue donc logiquement autour du cours du marché du cacao.
	 */

	

	public Catalogue getCatalogue();
	
	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list);

	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list);
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	public List<CommandeDistri> offre(List<CommandeDistri> list);
	
	/**
	 * @deprecated
	 */
	public List<CommandeDistri> Offre(List<CommandeDistri> o);
}