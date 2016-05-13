package abstraction.commun;

import java.util.List;

public interface ITransformateur {
	/**
	 * @deprecated
	 * Indique la quantité demandée au producteur p.
	 */

	public double annonceQuantiteDemandee(IProducteur p);
	public CommandeProduc annonceQuantiteDemandeeV2(IProducteur p);
	// Rajouter une annonce de prix Transformateur
	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	
	
	public void notificationVente(IProducteur p); 
	
	
	
	public Catalogue getCatalogue();
	
	public List<CommandeDistri> Offre (List<CommandeDistri> o);
	
	
}
