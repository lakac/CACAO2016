package abstraction.commun;

public interface IProducteur {
	/**
	 * Indique la quantité disponible à la vente pour le transformateur t.
	 */
	public double annonceQuantiteMiseEnVente(ITransformateur t);
	
	/**
	 * Indique le prix de vente par tonne.
	 */
	public double annoncePrix();
}