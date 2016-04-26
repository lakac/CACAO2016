package abstraction.equipe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Compteur {
	 private List<Double> compt = new ArrayList<Double>();
	 
	 public Compteur() {
			this.compt = new ArrayList<Double>();
			compt.add(0.);
			compt.add(0.);
			compt.add(0.);
			compt.add(0.);
		}
	 
	 public void ajouter (double valeur, List<Double> compt) {
		 compt.add(valeur);
		 Collections.rotate(compt, 4);
		 compt.remove(4);
	 }
}	 
	 
	 
			
		
