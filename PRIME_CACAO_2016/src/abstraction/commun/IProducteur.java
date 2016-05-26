package abstraction.commun;

public interface IProducteur {
	/**
	 * Indique la quantité disponible à la vente pour le transformateur t.
	 */
	public double annonceQuantiteMiseEnVente(ITransformateur t);

	
	/**
	 * Met à jour l'état interne de ce producteur suite à une vente auprès d'un transformateur.
	 * Toutes les informations (transformateur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * 
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);
	
	/**
	 * Indique le prix de vente par tonne.
	 * 
	 * @deprecated Le prix est désormais choisi par les transformateurs.
	 */
	public double annoncePrix();
}
