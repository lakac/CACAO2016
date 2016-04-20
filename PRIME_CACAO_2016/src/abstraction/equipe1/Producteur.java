package abstraction.equipe1;

import abstraction.fourni.Acteur;

public class Producteur implements Acteur {//, IProducteur {
	private String nom;
	private double stock;
	private double tresorerie;
	
	public Producteur(String nom, double stock_initial, double treso_initiale) {
		this.nom = nom;
		this.stock = stock_initial;
		this.tresorerie = treso_initiale;
	}
	
	private double productionInstantanee() {
		// TODO !
		return 0.0;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public void next() {
		// TODO Auto-generated method stub
		
	}
}
