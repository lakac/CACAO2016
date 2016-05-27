  package abstraction.commun;

import java.util.List;

public interface ITransformateur {
	/**
	 * Indique la quantité demandée au marché du cacao.
	 * 
	 */
	public double annonceQuantiteDemandee();
	/**
	 * @deprecated
	 * Indique la quantité demandée au marché du cacao.
	 */
	
	public double annonceQuantiteDemandee(IProducteur p);
	/**
	 * Indique le prix d'achat proposé pour ce transformateur. Fluctue donc logiquement autour du cours du marché du cacao.
	 */
	
	public double annoncePrix();
	
	public Catalogue getCatalogue();
	

	
	/**
	
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * Toutes les informations (producteur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);




	/**
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * Toutes les informations (producteur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * @deprecated
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(IProducteur p);



	public List<CommandeDistri> CommandeFinale(List<CommandeDistri> list);


	public List<CommandeDistri> livraisonEffective(List<CommandeDistri> list);

	public List<CommandeDistri> offre(List<CommandeDistri> o);
	/**
	 * @deprecated
	 * @param list
	 * @return
	 */
	public List<CommandeDistri> Offre(List<CommandeDistri> list);

}

