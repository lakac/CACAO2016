package abstraction.equipe5;

import abstraction.fourni.Acteur;

public class Lindt implements Acteur{


	public String getNom() {
		return "Lindt";}

	public void next() {
		/* Mettre toutes les méthodes que les autres ont créé
		getQuantiteDist();
		getPrixDist();
		
		quantiteSouhaitee(getQuantiteDist());
		
		getPrixProd();
		getQuantiteProd();*/
		
	}
	
	public double quantiteSouhaitee(int quantite){
		return(0.6*quantite);
	}
	
	
	public double stock_cacao( double stockInit, double quantiteSouhaitee, double quantiteATransformer){
		return stockInit+quantiteSouhaitee-quantiteATransformer;
	}
	
	public double stock_chocolat(double stockInit, double quantiteDemandee, double quantiteTransformee){
		return stockInit-quantiteDemandee+quantiteTransformee;
	}
	
	public double coutRevient(){
		return 0.0;
	}
	
	public double marge(double coutRevient){
		return (15-coutRevient);
	}
	
}

