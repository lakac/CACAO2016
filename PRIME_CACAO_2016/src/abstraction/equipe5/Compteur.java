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

	 public void rotation_compteur(List<Double> compt) {
		 compt.remove(4);
		 Collections.rotate(compt, 1);
	 }
	 
	 public void ajouter (double valeur) {
		 compt.add(valeur);
	 }
}	 
	 
	 
			
		
