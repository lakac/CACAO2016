package abstraction.equipe6;

import abstraction.commun.ITransformateurD;
import abstraction.commun.Produit;
import abstraction.fourni.Acteur;
import abstraction.fourni.Indicateur;

//Dans cette classe on définit un constructeur (et ses accesseurs) Achats qui va servir pour les achats aux transformateurs 
//dans la classe Carrefour
 
public class Achats {
	private ITransformateurD marque;
	private Indicateur quantite;
	private Produit produit;
	
	public Achats (ITransformateurD t, Indicateur quantite, Produit produit) {
		this.marque = t;
		this.quantite = quantite;
		this.produit = produit;
	}
	
	public ITransformateurD getMarque() {
		return marque;
	}
	public void setMarque(ITransformateurD marque) {
		this.marque = marque;
	}
	public Indicateur getQuantite() {
		return quantite;
	}
	public void setQuantite(double quantite, Acteur t) {
		this.quantite.setValeur(t, quantite);
	}
	public Produit getProduit() {
		return produit;
	}
	public void setProduit(Produit produit) {
		this.produit = produit;
	}
}

