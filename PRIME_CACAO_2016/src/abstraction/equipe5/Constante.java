package abstraction.equipe5;

import abstraction.commun.Produit;
import abstraction.fourni.Monde;

public class Constante {
	 public static int stepCourant() {return Monde.LE_MONDE.getStep();}
	 public static int stepPrecedent() {return Monde.LE_MONDE.getStep()-1;}
	 public static int step2() {return Monde.LE_MONDE.getStep()-2;}
	 public static int step3() {return Monde.LE_MONDE.getStep()-3;}
	 public static final double CHARGES_FIXES_STEP = 900980/24; // salaires+impots
	 public static final double[] RATIO_CACAO_CHOCOLAT = {0.5,0.6,0.7}; // pour 100g de chocolat, il faut par exemple 60g de cacao
	 public static final Produit[] LISTE_PRODUIT = {new Produit("50%",0.5),new Produit("60%",0.6),new Produit("70%",0.7)};
	 public static final double STOCK_MINIMAL_CACAO = 500;
	 public static final double STOCK_MINIMAL_CHOCO = 20;
	 public static final double COUT_TRANSFORMATION = 5000;
	 public static final double[] MARGE_PRODUIT = {0.07, 0.1, 0.12};
	 public static final double COUT_STOCK_TONNE_STEP = 18;
}
