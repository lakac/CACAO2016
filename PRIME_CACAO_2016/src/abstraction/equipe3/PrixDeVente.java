package abstraction.equipe3;

import java.util.ArrayList;

public class PrixDeVente {
	
	private ArrayList<Double> prixDeVente;   // prix différents selon le produit
	private ArrayList<Double> prixDeVenteMinimum;  // nécessaire de fixer une marge minimale

	public PrixDeVente() {
		// TODO Auto-generated constructor stub
		this.prixDeVente = new ArrayList<Double>();
	}
	
	public ArrayList<Double> getPrixDeVente() {
		return this.prixDeVente;
	}
	
	/*public void setPrixDeVente() {
		if (this.getPrixCarrefour > this.prixDeVente) {
			this.prixDeVente = 
		}
		else {
			
		}
	} */
	
}
