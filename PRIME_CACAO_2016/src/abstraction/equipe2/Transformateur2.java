package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;

public class Transformateur2 implements Acteur, ITransformateur2, IVendeur2{
	
	private String nom;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	private double[] T;

	public Transformateur2(String nom, Monde monde) {
		this.nom = nom;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
		this.T = new double[4];
			T[0]=0;
			T[1]=0;
			T[2]=0;
			T[3]=0;
	}
	
	public double[] getT() {
		return this.T;
	}
	
	public double[] setT(double qdd) {
		for (int i=1; i<4; i++) {
			T[i-1]=T[i];
		}
		T[3] = qdd;
		return T;
	}
	public String getNom() {
		return "Producteur "+this.nom;
	}

	public void next() {
		this.ventes.setValeur(this, 0.0);
	}
	
	
	//ce code calcule le cout de revient et le cout de revient unitaire de Nestlé France !
	//p en euros, q en kilos
	public static double[] CoutInts (double p, double []T){ 
		double[] CI =new double[2] ;
		CI[0] = 13003370+T[1]*(5+p);
		CI[1] = CI[0]*0.6/T[1]; // 600g de cacao équivalent à 1kg de chocolat
		return CI;
	}
	
	// le stosk à l'instant t dépend de la quantité demandé pour l'instant t+2 
	//et de la quantité produite pour l'instant t+1
	public static double stock_cacao (int[] T) {
		double s = (int) 0.6*T[2];
		return s;
	}
	
	public void notificationVente(double quantite) {
		this.achats.setValeur(this, quantite);
		this.solde.setValeur( this, this.solde.getValeur()-quantite*Marche.LE_MARCHE.getCours());
	}

	
	//la quantité demandée aux producteurs est proportionnelle 
	//à la quantité de chocolat que nous demande les distributeurs.
	public static double quantiteDemandee (double[] T) {
		double qdp = 0.6*T[3];
		return qdp;  
	}
	
	//Le prix du kilo de chocolat étant fixé, tout ce que l'on peut calculer c'est la marge que l'on se fait.
	public static double Marge (double prixDeVente, double p, double[] T) {
		double M = ((prixDeVente-CoutInts(p,T)[1])/(CoutInts(p,T))[1])*100;
		return M;
	}
	
	public static double Benefice (double []T, double prixDeVente, double p){
		double s=0;
		s+=T[0]*Marge(prixDeVente,p,T);
		return s;
	}
	
	//Méthode principale de test de CoutInts, déféaire les "/*" pour l'activer
	/*
	    public static void main(String[] args) {
		int p = 3;
		int q = 1153000;
		double[] CI = CoutInts(p,q);
		System.out.println(CI.length);
		System.out.println("le cout de revient de Nestlé France à la période t est de "+CI[0]);
		System.out.println("le cout de revient unitaire de Nestlé France à la période t est de "+CI[1]);
		
		System.out.println("la marge sur couts directs que Nestlé se fait est de : "+Marge(CI[1])+"%");
		
		double s0 = 300.6;
		int qd = 100;
		int qp = 200;
		
		double qdd = 30000;
		System.out.println("la quantité demandée est de "+quantiteDemandee(qdd));
		
	}
	*/
	

}
