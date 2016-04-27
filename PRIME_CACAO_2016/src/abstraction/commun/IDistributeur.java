package abstraction.commun;

public interface IDistributeur {
	
	public double getDemande(ITransformateur t, int step);
	
	public double getPrix();
	
}
