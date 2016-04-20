package abstraction.Equipe5;

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
	
	public double marge(){
		return 0.0;
	}
	
	public double stock_cacao(){
		return 0.0;
	}
	
	public double stock_chocolat(){
		return 0.0;
	}
	
	public double coutRevient(){
		return 0.0;
	}
	
}

