package abstraction.equipe5;

import abstraction.commun.IProducteur;
import abstraction.commun.ITransformateur;
import abstraction.fourni.Acteur;

public class Lindt implements Acteur, ITransformateur{
	
	private Compteur compt;
	private Stock stock_cacao;
	private Stock stock_chocolat;
	
	public Lindt(){
		this.compt = new Compteur();
		this.stock_cacao = new Stock();
		this.stock_chocolat = new Stock();
	}
	
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
	
	
	
	
	public double coutRevient(double quantite, double prix){
		int charges_fixes;
		charges_fixes=900980; //(salaires+impots)
		return charges_fixes+quantite*(5+prix);		
		
	}
	

	public double marge(double coutRevient, double quantite){
		return (15*quantite-coutRevient);
	}
	
	/**
	 * Indique la quantité demandée au producteur p.
	 */
	public double annonceQuantiteDemandee(IProducteur p){
		return 0.6*compt.valeur(3);
	}

	
	/**
	 * Met à jour l'état interne de ce transformateur
	 * suite à une vente auprès du producteur p.
	 * 
	 * Cette méthode est appelée par les producteurs.
	 */
	public void notificationVente(IProducteur p){
		System.out.println("Met à vous le stock et la tréso");
	}


}	 
	 
	 




