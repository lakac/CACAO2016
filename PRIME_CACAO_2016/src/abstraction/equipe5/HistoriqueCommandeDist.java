package abstraction.equipe5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HistoriqueCommandeDist {
	 private List<Double> compt = new ArrayList<Double>();
		
	 public HistoriqueCommandeDist() {
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
	 
	 
			
		
