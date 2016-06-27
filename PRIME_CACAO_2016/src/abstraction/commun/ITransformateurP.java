package abstraction.commun;

public interface ITransformateurP {
	/** Interface consacrée aux producteurs
	 * @author equipe5
	 */
	
	public double annoncePrix();
	
	/**
	 * Indique la quantité demandée au marché du cacao.
	 * 
	 */
	public double annonceQuantiteDemandee();
	
	/**
	 * Met à jour l'état interne de ce transformateur suite à une vente auprès d'un producteur.
	 * Toutes les informations (producteur, quantité, prix unitaire) sont stockées dans une CommandeProduc.
	 * Cette méthode est appelée par le marché.
	 */
	public void notificationVente(CommandeProduc c);
}
