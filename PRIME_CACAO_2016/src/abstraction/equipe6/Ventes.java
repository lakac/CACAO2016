package abstraction.equipe6;

import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;
import abstraction.fourni.Indicateur;

public class Ventes {
	private ITransformateur marque;
	private Indicateur quantite;
	private Produit produit;
	
	public Ventes (ITransformateur marque, Indicateur quantite, Produit produit) {
		this.marque = marque;
		this.quantite = quantite;
		this.produit = produit;
	}
	
	public ITransformateur getMarque() {
		return marque;
	}
	public void setMarque(ITransformateur marque) {
		this.marque = marque;
	}
	public Indicateur getQuantite() {
		return quantite;
	}
	public void setQuantite(Indicateur quantite) {
		this.quantite = quantite;
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}
