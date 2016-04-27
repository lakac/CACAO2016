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
	private double[] S1;
	private double[] S2;

	public Transformateur2(String nom, Monde monde) {
		this.nom = nom;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );
		this.T = new double[4];
		this.S1=new double[2];
		this.S2=new double[2];
			T[0]=0;
			T[1]=0;
			T[2]=0;
			T[3]=0;
			S1[0]=0;
			S2[0]=0;
			S1[1]=0;
			S2[1]=0;
		
	}
	
	public double[] getS1() {
		return this.S1;
	}
	
	public double[] getS2() {
		return this.S2;
	}
	
	public double[] getT() {
		return this.T;
	}
	
	//Suivi du stock de cacao au fil des step
	
	public double[] setS1(double[] T){
		S1[1]=S1[0];
		S1[0]=0.6*T[2];
		return S1;
		// A tester, j'y arrive pas
	}
	
	
	//suivi du stock de chocolat au fil des step
	public double[] setS2(double[] T){
		S2[1]=S2[0];
		S2[0]=T[1];
		return S2;
		//A tester, j'y arrive pas
	}
	
	
	//evolution du tableau T au fil des step ( sert d'historique )
	public double[] setT(double qdd) {
		for (int i=1; i<4; i++) {
			T[i-1]=T[i];
		}
		T[3] = qdd;
		return T;
		//A tester, j'y arrive pas
	}
	public String getNom() {
		return "Producteur "+this.nom;
	}

	public void next() {
		this.ventes.setValeur(this, 0.0);
		
	}
	
	public double prixDeVente(){
		return 15.0;
	}
	
	
	//ce code calcule le cout de revient et le cout de revient unitaire de Nestlé France !
	//p en euros, q en kilos
	public static double[] CoutInts (double p, double []T){ 
		double[] CI =new double[2] ;
		CI[0] = 13003370+T[1]*(5+p);
		CI[1] = CI[0]*0.6/T[1];
		// 600g de cacao équivalent à 1kg de chocolat
		return CI;
		//Test OK
	}
	
	// le stosk à l'instant t dépend de la quantité demandé pour l'instant t+2 
	//et de la quantité produite pour l'instant t+1
	public static double stock_cacao (double[] T, double[] S1) {
		double s = S1[1]+S1[0]-0.6*T[1];
		if (s>=0){
			return s;}
		else{
			return -1;
		}
		//Test OK
	}
	
	public static double stock_chocolat (double[] T, double[] S2) {
		double s= S2[1]+S2[0]-T[0];
		return s;
		//Test OK
	}
	
	public void notificationVente(double quantite) {
		this.achats.setValeur(this, quantite);
		this.solde.setValeur( this, this.solde.getValeur()-quantite*Marche.LE_MARCHE.getCours());
	}

	
	//la quantité demandée aux producteurs est proportionnelle 
	//à la quantité de chocolat que nous demande les distributeurs.
	
	public static double quantiteDemandee (double[] T) {
		double qdp = T[3]*0.6;
		return qdp; 
		//Test OK
	}
	
	// Quantitée demandée au producteur 1 
	
	public static double quantiteDemandeeP1 (double[] T, double p){
		double qdp = T[3]*0.6*p;
		return qdp;
	}
	
	// Quantitée demandée au producteur 2
	
	public static double quantiteDemandeeP2 (double[] T, double p){
		double qdp = T[3]*0.6*p;
		return qdp;
	}
	
	// Quantitée demandée au reste du Monde
	
	public static double quantiteDemandeeP3 (double[] T, double p){
		double qdp = T[3]*p;
		return qdp;
	}
	
	
	//Le prix du kilo de chocolat étant fixé, tout ce que l'on peut calculer c'est la marge que l'on se fait.
	public static double Marge (double prixDeVente, double p, double[] T) {
		double M = ((prixDeVente-CoutInts(p,T)[1])/(CoutInts(p,T))[1])*100;
		return M;
		//Test OK
	}
	
	public static double Benefice (double []T, double prixDeVente, double p){
		double s=0;
		s+=T[0]*(Marge(prixDeVente,p,T)*prixDeVente+prixDeVente) ;
		return s;
		//Test OK
	}
	
	//Méthode principale de test de CoutInts, déféaire les "/*" pour l'activer
	
	    public static void main(String[] args) {
		double p = 3;
		double[]T=new double[4];
		double[]S1=new double[2];
		double[]S2=new double[2];
		T[0]=1000000;
		T[1]=-1;
		T[2]=0;
		T[3]=790000;
		S1[0]=500000;
		S1[1]=800000;
		S2[0]=600000;
		S2[1]=400000;
		double prixdevente=15;
		double[] CI = CoutInts(p,T);
		System.out.println("La longueur du tableau CI est de :" + CI.length);
		System.out.println("La longueur du tableau S1 est de :" +S1.length);
		System.out.println("La longueur du tableau S2 est de :" +S2.length);

		System.out.println("le cout de revient de Nestlé France à la période t est de "+CI[0]);
		System.out.println("le cout de revient unitaire de Nestlé France à la période t est de "+CI[1]);
		System.out.println("la marge sur couts directs que Nestlé se fait est de : "+Marge(prixdevente,p,T)+"%");
		
		System.out.println("la quantite de cacao achetee est "+0.6*T[2] +"kg de cacao");
		System.out.println("la quantite de chocolat demandee par les distributeurs est"+ T[3]+"kg de chocolat");
		System.out.println("la quantite de cacao transformee en chocolat à cet step est de "+ 0.6*T[1]+"kg");
		System.out.println("la quantite de chocolat livre est de" +T[0] + "kg");
		
		System.out.println("le stock de cacao est de :" + stock_cacao(T,S1) + "kg");
		System.out.println("le stock de chocolat est de :" + stock_chocolat(T,S2)+"kg");
		System.out.println("le bénéfice fait a cet step est de :" + Benefice(T,prixdevente,p) + "€");
		
		if (stock_cacao(T,S1)<0){
		double qdd = 30000;
		if (stock_cacao(T,S1)<0){

			System.out.println("Erreur dans le système");
		}else{
			System.out.println("Le stock de cacao semble valide");
		}
		
		if (stock_chocolat(T,S2)<0) {
			System.out.println("Erreur dans le système");
		}else{
			System.out.println("Le stock de chocolat semble valide");
		}
	
		
		for(int i=0;i<4;i++){
			if (T[i]<0){
				System.out.println("Erreur dans les valeurs du tableau des demandes");
			}else {
				if (T[i]==0){
					System.out.println("Grève des distributeurs");
				}else{
					System.out.println("Les demandes semblent valides");
				}
			}
		}
		
		//Pour nous permettre de savoir si on a mit le bon prix pour les coûts d'achats du cacao et de vente du chocolat
		
		if (Marge(prixdevente,p,T)<0){
			System.out.println("Vente à perte -> Acheter moins cher ou vendre plus cher");
		}else{
			if (Marge(prixdevente,p,T)<30){
				System.out.println("Les prix d'achats du cacao et les prix de vente du chocolat sont bons ");
			}else{
				System.out.println("On peut baisser les prix de vente du chocolat");
			}
		}
		
		
		
		
		
		
	}
	
	   }

}
