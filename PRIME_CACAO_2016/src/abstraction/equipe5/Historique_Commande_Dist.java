package abstraction.equipe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Historique_Commande_Dist {
	 private List<Double> compt = new ArrayList<Double>();
	 public static final int STEP_COURANT = 3;
	 public static final int STEP_PRECEDENT = 2;
	 public static final int STEP_PRECEDENT_MOINS_2 = 1;
	 public static final int STEP_PRECEDENT_MOINS_3 = 0;
		
	 public Historique_Commande_Dist() {
			this.compt = new ArrayList<Double>();
			compt.add(0.);
			compt.add(0.);
			compt.add(0.);
			compt.add(0.);
		}
	 
	 public double valeur(int i) {
		 return compt.get(i);
	 }
	 
	 public void ajouter (double valeur) {
		 compt.add(valeur);
		 Collections.rotate(compt, 4);
		 compt.remove(4);
	 }
}	 
	 
	 
			
		
