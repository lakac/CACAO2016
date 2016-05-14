package abstraction.commun;

public interface ITransformateur {
	// Méthodes obsolètes --- à supprimer une fois que tout le monde sera passé sur la nouvelle structure d'échanges
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p);
	// Rajouter une annonce de prix Transformateur
	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	public void notificationVente(IProducteur p); 
	
	// Méthodes actuelles --- celles-ci doivent absolument être implémentées
	/**
	 * Indique la quantité demandée au marché du cacao.
	 */
	public double annonceQuantiteDemandee();
	
	/**
	 * Indique le prix d'achat proposé pour ce transformateur. Fluctue donc logiquement autour du cours du marché du cacao.
	 */
	public double annoncePrix();
	
	/**
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * Toutes les informations (producteur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * 
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);
}
