package abstraction.equipe3;

import java.util.ArrayList;


public class Ventes {

	private ArrayList<Double[]> ventes;
	
	public Ventes(){
		this.ventes = new ArrayList<Double[]>();
	}
	public void InitialiseVentes(Double[][] ventes){ //on se donne une base de vente pour faire nos commandes en fonction des historiques de vente
		for (Double[] i : ventes){
			this.ventes.add(i);
		}
	}
	public Double[] getVentes(int step){
		return this.ventes.get(step+25);
	}
	public void addVentes(Double[] vente){
		this.ventes.add(vente);
	}

}
