package abstraction.equipe5;

import abstraction.fourni.Acteur;

public class Lindt implements Acteur{
	
	Compteur compt = new Compteur();
	Stock stock_cacao = new Stock();
	Stock stock_chocolat = new Stock();
	
	public String getNom() {
		return "Lindt";}

	public void next() {
		/*compt.ajouter(getQuantiteDist());*/
		stock_cacao.ajouterStock(0.6*compt.valeur(2));
		stock_chocolat.ajouterStock(compt.valeur(1));
		stock_cacao.retirerStock(0.6*compt.valeur(1));
		stock_chocolat.retirerStock(compt.valeur(0));
		
		/* Mettre toutes les m�thodes que les autres ont cr��
		getQuantiteDist();
		getPrixDist();
		 
		quantiteSouhaitee(getQuantiteDist());
		
		getPrixProd();
		getQuantiteProd();*/
		
	}
	
	public double quantiteSouhaitee(){
		return(0.6*compt.valeur(3));
	}
	
	public double coutRevient() {
		int charges_fixes = 900980; // salaires+impots
		return charges_fixes + 0.6*compt.valeur(2) * (5000 /*+ ((p1.annoncePrix() + p2.annoncePrix())/2)*/); 	
	}
	
	public double marge(){
		return (15000*compt.valeur(0)-coutRevient());
	}
}	 
	 
	 




