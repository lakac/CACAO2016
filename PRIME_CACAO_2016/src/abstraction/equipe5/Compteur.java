package abstraction.equipe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Compteur {
	 private List<Double> compt = new ArrayList<Double>();
	 
	 public Compteur() {
			this.compt = new ArrayList<Double>();
		}
	 
	 public void initialisation() {
		for (int i=0; i<4; i++) {
			this.compt.add(0.);
		}
	 }

	 public void rotation_compteur(List<Double> compt) {
		 Collections.rotate(compt, 1);
		 compt.remove(3);
	 }
	 
	 public void ajouter (double valeur) {
		 compt.add(valeur);
	 }
}	 
	 
	 
			
		
