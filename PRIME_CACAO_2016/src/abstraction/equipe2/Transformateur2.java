package abstraction.equipe2;

import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;
import abstraction.fourni.Monde;
import abstraction.fourni.v0.Marche;
import abstraction.commun.*;;

public class Transformateur2 implements Acteur, ITransformateur{
	
	private String nom;
	private Indicateur achats;
	private Indicateur ventes;
	private Indicateur solde;
	
	public Transformateur2(Monde monde) {
		this.nom = Constantes.NOM_TRANSFORMATEUR_1;
		this.achats = new Indicateur("Achats de "+this.nom, this, 0.0);
		this.ventes = new Indicateur("Ventes de "+this.nom, this, 0.0);
		this.solde = new Indicateur("Solde de "+this.nom, this, 10000000.0);
		Monde.LE_MONDE.ajouterIndicateur( this.achats );
		Monde.LE_MONDE.ajouterIndicateur( this.ventes );
		Monde.LE_MONDE.ajouterIndicateur( this.solde );		
	}
	
	public static final Stock stock_cacao=new Stock();
	public static final Stock stock_chocolat=new Stock();
	public static final Banque tresorerie=new Banque();
	

	public static final Commandes commandes = new Commandes();
	

	public String getNom() {
		return "Producteur "+this.nom;
	}
	
	public static double prixDeVente(){
		return 15.0;
	}
	
	
	public void notificationVente(double quantite) {
		this.achats.setValeur(this, quantite);
		this.solde.setValeur( this, this.solde.getValeur()-quantite*Marche.LE_MARCHE.getCours());
	}

	
	//la quantité demandée aux producteurs est proportionnelle 

	
	// Quantité annoncée aux producteurs 
	
	public double annonceQuantiteDemandee(IProducteur p) {
		if(MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_1)==p){
			return Math.min(commandes.quantiteDemandeeP1(0.3), p.annonceQuantiteMiseEnVente(this)) ;
		}
		else{
			if(MondeV1.LE_MONDE.getActeur(Constantes.NOM_PRODUCTEUR_2)==p){
				return Math.min(commandes.quantiteDemandeeP1(0.3), p.annonceQuantiteMiseEnVente(this)) ;
			}
			else{
				return 0.0;
			}
		}
	}

	
	public void notificationVente(IProducteur p) {
		// TODO Auto-generated method stub
		
	}
	
	

	
	//Méthode principale de test de CoutInts, déféaire les "/*" pour l'activer
	public void next() {}
		//setT(qdd);
		/*quantiteDemandee(T, 0.3);
		quantiteDemandee(T, 0.3);
		quantiteDemandee(T, 0.4);
		setS1(T);
		setS2(T);
		stock_cacao(T, S1);
		stock_chocolat(T, S2);
	}*/
	/*    public static void main(String[] args) {
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
		
		
		System.out.println("le bénéfice fait a cet step est de :" + Benefice(T,prixdevente,p) + "€");
		

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
	
	   */

		

}
