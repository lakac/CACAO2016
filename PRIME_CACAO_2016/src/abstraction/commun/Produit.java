package abstraction.commun;

/**
 * Classe modelisant les produits vendus par les transformateurs aux distributeurs 
 * 
 * @author equipe 5
 */

public class Produit {
	private String produit;
	private double ratioCacao;
	
	public Produit(String produit, double ratio) {
		this.produit = produit;
		this.ratioCacao = ratio;
	}
	
	public String getNomProduit() {
		return this.produit;
	}
	
	public double getRatioCacao() {
		return this.ratioCacao;
	}
	
	public void setRatioCacao(int i) {
		this.ratioCacao = i;
	}
	
	public void setProduit(String produit) {
		this.produit = produit;
	}
	
	/** méthode qui permet de savoir si un produit est égal à un autre */
	public boolean equals(Object o) {
		return ((o instanceof Produit)
				&& (this.getNomProduit() == ((Produit)o).getNomProduit()));
	}
}
