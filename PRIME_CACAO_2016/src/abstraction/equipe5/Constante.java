package abstraction.equipe5;

import abstraction.commun.Produit;
import abstraction.fourni.Monde;

public class Constante {
	 public static int stepCourant() {return Monde.LE_MONDE.getStep();}
	 public static int stepSuivant() {return Monde.LE_MONDE.getStep()+1;}
	 public static int step2() {return Monde.LE_MONDE.getStep()+2;}
	 public static int step3() {return Monde.LE_MONDE.getStep()+3;}
	 public static final double CHARGES_FIXES_STEP = 900980/24; // salaires+impots
	 public static final double[] RATIO_CACAO_CHOCOLAT = {0.5,0.6,0.7}; // pour 100g de chocolat, il faut par exemple 60g de cacao pour un chocolat à 60%
	 public static final Produit[] LISTE_PRODUIT = {new Produit("50%",0.5),new Produit("60%",0.6),new Produit("70%",0.7)};
	 public static final double STOCK_MINIMAL_CACAO = 50;
	 public static final double STOCK_MINIMAL_CHOCO = 20;
	 public static final double COUT_TRANSFORMATION = 5000;
	 public static final double[] MARGE_PRODUIT = {0.07, 0.12, 0.10};
	 public static final double COUT_STOCK_TONNE_STEP = 18;
	 public static double perteCacao() {return (20+10*Math.random())/100;}; // on perd entre 20 et 30% de cacao a chaque livraison de cacao 
	 public static final double STOCK_MAXIMAL_CACAO = 20000; //enleve car solde trop rapidement a Infinity
}
