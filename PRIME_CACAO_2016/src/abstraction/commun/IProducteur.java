package abstraction.commun;

public interface IProducteur {
	/**
	 * Indique la quantité disponible à la vente pour le transformateur t.
	 */
	@Deprecated
	public double annonceQuantiteMiseEnVente(ITransformateurP t);

	//Conformement à la reunion du 03/06, cree par l'equipe 2 le 8/06.
	/**
	 * Indique la quantité de cacao totale mise en vente par le IProducteur p à la step considéré. 
	 */
	
	public double annonceQuantiteProposee();
	
	/**
	 * Met à jour l'état interne de ce producteur suite à une vente auprès d'un transformateur.
	 * Toutes les informations (transformateur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * 
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);

}
