package abstraction.equipe5;

import abstraction.fourni.Acteur;

public class Lindt implements Acteur{
	
	Compteur compt = new Compteur();
	private int stock_cacao;
	
	
	
	public String getNom() {
		return "Lindt";}

	public void next() {
		
		/* Mettre toutes les m�thodes que les autres ont cr��
		getQuantiteDist();
		getPrixDist();
		
		Compteur.ajouter(getQuantiteDist()); 
		quantiteSouhaitee(getQuantiteDist());
		
		getPrixProd();
		getQuantiteProd();*/
		
	}
	
	
	public double quantiteSouhaitee(){
		return(0.6*compt.valeur(3));
	}
	
	public double stock_cacao( double stockInit, double quantiteATransformer){
		return stockInit+quantiteSouhaitee()-quantiteATransformer;
	}
	
	public double stock_chocolat(double stockInit, double quantiteDemandee, double quantiteTransformee){
		return stockInit-quantiteDemandee+quantiteTransformee;
	}
	
	public double coutRevient(double quantite, double prix){
		int charges_fixes;
		charges_fixes=900980; //(salaires+impots)
		return charges_fixes+quantite*(5+prix);		
		
	}
	

	public double marge(double coutRevient, double quantite){
		return (15*quantite-coutRevient);
	}
}	 
	 
	 




