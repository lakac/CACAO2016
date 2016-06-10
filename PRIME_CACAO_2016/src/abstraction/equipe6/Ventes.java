package abstraction.equipe6;

import abstraction.commun.ITransformateur;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//Dans cette classe on définit un constructeur (et ses accesseurs) Ventes qui correspond au ventes de Carrefour (à ses clients)
//dans chaque produit et pour chaque transformateur. 

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

	public void setQuantite(Double quantite2, Acteur t) {
		this.quantite.setValeur(t, quantite2);
	}
}
