package abstraction.equipe2;

import java.util.HashMap;

import abstraction.commun.IProducteur;

public class Banque {
	
	public static final double CHARGES_FIXES=13003370;
	public static final double RATIO_TRANSFORMATION=0.6;
	public static final double TRESORERIE_INITIALE=300000;
	private double banque;
	private HashMap<IProducteur,Double> prixdevente;
	
	public Banque(double banque){
	this.banque=banque;
	this.prixdevente=new HashMap<IProducteur, Double>();
	}
	
	public void addPrixdevente(IProducteur p, Double prix) {
		this.prixdevente.put(p,prix);
	}
	
	public HashMap<IProducteur, Double> getPrixdevente() {
		return prixdevente;
	}	
	
	public double getBanque() {
		return banque;
	}

	public void setBanque(double banque) {
		this.banque = TRESORERIE_INITIALE;
	}
	
	public static double CoutIntermediaireUnitaire (IProducteur p){
		return CHARGES_FIXES + 
	}
	

	public static double[] CoutIntermediaires (IProducteur p1, IProducteur p2){ 
		double[] CI =new double[2] ;
		CI[0] = 13003370+Nestle.commandes.quantiteDemandee(0.3)*(5+p1.annoncePrix())
						+Nestle.commandes.quantiteDemandee(0.3)*(5+p2.annoncePrix())
						+Nestle.commandes.quantiteDemandeeMonde(0.4)*(5+3000);			//Prix d'achat au monde :3000€ la tonne
						;			//Prix d'achat au monde :3000€ la tonne
		CI[1] = CI[0]*0.6/Nestle.commandes.getCommandes()[1];
			CI[0] = 13003370+Nestle.commandes.quantiteDemandee(0.3)*(5+p1.annoncePrix())
						+Nestle.commandes.quantiteDemandee(0.3)*(5+p2.annoncePrix());
						//+Transformateur2.commandes.quantiteDemandeeMonde(0.4)*(5+3000)

}
