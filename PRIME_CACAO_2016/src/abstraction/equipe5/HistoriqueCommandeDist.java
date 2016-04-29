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
	 public static void main(String[] args) {
		HistoriqueCommandeDist hist = new HistoriqueCommandeDist();
		System.out.println(hist.valeur(0));
		System.out.println(hist.valeur(1));
		System.out.println(hist.valeur(2));
		System.out.println(hist.valeur(3));
		hist.ajouter(24);
		System.out.println("test 2");
		System.out.println(hist.valeur(0));
		System.out.println(hist.valeur(1));
		System.out.println(hist.valeur(2));
		System.out.println(hist.valeur(3));
		hist.ajouter(48);
		System.out.println("test 3");
		System.out.println(hist.valeur(0));
		System.out.println(hist.valeur(1));
		System.out.println(hist.valeur(2));
		System.out.println(hist.valeur(3));
		
		
	}
}	 
	 
	 
			
		
