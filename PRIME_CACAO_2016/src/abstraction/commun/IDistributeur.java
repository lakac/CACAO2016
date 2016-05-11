
package abstraction.commun;

public interface IDistributeur {	
	public double getDemande(ITransformateur t);
	
	public double getPrixAchat();

	// Rajouter une variable delais de livraisons
	public double getPrix();

}
