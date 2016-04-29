package abstraction.v1;

import abstraction.fourni.Acteur;

public interface IProducteur {
	
	public double qtDisponible();
	public double prixDeVente();
	public void notificationVente(double quantite);
	
	
}
