package abstraction.commun;

public interface IProducteur {
	/**
	 * Indique la quantité disponible à la vente sur le marché.
	 */
	public double annonceQuantiteMiseEnVente();

	
	/**
	 * Met à jour l'état interne de ce producteur suite à une vente auprès du marché.
	 * Toutes les informations (quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * 
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);

}
