package abstraction.commun;

/**
 * Classe modelisant les produits
**/

public class Produit {
	private String produit;
	private int ratioCacao;
	
	public Produit(String produit, int ratio) {
		this.produit = produit;
		this.ratioCacao = ratio;
	}
	
	public String getNomProduit() {
		return this.produit;
	}
	
	public int getRatioCacao() {
		return this.ratioCacao;
	}
	
	public void setRatioCacao(int i) {
		this.ratioCacao = i;
	}
	
	public void setProduit(String produit) {
		this.produit = produit;
	}
	
	public boolean equals(Object o) {
		return ((o instanceof Produit)
				&& (this.getNomProduit() == ((Produit)o).getNomProduit()));
	}
}
