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
	
	/** 
	 * méthode qui permet de savoir si un produit est égal à un autre
	 * @param o
	 * @return true si se sont les memes, false sinon
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produit other = (Produit) obj;
		if (produit == null) {
			if (other.produit != null)
				return false;
		} else if (!produit.equals(other.produit))
			return false;
		return true;
	}
	


}
