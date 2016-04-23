package abstraction.equipe5;

import abstraction.fourni.Acteur;

public class Lindt implements Acteur{
	
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
	
	public double quantiteSouhaitee(double quantite){
		return(0.6*quantite);
	}
	
/* si qqu sait comment je peux avoir accès aux valeurs de la liste compt de la classe 
 * Compteur dans la classe Lindt pour pouvoir faire un truc du genre pour
 * quantiteSouhaitee return (0.6*compt[0])   
 * Parce que du coup je sais qu'il fallait faire un compteur parce que le prof a dit de 
 * le faire, mais là j'ai du mal à voir l'utilité …       - Joaly
 */
	
	public double stock_cacao( double stockInit, double quantiteSouhaitee, double quantiteATransformer){
		return stockInit+quantiteSouhaitee-quantiteATransformer;
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
	 
	 




