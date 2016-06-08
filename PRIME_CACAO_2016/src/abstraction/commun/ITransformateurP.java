package abstraction.commun;

public interface ITransformateurP {
	/** Interface consacrée aux producteurs
	 * @author equipe5
	 */
	
	
	/**
	 * Indique la quantité demandée au marché du cacao.
	 * 
	 */
	public double annonceQuantiteDemandee();

	
	/**
	 * Indique la quantité demandée au producteur p.
	 * 
	 * @deprecated Remplacé par {@link #annonceQuantiteDemandee()}.
	 */
	public double annonceQuantiteDemandee(IProducteur p);
	
	
	/**
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * Toutes les informations (producteur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);
	
	
	/**
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * 
	 * @deprecated Remplacé par {@link #notificationVente(CommandeProduc)}.
	 */
	public void notificationVente(IProducteur p);


}
