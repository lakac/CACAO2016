package abstraction.equipe2;

import abstraction.commun.ITransformateur;
import abstraction.commun.IProducteur;
import abstraction.commun.IDistributeur;
import abstraction.commun.MondeV1;
import abstraction.commun.*;

public class Banque {

	private double tresorerie;

	public double getTresorerie() {
		return tresorerie;
	}
	
	public Banque(){
		tresorerie=(double)1000000;
	}
	
	//ce code calcule le cout de revient et le cout de revient unitaire de Nestlé France !
	//p en euros, q en kilos
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
		if (Nestle.commandes.getCommandes()[1] != 0) {;			//Prix d'achat au monde :3000€ la tonne
			CI[1] = CI[0]*0.6/Nestle.commandes.getCommandes()[1];
		// 600g de cacao équivalent à 1kg de chocolat
		return CI;
		//Test OK
		}
		else {
			CI[1] = 0;
			return CI;
		}
	}
	
	
	public double Tresorerie (IProducteur p1,IProducteur p2){
		this.tresorerie = this.tresorerie+ resultatSurUneStep(p1,p2);
		return tresorerie;
	}
	
	//Le prix du kilo de chocolat étant fixé, tout ce que l'on peut calculer c'est la marge que l'on se fait.
	public static double Marge (IProducteur p1,IProducteur p2) {
		if (CoutIntermediaires(p1,p2)[1] != 0) {
		double M = ((Nestle.prixDeVente()
				-CoutIntermediaires(p1,p2)[1]
				/(CoutIntermediaires(p1,p2)[1])*100));
		return M;
		}
		else {
			return 0;
		}
		//Test OK
	}
	
	public static double resultatSurUneStep ( IProducteur p1, IProducteur p2){
		double s=0;
		s=Nestle.commandes.getCommandes()[0]*(Marge(p1,p2)*Nestle.prixDeVente()) 
				+Nestle.commandes.getCommandes()[0]*(Marge(p1,p2)*Nestle.prixDeVente());
		return s;
		//Test OK
	}
	
	public static void main(String[] args) {
	}
	
}
