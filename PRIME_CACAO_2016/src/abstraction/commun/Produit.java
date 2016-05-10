package abstraction.commun;

/**
 * Classe modelisant les produits
 * 
 * @author Joaly Magloire
 */

public class Produit {
	private String produit;
	
	public Produit(String produit) {
		this.produit = produit;
	}
	
	public String getProduit() {
		return this.produit;
	}
	
	public void setProduit(String produit) {
		this.produit = produit;
	}
}
