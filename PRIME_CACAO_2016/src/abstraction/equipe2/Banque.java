package abstraction.equipe2;

import abstraction.commun.ITransformateur;

public class Banque {

	private double tresorerie;

	public double getTresorerie() {
		return tresorerie;
	}

	public void setTresorerie(double tresorerie) {
		this.tresorerie = tresorerie;
	}
	
	public Banque(){
		tresorerie=(double)0;
	}
	
	//ce code calcule le cout de revient et le cout de revient unitaire de Nestlé France !
	//p en euros, q en kilos
	public static double[] CoutIntermediaires (double prixDachatCacao){ 
		double[] CI =new double[2] ;
		CI[0] = 13003370+Transformateur2.commandes.getCommandes()[1]*(5+prixDachatCacao);
		CI[1] = CI[0]*0.6/Transformateur2.commandes.getCommandes()[1];
		// 600g de cacao équivalent à 1kg de chocolat
		return CI;
		//Test OK
	}
	
	
	public double Tresorerie (){
		this.getTresorerie()+=resultatSurUneStep();
	}
	
	//Le prix du kilo de chocolat étant fixé, tout ce que l'on peut calculer c'est la marge que l'on se fait.
	public static double Marge (abstraction.commun.IProducteur p) {
		double M = ((Transformateur2.prixDeVente()-CoutIntermediaires(p.annoncePrix())[1]/(CoutIntermediaires(p.annoncePrix())[1])*100));
		return M;
		//Test OK
	}
	
	public static double resultatSurUneStep ( abstraction.commun.IProducteur p){
		double s=0;
		s=Transformateur2.commandes.getCommandes()[0]*(Marge(p)*Transformateur2.prixDeVente()) ;
		return s;
		//Test OK
	}
	
}
