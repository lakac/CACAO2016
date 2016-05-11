package abstraction.equipe5;

import abstraction.commun.Produit;

public class Constante {
	 public static final int STEP_COURANT = 3;
	 public static final int STEP_PRECEDENT = 2;
	 public static final int STEP_2 = 1;
	 public static final int STEP_3 = 0;
	 public static final double[] RATIO_CACAO_CHOCOLAT = {0.6,0.7,0.8}; // pour 100g de chocolat, il faut par exemple 60g de cacao
	 public static final Produit[] listeProduit = {new Produit("60%"),new Produit("70%"),new Produit("80%")};
}
