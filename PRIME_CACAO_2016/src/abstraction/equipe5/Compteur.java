package abstraction.equipe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Compteur {
	public static final int STEP_COURANT = 3;
	public static final int STEP_PRECEDENT = 2;
	 private ArrayList<Double> compt = new ArrayList<Double>();
	 
	 public Compteur() {
			this.compt = new ArrayList<Double>();
			compt.add(100.);
			compt.add(10.);
			compt.add(20.);
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
	 
	 
			
		
